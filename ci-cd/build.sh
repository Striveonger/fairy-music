#!/bin/bash
set -x

# select arch in "amd64" "arm64"; do
#     break;
# done

# echo $arch

# 获取当前脚本的上一级目录的绝对路径, 并将其赋值给 APP_WORKSHOP 变量
APP_WORKSHOP=$(realpath "$(dirname "$0")/..")

pushd "${APP_WORKSHOP}" || exit

# build service
mvn -f internal/pom.xml clean package -DskipTests

# build ui
rm -rf website/dist website/node_modules
pnpm -C website install
pnpm -C website run build

# --------------------------------------------------------------------------------------------
# uninstall helm
helm uninstall fairy-music -n fm
kubectl delete ns fm
# remove image
docker rmi striveonger/fairy-music-api:$(cat ./ci-cd/VERSION)
docker rmi striveonger/fairy-music-ui:$(cat ./ci-cd/VERSION)

# --------------------------------------------------------------------------------------------
# build image
docker build -f ./ci-cd/docker/api/Dockerfile -t striveonger/fairy-music-api:$(cat ./ci-cd/VERSION) .
docker build -f ./ci-cd/docker/ui/Dockerfile -t striveonger/fairy-music-ui:$(cat ./ci-cd/VERSION) .

# docker push striveonger/fairy-music-api:$(cat ./ci-cd/VERSION)
# docker push striveonger/fairy-music-ui:$(cat ./ci-cd/VERSION)

# package helm
helm package ci-cd/helm
mv fairy-music-$(cat ./ci-cd/VERSION).tgz ci-cd/package
helm show values ci-cd/helm > ci-cd/package/values.yaml

# deploy
# helm uninstall fairy-music -n fm
helm upgrade --install fairy-music ci-cd/package/fairy-music-$(cat ./ci-cd/VERSION).tgz --values ci-cd/package/values.yaml -n fm --create-namespace

popd || exit

