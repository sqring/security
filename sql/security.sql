/*
 Navicat Premium Data Transfer

 Source Server         : 47.105.186.229
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : 47.105.186.229:3306
 Source Schema         : security

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 12/05/2021 23:05:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins`  (
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `series` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `token` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of persistent_logins
-- ----------------------------
INSERT INTO `persistent_logins` VALUES ('18158990123', 'o5PqOtWT9bUkpMio2nGhFQ==', 'VBY1IFWxnnM735ITKLGr1A==', '2021-05-08 13:05:03');

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限 ID',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父权限 ID (0为顶级菜单)',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限名称',
  `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权标识符',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权路径',
  `type` int(2) NOT NULL DEFAULT 1 COMMENT '类型(1菜单，2按钮)',
  `icon` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (11, 0, '首页', 'sys:index', '/', 1, 'fa fa-dashboard', '', '2023-08-08 11:11:11', '2023-08-09 15:26:28');
INSERT INTO `sys_permission` VALUES (17, 0, '系统管理', 'sys:manage', NULL, 1, 'fa fa-cogs', NULL, '2023-08-08 11:11:11', '2023-08-09 15:26:28');
INSERT INTO `sys_permission` VALUES (18, 17, '用户管理', 'sys:user', '/user', 1, 'fa fa-users', NULL, '2023-08-08 11:11:11', '2023-08-09 15:26:28');
INSERT INTO `sys_permission` VALUES (19, 18, '列表', 'sys:user:list', '', 2, '', '员工列表', '2023-08-08 11:11:11', '2023-08-08 11:11:11');
INSERT INTO `sys_permission` VALUES (20, 18, '新增', 'sys:user:add', '', 2, '', '新增用户', '2023-08-08 11:11:11', '2023-08-09 15:26:28');
INSERT INTO `sys_permission` VALUES (21, 18, '修改', 'sys:user:edit', '', 2, '', '修改用户', '2023-08-08 11:11:11', '2023-08-09 15:26:28');
INSERT INTO `sys_permission` VALUES (22, 18, '删除', 'sys:user:delete', '', 2, '', '删除用户', '2023-08-08 11:11:11', '2023-08-09 15:26:28');
INSERT INTO `sys_permission` VALUES (23, 17, '角色管理', 'sys:role', '/role', 1, 'fa fa-user-secret', NULL, '2023-08-08 11:11:11', '2023-08-09 15:26:28');
INSERT INTO `sys_permission` VALUES (24, 23, '列表', 'sys:role:list', NULL, 2, NULL, '角色列表', '2023-08-08 11:11:11', '2023-08-08 11:11:11');
INSERT INTO `sys_permission` VALUES (25, 23, '新增', 'sys:role:add', '', 2, '', '新增角色', '2023-08-08 11:11:11', '2023-08-09 15:26:28');
INSERT INTO `sys_permission` VALUES (26, 23, '修改', 'sys:role:edit', '', 2, '', '修改角色', '2023-08-08 11:11:11', '2023-08-09 15:26:28');
INSERT INTO `sys_permission` VALUES (27, 23, '删除', 'sys:role:delete', '', 2, '', '删除角色', '2023-08-08 11:11:11', '2023-08-09 15:26:28');
INSERT INTO `sys_permission` VALUES (28, 17, '权限管理', 'sys:permission', '/permission', 1, 'fa fa-cog', NULL, '2023-08-08 11:11:11', '2023-08-09 15:26:28');
INSERT INTO `sys_permission` VALUES (29, 28, '列表', 'sys:permission:list', NULL, 2, NULL, '权限列表', '2023-08-08 11:11:11', '2023-08-08 11:11:11');
INSERT INTO `sys_permission` VALUES (30, 28, '新增', 'sys:permission:add', '', 2, NULL, '新增权限', '2023-08-08 11:11:11', '2023-08-09 15:26:28');
INSERT INTO `sys_permission` VALUES (31, 28, '修改', 'sys:permission:edit', '', 2, NULL, '修改权限', '2023-08-08 11:11:11', '2023-08-09 15:26:28');
INSERT INTO `sys_permission` VALUES (32, 28, '删除', 'sys:permission:delete', '', 2, '', '删除权限', '2023-08-08 11:11:11', '2023-08-09 15:26:28');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色 ID',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色说明',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (9, '超级管理员', '拥有所有的权限', '2023-08-08 11:11:11', '2023-08-08 11:11:11');
INSERT INTO `sys_role` VALUES (10, '普通管理员', '拥有查看权限', '2023-08-08 11:11:11', '2023-08-08 11:11:11');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色 ID',
  `permission_id` bigint(20) NOT NULL COMMENT '权限 ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (1, 9, 11);
INSERT INTO `sys_role_permission` VALUES (2, 9, 17);
INSERT INTO `sys_role_permission` VALUES (3, 9, 18);
INSERT INTO `sys_role_permission` VALUES (4, 9, 19);
INSERT INTO `sys_role_permission` VALUES (5, 9, 20);
INSERT INTO `sys_role_permission` VALUES (6, 9, 21);
INSERT INTO `sys_role_permission` VALUES (7, 9, 22);
INSERT INTO `sys_role_permission` VALUES (8, 9, 23);
INSERT INTO `sys_role_permission` VALUES (9, 9, 24);
INSERT INTO `sys_role_permission` VALUES (10, 9, 25);
INSERT INTO `sys_role_permission` VALUES (11, 9, 26);
INSERT INTO `sys_role_permission` VALUES (12, 9, 27);
INSERT INTO `sys_role_permission` VALUES (13, 9, 28);
INSERT INTO `sys_role_permission` VALUES (14, 9, 29);
INSERT INTO `sys_role_permission` VALUES (15, 9, 30);
INSERT INTO `sys_role_permission` VALUES (16, 9, 31);
INSERT INTO `sys_role_permission` VALUES (17, 9, 32);
INSERT INTO `sys_role_permission` VALUES (18, 10, 11);
INSERT INTO `sys_role_permission` VALUES (19, 10, 17);
INSERT INTO `sys_role_permission` VALUES (20, 10, 18);
INSERT INTO `sys_role_permission` VALUES (21, 10, 19);
INSERT INTO `sys_role_permission` VALUES (22, 10, 23);
INSERT INTO `sys_role_permission` VALUES (23, 10, 24);
INSERT INTO `sys_role_permission` VALUES (24, 10, 28);
INSERT INTO `sys_role_permission` VALUES (25, 10, 29);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户 ID',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码，加密存储, admin/1234',
  `is_account_non_expired` int(2) NULL DEFAULT 1 COMMENT '帐户是否过期(1 未过期，0已过期)',
  `is_account_non_locked` int(2) NULL DEFAULT 1 COMMENT '帐户是否被锁定(1 未过期，0已过期)',
  `is_credentials_non_expired` int(2) NULL DEFAULT 1 COMMENT '密码是否过期(1 未过期，0已过期)',
  `is_enabled` int(2) NULL DEFAULT 1 COMMENT '帐户是否可用(1 可用，0 删除用户)',
  `nick_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '注册手机号',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '注册邮箱',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  UNIQUE INDEX `mobile`(`mobile`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (9, 'admin', '$2a$10$rDkPvvAFV8kqwvKJzwlRv.i.q.wz1w1pz0SFsHn/55jNeZFQv/eCm', 1, 1, 1, 1, '管理员', '16888888888', 'mengxuegu888@163.com', '2023-08-08 11:11:11', '2019-12-16 10:25:53');
INSERT INTO `sys_user` VALUES (10, 'test', '$2a$10$rDkPvvAFV8kqwvKJzwlRv.i.q.wz1w1pz0SFsHn/55jNeZFQv/eCm', 1, 1, 1, 1, '测试', '16888886666', 'test11@qq.com', '2023-08-08 11:11:11', '2023-08-08 11:11:11');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户 ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色 ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 9, 9);
INSERT INTO `sys_user_role` VALUES (2, 10, 10);

SET FOREIGN_KEY_CHECKS = 1;
