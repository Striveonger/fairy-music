FROM openjdk:17-slim
LABEL maintainer="striveonger@163.com"
LABEL version="1.0.0"

WORKDIR /opt/app

COPY internal/target/fairy-music.jar /opt/app/app.jar
COPY ci-cd/docker/service/app.sh /opt/app/app.sh

EXPOSE 18081

ENTRYPOINT ["sh", "/opt/app/app.sh"]