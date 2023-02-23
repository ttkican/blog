## 1. 服务器配置

1. 2 核 4G（使用 ES 搜索策略）
2. 2 核 2G（使用 MySQL 搜素策略，关闭 ES、Canal）

## 2. CentOS 安装 Docker

官方网站上有各种环境下的 [安装指南](https://docs.docker.com/install/)，这里主要介绍 Docker CE 在 CentOS 上的安装。

### 2.1 卸载（可选）

如果之前安装过旧版本的 Docker，可以使用下面命令卸载：

```shell
yum remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-engine
```

### 2.2 安装 Docker

1. 安装 yum 工具

   ```shell
   yum install -y yum-utils
   ```

2. 设置镜像仓库

   ```shell
   yum-config-manager --add-repo https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
   ```

3. 更新 yum 软件包索引

   ```shell
   yum makecache fast
   ```

4. 安装 Docker CE

   ```shell
   yum install docker-ce docker-ce-cli containerd.io
   ```

### 2.3 启动 Docker

1. 启动 Docker

   ```shell
   systemctl start docker
   ```

2. 查看 Docker 版本

   ```shell
   docker version
   ```

## 3. CentOS 安装 DockerCompose

### 3.1 安装 DockerCompose

```shell
curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
```

> 如果下载速度较慢或者下载失败，去[Release 1.29.2 · docker/compose (github.com)](https://github.com/docker/compose/releases/tag/1.29.2)选择相应版本，下载 docker-compose-Linux-x86_64 到本地，更名为`docker-compose`，上传到服务器的`/usr/local/bin`目录下

### 3.2 修改文件权限

```shell
chmod +x /usr/local/bin/docker-compose
```

## 4.数据卷挂载 Canal

1. 拉取 canal 镜像

   ```shell
   docker pull canal/canal-server:latest
   ```

2. 启动 canal 镜像

   ```shell
   docker run --name canal -d canal/canal-server:latest
   ```

3. 创建映射文件

   ```shell
   mkdir /usr/local/canal
   cd /usr/local/canal
   touch canal.properties instance.properties
   ```

4. 将容器内的配置文件复制到刚创建好的文件里

   ```shell
   docker cp canal:/home/admin/canal-server/conf/canal.properties /usr/local/canal/canal.properties

   docker cp canal:/home/admin/canal-server/conf/example/instance.properties /usr/local/canal/instance.properties
   ```

5. 关闭容器并移除容器

   ```shell
   docker stop canal
   docker rm canal
   ```

6. 修改外部配置文件 instance.properties

   ![](https://static.ttkwsd.top/article/c9054133278adf8e3a130158c1088e3f.png) ![](https://static.ttkwsd.top/article/2ce6cb4bfa0f51b5ba5364f9e94a3bb2.png)

7. 修改外部配置文件 canal.properties

   > 找到以下这行将`tcp`改成`rabbitMQ`

   ```properties
   # tcp, kafka, rocketMQ, rabbitMQ, pulsarMQ
   canal.serverMode = rabbitMQ
   ```

   > 接着找到以下这几行

   ```properties
   ##################################################
   ######### 		    RabbitMQ	     #############
   ##################################################
   rabbitmq.host = 你的ip
   rabbitmq.virtual.host = /
   rabbitmq.exchange = article.topic # 交换机名称
   rabbitmq.username = rabbitmq用户名
   rabbitmq.password = rabbitmq密码
   rabbitmq.deliveryMode = topic # exchange的模式
   ```

## 5. 添加 Kibana 配置文件

1. 复制以下配置

   ```shell
   server.host: "0.0.0.0"
   server.shutdownTimeout: "5s"
   elasticsearch.hosts: [ "http://elasticsearch:9200" ]
   elasticsearch.username: "elastic"
   elasticsearch.password: "密码跟elasticsearch一样"
   monitoring.ui.container.elasticsearch.enabled: true
   ```

2. 创建 kibana.yml

   ```shell
   mkdir /usr/local/kibana
   vim kibana.yml
   ```

3. 将刚才复制的配置信息黏贴到 kibana.yml 中，按`Esc`键退出，再输入`:wq`保存退出

## 6. nginx 配置 https

1. 先去腾讯云或者阿里云申请免费 SSL 证书，然后选择 Nginx 服务器类型下载

   ![](https://static.ttkwsd.top/article/e3edda8de1eea93b86556c39d116a256.png)

2. 证书下载之后上传到`/etc/ssl/certs`目录下

3. 在`/usr/local`目录下新建`nginx`文件夹

   ```shell
   cd /usr/local
   mkdir nginx
   ```

4. 创建目录`/usr/local/upload`作为本地文件存储路径

5. 将以下的`nginx.conf`改好后上传到`/usr/local/nginx`下

   ```shell
   events {
       worker_connections  1024;
   }

   http {

       include       mime.types;
       default_type  application/octet-stream;
       sendfile        on;
       keepalive_timeout  65;

       client_max_body_size     50m;
       client_body_buffer_size  10m;
       client_header_timeout    1m;
       client_body_timeout      1m;

       gzip on;
       gzip_min_length  1k;
       gzip_buffers     4 16k;
       gzip_comp_level  4;
       gzip_types text/plain application/javascript application/x-javascript text/css application/xml text/javascript application/x-httpd-php image/jpeg image/gif image/png;
       gzip_vary on;

   server {
           listen  443 ssl;
           server_name  前台域名;

           ssl_certificate    /etc/ssl/certs/xxxxxxxxxxxx.pem;
           ssl_certificate_key  /etc/ssl/certs/xxxxxxxxxxxx.key;
           ssl_session_timeout 5m;
           ssl_ciphers ECDHE-RSA-AES128-GCM-SHA256:ECDHE:ECDH:AES:HIGH:!NULL:!aNULL:!MD5:!ADH:!RC4;
           ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
           ssl_prefer_server_ciphers on;

          location / {
               root   /usr/local/vue/blog;
               index  index.html index.htm;
               try_files $uri $uri/ /index.html;
           }

           location ^~ /api/ {
               proxy_pass http://你的ip:8080/;
               proxy_set_header   Host             $host;
               proxy_set_header   X-Real-IP        $remote_addr;
               proxy_set_header   X-Forwarded-For  $proxy_add_x_forwarded_for;
           }

       }

   server {
           listen  443 ssl;
           server_name  后台域名;

           ssl_certificate    /etc/ssl/certs/xxxxxxxxxxxx.pem;
           ssl_certificate_key  /etc/ssl/certs/xxxxxxxxxxxx.key;
           ssl_session_timeout 5m;
           ssl_ciphers ECDHE-RSA-AES128-GCM-SHA256:ECDHE:ECDH:AES:HIGH:!NULL:!aNULL:!MD5:!ADH:!RC4;
           ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
           ssl_prefer_server_ciphers on;

          location / {
               root   /usr/local/vue/admin;
               index  index.html index.htm;
               try_files $uri $uri/ /index.html;
           }

           location ^~ /api/ {
               proxy_pass http://你的ip:8080/;
               proxy_set_header   Host             $host;
               proxy_set_header   X-Real-IP        $remote_addr;
               proxy_set_header   X-Forwarded-For  $proxy_add_x_forwarded_for;
           }

       }

   server {
           listen  443 ssl;

           add_header 'Access-Control-Allow-Origin' 'https://www.ttkwsd.top';
           add_header 'Access-Control-Allow-Methods' *;
           server_name  文件上传域名;
           ssl_certificate    /etc/ssl/certs/xxxxxxxxxxxx.pem;
           ssl_certificate_key  /etc/ssl/certs/xxxxxxxxxxxx.key;
           ssl_session_timeout 5m;
           ssl_ciphers ECDHE-RSA-AES128-GCM-SHA256:ECDHE:ECDH:AES:HIGH:!NULL:!aNULL:!MD5:!ADH:!RC4;
           ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
           ssl_prefer_server_ciphers on;

           location / {
             root /usr/local/upload/;
           }

       }

   server {
           listen       80;
           server_name  前台域名;

           rewrite ^(.*)$	https://$host$1	permanent;

       }

   server {
           listen       80;
           server_name  后台域名;

           rewrite ^(.*)$	https://$host$1	permanent;

       }

   server {
           listen       80;
           server_name  文件上传域名;

           rewrite ^(.*)$	https://$host$1	permanent;

       }

   }
   ```

## 7. 打包前端

1. `npm install`

2. 修改`shoka-admin`和`shoka-blog`的 utils 文件下的 token.ts，将 domain 改成自己的所在域

3. 接着修改前端的`vite.config.ts`代理地址，改成自己的 ip

4. 申请了 Gitee 和 Github 的第三方登录，记得修改前端的应用 id 和回调地址

![](https://static.ttkwsd.top/article/3564e1ce1a8621bc04b8a38beb6a1278.png)

5. `npm run build`

6. 打包之后将博客前台和后台的`dist`分别重命名为`blog`和`admin`，然后上传到服务器的`/usr/local/vue`目录下（没有就创建）

![](https://static.ttkwsd.top/article/f175e72749ed8b8c22975dee0e432254.png)

## 8. 打包后端

1. 修改`application.yml`信息后，执行 maven 的 package 命令选择 jar 方式打包

![](https://static.ttkwsd.top/article/5f8b43d347425098e8d5a8a55d0f5218.png)

2. 在服务器创建`/usr/local/docker`目录，并将`target`中的 jar 包上传到该目录
3. 修改`deploy`文件夹中的`.env`配置文件中的信息，一定确认`docker-compose.yml`中挂载的目录和文件是否存在
4. 将`deploy`文件夹中的文件上传到`/usr/local/docker`目录中

![](https://static.ttkwsd.top/article/9eb54ea259769f45c374309a27b95cec.png)

## 9. 执行 sh 脚本

**服务器的防火墙端口一定要放开**

```shell
cd /usr/local/docker
sh blog-start.sh
```

项目就部署完成了，用`docker ps`查看正在运行的容器。

![](https://static.ttkwsd.top/article/40728f0289f3618d49b11d29387b4133.png)

## 10. 创建 ES 索引

### 10.1 安装 ik 分词器

```shell
# 进入容器
docker exec -it elasticsearch /bin/bash
# 安装ik分词器
./bin/elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.17.3/elasticsearch-analysis-ik-7.17.3.zip
# 退出容器
exit
# 重启容器
docker restart elasticsearch
```

### 10.2 创建 ES 索引

在浏览器窗口输入`你的ip:5601`，然后用密码登录 kibana（用户名是 elastic），复制`mapping.json`文件中的代码黏贴到`Dev Tools`

![](https://static.ttkwsd.top/article/0c79a7a4a0c2eec461291d5ad3956a91.png)

执行成功状态为 200，可以执行`GET /article`查看是否创建成功

kibana 不用的话，一定要关掉（好像带不动），需要的时候再启动就行了。

## 11. 最后

有什么问题的话，可以在下方评论区评论。不方便评论的话，可以加我联系方式私聊。写的不对的地方欢迎大家在评论区指出