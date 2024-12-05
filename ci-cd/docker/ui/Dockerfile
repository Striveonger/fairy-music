FROM nginx:1.27.2
LABEL maintainer="striveonger@163.com"
LABEL version="1.0.0"

WORKDIR /opt/app

COPY website/dist /opt/app
COPY docker/ui/nginx.conf /etc/nginx/conf.d/app.conf

EXPOSE 80