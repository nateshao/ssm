/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : mybatis

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2021-10-22 22:24:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_customer`
-- ----------------------------
DROP TABLE IF EXISTS `t_customer`;
CREATE TABLE `t_customer` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `jobs` varchar(50) DEFAULT NULL,
  `phone` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_customer
-- ----------------------------
INSERT INTO `t_customer` VALUES ('1', 'aaa', 'dada', '11111111');
INSERT INTO `t_customer` VALUES ('2', 'jack', 'teacher', '13521210112');
INSERT INTO `t_customer` VALUES ('3', 'worker', 'worker', '13311111234');
INSERT INTO `t_customer` VALUES ('4', 'zhangsan', 'maiyu', '10086');
INSERT INTO `t_customer` VALUES ('5', 'zhangsan', 'manager', '13233334444');
INSERT INTO `t_customer` VALUES ('6', 'zhangsan', 'manager', '13233334444');
