#!/bin/bash
# 源jar路径
SOURCE_PATH=/usr/local/docker
# docker 镜像/容器名字或者jar名字 这里都命名为这个
SERVER_NAME=blog-springboot
TAG=latest
SERVER_PORT=8080
# 容器id
CID=$(docker ps | grep "$SERVER_NAME" | awk '{print $1}')
# 镜像id
IID=$(docker images | grep "$SERVER_NAME:$TAG" | awk '{print $3}')
echo "存在$SERVER_NAME容器, CID-$CID"
docker stop $CID
docker rm $CID
echo "存在$SERVER_NAME:$TAG镜像，IID=$IID"
docker rmi $IID
# 重新打包镜像并运行
docker-compose up -d --build
echo "容器创建完成"
