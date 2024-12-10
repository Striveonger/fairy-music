FROM ubuntu:22.04
LABEL maintainer="striveonger@163.com"
LABEL version="1.0.0"

COPY --from=ubuntu/jre:17_edge /opt/java /opt/java

WORKDIR /opt/app

COPY internal/target/fairy-music.jar /opt/app/app.jar
COPY ci-cd/docker/api/app.sh /opt/app/app.sh

ENV JAVA_HOME=/opt/java

EXPOSE 18081

ENTRYPOINT ["sh", "/opt/app/app.sh"]