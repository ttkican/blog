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

 Date: 21/02/2023 18:18:57
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
) ENGINE = InnoDB AUTO_INCREMENT = 58 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_article
-- ----------------------------

-- ----------------------------
-- Table structure for t_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_article_tag`;
CREATE TABLE `t_article_tag`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `article_id` int NOT NULL COMMENT '文章id',
  `tag_id` int NOT NULL COMMENT '标签id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 91 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_article_tag
-- ----------------------------

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
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_category
-- ----------------------------

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
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

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
) ENGINE = InnoDB AUTO_INCREMENT = 180 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

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
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

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
) ENGINE = InnoDB AUTO_INCREMENT = 107 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

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
INSERT INTO `t_message` VALUES (4, 'Gnaf', 'https://q1.qlogo.cn/g?b=qq&nk=1519467168&s=40', '测试', '223.146.142.215', '中国|湖南省|衡阳市|电信', 1, '2022-08-25 20:32:13', NULL);
INSERT INTO `t_message` VALUES (6, '彰', 'https://q1.qlogo.cn/g?b=qq&nk=981038470&s=40', '太强了', '39.144.168.207', '中国|移动', 1, '2022-08-25 21:21:51', NULL);
INSERT INTO `t_message` VALUES (7, '存于心', 'https://q1.qlogo.cn/g?b=qq&nk=2390885491&s=40', '牛蛙牛蛙', '171.34.214.142', '中国|江西省|南昌市|联通', 1, '2022-08-26 14:48:08', NULL);
INSERT INTO `t_message` VALUES (8, '游客', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', '111', '27.38.212.223', '中国|广东省|深圳市|联通', 1, '2022-09-08 19:50:35', NULL);
INSERT INTO `t_message` VALUES (9, '游客', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', 'nice', '171.34.215.57', '中国|江西省|南昌市|联通', 1, '2022-09-12 09:52:40', NULL);
INSERT INTO `t_message` VALUES (10, '游客', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', '牛蛙', '58.37.10.147', '中国|上海|上海市|电信', 1, '2022-09-13 10:37:56', NULL);
INSERT INTO `t_message` VALUES (11, '游客', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', '111', '114.93.24.95', '中国|上海|上海市|电信', 1, '2022-09-13 12:04:52', NULL);
INSERT INTO `t_message` VALUES (12, '游客', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', '测试', '117.162.73.5', '中国|江西省|吉安市|移动', 1, '2022-09-13 23:24:37', NULL);
INSERT INTO `t_message` VALUES (13, '游客', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', 'dog', '111.22.252.94', '中国|湖南省|长沙市|移动', 1, '2022-09-14 14:36:40', NULL);
INSERT INTO `t_message` VALUES (14, '游客', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', 'hello', '223.104.22.206', '中国|广西|南宁市|移动', 1, '2022-09-15 19:04:03', NULL);
INSERT INTO `t_message` VALUES (15, '故人已往', 'http://thirdqq.qlogo.cn/g?b=oidb&k=pbicG5Ircgp6AfMhdYR3usA&s=40&t=1660705139', '加油', '117.162.73.5', '中国|江西省|吉安市|移动', 1, '2022-09-16 14:49:53', NULL);
INSERT INTO `t_message` VALUES (16, '故人已往', 'http://thirdqq.qlogo.cn/g?b=oidb&k=pbicG5Ircgp6AfMhdYR3usA&s=40&t=1660705139', '666', '117.162.73.5', '中国|江西省|吉安市|移动', 1, '2022-09-19 11:42:35', NULL);
INSERT INTO `t_message` VALUES (17, '游客', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', '牛啊牛啊', '111.222.51.142', '中国|广东省|深圳市|天威', 1, '2022-09-20 21:20:59', NULL);
INSERT INTO `t_message` VALUES (18, '游客', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', '欢迎回访~ https://www.hqxiaozou.top/category/java', '116.22.180.131', '中国|广东省|广州市|电信', 1, '2022-09-22 14:21:57', NULL);
INSERT INTO `t_message` VALUES (19, '游客', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', '厉害厉害', '61.242.132.29', '中国|江西省|联通', 1, '2022-09-23 16:26:35', NULL);
INSERT INTO `t_message` VALUES (20, '游客', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', 'ceshi', '1.202.254.178', '中国|北京|北京市|电信', 1, '2022-09-27 15:28:12', NULL);
INSERT INTO `t_message` VALUES (21, '游客', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', '11', '113.247.68.248', '中国|湖南省|长沙市|电信', 1, '2022-09-30 12:21:49', NULL);
INSERT INTO `t_message` VALUES (22, '游客', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', '234', '123.180.194.48', '中国|河北省|衡水市|电信', 1, '2022-10-07 09:11:15', NULL);
INSERT INTO `t_message` VALUES (23, '游客', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', '测试', '171.34.214.123', '中国|江西省|南昌市|联通', 1, '2022-10-14 22:49:34', NULL);
INSERT INTO `t_message` VALUES (24, '游客', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', '非非', '220.200.104.47', '中国|海南省|海口市|联通', 1, '2022-10-16 11:25:36', NULL);
INSERT INTO `t_message` VALUES (25, '游客', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', '厉害', '183.157.14.251', '中国|浙江省|杭州市|电信', 1, '2022-11-06 09:23:04', NULL);
INSERT INTO `t_message` VALUES (26, '游客', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', '厉害', '117.136.111.102', '中国|浙江省|杭州市|移动', 1, '2022-11-07 09:40:09', NULL);
INSERT INTO `t_message` VALUES (27, '游客', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', '321313', '60.4.125.156', '中国|河北省|保定市|联通', 1, '2022-11-08 11:07:03', NULL);
INSERT INTO `t_message` VALUES (28, '清晨、初阳', 'http://thirdqq.qlogo.cn/g?b=oidb&k=iaY7ulx7PZp2886QNMCZSOA&s=40&t=1551769321', '**', '171.113.123.31', '中国|湖北省|武汉市|电信', 1, '2022-11-08 23:36:17', NULL);
INSERT INTO `t_message` VALUES (29, '游客', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', '厉害', '183.157.81.152', '中国|浙江省|杭州市|电信', 1, '2022-11-09 07:22:40', NULL);
INSERT INTO `t_message` VALUES (30, '游客', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', '顶顶顶顶顶', '124.119.133.56', '中国|新疆|伊犁|电信', 1, '2022-11-09 12:03:23', NULL);
INSERT INTO `t_message` VALUES (31, '王小王', 'https://gitee.com/assets/no_portrait.png', '参考一下', '120.245.112.138', '中国|北京|北京市|移动', 1, '2022-11-09 16:06:47', NULL);
INSERT INTO `t_message` VALUES (32, '游客', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', '牛牛', '110.86.200.86', '中国|福建省|厦门市|电信', 1, '2022-11-14 00:09:34', NULL);
INSERT INTO `t_message` VALUES (33, '游客', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', '这是真的？', '60.166.99.0', '中国|安徽省|合肥市|电信', 1, '2022-11-14 21:39:39', NULL);
INSERT INTO `t_message` VALUES (34, '游客', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', '马走*', '36.143.145.3', '中国|移动', 1, '2022-11-15 15:35:48', NULL);
INSERT INTO `t_message` VALUES (35, '游客', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', '666', '211.82.216.136', '中国|河北省|衡水市|教育网', 1, '2022-11-15 20:32:03', NULL);
INSERT INTO `t_message` VALUES (36, '游客', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', '这个功能秀啊', '106.224.33.58', '中国|江西省|南昌市|电信', 1, '2022-11-16 10:31:53', NULL);
INSERT INTO `t_message` VALUES (37, '游客', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', '牛啊', '119.36.87.228', '中国|湖北省|武汉市|联通', 1, '2022-11-20 21:55:31', NULL);
INSERT INTO `t_message` VALUES (38, '游客', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', '牛', '113.88.84.236', '中国|广东省|深圳市|电信', 1, '2022-11-22 16:33:03', NULL);
INSERT INTO `t_message` VALUES (39, '游客', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', '666666', '117.181.105.64', '中国|广西|南宁市|移动', 1, '2022-11-25 12:26:05', NULL);
INSERT INTO `t_message` VALUES (40, '小宇宙', 'http://thirdqq.qlogo.cn/g?b=oidb&k=L1m1ugGEjUvppP71vvicgCQ&kti=Y4BEGgAAAAE&s=40&t=1632151073', '请问而放弃阿**的', '117.181.105.64', '中国|广西|南宁市|移动', 1, '2022-11-25 12:34:15', NULL);
INSERT INTO `t_message` VALUES (41, '游客', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', '123', '183.197.90.114', '中国|河北省|保定市|移动', 1, '2022-11-28 13:35:33', NULL);
INSERT INTO `t_message` VALUES (42, '游客', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', '6', '111.16.58.249', '中国|山东省|临沂市|移动', 1, '2022-12-01 18:43:25', NULL);
INSERT INTO `t_message` VALUES (43, '游客', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', '牛哇', '14.150.30.72', '中国|广东省|广州市|电信', 1, '2022-12-02 18:01:56', NULL);
INSERT INTO `t_message` VALUES (45, '游客', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', '好漂亮啊', '221.2.69.42', '中国|山东省|临沂市|联通', 1, '2022-12-04 21:22:59', NULL);
INSERT INTO `t_message` VALUES (47, '游客', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', '请问下音乐播放器怎么让他连图片页也藏起来呀', '115.201.100.204', '中国|浙江省|台州市|电信', 1, '2022-12-24 14:27:54', '2022-12-30 22:30:10');
INSERT INTO `t_message` VALUES (48, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', '哈哈', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 11:52:05', NULL);
INSERT INTO `t_message` VALUES (49, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', '哈哈', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 11:52:05', NULL);
INSERT INTO `t_message` VALUES (50, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', '哈哈', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 11:52:10', NULL);
INSERT INTO `t_message` VALUES (51, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', '哈哈', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 11:52:11', NULL);
INSERT INTO `t_message` VALUES (52, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', '哈哈', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 11:52:12', NULL);
INSERT INTO `t_message` VALUES (53, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', '哈哈', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 11:52:12', NULL);
INSERT INTO `t_message` VALUES (54, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', '哈哈', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 11:52:13', NULL);
INSERT INTO `t_message` VALUES (55, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', '哈哈', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 11:52:14', NULL);
INSERT INTO `t_message` VALUES (56, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', '哈哈', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 11:52:15', NULL);
INSERT INTO `t_message` VALUES (57, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', '哈哈', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 11:52:15', NULL);
INSERT INTO `t_message` VALUES (58, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', '哈哈', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 11:52:15', NULL);
INSERT INTO `t_message` VALUES (59, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', '哈哈', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 11:52:15', NULL);
INSERT INTO `t_message` VALUES (60, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', '哈哈', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 11:52:15', NULL);
INSERT INTO `t_message` VALUES (61, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', '哈哈', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 11:52:16', NULL);
INSERT INTO `t_message` VALUES (62, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', '哈哈', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 11:53:02', NULL);
INSERT INTO `t_message` VALUES (63, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', 'test', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 11:58:11', NULL);
INSERT INTO `t_message` VALUES (64, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', 'test', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 11:58:11', NULL);
INSERT INTO `t_message` VALUES (65, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', 'test', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 11:58:13', NULL);
INSERT INTO `t_message` VALUES (66, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', 'test', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 11:58:14', NULL);
INSERT INTO `t_message` VALUES (67, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', 'fff', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 12:04:55', NULL);
INSERT INTO `t_message` VALUES (68, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', 'fff', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 12:05:01', NULL);
INSERT INTO `t_message` VALUES (69, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', 'fff', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 12:05:03', NULL);
INSERT INTO `t_message` VALUES (70, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', 'fff', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 12:05:04', NULL);
INSERT INTO `t_message` VALUES (71, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', 'fff', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 12:05:11', NULL);
INSERT INTO `t_message` VALUES (72, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', 'fff', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 12:06:08', NULL);
INSERT INTO `t_message` VALUES (73, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', 'fff', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 12:06:09', NULL);
INSERT INTO `t_message` VALUES (74, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', 'fff', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 12:06:10', NULL);
INSERT INTO `t_message` VALUES (75, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', 'fff', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 12:06:11', NULL);
INSERT INTO `t_message` VALUES (76, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', 'fff', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 12:07:23', NULL);
INSERT INTO `t_message` VALUES (77, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', 'fff', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 12:07:29', NULL);
INSERT INTO `t_message` VALUES (78, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', 'ee', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 12:07:43', NULL);
INSERT INTO `t_message` VALUES (79, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', 'ddd', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 12:12:34', NULL);
INSERT INTO `t_message` VALUES (80, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', 'dd', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 12:12:36', NULL);
INSERT INTO `t_message` VALUES (81, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', 'ddd', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-01 12:12:38', NULL);
INSERT INTO `t_message` VALUES (82, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', 'dd', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-08 19:17:55', NULL);
INSERT INTO `t_message` VALUES (83, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', '111', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-10 11:21:55', NULL);
INSERT INTO `t_message` VALUES (84, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', '111', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-10 11:35:07', NULL);
INSERT INTO `t_message` VALUES (85, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', '111', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-10 11:35:29', NULL);
INSERT INTO `t_message` VALUES (86, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', '111', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-10 11:40:29', NULL);
INSERT INTO `t_message` VALUES (87, '阿冬', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', '111', '192.168.23.1', '内网IP|内网IP', 1, '2023-02-10 11:40:57', '2023-02-10 11:43:21');

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
) ENGINE = InnoDB AUTO_INCREMENT = 833 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_operation_log
-- ----------------------------

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
INSERT INTO `t_role` VALUES ('1', 'admin', '管理员', 0, '2022-11-03 17:41:57', '2023-02-21 15:40:28');
INSERT INTO `t_role` VALUES ('2', 'user', '普通用户', 1, '2022-11-03 17:42:17', '2023-02-21 13:31:28');
INSERT INTO `t_role` VALUES ('3', 'test', '测试账号', 0, '2022-11-03 17:42:31', '2023-02-21 13:31:31');

-- ----------------------------
-- Table structure for t_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色id',
  `menu_id` int NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3551 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_role_menu
-- ----------------------------
INSERT INTO `t_role_menu` VALUES (4041, '2', 1);
INSERT INTO `t_role_menu` VALUES (4042, '2', 3);
INSERT INTO `t_role_menu` VALUES (4043, '2', 4);
INSERT INTO `t_role_menu` VALUES (4044, '2', 36);
INSERT INTO `t_role_menu` VALUES (4045, '2', 38);
INSERT INTO `t_role_menu` VALUES (4046, '2', 99);
INSERT INTO `t_role_menu` VALUES (4047, '2', 102);
INSERT INTO `t_role_menu` VALUES (4048, '2', 37);
INSERT INTO `t_role_menu` VALUES (4049, '2', 11);
INSERT INTO `t_role_menu` VALUES (4050, '2', 12);
INSERT INTO `t_role_menu` VALUES (4051, '2', 60);
INSERT INTO `t_role_menu` VALUES (4052, '2', 13);
INSERT INTO `t_role_menu` VALUES (4053, '2', 14);
INSERT INTO `t_role_menu` VALUES (4054, '2', 95);
INSERT INTO `t_role_menu` VALUES (4055, '2', 96);
INSERT INTO `t_role_menu` VALUES (4056, '2', 97);
INSERT INTO `t_role_menu` VALUES (4057, '2', 98);
INSERT INTO `t_role_menu` VALUES (4058, '2', 21);
INSERT INTO `t_role_menu` VALUES (4059, '2', 22);
INSERT INTO `t_role_menu` VALUES (4060, '2', 23);
INSERT INTO `t_role_menu` VALUES (4061, '2', 93);
INSERT INTO `t_role_menu` VALUES (4062, '2', 26);
INSERT INTO `t_role_menu` VALUES (4063, '2', 53);
INSERT INTO `t_role_menu` VALUES (4064, '2', 27);
INSERT INTO `t_role_menu` VALUES (4065, '2', 34);
INSERT INTO `t_role_menu` VALUES (4066, '2', 35);
INSERT INTO `t_role_menu` VALUES (4067, '2', 61);
INSERT INTO `t_role_menu` VALUES (4068, '2', 65);
INSERT INTO `t_role_menu` VALUES (4069, '2', 51);
INSERT INTO `t_role_menu` VALUES (4070, '2', 57);
INSERT INTO `t_role_menu` VALUES (4071, '2', 52);
INSERT INTO `t_role_menu` VALUES (4072, '2', 46);
INSERT INTO `t_role_menu` VALUES (4073, '3', 1);
INSERT INTO `t_role_menu` VALUES (4074, '3', 3);
INSERT INTO `t_role_menu` VALUES (4075, '3', 4);
INSERT INTO `t_role_menu` VALUES (4076, '3', 36);
INSERT INTO `t_role_menu` VALUES (4077, '3', 38);
INSERT INTO `t_role_menu` VALUES (4078, '3', 37);
INSERT INTO `t_role_menu` VALUES (4079, '3', 11);
INSERT INTO `t_role_menu` VALUES (4080, '3', 12);
INSERT INTO `t_role_menu` VALUES (4081, '3', 60);
INSERT INTO `t_role_menu` VALUES (4082, '3', 13);
INSERT INTO `t_role_menu` VALUES (4083, '3', 14);
INSERT INTO `t_role_menu` VALUES (4084, '3', 21);
INSERT INTO `t_role_menu` VALUES (4085, '3', 22);
INSERT INTO `t_role_menu` VALUES (4086, '3', 23);
INSERT INTO `t_role_menu` VALUES (4087, '3', 104);
INSERT INTO `t_role_menu` VALUES (4088, '3', 93);
INSERT INTO `t_role_menu` VALUES (4089, '3', 26);
INSERT INTO `t_role_menu` VALUES (4090, '3', 53);
INSERT INTO `t_role_menu` VALUES (4091, '3', 27);
INSERT INTO `t_role_menu` VALUES (4092, '3', 34);
INSERT INTO `t_role_menu` VALUES (4093, '3', 35);
INSERT INTO `t_role_menu` VALUES (4094, '3', 61);
INSERT INTO `t_role_menu` VALUES (4095, '3', 65);
INSERT INTO `t_role_menu` VALUES (4096, '3', 51);
INSERT INTO `t_role_menu` VALUES (4097, '3', 57);
INSERT INTO `t_role_menu` VALUES (4098, '3', 52);
INSERT INTO `t_role_menu` VALUES (4099, '3', 46);
INSERT INTO `t_role_menu` VALUES (4542, '1', 1);
INSERT INTO `t_role_menu` VALUES (4543, '1', 108);
INSERT INTO `t_role_menu` VALUES (4544, '1', 73);
INSERT INTO `t_role_menu` VALUES (4545, '1', 80);
INSERT INTO `t_role_menu` VALUES (4546, '1', 81);
INSERT INTO `t_role_menu` VALUES (4547, '1', 82);
INSERT INTO `t_role_menu` VALUES (4548, '1', 83);
INSERT INTO `t_role_menu` VALUES (4549, '1', 84);
INSERT INTO `t_role_menu` VALUES (4550, '1', 85);
INSERT INTO `t_role_menu` VALUES (4551, '1', 86);
INSERT INTO `t_role_menu` VALUES (4552, '1', 87);
INSERT INTO `t_role_menu` VALUES (4553, '1', 88);
INSERT INTO `t_role_menu` VALUES (4554, '1', 3);
INSERT INTO `t_role_menu` VALUES (4555, '1', 5);
INSERT INTO `t_role_menu` VALUES (4556, '1', 6);
INSERT INTO `t_role_menu` VALUES (4557, '1', 7);
INSERT INTO `t_role_menu` VALUES (4558, '1', 4);
INSERT INTO `t_role_menu` VALUES (4559, '1', 8);
INSERT INTO `t_role_menu` VALUES (4560, '1', 9);
INSERT INTO `t_role_menu` VALUES (4561, '1', 10);
INSERT INTO `t_role_menu` VALUES (4562, '1', 111);
INSERT INTO `t_role_menu` VALUES (4563, '1', 36);
INSERT INTO `t_role_menu` VALUES (4564, '1', 38);
INSERT INTO `t_role_menu` VALUES (4565, '1', 99);
INSERT INTO `t_role_menu` VALUES (4566, '1', 100);
INSERT INTO `t_role_menu` VALUES (4567, '1', 101);
INSERT INTO `t_role_menu` VALUES (4568, '1', 102);
INSERT INTO `t_role_menu` VALUES (4569, '1', 37);
INSERT INTO `t_role_menu` VALUES (4570, '1', 42);
INSERT INTO `t_role_menu` VALUES (4571, '1', 45);
INSERT INTO `t_role_menu` VALUES (4572, '1', 11);
INSERT INTO `t_role_menu` VALUES (4573, '1', 12);
INSERT INTO `t_role_menu` VALUES (4574, '1', 15);
INSERT INTO `t_role_menu` VALUES (4575, '1', 16);
INSERT INTO `t_role_menu` VALUES (4576, '1', 17);
INSERT INTO `t_role_menu` VALUES (4577, '1', 60);
INSERT INTO `t_role_menu` VALUES (4578, '1', 13);
INSERT INTO `t_role_menu` VALUES (4579, '1', 18);
INSERT INTO `t_role_menu` VALUES (4580, '1', 19);
INSERT INTO `t_role_menu` VALUES (4581, '1', 20);
INSERT INTO `t_role_menu` VALUES (4582, '1', 33);
INSERT INTO `t_role_menu` VALUES (4583, '1', 14);
INSERT INTO `t_role_menu` VALUES (4584, '1', 69);
INSERT INTO `t_role_menu` VALUES (4585, '1', 70);
INSERT INTO `t_role_menu` VALUES (4586, '1', 21);
INSERT INTO `t_role_menu` VALUES (4587, '1', 22);
INSERT INTO `t_role_menu` VALUES (4588, '1', 24);
INSERT INTO `t_role_menu` VALUES (4589, '1', 23);
INSERT INTO `t_role_menu` VALUES (4590, '1', 25);
INSERT INTO `t_role_menu` VALUES (4591, '1', 93);
INSERT INTO `t_role_menu` VALUES (4592, '1', 94);
INSERT INTO `t_role_menu` VALUES (4593, '1', 104);
INSERT INTO `t_role_menu` VALUES (4594, '1', 105);
INSERT INTO `t_role_menu` VALUES (4595, '1', 106);
INSERT INTO `t_role_menu` VALUES (4596, '1', 26);
INSERT INTO `t_role_menu` VALUES (4597, '1', 53);
INSERT INTO `t_role_menu` VALUES (4598, '1', 71);
INSERT INTO `t_role_menu` VALUES (4599, '1', 27);
INSERT INTO `t_role_menu` VALUES (4600, '1', 28);
INSERT INTO `t_role_menu` VALUES (4601, '1', 29);
INSERT INTO `t_role_menu` VALUES (4602, '1', 30);
INSERT INTO `t_role_menu` VALUES (4603, '1', 31);
INSERT INTO `t_role_menu` VALUES (4604, '1', 32);
INSERT INTO `t_role_menu` VALUES (4605, '1', 34);
INSERT INTO `t_role_menu` VALUES (4606, '1', 35);
INSERT INTO `t_role_menu` VALUES (4607, '1', 39);
INSERT INTO `t_role_menu` VALUES (4608, '1', 40);
INSERT INTO `t_role_menu` VALUES (4609, '1', 41);
INSERT INTO `t_role_menu` VALUES (4610, '1', 61);
INSERT INTO `t_role_menu` VALUES (4611, '1', 62);
INSERT INTO `t_role_menu` VALUES (4612, '1', 63);
INSERT INTO `t_role_menu` VALUES (4613, '1', 64);
INSERT INTO `t_role_menu` VALUES (4614, '1', 65);
INSERT INTO `t_role_menu` VALUES (4615, '1', 66);
INSERT INTO `t_role_menu` VALUES (4616, '1', 103);
INSERT INTO `t_role_menu` VALUES (4617, '1', 51);
INSERT INTO `t_role_menu` VALUES (4618, '1', 54);
INSERT INTO `t_role_menu` VALUES (4619, '1', 55);
INSERT INTO `t_role_menu` VALUES (4620, '1', 56);
INSERT INTO `t_role_menu` VALUES (4621, '1', 57);
INSERT INTO `t_role_menu` VALUES (4622, '1', 58);
INSERT INTO `t_role_menu` VALUES (4623, '1', 52);
INSERT INTO `t_role_menu` VALUES (4624, '1', 76);
INSERT INTO `t_role_menu` VALUES (4625, '1', 77);
INSERT INTO `t_role_menu` VALUES (4626, '1', 78);
INSERT INTO `t_role_menu` VALUES (4627, '1', 79);
INSERT INTO `t_role_menu` VALUES (4628, '1', 46);
INSERT INTO `t_role_menu` VALUES (4629, '1', 67);
INSERT INTO `t_role_menu` VALUES (4630, '1', 68);

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
  `site_notice` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '网站公告',
  `create_site_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '建站日期',
  `record_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '备案号',
  `author_avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '作者头像',
  `site_author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '网站作者',
  `about_me` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '关于我',
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
INSERT INTO `t_site_config` VALUES (1, 'https://static.ttkwsd.top/config/7b6f25adc2b9627b8918176888bee3b5.png', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', 'Hello World', 'https://www.ttkwsd.top', '网站介绍', '网站公告', '2022-08-25', '赣ICP备2022007910号', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', '阿冬', '🍀个人简介\n\n想进大厂的小白\n\n感谢大佬们的开源精神💖', 'https://github.com/ICAN1999', 'https://gitee.com/wu_shengdong', 'https://space.bilibili.com/505715511', '1632167813', 0, 0, 1, 'https://static.ttkwsd.top/config/994a286571b002e93281899cb402bd15.png', 'https://static.ttkwsd.top/config/93cc02ba2f2e0926cbfbf0c48be9f649.jpg', 1, 'gitee,bilibili,github,qq', ',gitee', 1, '7611185981', '2023-01-07 19:31:33', '2023-02-14 16:41:40');

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
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_tag
-- ----------------------------
INSERT INTO `t_tag` VALUES (14, '的', '2023-02-21 15:38:05', NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_talk
-- ----------------------------

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
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_task
-- ----------------------------
INSERT INTO `t_task` VALUES (4, '清除每日游客访问记录', 'SYSTEM', 'timedTask.clear', '0 0 0 * * ?', 3, 0, 0, '清除redis中的游客记录', '2023-02-14 09:49:39', '2023-02-18 15:48:41');
INSERT INTO `t_task` VALUES (5, '测试任务', 'SYSTEM', 'timedTask.test', '0/10 * * * * ? ', 3, 0, 1, '测试任务', '2023-02-17 21:54:47', NULL);

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
INSERT INTO `t_task_log` VALUES (1, '测试任务', 'SYSTEM', 'timedTask.test', '测试任务 总共耗时：6毫秒', 1, NULL, '2023-02-17 21:54:50');
INSERT INTO `t_task_log` VALUES (2, '测试任务', 'SYSTEM', 'timedTask.test', '测试任务 总共耗时：0毫秒', 1, NULL, '2023-02-17 21:55:00');
INSERT INTO `t_task_log` VALUES (3, '测试任务', 'SYSTEM', 'timedTask.test', '测试任务 总共耗时：0毫秒', 1, NULL, '2023-02-17 21:55:10');
INSERT INTO `t_task_log` VALUES (4, '测试任务', 'SYSTEM', 'timedTask.test', '测试任务 总共耗时：0毫秒', 1, NULL, '2023-02-17 21:55:20');
INSERT INTO `t_task_log` VALUES (5, '测试任务', 'SYSTEM', 'timedTask.test', '测试任务 总共耗时：0毫秒', 1, NULL, '2023-02-17 21:55:30');
INSERT INTO `t_task_log` VALUES (6, '测试任务', 'SYSTEM', 'timedTask.test', '测试任务 总共耗时：0毫秒', 1, NULL, '2023-02-17 21:55:40');
INSERT INTO `t_task_log` VALUES (7, '清除每日游客访问记录', 'SYSTEM', 'timedTask.clear', '清除每日游客访问记录 总共耗时：10毫秒', 1, NULL, '2023-02-18 15:46:30');
INSERT INTO `t_task_log` VALUES (8, '清除每日游客访问记录', 'SYSTEM', 'timedTask.clear', '清除每日游客访问记录 总共耗时：1毫秒', 1, NULL, '2023-02-18 15:46:40');
INSERT INTO `t_task_log` VALUES (9, '清除每日游客访问记录', 'SYSTEM', 'timedTask.clear', '清除每日游客访问记录 总共耗时：1毫秒', 1, NULL, '2023-02-18 15:46:50');
INSERT INTO `t_task_log` VALUES (10, '清除每日游客访问记录', 'SYSTEM', 'timedTask.clear', '清除每日游客访问记录 总共耗时：0毫秒', 1, NULL, '2023-02-18 15:47:00');
INSERT INTO `t_task_log` VALUES (11, '清除每日游客访问记录', 'SYSTEM', 'timedTask.clear', '清除每日游客访问记录 总共耗时：0毫秒', 1, NULL, '2023-02-18 15:47:10');
INSERT INTO `t_task_log` VALUES (12, '清除每日游客访问记录', 'SYSTEM', 'timedTask.clear', '清除每日游客访问记录 总共耗时：0毫秒', 1, NULL, '2023-02-18 15:47:20');
INSERT INTO `t_task_log` VALUES (13, '清除每日游客访问记录', 'SYSTEM', 'timedTask.clear', '清除每日游客访问记录 总共耗时：0毫秒', 1, NULL, '2023-02-18 15:47:30');
INSERT INTO `t_task_log` VALUES (14, '清除每日游客访问记录', 'SYSTEM', 'timedTask.clear', '清除每日游客访问记录 总共耗时：0毫秒', 1, NULL, '2023-02-18 15:47:40');
INSERT INTO `t_task_log` VALUES (15, '清除每日游客访问记录', 'SYSTEM', 'timedTask.clear', '清除每日游客访问记录 总共耗时：1毫秒', 1, NULL, '2023-02-18 15:47:50');
INSERT INTO `t_task_log` VALUES (16, '清除每日游客访问记录', 'SYSTEM', 'timedTask.clear', '清除每日游客访问记录 总共耗时：1毫秒', 1, NULL, '2023-02-18 15:49:15');

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
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, '阿冬', 'admin@qq.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'https://static.ttkwsd.top/config/9c65807710f54d9d5ad398a78216ebfb.jpg', NULL, NULL, '1632167813@qq.com', '192.168.23.1', '内网IP|内网IP', 1, 0, '2023-02-21 16:39:27', '2022-11-29 21:45:48', '2023-02-21 16:39:27');
INSERT INTO `t_user` VALUES (3, '测试账号', 'test@qq.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'https://static.ttkwsd.top/config/0bca52afdb2b9998132355d716390c9f.png', 'https://www.ttkwsd.top', '个人简介', 'test@qq.com', '192.168.23.1', '内网IP|内网IP', 1, 0, '2023-02-21 12:50:37', '2022-11-30 21:34:26', '2023-02-21 12:50:37');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int NOT NULL COMMENT '用户id',
  `role_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES (29, 1, '1');
INSERT INTO `t_user_role` VALUES (36, 3, '3');

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
) ENGINE = InnoDB AUTO_INCREMENT = 1626 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_visit_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
