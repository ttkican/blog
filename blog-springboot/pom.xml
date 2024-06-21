<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.6.14</version>
        <relativePath/>
    </parent>

    <groupId>com.ican</groupId>
    <artifactId>blog-springboot</artifactId>
    <version>2.0</version>
    <name>blog</name>
    <description>基于springboot + vue3开发的前后端分离博客</description>
    <packaging>jar</packaging>

    <properties>
        <java.version>11</java.version>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
<!--        <elasticsearch.version>7.17.3</elasticsearch.version>-->
        <jakarta-json.version>2.0.1</jakarta-json.version>
        <druid.version>1.2.16</druid.version>
        <mybatis.plus.version>3.5.2</mybatis.plus.version>
        <swagger.version>2.0.9</swagger.version>
        <saToken.version>1.37.0</saToken.version>
        <fastjson2.version>2.0.25</fastjson2.version>
        <yauaa.version>7.19.1</yauaa.version>
        <sensitive.version>0.2.1</sensitive.version>
        <oss.version>3.15.1</oss.version>
        <cos.version>5.6.89</cos.version>
        <qiniu.version>[7.2.0, 7.2.99]</qiniu.version>
        <hutool.version>5.8.18</hutool.version>
        <commons-lang3.version>3.12.0</commons-lang3.version>
        <commons-io.version>2.11.0</commons-io.version>
    </properties>

    <dependencies>
        <!-- spring boot -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- spring websocket -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-websocket</artifactId>
        </dependency>
        <!-- spring aop -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>
        <!-- spring mail -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-mail</artifactId>
        </dependency>
        <!-- thymeleaf -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <!-- configuration-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>
<!--        &lt;!&ndash; elasticsearch &ndash;&gt;-->
<!--        <dependency>-->
<!--            <groupId>co.elastic.clients</groupId>-->
<!--            <artifactId>elasticsearch-java</artifactId>-->
<!--            <version>${elasticsearch.version}</version>-->
<!--        </dependency>-->
        <dependency>
            <groupId>jakarta.json</groupId>
            <artifactId>jakarta.json-api</artifactId>
            <version>${jakarta-json.version}</version>
        </dependency>
        <!-- spring validation -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
<!--        &lt;!&ndash; spring amqp &ndash;&gt;-->
<!--        <dependency>-->
<!--            <groupId>org.springframework.boot</groupId>-->
<!--            <artifactId>spring-boot-starter-amqp</artifactId>-->
<!--        </dependency>-->
        <!-- spring quartz -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-quartz</artifactId>
        </dependency>
        <!-- Sa-Token -->
        <dependency>
            <groupId>cn.dev33</groupId>
            <artifactId>sa-token-spring-boot-starter</artifactId>
            <version>${saToken.version}</version>
        </dependency>
        <!-- Sa-Token 整合 Redis （使用 jackson 序列化方式） -->
        <dependency>
            <groupId>cn.dev33</groupId>
            <artifactId>sa-token-redis-jackson</artifactId>
            <version>${saToken.version}</version>
        </dependency>
        <!-- Redis连接池 -->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-pool2</artifactId>
        </dependency>
        <!-- mysql-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <!-- redis -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
        <!-- lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <!-- druid -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>${druid.version}</version>
        </dependency>
        <!-- mybatis plus -->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>${mybatis.plus.version}</version>
        </dependency>
        <!-- swagger -->
        <dependency>
            <groupId>com.github.xiaoymin</groupId>
            <artifactId>knife4j-spring-boot-starter</artifactId>
            <version>${swagger.version}</version>
        </dependency>
        <!-- fastjson2 -->
        <dependency>
            <groupId>com.alibaba.fastjson2</groupId>
            <artifactId>fastjson2</artifactId>
            <version>${fastjson2.version}</version>
        </dependency>
        <!-- 解析客户端操作系统、浏览器等 -->
        <dependency>
            <groupId>nl.basjes.parse.useragent</groupId>
            <artifactId>yauaa</artifactId>
            <version>${yauaa.version}</version>
        </dependency>
        <!-- 过滤敏感词 -->
        <dependency>
            <groupId>com.github.houbb</groupId>
            <artifactId>sensitive-word</artifactId>
            <version>${sensitive.version}</version>
        </dependency>
        <!-- 阿里云oss -->
        <dependency>
            <groupId>com.aliyun.oss</groupId>
            <artifactId>aliyun-sdk-oss</artifactId>
            <version>${oss.version}</version>
        </dependency>
        <!-- 腾讯云cos -->
        <dependency>
            <groupId>com.qcloud</groupId>
            <artifactId>cos_api</artifactId>
            <version>${cos.version}</version>
            <exclusions>
                <exclusion>
                    <artifactId>okio</artifactId>
                    <groupId>com.squareup.okio</groupId>
                </exclusion>
            </exclusions>
        </dependency>
        <!-- 七牛云qiniu -->
        <dependency>
            <groupId>com.qiniu</groupId>
            <artifactId>qiniu-java-sdk</artifactId>
            <version>${qiniu.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>${commons-lang3.version}</version>
        </dependency>
        <!-- hutool -->
        <dependency>
            <groupId>cn.hutool</groupId>
            <artifactId>hutool-all</artifactId>
            <version>${hutool.version}</version>
        </dependency>
        <!-- commons-io -->
        <dependency>
            <groupId>commons-io</groupId>
            <artifactId>commons-io</artifactId>
            <version>${commons-io.version}</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
