#!/bin/bash
# 打包之后上传的jar包路径
SOURCE_PATH=/usr/local/docker
# docker的镜像和容器名称都命名为这个
SERVER_NAME=blog-springboot
TAG=latest
# 容器id
CID=$(docker ps | grep "$SERVER_NAME" | awk '{print $1}')
# 镜像id
IID=$(docker images | grep "$SERVER_NAME" | awk '{print $3}')
if [ -n "$CID" ]; then
  echo "存在容器$SERVER_NAME, CID-$CID"
  docker stop $CID
  docker rm $CID
fi
# 构建docker镜像
if [ -n "$IID" ]; then
  echo "存在$SERVER_NAME:$TAG镜像，IID=$IID"
  docker rmi $IID
fi
# 重新构建镜像并运行
docker-compose up -d --build
echo "$SERVER_NAME容器创建完成"