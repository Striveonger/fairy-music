#!/bin/bash
set -x

# select arch in "amd64" "arm64"; do
#     break;
# done

# echo $arch

APP_WORKSHOP=$(realpath "$(dirname "$0")/..")

pushd "${APP_WORKSHOP}" || exit

# build service
mvn -f internal/pom.xml clean package -DskipTests

# build ui
rm -rf website/dist website/node_modules
pnpm -C website install
pnpm -C website run build

# build image
docker rmi fairy-music:1.0.0
# docker rmi fairy-music-ui:1.0.0

docker build -f ./ci-cd/docker/service/Dockerfile -t fairy-music:1.0.0 .

# docker build -f ./ci-cd/docker/ui/Dockerfile -t fairy-music-ui:1.0.0 .

# helm package cicd/helm  -d ./images/${arch}/

popd  || exit

