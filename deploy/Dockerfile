# 基于openjdk11镜像
FROM openjdk:11
# 在主机 /var/lib/docker 目录下创建了一个临时文件并链接到容器的/tmp
VOLUME /tmp
# 将当前目录下的jar包复制到docker容器的目录下
ADD blog-springboot-2.0.jar blog.jar
# 运行jar并加快随机数产生过程
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/blog.jar"]
# 定义时区参数
ENV TZ=Asia/Shanghai
# 设置时区
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone