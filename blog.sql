/*
 Navicat Premium Data Transfer

 Source Server         : gulimall
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : blog

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 10/03/2023 23:40:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_album
-- ----------------------------
DROP TABLE IF EXISTS `t_album`;
CREATE TABLE `t_album`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '相册id',
  `album_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '相册名',
  `album_cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '相册封面',
  `album_desc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '相册描述',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态 (1公开 2私密)',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_album
-- ----------------------------
INSERT INTO `t_album` VALUES (1, '精选壁纸', 'https://static.ttkwsd.top/articles/3d566c68efa970bdf71816b607feee4a.jpg', '壁纸', 1, '2023-01-02 16:24:56', '2023-01-06 10:18:56');
INSERT INTO `t_album` VALUES (2, 'dfd', 'https://static.ttkwsd.top/articles/3d566c68efa970bdf71816b607feee4a.jpg', 'df', 1, '2023-01-10 18:41:41', '2023-01-16 09:30:02');

-- ----------------------------
-- Table structure for t_article
-- ----------------------------
DROP TABLE IF EXISTS `t_article`;
CREATE TABLE `t_article`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `user_id` int NOT NULL COMMENT '作者id',
  `category_id` int NOT NULL COMMENT '分类id',
  `article_cover` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '缩略图',
  `article_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章标题',
  `article_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章内容',
  `article_type` tinyint(1) NOT NULL DEFAULT 1 COMMENT '类型 (1原创 2转载 3翻译)',
  `is_top` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否置顶 (0否 1是）',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除 (0否 1是)',
  `is_recommend` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否推荐 (0否 1是)',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态 (1公开 2私密 3评论可见)',
  `create_time` datetime NOT NULL COMMENT '发表时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 71 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_article
-- ----------------------------
INSERT INTO `t_article` VALUES (61, 1, 50, 'https://static.ttkwsd.top/article/d6a00d059a1e9729e763469deb4870df.jpg', '2023-02-22', '# 字符效果\n\n- ~~删除线~~ <s>删除线（开启识别 HTML 标签时）</s>\n\n- _斜体字_ _斜体字_\n- **粗体** **粗体**\n- **_粗斜体_** **_粗斜体_**\n\n- 上标：X<sub>2</sub>，下标：O<sup>2</sup>\n\n- ==高亮==\n\n- `Inline Code`\n\n> 引用：如果想要插入空白换行（即 `<br>` 标签），在插入处先键入两个以上的空格然后回车即可\n\n# 超链接\n\n- [普通链接](https://www.ttkwsd.top)\n- [_斜体链接_](https://www.ttkwsd.top)\n- [**粗体链接**](https://www.ttkwsd.top)\n\n# 脚注\n\n这是一个简单的脚注 [^1] 而这是一个更长的脚注 [^bignote].\n\n[^1]: 这是第一个脚注.\n[^bignote]: 这是一个非常长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长的脚注.\n\n# 图像\n\n下面是一张我家硝子的壁纸?:\n![硝子1](https://i.niupic.com/images/2022/03/11/9Wl7.jpg)\n再来一张好了?：\n![硝子2](https://i.niupic.com/images/2022/03/12/9Wme.jpg)\n\n# 代码\n\n## 行内代码\n\n在 VS Code 中按下 <kbd>Alt</kbd> + <kbd>T</kbd> + <kbd>R</kbd> 执行命令：`npm install marked`\n\n## 代码片\n\n### Python 代码\n\n```python\nclass Animal:\n    \"\"\" 动物类 \"\"\"\n\n    def __init__(self, age: int, name: str):\n        self.age = age\n        self.name = name\n\n    def getInfo(self) -> str:\n        \"\"\" 返回信息 \"\"\"\n        return f\'age: {self.age}; name: {self.name}\'\n\n\nclass Dog(Animal):\n    \"\"\" 狗狗类 \"\"\"\n\n    def __init__(self, age: int, name: str, gender=\'female\', color=\'white\'):\n        super().__init__(age, name)\n        self.gender = gender\n        self.__color = color\n\n    def bark(self):\n        \"\"\" 狗叫 \"\"\"\n        print(\'lololo\')\n\n    @property\n    def color(self):\n        return self.__color\n\n    @color.setter\n    def color(self, color: str):\n        if color not in [\'red\', \'white\', \'black\']:\n            raise ValueError(\'颜色不符合要求\')\n        self.__color = color\n\n\nif __name__ == \'__main__\':\n    dog = Dog(16, \'啸天\', gender=\'male\')\n    # 狗叫\n    dog.bark()\n    # 设置狗狗毛色\n    dog.color = \'blue\'\n```\n\n### HTML 代码\n\n```html\n<!DOCTYPE html>\n<html>\n    <head>\n        <mate charest=\"utf-8\" />\n        <title>Hello world!</title>\n    </head>\n    <body>\n        <h1>Hello world!</h1>\n    </body>\n</html>\n```\n\n# 列表\n\n## 无序列表\n\n- 福建\n  - 厦门\n  - 福州\n- 浙江\n- 江苏\n\n## 有序列表\n\n1. 动物\n   1. 人类\n   2. 犬类\n2. 植物\n3. 微生物\n\n## 任务列表\n\n- [x] 预习计算机网络\n- [ ] 复习现代控制理论\n- [ ] 刷现代控制理论历年卷\n  - [ ] 2019 年期末试卷\n  - [ ] 2020 年期末试卷\n\n# 表格\n\n| 项目   |  价格 | 数量 |\n| ------ | ----: | :--: |\n| 计算机 | $1600 |  5   |\n| 手机   |   $12 |  12  |\n| 管线   |    $1 | 234  |\n\n---\n\n# 特殊符号\n\n&copy; & &uml; &trade; &iexcl; &pound;\n&amp; &lt; &gt; &yen; &euro; &reg; &plusmn; &para; &sect; &brvbar; &macr; &laquo; &middot;\n\nX&sup2; Y&sup3; &frac34; &frac14; &times; &divide; &raquo;\n\n18&ordm;C &quot; &apos;\n\n# Emoji 表情 🎉\n\n- 马：🐎\n- 星星：✨\n- 笑脸：😀\n- 跑步：🏃‍\n\n# 数学公式\n\n行间公式：\n$\\sin(\\alpha)^{\\theta}=\\sum_{i=0}^{n}(x^i + \\cos(f))$\n行内公式 $E=mc^2$\n\n# Tip提示\n\n::: tip\n  在此输入内容\n:::\n::: warning\n  在此输入内容\n:::\n::: danger\n  在此输入内容\n:::\n::: details\n  内容\n:::', 1, 1, 0, 1, 1, '2023-02-22 12:10:21', '2023-03-10 22:36:03');

-- ----------------------------
-- Table structure for t_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_article_tag`;
CREATE TABLE `t_article_tag`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `article_id` int NOT NULL COMMENT '文章id',
  `tag_id` int NOT NULL COMMENT '标签id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 107 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_article_tag
-- ----------------------------
INSERT INTO `t_article_tag` VALUES (106, 61, 15);

-- ----------------------------
-- Table structure for t_blog_file
-- ----------------------------
DROP TABLE IF EXISTS `t_blog_file`;
CREATE TABLE `t_blog_file`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '文件id',
  `file_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件url',
  `file_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件名',
  `file_size` int NOT NULL DEFAULT 0 COMMENT '文件大小',
  `extend_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '文件类型',
  `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件路径',
  `is_dir` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否为目录 (0否 1是)',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 125 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_blog_file
-- ----------------------------
INSERT INTO `t_blog_file` VALUES (2, NULL, 'talk', 0, '', '/', 1, '2023-03-08 12:16:37', NULL);
INSERT INTO `t_blog_file` VALUES (3, NULL, 'config', 0, '', '/', 1, '2023-03-08 12:17:10', NULL);
INSERT INTO `t_blog_file` VALUES (106, NULL, 'photo', 0, '', '/', 1, '2023-03-10 22:33:29', NULL);
INSERT INTO `t_blog_file` VALUES (107, NULL, 'article', 0, '', '/', 1, '2023-03-10 22:33:33', NULL);
INSERT INTO `t_blog_file` VALUES (108, 'https://static.ttkwsd.top/article/d6a00d059a1e9729e763469deb4870df.jpg', 'd6a00d059a1e9729e763469deb4870df', 203270, 'jpg', '/article', 0, '2023-03-10 22:36:01', NULL);
INSERT INTO `t_blog_file` VALUES (109, 'https://static.ttkwsd.top/photo/fcb7cad9dcec4f1ba21781ffd482dd7a.jpg', 'fcb7cad9dcec4f1ba21781ffd482dd7a', 207664, 'jpg', '/photo', 0, '2023-03-10 22:37:38', NULL);
INSERT INTO `t_blog_file` VALUES (110, 'https://static.ttkwsd.top/config/7b6f25adc2b9627b8918176888bee3b5.png', '7b6f25adc2b9627b8918176888bee3b5', 94649, 'png', '/config', 0, '2023-03-10 22:48:26', NULL);
INSERT INTO `t_blog_file` VALUES (111, 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', '0bca52afdb2b9998132355d716390c9f', 1323, 'png', '/config', 0, '2023-03-10 22:53:35', NULL);
INSERT INTO `t_blog_file` VALUES (114, 'https://static.ttkwsd.top/config/994a286571b002e93281899cb402bd15.png', '994a286571b002e93281899cb402bd15', 43466, 'png', '/config', 0, '2023-03-10 22:57:18', NULL);
INSERT INTO `t_blog_file` VALUES (116, 'https://static.ttkwsd.top/config/f0be9dc73e230d8821179b9303a9ff49.jpg', 'f0be9dc73e230d8821179b9303a9ff49', 43981, 'jpg', '/config', 0, '2023-03-10 22:58:21', NULL);
INSERT INTO `t_blog_file` VALUES (117, 'https://static.ttkwsd.top/config/05409c1ed50047535f28a40464f7b1ab.jpg', '05409c1ed50047535f28a40464f7b1ab', 196780, 'jpg', '/config', 0, '2023-03-10 22:58:53', NULL);
INSERT INTO `t_blog_file` VALUES (118, 'https://static.ttkwsd.top/config/d6a00d059a1e9729e763469deb4870df.jpg', 'd6a00d059a1e9729e763469deb4870df', 203270, 'jpg', '/config', 0, '2023-03-10 23:00:06', NULL);

-- ----------------------------
-- Table structure for t_category
-- ----------------------------
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `category_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_category
-- ----------------------------
INSERT INTO `t_category` VALUES (50, '测试分类', '2023-02-22 12:10:21', NULL);

-- ----------------------------
-- Table structure for t_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `comment_type` tinyint(1) NOT NULL COMMENT '类型 (1文章 2友链 3说说)',
  `type_id` int NULL DEFAULT NULL COMMENT '类型id (类型为友链则没有值)',
  `parent_id` int NULL DEFAULT NULL COMMENT '父评论id',
  `reply_id` int NULL DEFAULT NULL COMMENT '回复评论id',
  `comment_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
  `from_uid` int NOT NULL COMMENT '评论用户id',
  `to_uid` int NULL DEFAULT NULL COMMENT '回复用户id',
  `is_check` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否通过 (0否 1是)',
  `create_time` datetime NOT NULL COMMENT '评论时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_comment
-- ----------------------------

-- ----------------------------
-- Table structure for t_exception_log
-- ----------------------------
DROP TABLE IF EXISTS `t_exception_log`;
CREATE TABLE `t_exception_log`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '异常id',
  `module` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '异常模块',
  `uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '异常uri',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '异常名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作描述',
  `error_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '异常方法',
  `message` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '异常信息',
  `params` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '请求参数',
  `request_method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '请求方式',
  `ip_address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作ip',
  `ip_source` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作地址',
  `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作系统',
  `browser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '浏览器',
  `create_time` datetime NOT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 248 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_exception_log
-- ----------------------------

-- ----------------------------
-- Table structure for t_friend
-- ----------------------------
DROP TABLE IF EXISTS `t_friend`;
CREATE TABLE `t_friend`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '友链id',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '友链名称',
  `color` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '友链颜色',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '友链头像',
  `url` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '友链地址',
  `introduction` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '友链介绍',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `friend_user`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_friend
-- ----------------------------

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` int NOT NULL DEFAULT 0 COMMENT '父菜单id (paren_id为0且type为M则是一级菜单)',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限类型 (M目录 C菜单 B按钮)',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由地址',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `component` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单组件',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '权限标识',
  `is_hidden` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否隐藏 (0否 1是)',
  `is_disable` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否禁用 (0否 1是)',
  `order_num` int NOT NULL DEFAULT 1 COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 127 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES (1, 0, 'M', '文章管理', 'article', 'archives', '', '', 0, 0, 1, '2022-12-04 09:13:31', '2023-02-21 15:36:45');
INSERT INTO `t_menu` VALUES (3, 1, 'C', '分类管理', 'category', 'category', '/blog/category/index', 'blog:category:list', 0, 0, 3, '2022-12-04 09:22:20', '2023-02-21 15:21:19');
INSERT INTO `t_menu` VALUES (4, 1, 'C', '标签管理', 'tag', 'tag', '/blog/tag/index', 'blog:tag:list', 0, 0, 4, '2022-12-04 09:23:01', '2023-02-21 15:21:23');
INSERT INTO `t_menu` VALUES (5, 3, 'B', '添加分类', NULL, NULL, NULL, 'blog:category:add', 0, 0, 1, '2022-12-04 09:30:55', NULL);
INSERT INTO `t_menu` VALUES (6, 3, 'B', '删除分类', NULL, NULL, NULL, 'blog:category:delete', 0, 0, 2, '2022-12-04 09:32:15', '2022-12-26 15:39:20');
INSERT INTO `t_menu` VALUES (7, 3, 'B', '修改分类', NULL, NULL, NULL, 'blog:category:update', 0, 0, 3, '2022-12-04 09:33:52', NULL);
INSERT INTO `t_menu` VALUES (8, 4, 'B', '添加标签', NULL, NULL, '', 'blog:tag:add', 0, 0, 1, '2022-12-04 10:19:51', NULL);
INSERT INTO `t_menu` VALUES (9, 4, 'B', '删除标签', NULL, NULL, NULL, 'blog:tag:delete', 0, 0, 2, '2022-12-04 10:20:41', NULL);
INSERT INTO `t_menu` VALUES (10, 4, 'B', '修改标签', NULL, NULL, NULL, 'blog:tag:update', 0, 0, 3, '2022-12-04 10:21:32', NULL);
INSERT INTO `t_menu` VALUES (11, 0, 'M', '系统管理', 'system', 'system', '', '', 0, 0, 3, '2022-12-06 10:58:50', '2023-01-03 18:47:19');
INSERT INTO `t_menu` VALUES (12, 11, 'C', '菜单管理', 'menu', 'tree-table', '/system/menu/index', 'system:menu:list', 0, 0, 1, '2022-12-06 16:33:56', NULL);
INSERT INTO `t_menu` VALUES (13, 11, 'C', '角色管理', 'role', 'peoples', '/system/role/index', 'system:role:list', 0, 0, 2, '2022-12-06 17:09:55', NULL);
INSERT INTO `t_menu` VALUES (14, 11, 'C', '用户管理', 'user', 'user', '/system/user/index', 'system:user:list', 0, 0, 3, '2022-12-06 17:10:28', NULL);
INSERT INTO `t_menu` VALUES (15, 12, 'B', '添加菜单', NULL, '', NULL, 'system:menu:add', 0, 0, 1, '2022-12-07 10:50:22', NULL);
INSERT INTO `t_menu` VALUES (16, 12, 'B', '删除菜单', NULL, '', NULL, 'system:menu:delete', 0, 0, 2, '2022-12-07 10:50:54', NULL);
INSERT INTO `t_menu` VALUES (17, 12, 'B', '修改菜单', NULL, '', NULL, 'system:menu:update', 0, 0, 3, '2022-12-07 10:55:21', NULL);
INSERT INTO `t_menu` VALUES (18, 13, 'B', '添加角色', NULL, NULL, NULL, 'system:role:add', 0, 0, 1, '2022-12-07 10:56:24', NULL);
INSERT INTO `t_menu` VALUES (19, 13, 'B', '删除角色', NULL, NULL, NULL, 'system:role:delete', 0, 0, 2, '2022-12-07 10:56:50', NULL);
INSERT INTO `t_menu` VALUES (20, 13, 'B', '修改角色', NULL, NULL, NULL, 'system:role:update', 0, 0, 3, '2022-12-07 10:57:15', NULL);
INSERT INTO `t_menu` VALUES (21, 0, 'M', '日志管理', 'log', 'log', '', '', 0, 0, 4, '2022-12-21 17:36:39', '2023-02-21 15:20:13');
INSERT INTO `t_menu` VALUES (22, 21, 'C', '操作日志', 'operation', 'form', '/system/log/operation', 'log:operation:list', 0, 0, 1, '2022-12-21 20:14:01', NULL);
INSERT INTO `t_menu` VALUES (23, 21, 'C', '异常日志', 'exception', 'bug', '/system/log/exception', 'log:exception:list', 0, 0, 2, '2022-12-21 20:48:25', NULL);
INSERT INTO `t_menu` VALUES (24, 22, 'B', '删除操作日志', NULL, NULL, NULL, 'log:operation:delete', 0, 0, 1, '2022-12-26 16:43:00', NULL);
INSERT INTO `t_menu` VALUES (25, 23, 'B', '删除异常日志', NULL, NULL, NULL, 'log:exception:delete', 0, 0, 1, '2022-12-27 13:21:50', NULL);
INSERT INTO `t_menu` VALUES (26, 0, 'M', '系统监控', 'monitor', 'monitor', NULL, '', 0, 0, 5, '2022-12-27 13:23:29', '2023-01-03 18:47:27');
INSERT INTO `t_menu` VALUES (27, 26, 'C', '定时任务', 'task', 'job', '/monitor/task/index', 'monitor:task:list', 0, 0, 2, '2022-12-27 13:26:29', '2023-01-01 21:08:35');
INSERT INTO `t_menu` VALUES (28, 27, 'B', '添加任务', NULL, NULL, NULL, 'monitor:task:add', 0, 0, 1, '2022-12-27 13:32:42', NULL);
INSERT INTO `t_menu` VALUES (29, 27, 'B', '修改任务', NULL, NULL, NULL, 'monitor:task:update', 0, 0, 2, '2022-12-27 13:33:45', NULL);
INSERT INTO `t_menu` VALUES (30, 27, 'B', '删除任务', NULL, NULL, NULL, 'monitor:task:delete', 0, 0, 3, '2022-12-27 13:34:29', NULL);
INSERT INTO `t_menu` VALUES (31, 27, 'B', '修改任务状态', NULL, NULL, NULL, 'monitor:task:status', 0, 0, 4, '2022-12-27 13:43:24', NULL);
INSERT INTO `t_menu` VALUES (32, 27, 'B', '运行任务', NULL, NULL, NULL, 'monitor:task:run', 0, 0, 5, '2022-12-27 13:45:34', NULL);
INSERT INTO `t_menu` VALUES (33, 13, 'B', '修改角色状态', NULL, NULL, NULL, 'system:role:status', 0, 0, 4, '2022-12-27 13:46:39', NULL);
INSERT INTO `t_menu` VALUES (34, 0, 'M', '网站管理', 'web', 'international', NULL, '', 0, 0, 6, '2022-12-30 17:22:33', '2023-02-14 09:46:29');
INSERT INTO `t_menu` VALUES (35, 34, 'C', '友链管理', 'friend', 'friend', '/web/friend/index', 'web:friend:list', 0, 0, 1, '2022-12-30 17:33:15', NULL);
INSERT INTO `t_menu` VALUES (36, 0, 'M', '消息管理', 'news', 'email', NULL, '', 0, 0, 2, '2022-12-30 17:50:06', '2022-12-30 18:02:12');
INSERT INTO `t_menu` VALUES (37, 36, 'C', '留言管理', 'message', 'form', '/news/message/index', 'news:message:list', 0, 0, 2, '2022-12-30 17:58:25', '2022-12-30 18:01:47');
INSERT INTO `t_menu` VALUES (38, 36, 'C', '评论管理', 'comment', 'comment', '/news/comment/index', 'news:comment:list', 0, 0, 1, '2022-12-30 17:59:37', '2022-12-30 18:03:35');
INSERT INTO `t_menu` VALUES (39, 35, 'B', '添加友链', NULL, NULL, NULL, 'web:friend:add', 0, 0, 1, '2022-12-30 18:56:22', NULL);
INSERT INTO `t_menu` VALUES (40, 35, 'B', '删除友链', NULL, NULL, NULL, 'web:friend:delete', 0, 0, 2, '2022-12-30 18:56:42', NULL);
INSERT INTO `t_menu` VALUES (41, 35, 'B', '修改友链', NULL, NULL, NULL, 'web:friend:update', 0, 0, 3, '2022-12-30 18:57:08', NULL);
INSERT INTO `t_menu` VALUES (42, 37, 'B', '删除留言', NULL, NULL, NULL, 'news:message:delete', 0, 0, 1, '2022-12-30 22:05:53', NULL);
INSERT INTO `t_menu` VALUES (45, 37, 'B', '审核留言', NULL, NULL, NULL, 'news:message:pass', 0, 0, 2, '2022-12-30 22:29:24', NULL);
INSERT INTO `t_menu` VALUES (46, 34, 'C', '网站配置', 'site', 'example', '/web/site/index', 'web:site:list', 0, 0, 5, '2022-12-31 11:50:45', '2023-01-03 18:49:17');
INSERT INTO `t_menu` VALUES (51, 34, 'C', '相册管理', 'album', 'album', '/web/album/index', 'web:album:list', 0, 0, 3, '2023-01-01 18:16:40', '2023-01-03 18:49:06');
INSERT INTO `t_menu` VALUES (52, 34, 'C', '照片管理', 'photo/:albumId', 'photo', '/web/photo/index', 'web:photo:list', 1, 0, 4, '2023-01-01 18:18:11', '2023-01-01 18:39:22');
INSERT INTO `t_menu` VALUES (53, 26, 'C', '在线用户', 'online', 'online', '/monitor/online/index', 'monitor:online:list', 0, 0, 1, '2023-01-01 21:07:48', '2023-01-01 21:08:29');
INSERT INTO `t_menu` VALUES (54, 51, 'B', '添加相册', NULL, NULL, NULL, 'web:album:add', 0, 0, 1, '2023-01-02 19:01:33', NULL);
INSERT INTO `t_menu` VALUES (55, 51, 'B', '删除相册', NULL, NULL, NULL, 'web:album:delete', 0, 0, 2, '2023-01-02 19:02:03', NULL);
INSERT INTO `t_menu` VALUES (56, 51, 'B', '修改相册', NULL, NULL, NULL, 'web:album:update', 0, 0, 3, '2023-01-02 19:02:50', NULL);
INSERT INTO `t_menu` VALUES (57, 51, 'B', '编辑相册', NULL, NULL, NULL, 'web:album:edit', 0, 0, 4, '2023-01-02 19:03:40', NULL);
INSERT INTO `t_menu` VALUES (58, 51, 'B', '上传相册封面', NULL, NULL, NULL, 'web:album:upload', 0, 0, 5, '2023-01-02 19:04:38', NULL);
INSERT INTO `t_menu` VALUES (60, 12, 'B', '编辑菜单', NULL, NULL, NULL, 'system:menu:edit', 0, 0, 4, '2023-01-03 18:29:57', NULL);
INSERT INTO `t_menu` VALUES (61, 34, 'C', '说说管理', 'talk', 'talk', '/web/talk/index', 'web:talk:list', 0, 0, 2, '2023-01-03 18:48:28', '2023-01-03 18:48:41');
INSERT INTO `t_menu` VALUES (62, 61, 'B', '添加说说', NULL, NULL, NULL, 'web:talk:add', 0, 0, 1, '2023-01-05 19:16:42', NULL);
INSERT INTO `t_menu` VALUES (63, 61, 'B', '删除说说', NULL, NULL, NULL, 'web:talk:delete', 0, 0, 2, '2023-01-05 19:17:07', NULL);
INSERT INTO `t_menu` VALUES (64, 61, 'B', '修改说说', NULL, NULL, NULL, 'web:talk:update', 0, 0, 3, '2023-01-05 19:17:36', NULL);
INSERT INTO `t_menu` VALUES (65, 61, 'B', '编辑说说', NULL, NULL, NULL, 'web:talk:edit', 0, 0, 4, '2023-01-05 19:18:27', NULL);
INSERT INTO `t_menu` VALUES (66, 61, 'B', '上传说说图片', NULL, NULL, NULL, 'web:talk:upload', 0, 0, 5, '2023-01-05 19:18:52', NULL);
INSERT INTO `t_menu` VALUES (67, 46, 'B', '修改网站配置', NULL, NULL, NULL, 'web:site:update', 0, 0, 1, '2023-01-08 09:15:56', NULL);
INSERT INTO `t_menu` VALUES (68, 46, 'B', '上传网站配置图片', NULL, NULL, NULL, 'web:site:upload', 0, 0, 2, '2023-01-08 14:53:16', NULL);
INSERT INTO `t_menu` VALUES (69, 14, 'B', '修改用户', NULL, NULL, NULL, 'system:user:update', 0, 0, 1, '2023-01-09 17:03:18', NULL);
INSERT INTO `t_menu` VALUES (70, 14, 'B', '修改用户状态', NULL, NULL, NULL, 'system:user:status', 0, 0, 2, '2023-01-09 17:03:51', NULL);
INSERT INTO `t_menu` VALUES (71, 53, 'B', '下线用户', NULL, NULL, NULL, 'monitor:online:kick', 0, 0, 1, '2023-01-09 19:18:33', NULL);
INSERT INTO `t_menu` VALUES (73, 1, 'C', '文章列表', 'list', 'chart', '/blog/article/list', 'blog:article:list', 0, 0, 2, '2023-01-10 17:37:29', '2023-02-21 15:36:09');
INSERT INTO `t_menu` VALUES (76, 52, 'B', '添加照片', NULL, NULL, NULL, 'web:photo:add', 0, 0, 1, '2023-01-11 18:45:28', NULL);
INSERT INTO `t_menu` VALUES (77, 52, 'B', '删除照片', NULL, NULL, NULL, 'web:photo:delete', 0, 0, 2, '2023-01-11 18:45:51', NULL);
INSERT INTO `t_menu` VALUES (78, 52, 'B', '修改照片', NULL, NULL, NULL, 'web:photo:update', 0, 0, 3, '2023-01-11 18:46:12', NULL);
INSERT INTO `t_menu` VALUES (79, 52, 'B', '上传照片', NULL, NULL, NULL, 'web:photo:upload', 0, 0, 3, '2023-01-11 18:46:48', NULL);
INSERT INTO `t_menu` VALUES (80, 73, 'B', '添加文章', NULL, NULL, NULL, 'blog:article:add', 0, 0, 1, '2023-01-14 15:25:29', NULL);
INSERT INTO `t_menu` VALUES (81, 73, 'B', '物理删除文章', NULL, NULL, NULL, 'blog:article:delete', 0, 0, 2, '2023-01-14 15:26:44', NULL);
INSERT INTO `t_menu` VALUES (82, 73, 'B', '逻辑删除文章', NULL, NULL, NULL, 'blog:article:recycle', 0, 0, 3, '2023-01-14 15:28:32', NULL);
INSERT INTO `t_menu` VALUES (83, 73, 'B', '更新文章', NULL, NULL, NULL, 'blog:article:update', 0, 0, 4, '2023-01-14 15:30:11', NULL);
INSERT INTO `t_menu` VALUES (84, 73, 'B', '编辑文章', NULL, NULL, NULL, 'blog:article:edit', 0, 0, 5, '2023-01-14 15:32:34', '2023-01-14 15:32:54');
INSERT INTO `t_menu` VALUES (85, 73, 'B', '上传文章图片', NULL, NULL, NULL, 'blog:article:upload', 0, 0, 6, '2023-01-14 15:34:05', '2023-01-15 11:19:13');
INSERT INTO `t_menu` VALUES (86, 73, 'B', '置顶文章', NULL, NULL, NULL, 'blog:article:top', 0, 0, 7, '2023-01-14 15:35:33', NULL);
INSERT INTO `t_menu` VALUES (87, 73, 'B', '推荐文章', NULL, NULL, NULL, 'blog:article:recommend', 0, 0, 8, '2023-01-14 15:36:08', NULL);
INSERT INTO `t_menu` VALUES (88, 73, 'B', '点赞文章', NULL, NULL, NULL, 'blog:article:like', 0, 0, 9, '2023-01-14 15:36:39', NULL);
INSERT INTO `t_menu` VALUES (93, 21, 'C', '访问日志', 'visit', 'logininfor', '/system/log/visit', 'log:visit:list', 0, 0, 3, '2023-01-28 19:04:09', '2023-01-28 19:06:52');
INSERT INTO `t_menu` VALUES (94, 93, 'B', '删除访问日志', NULL, NULL, NULL, 'log:visit:delete', 0, 0, 1, '2023-01-28 19:05:31', NULL);
INSERT INTO `t_menu` VALUES (95, 14, 'B', '修改用户邮箱', NULL, NULL, NULL, 'user:email:update', 0, 0, 3, '2023-01-31 09:26:22', NULL);
INSERT INTO `t_menu` VALUES (96, 14, 'B', '修改用户头像', NULL, NULL, NULL, 'user:avatar:update', 0, 0, 4, '2023-01-31 09:27:03', NULL);
INSERT INTO `t_menu` VALUES (97, 14, 'B', '修改用户信息', NULL, NULL, NULL, 'user:info:update', 0, 0, 5, '2023-01-31 09:27:37', NULL);
INSERT INTO `t_menu` VALUES (98, 14, 'B', '修改用户密码', NULL, NULL, NULL, 'user:password:update', 0, 0, 6, '2023-01-31 09:28:10', NULL);
INSERT INTO `t_menu` VALUES (99, 38, 'B', '添加评论', NULL, NULL, NULL, 'news:comment:add', 0, 0, 1, '2023-02-08 19:09:25', NULL);
INSERT INTO `t_menu` VALUES (100, 38, 'B', '删除评论', NULL, NULL, NULL, 'news:comment:delete', 0, 0, 2, '2023-02-08 19:09:57', NULL);
INSERT INTO `t_menu` VALUES (101, 38, 'B', '审核评论', NULL, NULL, NULL, 'news:comment:pass', 0, 0, 3, '2023-02-08 19:10:26', NULL);
INSERT INTO `t_menu` VALUES (102, 38, 'B', '点赞评论', NULL, NULL, NULL, 'news:comment:like', 0, 0, 4, '2023-02-08 19:10:45', NULL);
INSERT INTO `t_menu` VALUES (103, 61, 'B', '点赞说说', NULL, NULL, NULL, 'web:talk:like', 0, 0, 6, '2023-02-10 11:16:23', NULL);
INSERT INTO `t_menu` VALUES (104, 21, 'C', '任务日志', 'task', 'job', '/system/log/task', 'log:task:list', 0, 0, 3, '2023-02-14 10:28:28', '2023-02-14 10:28:41');
INSERT INTO `t_menu` VALUES (105, 104, 'B', '删除任务日志', NULL, NULL, NULL, 'log:task:delete', 0, 0, 1, '2023-02-14 11:21:06', NULL);
INSERT INTO `t_menu` VALUES (106, 104, 'B', '清空任务日志', NULL, NULL, NULL, 'log:task:clear', 0, 0, 2, '2023-02-14 11:21:28', NULL);
INSERT INTO `t_menu` VALUES (108, 1, 'C', '发布文章', 'write', 'edit', '/blog/article/write', 'blog:article:list', 0, 0, 1, '2023-02-21 13:32:22', '2023-02-21 15:36:04');
INSERT INTO `t_menu` VALUES (111, 1, 'C', '修改文章', 'write/:articleId', 'edit', '/blog/article/write', 'blog:article:list', 1, 0, 5, '2023-02-21 15:40:11', '2023-02-21 15:41:07');
INSERT INTO `t_menu` VALUES (123, 11, 'C', '文件管理', 'file', 'file', '/system/file/index', 'system:file:list', 0, 0, 4, '2023-03-09 10:57:29', NULL);
INSERT INTO `t_menu` VALUES (124, 123, 'B', '上传文件', NULL, NULL, NULL, 'system:file:upload', 0, 0, 1, '2023-03-10 23:11:33', NULL);
INSERT INTO `t_menu` VALUES (125, 123, 'B', '新建文件夹', NULL, NULL, NULL, 'system:file:createFolder', 0, 0, 2, '2023-03-10 23:12:11', NULL);
INSERT INTO `t_menu` VALUES (126, 123, 'B', '删除文件', NULL, NULL, NULL, 'system:file:delete', 0, 0, 3, '2023-03-10 23:12:45', NULL);

-- ----------------------------
-- Table structure for t_message
-- ----------------------------
DROP TABLE IF EXISTS `t_message`;
CREATE TABLE `t_message`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '留言id',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '头像',
  `message_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '留言内容',
  `ip_address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ip',
  `ip_source` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户地址',
  `is_check` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否通过 (0否 1是)',
  `create_time` datetime NOT NULL COMMENT '留言时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 88 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_message
-- ----------------------------

-- ----------------------------
-- Table structure for t_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `t_operation_log`;
CREATE TABLE `t_operation_log`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '操作id',
  `module` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作模块',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作类型',
  `uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作uri',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '方法名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作描述',
  `params` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '请求参数',
  `method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '请求方式',
  `data` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '返回数据',
  `user_id` int NOT NULL COMMENT '用户id',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户昵称',
  `ip_address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作ip',
  `ip_source` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作地址',
  `times` int NOT NULL COMMENT '操作耗时 (毫秒)',
  `create_time` datetime NOT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1152 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_operation_log
-- ----------------------------
INSERT INTO `t_operation_log` VALUES (1151, '文件模块', '上传', '/admin/file/upload', 'com.ican.controller.BlogFileController.uploadFile', '上传文件', 'amili.gif', 'POST', '{\"code\":200,\"flag\":true,\"msg\":\"操作成功\"}', 1, '阿冬', '192.168.23.1', '内网IP|内网IP', 216, '2023-03-10 23:27:15');

-- ----------------------------
-- Table structure for t_photo
-- ----------------------------
DROP TABLE IF EXISTS `t_photo`;
CREATE TABLE `t_photo`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '照片id',
  `album_id` int NOT NULL COMMENT '相册id',
  `photo_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '照片名',
  `photo_desc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '照片描述',
  `photo_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '照片链接',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_photo
-- ----------------------------
INSERT INTO `t_photo` VALUES (1, 1, '测试照片', '我的地', 'https://static.ttkwsd.top/articles/3d566c68efa970bdf71816b607feee4a.jpg', '2023-01-06 17:21:08', '2023-01-16 09:30:17');
INSERT INTO `t_photo` VALUES (3, 1, 'dsf', 'sdf', 'https://static.ttkwsd.top/articles/3d566c68efa970bdf71816b607feee4a.jpg', '2023-01-16 10:47:27', NULL);

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键id',
  `role_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `role_desc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色描述',
  `is_disable` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否禁用 (0否 1是)',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', 'admin', '管理员', 0, '2022-11-03 17:41:57', '2023-03-10 23:12:59');
INSERT INTO `t_role` VALUES ('2', 'user', '普通用户', 0, '2022-11-03 17:42:17', '2023-03-10 23:13:11');
INSERT INTO `t_role` VALUES ('3', 'test', '测试账号', 0, '2022-11-03 17:42:31', '2023-03-10 23:13:17');

-- ----------------------------
-- Table structure for t_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色id',
  `menu_id` int NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5867 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_role_menu
-- ----------------------------
INSERT INTO `t_role_menu` VALUES (5703, '1', 1);
INSERT INTO `t_role_menu` VALUES (5704, '1', 108);
INSERT INTO `t_role_menu` VALUES (5705, '1', 73);
INSERT INTO `t_role_menu` VALUES (5706, '1', 80);
INSERT INTO `t_role_menu` VALUES (5707, '1', 81);
INSERT INTO `t_role_menu` VALUES (5708, '1', 82);
INSERT INTO `t_role_menu` VALUES (5709, '1', 83);
INSERT INTO `t_role_menu` VALUES (5710, '1', 84);
INSERT INTO `t_role_menu` VALUES (5711, '1', 85);
INSERT INTO `t_role_menu` VALUES (5712, '1', 86);
INSERT INTO `t_role_menu` VALUES (5713, '1', 87);
INSERT INTO `t_role_menu` VALUES (5714, '1', 88);
INSERT INTO `t_role_menu` VALUES (5715, '1', 3);
INSERT INTO `t_role_menu` VALUES (5716, '1', 5);
INSERT INTO `t_role_menu` VALUES (5717, '1', 6);
INSERT INTO `t_role_menu` VALUES (5718, '1', 7);
INSERT INTO `t_role_menu` VALUES (5719, '1', 4);
INSERT INTO `t_role_menu` VALUES (5720, '1', 8);
INSERT INTO `t_role_menu` VALUES (5721, '1', 9);
INSERT INTO `t_role_menu` VALUES (5722, '1', 10);
INSERT INTO `t_role_menu` VALUES (5723, '1', 111);
INSERT INTO `t_role_menu` VALUES (5724, '1', 36);
INSERT INTO `t_role_menu` VALUES (5725, '1', 38);
INSERT INTO `t_role_menu` VALUES (5726, '1', 99);
INSERT INTO `t_role_menu` VALUES (5727, '1', 100);
INSERT INTO `t_role_menu` VALUES (5728, '1', 101);
INSERT INTO `t_role_menu` VALUES (5729, '1', 102);
INSERT INTO `t_role_menu` VALUES (5730, '1', 37);
INSERT INTO `t_role_menu` VALUES (5731, '1', 42);
INSERT INTO `t_role_menu` VALUES (5732, '1', 45);
INSERT INTO `t_role_menu` VALUES (5733, '1', 11);
INSERT INTO `t_role_menu` VALUES (5734, '1', 12);
INSERT INTO `t_role_menu` VALUES (5735, '1', 15);
INSERT INTO `t_role_menu` VALUES (5736, '1', 16);
INSERT INTO `t_role_menu` VALUES (5737, '1', 17);
INSERT INTO `t_role_menu` VALUES (5738, '1', 60);
INSERT INTO `t_role_menu` VALUES (5739, '1', 13);
INSERT INTO `t_role_menu` VALUES (5740, '1', 18);
INSERT INTO `t_role_menu` VALUES (5741, '1', 19);
INSERT INTO `t_role_menu` VALUES (5742, '1', 20);
INSERT INTO `t_role_menu` VALUES (5743, '1', 33);
INSERT INTO `t_role_menu` VALUES (5744, '1', 14);
INSERT INTO `t_role_menu` VALUES (5745, '1', 69);
INSERT INTO `t_role_menu` VALUES (5746, '1', 70);
INSERT INTO `t_role_menu` VALUES (5747, '1', 123);
INSERT INTO `t_role_menu` VALUES (5748, '1', 124);
INSERT INTO `t_role_menu` VALUES (5749, '1', 125);
INSERT INTO `t_role_menu` VALUES (5750, '1', 126);
INSERT INTO `t_role_menu` VALUES (5751, '1', 21);
INSERT INTO `t_role_menu` VALUES (5752, '1', 22);
INSERT INTO `t_role_menu` VALUES (5753, '1', 24);
INSERT INTO `t_role_menu` VALUES (5754, '1', 23);
INSERT INTO `t_role_menu` VALUES (5755, '1', 25);
INSERT INTO `t_role_menu` VALUES (5756, '1', 93);
INSERT INTO `t_role_menu` VALUES (5757, '1', 94);
INSERT INTO `t_role_menu` VALUES (5758, '1', 104);
INSERT INTO `t_role_menu` VALUES (5759, '1', 105);
INSERT INTO `t_role_menu` VALUES (5760, '1', 106);
INSERT INTO `t_role_menu` VALUES (5761, '1', 26);
INSERT INTO `t_role_menu` VALUES (5762, '1', 53);
INSERT INTO `t_role_menu` VALUES (5763, '1', 71);
INSERT INTO `t_role_menu` VALUES (5764, '1', 27);
INSERT INTO `t_role_menu` VALUES (5765, '1', 28);
INSERT INTO `t_role_menu` VALUES (5766, '1', 29);
INSERT INTO `t_role_menu` VALUES (5767, '1', 30);
INSERT INTO `t_role_menu` VALUES (5768, '1', 31);
INSERT INTO `t_role_menu` VALUES (5769, '1', 32);
INSERT INTO `t_role_menu` VALUES (5770, '1', 34);
INSERT INTO `t_role_menu` VALUES (5771, '1', 35);
INSERT INTO `t_role_menu` VALUES (5772, '1', 39);
INSERT INTO `t_role_menu` VALUES (5773, '1', 40);
INSERT INTO `t_role_menu` VALUES (5774, '1', 41);
INSERT INTO `t_role_menu` VALUES (5775, '1', 61);
INSERT INTO `t_role_menu` VALUES (5776, '1', 62);
INSERT INTO `t_role_menu` VALUES (5777, '1', 63);
INSERT INTO `t_role_menu` VALUES (5778, '1', 64);
INSERT INTO `t_role_menu` VALUES (5779, '1', 65);
INSERT INTO `t_role_menu` VALUES (5780, '1', 66);
INSERT INTO `t_role_menu` VALUES (5781, '1', 103);
INSERT INTO `t_role_menu` VALUES (5782, '1', 51);
INSERT INTO `t_role_menu` VALUES (5783, '1', 54);
INSERT INTO `t_role_menu` VALUES (5784, '1', 55);
INSERT INTO `t_role_menu` VALUES (5785, '1', 56);
INSERT INTO `t_role_menu` VALUES (5786, '1', 57);
INSERT INTO `t_role_menu` VALUES (5787, '1', 58);
INSERT INTO `t_role_menu` VALUES (5788, '1', 52);
INSERT INTO `t_role_menu` VALUES (5789, '1', 76);
INSERT INTO `t_role_menu` VALUES (5790, '1', 77);
INSERT INTO `t_role_menu` VALUES (5791, '1', 78);
INSERT INTO `t_role_menu` VALUES (5792, '1', 79);
INSERT INTO `t_role_menu` VALUES (5793, '1', 46);
INSERT INTO `t_role_menu` VALUES (5794, '1', 67);
INSERT INTO `t_role_menu` VALUES (5795, '1', 68);
INSERT INTO `t_role_menu` VALUES (5796, '2', 1);
INSERT INTO `t_role_menu` VALUES (5797, '2', 108);
INSERT INTO `t_role_menu` VALUES (5798, '2', 73);
INSERT INTO `t_role_menu` VALUES (5799, '2', 84);
INSERT INTO `t_role_menu` VALUES (5800, '2', 88);
INSERT INTO `t_role_menu` VALUES (5801, '2', 3);
INSERT INTO `t_role_menu` VALUES (5802, '2', 4);
INSERT INTO `t_role_menu` VALUES (5803, '2', 111);
INSERT INTO `t_role_menu` VALUES (5804, '2', 36);
INSERT INTO `t_role_menu` VALUES (5805, '2', 38);
INSERT INTO `t_role_menu` VALUES (5806, '2', 99);
INSERT INTO `t_role_menu` VALUES (5807, '2', 102);
INSERT INTO `t_role_menu` VALUES (5808, '2', 37);
INSERT INTO `t_role_menu` VALUES (5809, '2', 11);
INSERT INTO `t_role_menu` VALUES (5810, '2', 12);
INSERT INTO `t_role_menu` VALUES (5811, '2', 60);
INSERT INTO `t_role_menu` VALUES (5812, '2', 13);
INSERT INTO `t_role_menu` VALUES (5813, '2', 14);
INSERT INTO `t_role_menu` VALUES (5814, '2', 95);
INSERT INTO `t_role_menu` VALUES (5815, '2', 96);
INSERT INTO `t_role_menu` VALUES (5816, '2', 97);
INSERT INTO `t_role_menu` VALUES (5817, '2', 98);
INSERT INTO `t_role_menu` VALUES (5818, '2', 123);
INSERT INTO `t_role_menu` VALUES (5819, '2', 21);
INSERT INTO `t_role_menu` VALUES (5820, '2', 22);
INSERT INTO `t_role_menu` VALUES (5821, '2', 23);
INSERT INTO `t_role_menu` VALUES (5822, '2', 93);
INSERT INTO `t_role_menu` VALUES (5823, '2', 104);
INSERT INTO `t_role_menu` VALUES (5824, '2', 26);
INSERT INTO `t_role_menu` VALUES (5825, '2', 53);
INSERT INTO `t_role_menu` VALUES (5826, '2', 27);
INSERT INTO `t_role_menu` VALUES (5827, '2', 34);
INSERT INTO `t_role_menu` VALUES (5828, '2', 35);
INSERT INTO `t_role_menu` VALUES (5829, '2', 61);
INSERT INTO `t_role_menu` VALUES (5830, '2', 65);
INSERT INTO `t_role_menu` VALUES (5831, '2', 103);
INSERT INTO `t_role_menu` VALUES (5832, '2', 51);
INSERT INTO `t_role_menu` VALUES (5833, '2', 57);
INSERT INTO `t_role_menu` VALUES (5834, '2', 52);
INSERT INTO `t_role_menu` VALUES (5835, '2', 46);
INSERT INTO `t_role_menu` VALUES (5836, '3', 1);
INSERT INTO `t_role_menu` VALUES (5837, '3', 108);
INSERT INTO `t_role_menu` VALUES (5838, '3', 73);
INSERT INTO `t_role_menu` VALUES (5839, '3', 3);
INSERT INTO `t_role_menu` VALUES (5840, '3', 4);
INSERT INTO `t_role_menu` VALUES (5841, '3', 111);
INSERT INTO `t_role_menu` VALUES (5842, '3', 36);
INSERT INTO `t_role_menu` VALUES (5843, '3', 38);
INSERT INTO `t_role_menu` VALUES (5844, '3', 37);
INSERT INTO `t_role_menu` VALUES (5845, '3', 11);
INSERT INTO `t_role_menu` VALUES (5846, '3', 12);
INSERT INTO `t_role_menu` VALUES (5847, '3', 60);
INSERT INTO `t_role_menu` VALUES (5848, '3', 13);
INSERT INTO `t_role_menu` VALUES (5849, '3', 14);
INSERT INTO `t_role_menu` VALUES (5850, '3', 123);
INSERT INTO `t_role_menu` VALUES (5851, '3', 21);
INSERT INTO `t_role_menu` VALUES (5852, '3', 22);
INSERT INTO `t_role_menu` VALUES (5853, '3', 23);
INSERT INTO `t_role_menu` VALUES (5854, '3', 93);
INSERT INTO `t_role_menu` VALUES (5855, '3', 104);
INSERT INTO `t_role_menu` VALUES (5856, '3', 26);
INSERT INTO `t_role_menu` VALUES (5857, '3', 53);
INSERT INTO `t_role_menu` VALUES (5858, '3', 27);
INSERT INTO `t_role_menu` VALUES (5859, '3', 34);
INSERT INTO `t_role_menu` VALUES (5860, '3', 35);
INSERT INTO `t_role_menu` VALUES (5861, '3', 61);
INSERT INTO `t_role_menu` VALUES (5862, '3', 65);
INSERT INTO `t_role_menu` VALUES (5863, '3', 51);
INSERT INTO `t_role_menu` VALUES (5864, '3', 57);
INSERT INTO `t_role_menu` VALUES (5865, '3', 52);
INSERT INTO `t_role_menu` VALUES (5866, '3', 46);

-- ----------------------------
-- Table structure for t_site_config
-- ----------------------------
DROP TABLE IF EXISTS `t_site_config`;
CREATE TABLE `t_site_config`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户头像',
  `tourist_avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '游客头像',
  `site_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '网站名称',
  `site_address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '网站地址',
  `site_intro` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '网站简介',
  `site_notice` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '网站公告',
  `create_site_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '建站日期',
  `record_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '备案号',
  `author_avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '作者头像',
  `site_author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '网站作者',
  `article_cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章默认封面',
  `about_me` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '关于我',
  `github` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT 'Github',
  `gitee` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT 'Gitee',
  `bilibili` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '哔哩哔哩',
  `qq` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'QQ',
  `comment_check` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否评论审核 (0否 1是)',
  `message_check` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否留言审核 (0否 1是)',
  `is_reward` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否开启打赏 (0否 1是)',
  `wei_xin_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '微信二维码',
  `ali_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '支付宝二维码',
  `email_notice` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否邮箱通知 (0否 1是)',
  `social_list` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '社交列表',
  `login_list` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录方式',
  `is_music` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否开启音乐播放器 (0否 1是)',
  `music_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '网易云歌单id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_site_config
-- ----------------------------
INSERT INTO `t_site_config` VALUES (1, 'https://static.ttkwsd.top/config/7b6f25adc2b9627b8918176888bee3b5.png', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', 'Hello World', 'https://www.ttkwsd.top', '网站介绍', '后端基于SpringBoot开发，前端基于Vue3 Ts Navie UI开发，<a href=\"https://gitee.com/wu_shengdong/blog\" style=\"color: #49b1f5;\">网站源码</a>', '2022-08-25', '赣ICP备2022007910号', 'https://static.ttkwsd.top/config/05409c1ed50047535f28a40464f7b1ab.jpg', '阿冬', 'https://static.ttkwsd.top/config/d6a00d059a1e9729e763469deb4870df.jpg', '🍀个人简介\n\n想进大厂的小白\n\n感谢大佬们的开源精神💖', 'https://github.com/ICAN1999', 'https://gitee.com/wu_shengdong', 'https://space.bilibili.com/505715511', '1632167813', 0, 0, 1, 'https://static.ttkwsd.top/config/994a286571b002e93281899cb402bd15.png', 'https://static.ttkwsd.top/config/f0be9dc73e230d8821179b9303a9ff49.jpg', 1, 'gitee,bilibili,github,qq', ',gitee,github', 1, '7611185981', '2023-01-07 19:31:33', '2023-03-10 23:00:39');

-- ----------------------------
-- Table structure for t_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '标签id',
  `tag_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标签名',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_tag
-- ----------------------------
INSERT INTO `t_tag` VALUES (15, '测试标签', '2023-02-22 12:10:21', NULL);

-- ----------------------------
-- Table structure for t_talk
-- ----------------------------
DROP TABLE IF EXISTS `t_talk`;
CREATE TABLE `t_talk`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '说说id',
  `user_id` int NOT NULL COMMENT '用户id',
  `talk_content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '说说内容',
  `images` varchar(2500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '说说图片',
  `is_top` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否置顶 (0否 1是)',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态 (1公开  2私密)',
  `create_time` datetime NOT NULL COMMENT '发表时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_talk
-- ----------------------------
INSERT INTO `t_talk` VALUES (37, 1, '测试说说<img src=\"https://static.ttkwsd.top/emoji/chigua.png\" width=\"24\" height=\"24\" alt=\"[吃瓜]\" style=\"margin: 0 1px;vertical-align: text-bottom\">', '', 0, 1, '2023-02-22 12:04:47', NULL);

-- ----------------------------
-- Table structure for t_task
-- ----------------------------
DROP TABLE IF EXISTS `t_task`;
CREATE TABLE `t_task`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `task_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务名称',
  `task_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调用目标',
  `cron_expression` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'cron执行表达式',
  `misfire_policy` tinyint(1) NOT NULL DEFAULT 3 COMMENT '计划执行错误策略 (1立即执行 2执行一次 3放弃执行)',
  `concurrent` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否并发执行 (0否 1是)',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '任务状态 (0运行 1暂停)',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '任务备注信息',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_task
-- ----------------------------
INSERT INTO `t_task` VALUES (4, '清除每日游客访问记录', 'SYSTEM', 'timedTask.clear', '0 0 0 * * ?', 3, 0, 0, '清除redis中的游客记录', '2023-02-14 09:49:39', '2023-02-18 15:48:41');
INSERT INTO `t_task` VALUES (5, '测试任务', 'SYSTEM', 'timedTask.test', '0/10 * * * * ? ', 3, 0, 1, '测试任务', '2023-02-17 21:54:47', NULL);
INSERT INTO `t_task` VALUES (6, '清除访问日志', 'SYSTEM', 'timedTask.clearVistiLog', '0 0 2 * * ?', 3, 0, 0, '清除一周前的访问日志', '2023-02-22 15:56:32', NULL);

-- ----------------------------
-- Table structure for t_task_log
-- ----------------------------
DROP TABLE IF EXISTS `t_task_log`;
CREATE TABLE `t_task_log`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
  `task_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务名称',
  `task_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调用目标字符串',
  `task_message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '日志信息',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '执行状态 (0失败 1正常)',
  `error_info` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '错误信息',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_task_log
-- ----------------------------

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户昵称',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户密码',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '头像',
  `web_site` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '个人网站',
  `intro` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '个人简介',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '邮箱',
  `ip_address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '登录ip',
  `ip_source` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '登录地址',
  `login_type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '登录方式 (1邮箱 2QQ 3Gitee 4Github)',
  `is_disable` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否禁用 (0否 1是)',
  `login_time` datetime NULL DEFAULT NULL COMMENT '登录时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, '阿冬', 'admin@qq.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', NULL, NULL, '1632167813@qq.com', '192.168.23.1', '内网IP|内网IP', 1, 0, '2023-03-10 22:26:23', '2022-11-29 21:45:48', '2023-03-10 22:26:23');
INSERT INTO `t_user` VALUES (3, '测试账号', 'test@qq.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', 'https://www.ttkwsd.top', '个人简介', 'test@qq.com', '192.168.23.1', '内网IP|内网IP', 1, 0, '2023-02-24 10:45:59', '2022-11-30 21:34:26', '2023-02-24 10:45:59');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int NOT NULL COMMENT '用户id',
  `role_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES (50, 3, '3');
INSERT INTO `t_user_role` VALUES (54, 1, '1');

-- ----------------------------
-- Table structure for t_visit_log
-- ----------------------------
DROP TABLE IF EXISTS `t_visit_log`;
CREATE TABLE `t_visit_log`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `page` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '访问页面',
  `ip_address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '访问ip',
  `ip_source` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '访问地址',
  `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作系统',
  `browser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '浏览器',
  `create_time` datetime NOT NULL COMMENT '访问时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1671 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_visit_log
-- ----------------------------
INSERT INTO `t_visit_log` VALUES (1635, '首页', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-02-22 19:01:49');
INSERT INTO `t_visit_log` VALUES (1636, '首页', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-02-22 19:02:22');
INSERT INTO `t_visit_log` VALUES (1637, '文章', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-02-22 19:02:28');
INSERT INTO `t_visit_log` VALUES (1638, '首页', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-02-23 21:39:07');
INSERT INTO `t_visit_log` VALUES (1639, '首页', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-02-23 21:39:07');
INSERT INTO `t_visit_log` VALUES (1640, '首页', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-02-23 21:44:39');
INSERT INTO `t_visit_log` VALUES (1641, '首页', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-02-23 21:45:21');
INSERT INTO `t_visit_log` VALUES (1642, '首页', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-02-23 21:51:26');
INSERT INTO `t_visit_log` VALUES (1643, '首页', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-02-23 21:55:00');
INSERT INTO `t_visit_log` VALUES (1644, '首页', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-02-24 21:58:06');
INSERT INTO `t_visit_log` VALUES (1645, '首页', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-02-24 21:58:07');
INSERT INTO `t_visit_log` VALUES (1646, '文章', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-02-24 21:58:13');
INSERT INTO `t_visit_log` VALUES (1647, '首页', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-03-06 09:43:54');
INSERT INTO `t_visit_log` VALUES (1648, '首页', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-03-06 09:45:44');
INSERT INTO `t_visit_log` VALUES (1649, '首页', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-03-06 09:45:44');
INSERT INTO `t_visit_log` VALUES (1650, '首页', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-03-06 09:47:01');
INSERT INTO `t_visit_log` VALUES (1651, '文章', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-03-06 09:47:14');
INSERT INTO `t_visit_log` VALUES (1652, '首页', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-03-06 09:49:45');
INSERT INTO `t_visit_log` VALUES (1653, '文章', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-03-06 09:50:04');
INSERT INTO `t_visit_log` VALUES (1654, '首页', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-03-06 09:50:19');
INSERT INTO `t_visit_log` VALUES (1655, '首页', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-03-06 09:53:32');
INSERT INTO `t_visit_log` VALUES (1656, '文章', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-03-06 09:53:36');
INSERT INTO `t_visit_log` VALUES (1657, '首页', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-03-06 09:53:46');
INSERT INTO `t_visit_log` VALUES (1658, '首页', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-03-06 09:58:04');
INSERT INTO `t_visit_log` VALUES (1659, '首页', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-03-06 10:01:58');
INSERT INTO `t_visit_log` VALUES (1660, '首页', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-03-06 10:02:04');
INSERT INTO `t_visit_log` VALUES (1661, '首页', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-03-06 10:02:19');
INSERT INTO `t_visit_log` VALUES (1662, '首页', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-03-06 10:02:25');
INSERT INTO `t_visit_log` VALUES (1663, '首页', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-03-06 10:03:10');
INSERT INTO `t_visit_log` VALUES (1664, '首页', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-03-06 10:03:16');
INSERT INTO `t_visit_log` VALUES (1665, '首页', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-03-06 10:03:27');
INSERT INTO `t_visit_log` VALUES (1666, '首页', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-03-06 10:04:48');
INSERT INTO `t_visit_log` VALUES (1667, '首页', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-03-06 10:05:02');
INSERT INTO `t_visit_log` VALUES (1668, '首页', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-03-06 10:05:09');
INSERT INTO `t_visit_log` VALUES (1669, '文章', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-03-06 10:05:27');
INSERT INTO `t_visit_log` VALUES (1670, '文章', '192.168.23.1', '内网IP|内网IP', 'Windows NT ??', 'Edge 110', '2023-03-06 10:06:04');

SET FOREIGN_KEY_CHECKS = 1;
