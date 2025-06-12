/*
Navicat MySQL Data Transfer

Source Server         : 体验环境副库
Source Server Version : 80036
Source Host           : rm-bp1869mt19vkze7pj3o.mysql.rds.aliyuncs.com:3306
Source Database       : ceshi

Target Server Type    : MYSQL
Target Server Version : 80036
File Encoding         : 65001

Date: 2025-05-23 01:35:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for banner
-- ----------------------------
DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '递增主键ID',
  `object` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '源文件',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0待上传1已上传',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  `create_user_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建者用户ID',
  `create_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建者姓名',
  `update_user_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '更新者用户Id',
  `update_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '更新者用户姓名',
  `company_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '公司ID',
  `company_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '公司名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of banner
-- ----------------------------

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '递增ID',
  `uuid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '唯一ID',
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '分类标题',
  `parent_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '父ID',
  `category_path` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '分类路径',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态',
  `is_delete` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
  `weight` int unsigned NOT NULL DEFAULT '50' COMMENT '权重',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  `create_user_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建者用户ID',
  `update_user_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '更新者用户ID',
  `company_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '公司ID',
  `company_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '公司名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_uuid_comany_id` (`company_id`,`uuid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10477 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('7056', '830b1c71-31bf-4bbe-8c05-05a4a31291e6', '企业经营', '1', '1,830b1c71-31bf-4bbe-8c05-05a4a31291e6', '0', '0', '1', '2024-05-14 16:57:05', '2024-05-14 16:57:05', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `category` VALUES ('7057', '0985c1a0-4e30-4c6e-9faf-3d021b3ad3ca', '运营模块', '830b1c71-31bf-4bbe-8c05-05a4a31291e6', '1,830b1c71-31bf-4bbe-8c05-05a4a31291e6,0985c1a0-4e30-4c6e-9faf-3d021b3ad3ca', '0', '0', '2', '2024-05-14 16:57:13', '2024-05-14 16:57:13', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `category` VALUES ('7058', '21d428c8-c7ff-4699-92f7-1535a1d3694f', '领导模块', '0985c1a0-4e30-4c6e-9faf-3d021b3ad3ca', '1,830b1c71-31bf-4bbe-8c05-05a4a31291e6,0985c1a0-4e30-4c6e-9faf-3d021b3ad3ca,21d428c8-c7ff-4699-92f7-1535a1d3694f', '0', '0', '3', '2024-05-14 16:57:20', '2024-05-14 16:57:20', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `category` VALUES ('8281', '3466194f-0e21-43fb-a1b1-b975d6c9acb1', '经验分享', '1', '1,3466194f-0e21-43fb-a1b1-b975d6c9acb1', '0', '0', '4', '2024-05-22 08:42:37', '2024-05-22 08:42:37', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `category` VALUES ('8282', '37f075ce-6056-4122-8c97-e37682fb1203', '技术文档', '3466194f-0e21-43fb-a1b1-b975d6c9acb1', '1,3466194f-0e21-43fb-a1b1-b975d6c9acb1,37f075ce-6056-4122-8c97-e37682fb1203', '0', '0', '5', '2024-05-22 08:42:47', '2024-05-22 08:42:47', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `category` VALUES ('8283', 'e757555a-03ac-4670-bf8b-d1239e51cdff', '行业方案', '3466194f-0e21-43fb-a1b1-b975d6c9acb1', '1,3466194f-0e21-43fb-a1b1-b975d6c9acb1,e757555a-03ac-4670-bf8b-d1239e51cdff', '0', '0', '6', '2024-05-22 08:43:01', '2024-05-22 08:43:01', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `category` VALUES ('8284', 'c7e87ec1-0cbc-4f8c-b315-c5ebb4346c4c', '商务合作', '3466194f-0e21-43fb-a1b1-b975d6c9acb1', '1,3466194f-0e21-43fb-a1b1-b975d6c9acb1,c7e87ec1-0cbc-4f8c-b315-c5ebb4346c4c', '0', '0', '7', '2024-05-22 08:43:09', '2024-05-22 08:43:09', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `category` VALUES ('8285', '7b520996-fffa-4587-a260-07e585f56379', '宣传文案', '3466194f-0e21-43fb-a1b1-b975d6c9acb1', '1,3466194f-0e21-43fb-a1b1-b975d6c9acb1,7b520996-fffa-4587-a260-07e585f56379', '0', '0', '8', '2024-05-22 08:43:20', '2024-05-22 08:43:20', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `category` VALUES ('8286', '4ff47244-be24-4425-86db-bb44d86efd79', '售后服务', '3466194f-0e21-43fb-a1b1-b975d6c9acb1', '1,3466194f-0e21-43fb-a1b1-b975d6c9acb1,4ff47244-be24-4425-86db-bb44d86efd79', '0', '0', '9', '2024-05-22 08:43:34', '2024-05-22 08:43:34', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `category` VALUES ('8287', 'a6313b94-7354-4abf-8b59-925f9958d757', '客户方案', '3466194f-0e21-43fb-a1b1-b975d6c9acb1', '1,3466194f-0e21-43fb-a1b1-b975d6c9acb1,a6313b94-7354-4abf-8b59-925f9958d757', '0', '0', '10', '2024-05-22 08:43:43', '2024-05-22 08:43:43', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `category` VALUES ('8288', '45d28a46-50b7-4580-9523-81747eaea16e', '职业素养', '1', '1,45d28a46-50b7-4580-9523-81747eaea16e', '0', '0', '11', '2024-05-22 08:43:59', '2024-05-22 08:43:59', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `category` VALUES ('8289', 'e8209fc7-c254-45a8-8d7b-f7bd10e08c4b', '语言学习', '45d28a46-50b7-4580-9523-81747eaea16e', '1,45d28a46-50b7-4580-9523-81747eaea16e,e8209fc7-c254-45a8-8d7b-f7bd10e08c4b', '0', '0', '12', '2024-05-22 08:44:13', '2024-05-22 08:44:13', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `category` VALUES ('8290', 'cb00b41f-b902-4231-be57-88538132109d', '人际沟通', '45d28a46-50b7-4580-9523-81747eaea16e', '1,45d28a46-50b7-4580-9523-81747eaea16e,cb00b41f-b902-4231-be57-88538132109d', '0', '0', '13', '2024-05-22 08:44:26', '2024-05-22 08:44:26', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `category` VALUES ('8291', '7b54f43d-93b6-497c-b708-3c35e977224f', '职场心态', '45d28a46-50b7-4580-9523-81747eaea16e', '1,45d28a46-50b7-4580-9523-81747eaea16e,7b54f43d-93b6-497c-b708-3c35e977224f', '0', '0', '14', '2024-05-22 08:44:38', '2024-05-22 08:44:38', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `category` VALUES ('8292', 'bf831000-e4a7-4b99-840e-74cc7db9006e', '思维训练', '45d28a46-50b7-4580-9523-81747eaea16e', '1,45d28a46-50b7-4580-9523-81747eaea16e,bf831000-e4a7-4b99-840e-74cc7db9006e', '0', '0', '15', '2024-05-22 08:44:53', '2024-05-22 08:44:53', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `category` VALUES ('8293', 'ccb1f245-9104-4ae2-8c22-87fb3b318212', '专业技能', '1', '1,ccb1f245-9104-4ae2-8c22-87fb3b318212', '0', '0', '16', '2024-05-22 08:45:07', '2024-05-22 08:45:07', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `category` VALUES ('8294', '4f41a2b0-79b2-4366-b8a5-58fc0d3002f0', '生产研发', 'ccb1f245-9104-4ae2-8c22-87fb3b318212', '1,ccb1f245-9104-4ae2-8c22-87fb3b318212,4f41a2b0-79b2-4366-b8a5-58fc0d3002f0', '0', '0', '17', '2024-05-22 08:45:16', '2024-05-22 08:45:16', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `category` VALUES ('8295', '78d69739-30ca-4175-a47a-bb8c5883ffd7', '市场营销', 'ccb1f245-9104-4ae2-8c22-87fb3b318212', '1,ccb1f245-9104-4ae2-8c22-87fb3b318212,78d69739-30ca-4175-a47a-bb8c5883ffd7', '0', '0', '18', '2024-05-22 08:45:27', '2024-05-22 08:45:27', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `category` VALUES ('8296', '9f5e5378-5af9-4b4d-8d51-ce2c562cbfae', '人力资源', 'ccb1f245-9104-4ae2-8c22-87fb3b318212', '1,ccb1f245-9104-4ae2-8c22-87fb3b318212,9f5e5378-5af9-4b4d-8d51-ce2c562cbfae', '0', '0', '19', '2024-05-22 08:45:35', '2024-05-22 08:45:35', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `category` VALUES ('8297', '2a4b6b63-645f-4ebf-8cd8-286e6d088b64', '财务管理', 'ccb1f245-9104-4ae2-8c22-87fb3b318212', '1,ccb1f245-9104-4ae2-8c22-87fb3b318212,2a4b6b63-645f-4ebf-8cd8-286e6d088b64', '0', '0', '20', '2024-05-22 08:45:44', '2024-05-22 08:45:44', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `category` VALUES ('9849', '4932d915-fde8-4caf-b590-b6d8fb5cec5a', '阿迪达斯', '4f41a2b0-79b2-4366-b8a5-58fc0d3002f0', '1,ccb1f245-9104-4ae2-8c22-87fb3b318212,4f41a2b0-79b2-4366-b8a5-58fc0d3002f0,4932d915-fde8-4caf-b590-b6d8fb5cec5a', '0', '0', '21', '2024-05-31 14:12:08', '2024-05-31 14:12:08', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', '5d70aa10-b5af-4092-b968-d374dd133269', '');

-- ----------------------------
-- Table structure for courseware
-- ----------------------------
DROP TABLE IF EXISTS `courseware`;
CREATE TABLE `courseware` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '递增id',
  `uuid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '课件uuid',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '课件标题',
  `type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '课件类型',
  `file_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文件格式',
  `course_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '课程ID',
  `is_delete` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '0未删除1已删除',
  `length` int unsigned NOT NULL DEFAULT '0' COMMENT '课件时长',
  `weight` int unsigned NOT NULL DEFAULT '0' COMMENT '权重',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  `create_user_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建者用户ID',
  `update_user_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '更新者用户ID',
  `company_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '公司ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_uuid_company_id` (`company_id`,`course_id`,`uuid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=692 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of courseware
-- ----------------------------

-- ----------------------------
-- Table structure for courseware_user
-- ----------------------------
DROP TABLE IF EXISTS `courseware_user`;
CREATE TABLE `courseware_user` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '递增主键',
  `event_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '本表的唯一id',
  `course_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '课程和关联课程id关联课件为阶段id',
  `courseware_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '课件ID',
  `courseware_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '课件标题',
  `stage_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '阶段id',
  `user_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户ID',
  `user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户姓名',
  `learn_status` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'learning' COMMENT '学习状态''learning''学习中''finished''已完成',
  `start_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '开始学习时间',
  `finish_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '完成时间',
  `learn_time` float(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '学习时长',
  `progress_bar` float(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '上次观看时间',
  `length` int unsigned NOT NULL DEFAULT '0' COMMENT '课件总时长',
  `progress` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '单个课件进度',
  `source` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'course' COMMENT '课程培训来源区分',
  `source_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '来源id',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  `company_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '公司ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_lesson_id_dept_user_id_company_id` (`company_id`,`course_id`,`user_id`,`source_id`,`courseware_id`,`stage_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=487 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of courseware_user
-- ----------------------------

-- ----------------------------
-- Table structure for credit_log
-- ----------------------------
DROP TABLE IF EXISTS `credit_log`;
CREATE TABLE `credit_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `uuid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '唯一id',
  `target_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '对象id',
  `target_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '对象名称',
  `module` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'user' COMMENT '所属模块',
  `credit` int NOT NULL DEFAULT '0' COMMENT '获得学分数',
  `user_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '获得积分的用户id',
  `user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '获得积分的用户名',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '备注',
  `is_delete` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
  `company_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '企业id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_company_user_target` (`company_id`,`user_id`,`target_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户学分记录表';

-- ----------------------------
-- Records of credit_log
-- ----------------------------

-- ----------------------------
-- Table structure for knowledge
-- ----------------------------
DROP TABLE IF EXISTS `knowledge`;
CREATE TABLE `knowledge` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '递增主键',
  `uuid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '唯一uuid',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '课程标题',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '课程状态-1已删除0未发布1已发布2已关闭3已删除',
  `is_delete` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '1删除0未删除',
  `category_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '分类ID',
  `category_title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '分类名称',
  `credit` int NOT NULL DEFAULT '0' COMMENT '学分',
  `certificate` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '证书id',
  `learn_mode` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'liberty' COMMENT '学习模式  liberty为自由式,unlock为解锁式',
  `lector_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '讲师id',
  `sync_learn` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'close' COMMENT '同步已学习进度',
  `picture` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '课程封面',
  `speed` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'close' COMMENT 'close倍速关闭 open开启',
  `drag` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'close' COMMENT 'close关闭拖动open开启',
  `model` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '1是默认管理范围2是指定管理范围',
  `courseware_num` int NOT NULL DEFAULT '0' COMMENT '课件数量',
  `duration` int NOT NULL DEFAULT '0' COMMENT '课程时长',
  `eligible` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'all' COMMENT '奖励标准',
  `learn_progress` int NOT NULL DEFAULT '0' COMMENT '奖励学习进度',
  `publish_time` datetime DEFAULT '1970-01-01 00:00:00' COMMENT '发布时间',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  `create_user_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建者用户ID',
  `update_user_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '更新者用户ID',
  `company_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '公司ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=179 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of knowledge
-- ----------------------------

-- ----------------------------
-- Table structure for knowledge_user
-- ----------------------------
DROP TABLE IF EXISTS `knowledge_user`;
CREATE TABLE `knowledge_user` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '递增ID',
  `target_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '目标id',
  `target_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '目标标题',
  `user_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户ID',
  `user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建者用户姓名',
  `is_delete` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
  `credit` int DEFAULT '0' COMMENT '学分',
  `courseware_num` int unsigned DEFAULT '0' COMMENT '课件总数',
  `courseware_finish_num` int unsigned DEFAULT '0' COMMENT '课件完成数量',
  `progress` tinyint unsigned DEFAULT '0' COMMENT '学习总进度',
  `learn_status` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'learning' COMMENT '学习状态',
  `learn_time` int NOT NULL DEFAULT '0' COMMENT '学习时长',
  `finish_time` datetime DEFAULT '1970-01-01 00:00:00' COMMENT '完成时间',
  `publish_time` datetime DEFAULT '1970-01-01 00:00:00' COMMENT '课程发布时间',
  `source` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'course' COMMENT '来源',
  `source_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '来源id',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  `company_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '公司ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=758 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of knowledge_user
-- ----------------------------

-- ----------------------------
-- Table structure for knowledge_visible
-- ----------------------------
DROP TABLE IF EXISTS `knowledge_visible`;
CREATE TABLE `knowledge_visible` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '递增ID',
  `home_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '唯一ID',
  `name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '可见范围名称',
  `scope_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '范围类型dept部门user人员',
  `target_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '目标Id',
  `is_delete` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  `create_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建者用户ID',
  `update_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '更新者用户ID',
  `company_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '公司ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=502 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of knowledge_visible
-- ----------------------------

-- ----------------------------
-- Table structure for lector
-- ----------------------------
DROP TABLE IF EXISTS `lector`;
CREATE TABLE `lector` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `lector_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '讲师id',
  `lector_name` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '讲师名称',
  `lector_avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '头像',
  `lector_grade` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '讲师等级id',
  `lector_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'interior' COMMENT 'interior内部external外部',
  `contact_way` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '联系方式',
  `synopsis` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '讲师简介',
  `sex` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'man' COMMENT 'man男woman女',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '1是删除',
  `create_time` datetime DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `update_time` datetime DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间',
  `create_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建人',
  `update_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '修改人',
  `company_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '公司id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of lector
-- ----------------------------

-- ----------------------------
-- Table structure for lector_grade
-- ----------------------------
DROP TABLE IF EXISTS `lector_grade`;
CREATE TABLE `lector_grade` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `uuid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '唯一id',
  `title` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '等级名称',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'interior' COMMENT 'interior内部external外部',
  `synopsis` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '等级说明',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建人',
  `update_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '修改人id',
  `company_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '公司id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of lector_grade
-- ----------------------------

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '递增ID',
  `module` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '模块名称',
  `controller` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '控制器名称',
  `action` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '操作项',
  `request_url` varchar(2550) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '请求路由',
  `source` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'dingDingIsv' COMMENT '来源：dingDingIsv,dingDingSelf,weiXin,platform',
  `type` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT 'admin' COMMENT '操作类型：admin后台操作home前台操作other其他',
  `target_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '操作对象ID',
  `code` mediumint unsigned NOT NULL DEFAULT '0' COMMENT '错误码0代表没有错误大于0代表有错误',
  `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日志内容',
  `conditions` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '查询条件',
  `data` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '操作数据内容',
  `ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'ip地址',
  `level` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '日志级别1.error 2.warning 3.info',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `create_user_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建者用户ID',
  `create_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建者用户姓名',
  `create_dept_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建者公司内ID',
  `create_dept_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建者用户名称',
  `company_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '公司ID',
  `company_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '公司名称',
  PRIMARY KEY (`id`),
  KEY `idx_target_id_company_id` (`company_id`,`target_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of log
-- ----------------------------

-- ----------------------------
-- Table structure for material_library
-- ----------------------------
DROP TABLE IF EXISTS `material_library`;
CREATE TABLE `material_library` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '递增主键ID',
  `uuid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '唯一ID',
  `title` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '课件标题',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文件名',
  `category_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'empty' COMMENT '分类ID',
  `category_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '分类名称',
  `lector_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '讲师id',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0待上传1已上传',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `use_range` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'default' COMMENT 'all全部管理员default系统管理范围',
  `size` bigint unsigned NOT NULL DEFAULT '0' COMMENT '文件大小',
  `length` int unsigned NOT NULL DEFAULT '0' COMMENT '时长',
  `object` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '源文件',
  `trans_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '转完后的路径',
  `trans_code_status` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '0转码中1转码成功2转码失败',
  `file_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文件格式',
  `type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '媒体类型video视频audio音频document文档',
  `job_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '转码任务ID百度和阿里云的都放在这里面',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  `create_user_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建者用户ID',
  `create_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建者姓名',
  `update_user_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '更新者用户Id',
  `update_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '更新者用户姓名',
  `company_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '公司ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_uuid_company_id` (`company_id`,`uuid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=665 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of material_library
-- ----------------------------

-- ----------------------------
-- Table structure for mentor
-- ----------------------------
DROP TABLE IF EXISTS `mentor`;
CREATE TABLE `mentor` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `mentor_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '导师id',
  `mentor_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '导师名称',
  `mentor_avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mentor_grade` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '讲师等级id',
  `synopsis` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '简介',
  `sex` tinyint(1) DEFAULT '0' COMMENT '0男1女',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '1是删除',
  `create_time` datetime DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `update_time` datetime DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间',
  `create_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建人',
  `update_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '修改人',
  `company_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '公司id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of mentor
-- ----------------------------

-- ----------------------------
-- Table structure for mentor_grade
-- ----------------------------
DROP TABLE IF EXISTS `mentor_grade`;
CREATE TABLE `mentor_grade` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `uuid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '唯一id',
  `title` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '等级名称',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'interior' COMMENT 'interior内部external外部',
  `synopsis` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '等级说明',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建人',
  `update_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '修改人id',
  `company_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '公司id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of mentor_grade
-- ----------------------------

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '递增主键',
  `sync_action` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '事件英文名',
  `suite_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '用户购买第三方企业应用的suiteId',
  `suite_key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户购买第三方企业应用的suiteKey',
  `corp_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '购买企业的corpId',
  `goods_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '商品码',
  `item_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '规格码',
  `max_of_people` int unsigned NOT NULL DEFAULT '0' COMMENT '规格支持最大使用人数',
  `min_of_people` int unsigned NOT NULL DEFAULT '0' COMMENT '规格支持最小使用人数',
  `order_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '订单id',
  `paid_time` int unsigned NOT NULL DEFAULT '0' COMMENT '支付时间',
  `service_stop_time` int unsigned NOT NULL DEFAULT '0' COMMENT '服务结束时间',
  `pay_fee` int unsigned NOT NULL DEFAULT '0' COMMENT '实际支付价格（单位：分）',
  `order_create_source` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '订单创建来源，若值为TIANYUAN，表示来自天元系统平台',
  `nominal_pay_fee` int unsigned NOT NULL DEFAULT '0' COMMENT '名义票面费用（单位：分），现与payFee值相等',
  `discount_fee` int unsigned NOT NULL DEFAULT '0' COMMENT '折扣减免费用（单位：分），现值为0',
  `discount` double(11,2) unsigned NOT NULL DEFAULT '1.00' COMMENT '折扣，现值为1.00',
  `distributor_corp_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '分销商企业corpId',
  `distributor_corp_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '分销商企业名称',
  `solution_package_name` varchar(125) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '解决方案名称',
  `article_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'normal' COMMENT '商品类型：normal普通商品，image:OXM镜像商品',
  `sub_quantity` int unsigned NOT NULL DEFAULT '0' COMMENT '购买数量',
  `service_start_time` int unsigned NOT NULL DEFAULT '0' COMMENT '服务开始时间（单位：毫秒）',
  `order_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '订单类型，BUY新购，RENEW续费，UPGRADE升级，RENEW_UPGRADE续费升配，RENEW_DEGRADE续费降配',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '订单状态，0未生效1已生效',
  `order_time` int unsigned NOT NULL DEFAULT '0' COMMENT '订单创建时间',
  `leads_from` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'dingDingIsv' COMMENT '订单平台来源',
  `purchase_type` tinyint(1) DEFAULT '1' COMMENT '购买类型。',
  `event_data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '事件原内容',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_buy_corp_order_id` (`corp_id`,`order_id`) USING BTREE,
  KEY `idx_distributor_corp_id` (`distributor_corp_id`) USING BTREE,
  KEY `idx_paid_time` (`paid_time`) USING BTREE,
  KEY `idx_create_source` (`order_create_source`) USING BTREE,
  KEY `idx_article_type` (`article_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='订单表';

-- ----------------------------
-- Records of order
-- ----------------------------

-- ----------------------------
-- Table structure for picture
-- ----------------------------
DROP TABLE IF EXISTS `picture`;
CREATE TABLE `picture` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '递增主键ID',
  `uuid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '唯一ID',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'course' COMMENT '图片类型',
  `object` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '源文件',
  `style_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'colum' COMMENT '横板竖版',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0待上传1已上传',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  `create_user_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建者用户ID',
  `create_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建者姓名',
  `update_user_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '更新者用户Id',
  `update_user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '更新者用户姓名',
  `company_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '公司ID',
  `company_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '公司名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_uuid_company_id` (`company_id`,`uuid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=167 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of picture
-- ----------------------------

-- ----------------------------
-- Table structure for setting
-- ----------------------------
DROP TABLE IF EXISTS `setting`;
CREATE TABLE `setting` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sync` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'proceed' COMMENT '同步状态',
  `company_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '公司id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of setting
-- ----------------------------

-- ----------------------------
-- Table structure for sys_company
-- ----------------------------
DROP TABLE IF EXISTS `sys_company`;
CREATE TABLE `sys_company` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `company_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公司id',
  `company_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '公司名称',
  `corp_logo` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '企业logo',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '企业类型',
  `permanent_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT 'qv永久授权码',
  `is_lock` tinyint(1) DEFAULT '0' COMMENT '是否到期',
  `auth_user_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '授权人员',
  `auth_mode` tinyint(1) DEFAULT '0' COMMENT '0为管理员授权1为成员授权',
  `is_system_auth_open` tinyint(1) DEFAULT '0' COMMENT '是否开启系统内权限',
  `leader` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '负责人',
  `agent_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '授权应用id',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
  `auth_account` int DEFAULT '0' COMMENT '授权数量',
  `buy_account` int DEFAULT '0' COMMENT '购买账号数',
  `auth_time` datetime DEFAULT '1970-01-01 00:00:00' COMMENT '授权时间',
  `trans_code_length` bigint DEFAULT '0' COMMENT '转码时长',
  `used_space` bigint DEFAULT '0' COMMENT '使用oss存储',
  `extra_space` bigint DEFAULT '0' COMMENT '购买存储',
  `total_space` bigint DEFAULT '20480' COMMENT '总存储空间',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `is_pay` tinyint(1) DEFAULT '0' COMMENT '1已购买',
  `is_init` tinyint(1) DEFAULT '0' COMMENT '是否初始化企业数据',
  `period_validity` datetime DEFAULT '1970-01-01 00:00:00' COMMENT '有效期',
  `edition` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'free' COMMENT 'free免费norm标准版',
  `buy_num` int DEFAULT '0' COMMENT '购买账号数量',
  `accredit_num` int DEFAULT '0' COMMENT '授权数量',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=183 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='公司表';

-- ----------------------------
-- Records of sys_company
-- ----------------------------
INSERT INTO `sys_company` VALUES ('181', '5d70aa10-b5af-4092-b968-d374dd133269', '自建企业web版', '', '0', 'web', '', '0', '', '0', '0', null, '', null, null, '0', '0', '1970-01-01 00:00:00', '0', '2101', '0', '20480', '0', '0', '0', '0', '1970-01-01 00:00:00', 'free', '0', '0', '', null, '', '2024-06-04 00:00:00');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `config_id` int NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='参数配置表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `dept_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门id',
  `dept_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '部门名称',
  `parent_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '父部门id',
  `dept_id_path` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '部门ID父路径',
  `dept_name_path` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '部门姓名父路径',
  `ancestors` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '祖级列表',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `member_count` int DEFAULT '0' COMMENT '人员数量',
  `leader` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
  `status` tinyint(1) DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `create_time` datetime DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `update_time` datetime DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  `company_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '公司id',
  `company_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '公司名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `companydept` (`company_id`,`dept_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1150 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='部门表';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('1134', '1', '自建企业web版', '0', '1', '自建企业web版', '1', '1', '0', null, null, null, '1', '0', '', '', '2024-04-12 14:44:36', '2024-04-12 14:44:36', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `sys_dept` VALUES ('1144', 'd9e2575c-fe06-4539-831e-3c3b7e1b1fc8', '技术部', '180b70fc-8def-4771-8ce5-420c93b30029', '1,63a7c0e0-9238-454c-85d2-17c5b91b2f09,2e3bd226-ed99-4e67-8022-87228b9619a6,180b70fc-8def-4771-8ce5-420c93b30029,d9e2575c-fe06-4539-831e-3c3b7e1b1fc8', '自建企业web版,产品部,运营部,销售部1122,技术部', ',1', '1', '0', null, null, null, '1', '2', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', '2024-05-15 00:46:55', '2024-05-26 09:17:03', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `sys_dept` VALUES ('1145', '63a7c0e0-9238-454c-85d2-17c5b91b2f09', '产品部', '1', '1,63a7c0e0-9238-454c-85d2-17c5b91b2f09', '自建企业web版,产品部', ',1', '2', '0', null, null, null, '1', '0', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', '', '2024-05-15 00:47:04', '1970-01-01 00:00:00', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `sys_dept` VALUES ('1146', '07d6164d-0d7f-46e7-b4db-673c4d038b14', '市场部', '1', '1,07d6164d-0d7f-46e7-b4db-673c4d038b14', '自建企业web版,市场部', ',1', '3', '0', null, null, null, '1', '0', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', '', '2024-05-15 00:47:15', '1970-01-01 00:00:00', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `sys_dept` VALUES ('1148', '2e3bd226-ed99-4e67-8022-87228b9619a6', '运营部', '63a7c0e0-9238-454c-85d2-17c5b91b2f09', '1,63a7c0e0-9238-454c-85d2-17c5b91b2f09,2e3bd226-ed99-4e67-8022-87228b9619a6', '自建企业web版,产品部,运营部', ',1', '5', '0', null, null, null, '1', '0', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', '2024-05-25 23:27:47', '2024-05-26 16:41:19', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `sys_dept` VALUES ('1149', '180b70fc-8def-4771-8ce5-420c93b30029', '销售部1122', '2e3bd226-ed99-4e67-8022-87228b9619a6', '1,63a7c0e0-9238-454c-85d2-17c5b91b2f09,2e3bd226-ed99-4e67-8022-87228b9619a6,180b70fc-8def-4771-8ce5-420c93b30029', '自建企业web版,产品部,运营部,销售部1122', '1,1', '6', '0', null, null, null, '1', '0', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', '2024-05-25 23:31:02', '2024-05-26 09:13:11', '5d70aa10-b5af-4092-b968-d374dd133269', '');

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job` (
  `job_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`,`job_name`,`job_group`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='定时任务调度表';

-- ----------------------------
-- Records of sys_job
-- ----------------------------

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log` (
  `job_log_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '日志信息',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '异常信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=204781 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='定时任务调度日志表';

-- ----------------------------
-- Records of sys_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_logininfor
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor` (
  `info_id` bigint NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '人员id',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '操作系统',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '提示消息',
  `login_time` date DEFAULT '1970-01-01' COMMENT '访问时间',
  `company_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '公司id',
  PRIMARY KEY (`info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统访问记录';

-- ----------------------------
-- Records of sys_logininfor
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `menu_id` varchar(36) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '菜单ID',
  `menu_name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '菜单名称',
  `parent_id` varchar(36) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '组件路径',
  `query` varchar(255) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '路由参数',
  `is_frame` int DEFAULT '1' COMMENT '是否为外链（0是 1否）',
  `is_cache` int DEFAULT '0' COMMENT '是否缓存（0缓存 1不缓存）',
  `is_super` tinyint(1) DEFAULT '0' COMMENT '1超级管理菜单',
  `menu_type` char(1) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` tinyint(1) DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) COLLATE utf8mb4_general_ci DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=230 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='菜单权限表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('2', '1', '企业设置', '0', '7', 'system', null, '', '1', '0', '0', 'M', '0', '0', '', 'system', 'admin', '2021-11-14 14:56:14', '$userName=032459622024032452$', '2024-03-09 00:57:13', '系统管理目录');
INSERT INTO `sys_menu` VALUES ('13', '101', '角色管理', '7e6eee32-e800-4c1a-86aa-b135266cc4df', '4', 'role', 'system/role/index', '', '1', '0', '0', 'C', '0', '0', 'system:role:list', '', 'admin', '2021-11-14 14:56:14', '$userName=03245962201172172$', '2024-05-09 15:23:25', '角色管理菜单');
INSERT INTO `sys_menu` VALUES ('24', '102', '菜单管理', '1', '3', 'menu', 'system/menu/index', '', '1', '0', '1', 'C', '0', '0', 'system:menu:list', 'tree-table', 'admin', '2021-11-14 14:56:14', '', null, '菜单管理菜单');
INSERT INTO `sys_menu` VALUES ('175', 'b947272f-ae4a-47d6-bbbd-d0eea5924275', '知识中心', '0', '1', 'course', '', '', '1', '0', '0', 'M', '0', '0', '', 'excel', '$userName=wo9rEvEAAA08S-QDZI5fyL60LBbf8JuQ$', '2023-12-20 08:30:40', '$userName=0224293720401213546$', '2024-03-26 09:11:36', '');
INSERT INTO `sys_menu` VALUES ('176', 'e25477ed-fc39-47dd-a79f-70f7c5f3d756', '资源分类', 'b947272f-ae4a-47d6-bbbd-d0eea5924275', '1', 'category', 'course/category/index', '', '1', '0', '0', 'C', '0', '0', '', 'category', '$userName=wo9rEvEAAA08S-QDZI5fyL60LBbf8JuQ$', '2023-12-20 08:33:06', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', '2024-05-15 13:19:41', '');
INSERT INTO `sys_menu` VALUES ('177', 'c2bfafd5-a60c-4843-86d9-134da5e4d15e', '课程管理', 'b947272f-ae4a-47d6-bbbd-d0eea5924275', '3', 'courseList', 'course/course/index', '', '1', '0', '0', 'C', '0', '0', '', 'course', '$userName=wo9rEvEAAA08S-QDZI5fyL60LBbf8JuQ$', '2023-12-20 08:38:00', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', '2024-05-15 13:20:16', '');
INSERT INTO `sys_menu` VALUES ('178', '9b98a323-a855-46e3-94de-0c9dfbce46d2', '课件管理', 'b947272f-ae4a-47d6-bbbd-d0eea5924275', '2', 'lessonList', 'course/lesson/index', '', '1', '0', '0', 'C', '0', '0', '', 'lesson', '$userName=wo9rEvEAAA08S-QDZI5fyL60LBbf8JuQ$', '2023-12-20 08:39:47', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', '2024-05-15 13:20:01', '');
INSERT INTO `sys_menu` VALUES ('194', '7e6eee32-e800-4c1a-86aa-b135266cc4df', '组织架构', '1', '1', 'orgSetting', '', '', '1', '0', '0', 'M', '0', '0', null, 'tree', '$userName=032459622024032452$', '2024-03-09 00:52:12', '', null, '');
INSERT INTO `sys_menu` VALUES ('212', '753219a0-30a6-4059-b28d-88bfabe2cbfc', '用户管理', '7e6eee32-e800-4c1a-86aa-b135266cc4df', '2', 'user', 'system/user/index', '', '1', '0', '0', 'C', '0', '0', '', '', '$userName=03245962201172172$', '2024-05-09 15:21:46', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', '2024-05-14 23:11:12', '');
INSERT INTO `sys_menu` VALUES ('213', '0d3593f0-a691-4e13-875d-cf58098a9bf0', '部门管理', '7e6eee32-e800-4c1a-86aa-b135266cc4df', '1', 'dept', 'system/dept/index', '', '1', '0', '0', 'C', '0', '0', '', '', '$userName=03245962201172172$', '2024-05-09 15:22:19', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', '2024-05-14 23:11:08', '');
INSERT INTO `sys_menu` VALUES ('223', '51ddf0ff-fe9b-40da-9a53-29d5e86c7057', '文章管理', '1', '3', 'article', '', '', '1', '0', '0', 'M', '1', '1', '', 'archives', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', '2024-06-05 13:29:00', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', '2024-06-05 18:11:56', '');
INSERT INTO `sys_menu` VALUES ('224', '669244fa-8718-4e8f-846e-489dfcec0d60', '分类管理', '51ddf0ff-fe9b-40da-9a53-29d5e86c7057', '1', 'category', 'system/article/category/index', '', '1', '0', '0', 'C', '0', '0', '', '#', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', '2024-06-05 13:29:31', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', '2024-06-05 13:33:14', '');
INSERT INTO `sys_menu` VALUES ('225', '09c6fe98-ab91-4ea3-96c7-d51249a90b42', '文章管理', '51ddf0ff-fe9b-40da-9a53-29d5e86c7057', '2', 'article', 'system/article/article/index', '', '1', '0', '0', 'C', '0', '0', '', '#', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', '2024-06-05 13:30:02', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', '2024-06-05 13:33:21', '');
INSERT INTO `sys_menu` VALUES ('226', '17f99d7e-a07f-4355-8119-68e4125416ef', '师资管理', '0', '2', 'teachers', '', '', '1', '0', '0', 'M', '0', '0', null, '#', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', '2024-06-05 18:18:11', '', null, '');
INSERT INTO `sys_menu` VALUES ('227', 'b2578d94-d832-4779-8f8a-5a7641a72d16', '讲师管理', '17f99d7e-a07f-4355-8119-68e4125416ef', '1', 'lector', '', '', '1', '0', '0', 'M', '0', '0', null, 'lector', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', '2024-06-05 18:19:11', '', null, '');
INSERT INTO `sys_menu` VALUES ('228', 'c2c8ed51-a086-4a35-bf3f-41e598954552', '讲师列表', 'b2578d94-d832-4779-8f8a-5a7641a72d16', '1', 'lectorList', 'teachers/lector/lectorList', '', '1', '0', '0', 'C', '0', '0', null, '#', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', '2024-06-05 18:20:08', '', null, '');
INSERT INTO `sys_menu` VALUES ('229', 'b6003232-0ecf-48c0-962a-7313b372c199', '讲师等级', 'b2578d94-d832-4779-8f8a-5a7641a72d16', '2', 'lectorGrade', 'teachers/lector/gradeList', '', '1', '0', '0', 'C', '0', '0', null, '#', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', '2024-06-05 18:20:39', '', null, '');

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log` (
  `oper_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日志主键',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '模块标题',
  `business_type` int DEFAULT '0' COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '请求方式',
  `operator_type` int DEFAULT '0' COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '返回参数',
  `status` int DEFAULT '0' COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`oper_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='操作日志记录';

-- ----------------------------
-- Records of sys_oper_log
-- ----------------------------
INSERT INTO `sys_oper_log` VALUES ('17459d3b-2a9e-4a30-97b7-34a4afe108c7', '课程发布', '2', 'com.edu.web.controller.knowledge.KnowledgeController.publish()', 'GET', '1', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', null, '/knowledge/knowledge/publish/82b4c1ff-369d-4449-a69d-a8f4fa129a09', '144.52.176.61', 'XX XX', '{uuid=82b4c1ff-369d-4449-a69d-a8f4fa129a09}', '{\"msg\":\"操作成功\",\"temp\":\"j\",\"code\":200}', '0', null, '2024-06-04 14:14:44');
INSERT INTO `sys_oper_log` VALUES ('21d344e8-956a-4549-a9cf-2baf97f1dea4', '素材库', '1', 'com.edu.web.controller.knowledge.MaterialLibraryController.add()', 'POST', '1', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', null, '/learn/lesson', '144.52.176.61', 'XX XX', '{\"categoryId\":\"ccb1f245-9104-4ae2-8c22-87fb3b318212\",\"categoryTitle\":\"专业技能\",\"createTime\":\"2024-06-04 14:10:44\",\"fileType\":\"mp4\",\"jobId\":\"38707b9994bd41f3a02f238674b9b65e\",\"length\":476,\"name\":\"薪酬管理目标原则.mp4\",\"object\":\"5d70aa10-b5af-4092-b968-d374dd133269/lesson/video/FFQwDT62kK1717481430743.mp4\",\"relevancy\":\"yes\",\"size\":40923176,\"title\":\"薪酬管理目标原则\",\"transCodeStatus\":1,\"type\":\"video\",\"updateTime\":\"2024-06-04 14:10:44\",\"useRange\":\"default\",\"uuid\":\"dbd03bb4-ee5f-4f76-9d21-5257afe3f722\"}', '{\"msg\":\"操作成功\",\"temp\":\"j\",\"code\":200,\"data\":{\"categoryId\":\"ccb1f245-9104-4ae2-8c22-87fb3b318212\",\"categoryTitle\":\"专业技能\",\"createTime\":\"2024-06-04 14:10:44\",\"isDelete\":0,\"jobId\":\"38707b9994bd41f3a02f238674b9b65e\",\"lectorId\":\"\",\"length\":476,\"name\":\"薪酬管理目标原则.mp4\",\"object\":\"5d70aa10-b5af-4092-b968-d374dd133269/lesson/video/FFQwDT62kK1717481430743.mp4\",\"relevancy\":\"yes\",\"size\":40923176,\"status\":0,\"title\":\"薪酬管理目标原则\",\"transCodeStatus\":1,\"type\":\"video\",\"updateTime\":\"2024-06-04 14:10:44\",\"useRange\":\"default\",\"uuid\":\"dbd03bb4-ee5f-4f76-9d21-5257afe3f722\"}}', '0', null, '2024-06-04 14:10:45');
INSERT INTO `sys_oper_log` VALUES ('22e0e1a4-ae6b-4ca1-87ab-17b3cfbae4b3', '课程发布', '2', 'com.edu.web.controller.knowledge.KnowledgeController.publish()', 'GET', '1', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', null, '/knowledge/knowledge/publish/83069043-118a-4c30-a376-0f60a47d2ca5', '144.52.176.61', 'XX XX', '{uuid=83069043-118a-4c30-a376-0f60a47d2ca5}', '{\"msg\":\"操作成功\",\"temp\":\"j\",\"code\":200}', '0', null, '2024-06-04 14:15:18');
INSERT INTO `sys_oper_log` VALUES ('29dd3a40-4cff-4c51-b5f7-51f12b0a2a32', '课程主表', '1', 'com.edu.web.controller.knowledge.KnowledgeController.add()', 'POST', '1', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', null, '/knowledge/knowledge', '144.52.176.61', 'XX XX', '{\"action\":\"update\",\"categoryId\":\"ccb1f245-9104-4ae2-8c22-87fb3b318212\",\"categoryTitle\":\"专业技能\",\"createTime\":\"2024-06-04 14:15:07\",\"credit\":0,\"drag\":\"close\",\"eligible\":\"all\",\"learnMode\":\"1\",\"learnProgress\":0,\"picture\":\"5d70aa10-b5af-4092-b968-d374dd133269/course/image/H6G2256CXh1715763131325.png\",\"relevancy\":\"yes\",\"speed\":\"close\",\"syncLearn\":\"close\",\"title\":\"薪酬管理制度\",\"uuid\":\"83069043-118a-4c30-a376-0f60a47d2ca5\"}', '{\"msg\":\"操作成功\",\"temp\":\"j\",\"code\":200,\"data\":{\"categoryId\":\"ccb1f245-9104-4ae2-8c22-87fb3b318212\",\"categoryTitle\":\"专业技能\",\"cdnPicture\":\"https://chuanzhiedu.oss-cn-hangzhou.aliyuncs.com/5d70aa10-b5af-4092-b968-d374dd133269/course/image/H6G2256CXh1715763131325.png\",\"certificate\":\"\",\"coursewareNum\":0,\"createTime\":\"2024-06-04 14:15:07\",\"credit\":0,\"drag\":\"close\",\"duration\":0,\"eligible\":\"all\",\"learnMode\":\"1\",\"learnProgress\":0,\"lectorId\":\"\",\"model\":\"1\",\"picture\":\"5d70aa10-b5af-4092-b968-d374dd133269/course/image/H6G2256CXh1715763131325.png\",\"relevancy\":\"yes\",\"speed\":\"close\",\"status\":0,\"syncLearn\":\"close\",\"title\":\"薪酬管理制度\",\"updateTime\":\"1970-01-01 00:00:00\",\"uuid\":\"83069043-118a-4c30-a376-0f60a47d2ca5\"}}', '0', null, '2024-06-04 14:15:07');
INSERT INTO `sys_oper_log` VALUES ('2c4a1825-634e-4990-a5da-c11d03a86234', '素材库', '1', 'com.edu.web.controller.knowledge.MaterialLibraryController.add()', 'POST', '1', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', null, '/learn/lesson', '144.52.176.61', 'XX XX', '{\"categoryId\":\"ccb1f245-9104-4ae2-8c22-87fb3b318212\",\"categoryTitle\":\"专业技能\",\"createTime\":\"2024-06-04 13:43:37\",\"fileType\":\"mp4\",\"jobId\":\"91ff40aa647d4b9884ad37a9ea5b35ac\",\"length\":158,\"name\":\"企业培训人才发展.mp4\",\"object\":\"5d70aa10-b5af-4092-b968-d374dd133269/lesson/video/AEsTZXXfDZ1717479651912.mp4\",\"relevancy\":\"yes\",\"size\":239998369,\"title\":\"企业培训人才发展\",\"transCodeStatus\":1,\"type\":\"video\",\"updateTime\":\"2024-06-04 13:43:37\",\"useRange\":\"default\",\"uuid\":\"eab5ff63-2d18-4865-9e4e-bdddc680e6d5\"}', '{\"msg\":\"操作成功\",\"temp\":\"j\",\"code\":200,\"data\":{\"categoryId\":\"ccb1f245-9104-4ae2-8c22-87fb3b318212\",\"categoryTitle\":\"专业技能\",\"createTime\":\"2024-06-04 13:43:37\",\"isDelete\":0,\"jobId\":\"91ff40aa647d4b9884ad37a9ea5b35ac\",\"lectorId\":\"\",\"length\":158,\"name\":\"企业培训人才发展.mp4\",\"object\":\"5d70aa10-b5af-4092-b968-d374dd133269/lesson/video/AEsTZXXfDZ1717479651912.mp4\",\"relevancy\":\"yes\",\"size\":239998369,\"status\":0,\"title\":\"企业培训人才发展\",\"transCodeStatus\":1,\"type\":\"video\",\"updateTime\":\"2024-06-04 13:43:37\",\"useRange\":\"default\",\"uuid\":\"eab5ff63-2d18-4865-9e4e-bdddc680e6d5\"}}', '0', null, '2024-06-04 13:43:38');
INSERT INTO `sys_oper_log` VALUES ('4c879218-3be3-40d2-8112-ce95bc1292cb', '素材库', '1', 'com.edu.web.controller.knowledge.MaterialLibraryController.add()', 'POST', '1', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', null, '/learn/lesson', '144.52.176.61', 'XX XX', '{\"categoryId\":\"ccb1f245-9104-4ae2-8c22-87fb3b318212\",\"categoryTitle\":\"专业技能\",\"createTime\":\"2024-06-04 14:13:50\",\"fileType\":\"pptx\",\"length\":300,\"name\":\"职星学院数字化学习解决方案(3).pptx\",\"object\":\"5d70aa10-b5af-4092-b968-d374dd133269/lesson/document/k7mH54Ztew1717481627650.pptx\",\"relevancy\":\"yes\",\"size\":2883732,\"title\":\"职星学院数字化学习解决方案(3)\",\"transCodeStatus\":1,\"type\":\"document\",\"updateTime\":\"2024-06-04 14:13:50\",\"useRange\":\"default\",\"uuid\":\"08ba4c67-eed5-4635-b074-5232653b9fcd\"}', '{\"msg\":\"操作成功\",\"temp\":\"j\",\"code\":200,\"data\":{\"categoryId\":\"ccb1f245-9104-4ae2-8c22-87fb3b318212\",\"categoryTitle\":\"专业技能\",\"createTime\":\"2024-06-04 14:13:50\",\"isDelete\":0,\"jobId\":\"\",\"lectorId\":\"\",\"length\":300,\"name\":\"职星学院数字化学习解决方案(3).pptx\",\"object\":\"5d70aa10-b5af-4092-b968-d374dd133269/lesson/document/k7mH54Ztew1717481627650.pptx\",\"relevancy\":\"yes\",\"size\":2883732,\"status\":0,\"title\":\"职星学院数字化学习解决方案(3)\",\"transCodeStatus\":1,\"type\":\"document\",\"updateTime\":\"2024-06-04 14:13:50\",\"useRange\":\"default\",\"uuid\":\"08ba4c67-eed5-4635-b074-5232653b9fcd\"}}', '0', null, '2024-06-04 14:13:50');
INSERT INTO `sys_oper_log` VALUES ('58096349-e8d0-4d2a-8a38-3cde48d5210a', '课程主表', '1', 'com.edu.web.controller.knowledge.KnowledgeController.add()', 'POST', '1', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', null, '/knowledge/knowledge', '144.52.176.61', 'XX XX', '{\"action\":\"update\",\"categoryId\":\"ccb1f245-9104-4ae2-8c22-87fb3b318212\",\"categoryTitle\":\"专业技能\",\"createTime\":\"2024-06-04 14:14:18\",\"credit\":0,\"drag\":\"close\",\"eligible\":\"all\",\"learnMode\":\"1\",\"learnProgress\":0,\"picture\":\"5d70aa10-b5af-4092-b968-d374dd133269/course/image/cMEjDePdPK1715765841124.png\",\"relevancy\":\"yes\",\"speed\":\"close\",\"syncLearn\":\"close\",\"title\":\"开源培训系统介绍\",\"uuid\":\"82b4c1ff-369d-4449-a69d-a8f4fa129a09\"}', '{\"msg\":\"操作成功\",\"temp\":\"j\",\"code\":200,\"data\":{\"categoryId\":\"ccb1f245-9104-4ae2-8c22-87fb3b318212\",\"categoryTitle\":\"专业技能\",\"cdnPicture\":\"https://chuanzhiedu.oss-cn-hangzhou.aliyuncs.com/5d70aa10-b5af-4092-b968-d374dd133269/course/image/cMEjDePdPK1715765841124.png\",\"certificate\":\"\",\"coursewareNum\":0,\"createTime\":\"2024-06-04 14:14:18\",\"credit\":0,\"drag\":\"close\",\"duration\":0,\"eligible\":\"all\",\"learnMode\":\"1\",\"learnProgress\":0,\"lectorId\":\"\",\"model\":\"1\",\"picture\":\"5d70aa10-b5af-4092-b968-d374dd133269/course/image/cMEjDePdPK1715765841124.png\",\"relevancy\":\"yes\",\"speed\":\"close\",\"status\":0,\"syncLearn\":\"close\",\"title\":\"开源培训系统介绍\",\"updateTime\":\"1970-01-01 00:00:00\",\"uuid\":\"82b4c1ff-369d-4449-a69d-a8f4fa129a09\"}}', '0', null, '2024-06-04 14:14:18');
INSERT INTO `sys_oper_log` VALUES ('9455a216-21d9-48f0-b0e3-eaa1ab6f55ee', '素材库', '1', 'com.edu.web.controller.knowledge.MaterialLibraryController.add()', 'POST', '1', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', null, '/learn/lesson', '144.52.176.61', 'XX XX', '{\"categoryId\":\"ccb1f245-9104-4ae2-8c22-87fb3b318212\",\"categoryTitle\":\"专业技能\",\"createTime\":\"2024-06-04 14:12:59\",\"fileType\":\"pptx\",\"length\":300,\"name\":\"云上CT数字化学习方案.pptx\",\"object\":\"5d70aa10-b5af-4092-b968-d374dd133269/lesson/document/JKYX3jxRnM1717481566823.pptx\",\"relevancy\":\"yes\",\"size\":29639864,\"title\":\"云上CT数字化学习方案\",\"transCodeStatus\":1,\"type\":\"document\",\"updateTime\":\"2024-06-04 14:12:59\",\"useRange\":\"default\",\"uuid\":\"5cc69ff9-07a3-41b1-946a-eb81f782e78c\"}', '{\"msg\":\"操作成功\",\"temp\":\"j\",\"code\":200,\"data\":{\"categoryId\":\"ccb1f245-9104-4ae2-8c22-87fb3b318212\",\"categoryTitle\":\"专业技能\",\"createTime\":\"2024-06-04 14:12:59\",\"isDelete\":0,\"jobId\":\"\",\"lectorId\":\"\",\"length\":300,\"name\":\"云上CT数字化学习方案.pptx\",\"object\":\"5d70aa10-b5af-4092-b968-d374dd133269/lesson/document/JKYX3jxRnM1717481566823.pptx\",\"relevancy\":\"yes\",\"size\":29639864,\"status\":0,\"title\":\"云上CT数字化学习方案\",\"transCodeStatus\":1,\"type\":\"document\",\"updateTime\":\"2024-06-04 14:12:59\",\"useRange\":\"default\",\"uuid\":\"5cc69ff9-07a3-41b1-946a-eb81f782e78c\"}}', '0', null, '2024-06-04 14:12:59');
INSERT INTO `sys_oper_log` VALUES ('d448f71b-dabe-47e8-82ae-65103ddbc3bc', '素材库', '1', 'com.edu.web.controller.knowledge.MaterialLibraryController.add()', 'POST', '1', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', null, '/learn/lesson', '144.52.176.61', 'XX XX', '{\"categoryId\":\"ccb1f245-9104-4ae2-8c22-87fb3b318212\",\"categoryTitle\":\"专业技能\",\"createTime\":\"2024-06-04 13:40:37\",\"fileType\":\"mp4\",\"jobId\":\"1032c56d81b447aba8b62c45a243c4fe\",\"length\":71,\"name\":\"职星学院开源版.mp4\",\"object\":\"5d70aa10-b5af-4092-b968-d374dd133269/lesson/video/b7PkCnJKnM1717479632551.mp4\",\"relevancy\":\"yes\",\"size\":12341315,\"title\":\"职星学院开源版\",\"transCodeStatus\":1,\"type\":\"video\",\"updateTime\":\"2024-06-04 13:40:37\",\"useRange\":\"default\",\"uuid\":\"d911cf58-fa43-4b4e-8740-6b6ece29d991\"}', '{\"msg\":\"操作成功\",\"temp\":\"j\",\"code\":200,\"data\":{\"categoryId\":\"ccb1f245-9104-4ae2-8c22-87fb3b318212\",\"categoryTitle\":\"专业技能\",\"createTime\":\"2024-06-04 13:40:37\",\"isDelete\":0,\"jobId\":\"1032c56d81b447aba8b62c45a243c4fe\",\"lectorId\":\"\",\"length\":71,\"name\":\"职星学院开源版.mp4\",\"object\":\"5d70aa10-b5af-4092-b968-d374dd133269/lesson/video/b7PkCnJKnM1717479632551.mp4\",\"relevancy\":\"yes\",\"size\":12341315,\"status\":0,\"title\":\"职星学院开源版\",\"transCodeStatus\":1,\"type\":\"video\",\"updateTime\":\"2024-06-04 13:40:37\",\"useRange\":\"default\",\"uuid\":\"d911cf58-fa43-4b4e-8740-6b6ece29d991\"}}', '0', null, '2024-06-04 13:40:39');
INSERT INTO `sys_oper_log` VALUES ('d5eeb4df-1a66-4903-91a8-c1dc60cd1c11', '素材库', '1', 'com.edu.web.controller.knowledge.MaterialLibraryController.add()', 'POST', '1', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', null, '/learn/lesson', '144.52.176.61', 'XX XX', '{\"categoryId\":\"ccb1f245-9104-4ae2-8c22-87fb3b318212\",\"categoryTitle\":\"专业技能\",\"createTime\":\"2024-06-04 14:12:22\",\"fileType\":\"mp4\",\"jobId\":\"b079a3b9d88a461eabb00be224a67a70\",\"length\":456,\"name\":\"薪酬的定义和功能.mp4\",\"object\":\"5d70aa10-b5af-4092-b968-d374dd133269/lesson/video/QfKCPynHEX1717481517320.mp4\",\"relevancy\":\"yes\",\"size\":57686213,\"title\":\"薪酬的定义和功能\",\"transCodeStatus\":1,\"type\":\"video\",\"updateTime\":\"2024-06-04 14:12:22\",\"useRange\":\"default\",\"uuid\":\"c7867fe8-9f6e-4672-abb5-1d02efba7c5c\"}', '{\"msg\":\"操作成功\",\"temp\":\"j\",\"code\":200,\"data\":{\"categoryId\":\"ccb1f245-9104-4ae2-8c22-87fb3b318212\",\"categoryTitle\":\"专业技能\",\"createTime\":\"2024-06-04 14:12:22\",\"isDelete\":0,\"jobId\":\"b079a3b9d88a461eabb00be224a67a70\",\"lectorId\":\"\",\"length\":456,\"name\":\"薪酬的定义和功能.mp4\",\"object\":\"5d70aa10-b5af-4092-b968-d374dd133269/lesson/video/QfKCPynHEX1717481517320.mp4\",\"relevancy\":\"yes\",\"size\":57686213,\"status\":0,\"title\":\"薪酬的定义和功能\",\"transCodeStatus\":1,\"type\":\"video\",\"updateTime\":\"2024-06-04 14:12:22\",\"useRange\":\"default\",\"uuid\":\"c7867fe8-9f6e-4672-abb5-1d02efba7c5c\"}}', '0', null, '2024-06-04 14:12:23');
INSERT INTO `sys_oper_log` VALUES ('e89e40c0-969f-49f4-b793-17187ce7c69f', '课程主表', '1', 'com.edu.web.controller.knowledge.KnowledgeController.add()', 'POST', '1', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', null, '/knowledge/knowledge', '144.52.176.61', 'XX XX', '{\"action\":\"update\",\"categoryId\":\"ccb1f245-9104-4ae2-8c22-87fb3b318212\",\"categoryTitle\":\"专业技能\",\"createTime\":\"2024-06-04 14:16:37\",\"credit\":0,\"drag\":\"close\",\"eligible\":\"all\",\"learnMode\":\"1\",\"learnProgress\":0,\"picture\":\"5d70aa10-b5af-4092-b968-d374dd133269/course/image/wAEepSJaB51715765849083.png\",\"relevancy\":\"yes\",\"speed\":\"close\",\"syncLearn\":\"close\",\"title\":\"多种课件学习\",\"uuid\":\"2f8be339-c8ec-48bd-8a16-3f89efe4cd2c\"}', '{\"msg\":\"操作成功\",\"temp\":\"j\",\"code\":200,\"data\":{\"categoryId\":\"ccb1f245-9104-4ae2-8c22-87fb3b318212\",\"categoryTitle\":\"专业技能\",\"cdnPicture\":\"https://chuanzhiedu.oss-cn-hangzhou.aliyuncs.com/5d70aa10-b5af-4092-b968-d374dd133269/course/image/wAEepSJaB51715765849083.png\",\"certificate\":\"\",\"coursewareNum\":0,\"createTime\":\"2024-06-04 14:16:37\",\"credit\":0,\"drag\":\"close\",\"duration\":0,\"eligible\":\"all\",\"learnMode\":\"1\",\"learnProgress\":0,\"lectorId\":\"\",\"model\":\"1\",\"picture\":\"5d70aa10-b5af-4092-b968-d374dd133269/course/image/wAEepSJaB51715765849083.png\",\"relevancy\":\"yes\",\"speed\":\"close\",\"status\":0,\"syncLearn\":\"close\",\"title\":\"多种课件学习\",\"updateTime\":\"1970-01-01 00:00:00\",\"uuid\":\"2f8be339-c8ec-48bd-8a16-3f89efe4cd2c\"}}', '0', null, '2024-06-04 14:16:37');
INSERT INTO `sys_oper_log` VALUES ('f8928adc-b9da-4dde-9bfe-84c67738ef20', '课程发布', '2', 'com.edu.web.controller.knowledge.KnowledgeController.publish()', 'GET', '1', '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', null, '/knowledge/knowledge/publish/2f8be339-c8ec-48bd-8a16-3f89efe4cd2c', '144.52.176.61', 'XX XX', '{uuid=2f8be339-c8ec-48bd-8a16-3f89efe4cd2c}', '{\"msg\":\"操作成功\",\"temp\":\"j\",\"code\":200}', '0', null, '2024-06-04 14:16:54');

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '岗位编码',
  `post_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '岗位名称',
  `group_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '岗位组',
  `post_sort` int NOT NULL DEFAULT '50' COMMENT '显示顺序',
  `duty` varchar(1500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '岗位职责',
  `demand` varchar(1500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '岗位要求',
  `amount` int DEFAULT '0' COMMENT '人数',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '状态（0正常 1删除）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `update_user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `company_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '公司id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='岗位信息表';

-- ----------------------------
-- Records of sys_post
-- ----------------------------

-- ----------------------------
-- Table structure for sys_post_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_post_group`;
CREATE TABLE `sys_post_group` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '递增ID',
  `uuid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '唯一ID',
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '分类标题',
  `is_delete` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
  `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  `create_user_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建者用户ID',
  `update_user_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '更新者用户ID',
  `company_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '公司ID',
  `company_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '公司名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_uuid_comany_id` (`company_id`,`uuid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of sys_post_group
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `role_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '角色权限字符串',
  `role_sort` int DEFAULT '0' COMMENT '显示顺序',
  `data_scope` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) DEFAULT '1' COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) DEFAULT '1' COMMENT '部门树选择项是否关联显示',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '角色状态（0正常 1停用）',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `company_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '公司id',
  `company_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '公司名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=171 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色信息表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('168', '0cd789a0-1187-4c62-901d-a2801794e27a', '主管理员', 'admin', '1', '1', '1', '1', '0', '0', '', '2024-05-10 09:26:09', '', '2024-05-10 09:26:09', null, '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `sys_role` VALUES ('169', '19656797-182a-40dd-a22f-df2e8cad2e72', '部门负责人', 'dept', '1', '1', '1', '1', '0', '0', '', '2024-05-10 09:26:09', '', '2024-05-27 08:37:10', null, '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `sys_role` VALUES ('170', 'fd62f233-f7c2-4d76-bb20-0866b3f0eeb3', '部门组长', 'group', '1', '1', '1', '1', '0', '0', '', '2024-05-10 09:26:09', '', '2024-05-27 08:37:15', null, '5d70aa10-b5af-4092-b968-d374dd133269', '');

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `role_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '角色ID',
  `dept_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '部门ID',
  `company_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '公司id',
  `company_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '公司名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色和部门关联表';

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `role_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '角色ID',
  `menu_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单ID',
  `company_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '公司id',
  `company_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '公司名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=324 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色和菜单关联表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('314', '19656797-182a-40dd-a22f-df2e8cad2e72', 'b947272f-ae4a-47d6-bbbd-d0eea5924275', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `sys_role_menu` VALUES ('315', '19656797-182a-40dd-a22f-df2e8cad2e72', 'e25477ed-fc39-47dd-a79f-70f7c5f3d756', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `sys_role_menu` VALUES ('316', '19656797-182a-40dd-a22f-df2e8cad2e72', '9b98a323-a855-46e3-94de-0c9dfbce46d2', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `sys_role_menu` VALUES ('317', '19656797-182a-40dd-a22f-df2e8cad2e72', 'c2bfafd5-a60c-4843-86d9-134da5e4d15e', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `sys_role_menu` VALUES ('318', 'fd62f233-f7c2-4d76-bb20-0866b3f0eeb3', '1', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `sys_role_menu` VALUES ('319', 'fd62f233-f7c2-4d76-bb20-0866b3f0eeb3', '7e6eee32-e800-4c1a-86aa-b135266cc4df', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `sys_role_menu` VALUES ('320', 'fd62f233-f7c2-4d76-bb20-0866b3f0eeb3', '0d3593f0-a691-4e13-875d-cf58098a9bf0', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `sys_role_menu` VALUES ('321', 'fd62f233-f7c2-4d76-bb20-0866b3f0eeb3', '753219a0-30a6-4059-b28d-88bfabe2cbfc', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `sys_role_menu` VALUES ('322', 'fd62f233-f7c2-4d76-bb20-0866b3f0eeb3', '101', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `sys_role_menu` VALUES ('323', 'fd62f233-f7c2-4d76-bb20-0866b3f0eeb3', '102', '5d70aa10-b5af-4092-b968-d374dd133269', '');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `union_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '企业账号范围内的唯一标识',
  `dept_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '部门ID',
  `user_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户账号',
  `nick_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户昵称',
  `dept_ids` varchar(2500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '所属部门id',
  `dept_names` varchar(2500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '所属部门名称',
  `user_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '手机号码',
  `position` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '职位',
  `job_number` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '工号',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `is_master` tinyint(1) DEFAULT '0' COMMENT '是否是主管理员0不是1是',
  `is_leave` tinyint(1) DEFAULT '0' COMMENT '1离职',
  `is_new` tinyint(1) DEFAULT '0' COMMENT '1是新员工',
  `new_time` datetime DEFAULT '1970-01-01 00:00:00' COMMENT '新员工有效期',
  `avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '密码',
  `status` tinyint(1) DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `company_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '公司id',
  `company_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '公司名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `companyuser` (`company_id`,`user_id`,`dept_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=703 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户信息表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('694', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', '', '63a7c0e0-9238-454c-85d2-17c5b91b2f09', 'admin', '张三', '1,07d6164d-0d7f-46e7-b4db-673c4d038b14', '自建企业web版,市场部', '00', '', '', null, null, '0', '0', '0', '0', '1970-01-01 00:00:00', 'https://static-legacy.dingtalk.com/media/lQDPD4AHiuoBNVHNAbDNAbCwL1Jv_5sJwkIF3dZl41ZcAA_432_432.jpg', '$2a$10$hpuc8X1MAiCFVKyxnFZHr..lFbGF2q4alghPtTtGEoBMyy0cavK.C', '1', '0', '', null, '', '2024-05-11 16:01:02', '', '2024-05-26 17:01:59', null, '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `sys_user` VALUES ('699', 'e5c35111-4adf-4c7c-8338-748c52312779', '', '180b70fc-8def-4771-8ce5-420c93b30029', 'hahah', '测试新部门', '', '', '00', '', '', '', '', '0', '0', '0', '0', '1970-01-01 00:00:00', '', '$2a$10$CHCWqqFCfkOzzasW3xzCGu5xvvfOj5GXmcjTxJt9zlgWtxhU6i3tW', '0', '0', '', null, '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', '2024-05-27 08:36:24', '', '1970-01-01 00:00:00', null, '', '');
INSERT INTO `sys_user` VALUES ('700', 'c83c5796-2cab-44e1-8822-d0a15cc74e3a', '', '180b70fc-8def-4771-8ce5-420c93b30029', 'adminbacd', '张三', '', '', '00', '', '', '', '', '0', '0', '0', '0', '1970-01-01 00:00:00', '', '$2a$10$yk./h7TH0qb1p3JKb3W2UeRNeAU7RNniueKBOEgkRN.EsWuvRftoq', '0', '0', '', null, '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', '2024-05-27 09:55:21', '', '1970-01-01 00:00:00', null, '', '');
INSERT INTO `sys_user` VALUES ('701', '66bc6e89-2de6-404e-8bc0-11cb3f4bffcd', '', '180b70fc-8def-4771-8ce5-420c93b30029', 'bacnms', 'uuu', '1,63a7c0e0-9238-454c-85d2-17c5b91b2f09,2e3bd226-ed99-4e67-8022-87228b9619a6,180b70fc-8def-4771-8ce5-420c93b30029', '自建企业web版,产品部,运营部,销售部1122', '00', '', '', '', '', '0', '0', '0', '0', '1970-01-01 00:00:00', '', '$2a$10$9Io8.27ZVwcggQYsiLvy8usoif70heMjihfzoFGGld7L4O0wDzFyC', '1', '0', '', null, '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', '2024-05-27 10:08:42', '', '1970-01-01 00:00:00', null, '', '');
INSERT INTO `sys_user` VALUES ('702', '7c9c5779-af94-4c4e-be2f-39bf9afe0a45', '', '180b70fc-8def-4771-8ce5-420c93b30029', 'wukong', '寻悟空', '1,63a7c0e0-9238-454c-85d2-17c5b91b2f09,2e3bd226-ed99-4e67-8022-87228b9619a6,180b70fc-8def-4771-8ce5-420c93b30029', '自建企业web版,产品部,运营部,销售部1122', '00', '', '', '', '', '0', '0', '0', '0', '1970-01-01 00:00:00', '', '$2a$10$GClaVFqbrF0xaI3Sx9rcU.bEwZLRu21QVm44D4kh4oZrI1yVNx2.e', '1', '0', '', null, '$userName=fa36ec75-160a-47e4-8140-ef1e94c5d329$', '2024-05-27 10:11:25', '', '1970-01-01 00:00:00', null, '5d70aa10-b5af-4092-b968-d374dd133269', '');

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户ID',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '人员名称',
  `post_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '岗位ID',
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'all' COMMENT 'part兼职',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '1删除',
  `company_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '公司id',
  `company_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '公司名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_post_company` (`company_id`,`post_id`,`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户与岗位关联表';

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户ID',
  `role_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '角色ID',
  `role_type` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '角色类型',
  `scope_type` tinyint(1) DEFAULT '1' COMMENT '管理范围类型：1全公司，2本部门及下级部门，3指定部门，4继承角色管理范围',
  `scope_ids` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '权限范围',
  `scope_names` varchar(3000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '权限范围',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '1解授权',
  `update_time` datetime DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间',
  `company_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '公司id',
  `company_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '公司名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_company_role_user` (`company_id`,`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=142 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户和角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('130', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', '0cd789a0-1187-4c62-901d-a2801794e27a', 'admin', '1', '', '', '0', '1970-01-01 00:00:00', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `sys_user_role` VALUES ('131', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', '19656797-182a-40dd-a22f-df2e8cad2e72', 'dept', '2', '1,63a7c0e0-9238-454c-85d2-17c5b91b2f09,2e3bd226-ed99-4e67-8022-87228b9619a6,180b70fc-8def-4771-8ce5-420c93b30029,07d6164d-0d7f-46e7-b4db-673c4d038b14', '', '0', '2024-05-27 08:38:08', '5d70aa10-b5af-4092-b968-d374dd133269', '');
INSERT INTO `sys_user_role` VALUES ('138', 'fa36ec75-160a-47e4-8140-ef1e94c5d329', 'fd62f233-f7c2-4d76-bb20-0866b3f0eeb3', 'group', '1', '', '', '0', '1970-01-01 00:00:00', '5d70aa10-b5af-4092-b968-d374dd133269', '');
