#!/bin/bash
# 源jar路径
SOURCE_PATH=/usr/local/docker
# docker 镜像/容器名字或者jar名字 这里都命名为这个
SERVER_NAME=blog-springboot-2.0.jar
TAG=latest
SERVER_PORT=8080
# 容器id
CID=$(docker ps | grep "$SERVER_NAME" | awk '{print $1}')
# 镜像id
IID=$(docker images | grep "$SERVER_NAME:$TAG" | awk '{print $3}')
if [ -n "$CID" ]; then
  echo "存在容器$SERVER_NAME, CID-$CID"
  docker stop $CID
  docker rm $CID
fi
# 构建docker镜像
if [ -n "$IID" ]; then
  echo "存在$SERVER_NAME:$TAG镜像，IID=$IID"
  docker rmi $IID
else
  echo "不存在$SERVER_NAME:$TAG镜像，开始构建镜像"
  cd $SOURCE_PATH
  docker build -t $SERVER_NAME:$TAG .
fi
# 运行docker容器
docker run --name $SERVER_NAME -v /usr/local/upload:/usr/local/upload -d -p $SERVER_PORT:$SERVER_PORT $SERVER_NAME:$TAG
echo "$SERVER_NAME容器创建完成"
