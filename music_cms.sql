CREATE DATABASE `music_cms` DEFAULT CHARACTER SET utf8;

USE `music_cms`;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- 用户表
-- ----------------------------

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
	`id` int(11) NOT NULL AUTO_INCREMENT ,
  `phoneNumber` varchar(64) NOT NULL,
  `userName` varchar(64) NOT NULL,
	`sex` int(1) NOT NULL,
	`age` int(5) NOT NULL,
  `password` varchar(255) NOT NULL,
  `userImage` varchar(255) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
	 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

INSERT INTO `user` VALUES ('1', '12345678910', 'admin', '0', '20', 'admin', '', '2020-3-08 09:30:22');
INSERT INTO `user` VALUES ('2', '12345678911', '张三', '1', '20', '123', '', '2020-3-08 09:30:22');
INSERT INTO `user` VALUES ('3', '12345678912', '李四', '0', '20', '123', '', '2020-3-08 09:30:22');


-- ----------------------------
-- 角色表
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rolesName` varchar(64) NOT NULL,
  `explanation` varchar(225) DEFAULT NULL,
	`createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

INSERT INTO `roles` VALUES ('1', '超级管理员', '超级管理员说明', '2020-3-08 09:30:22');
INSERT INTO `roles` VALUES ('2', '普通用户', '普通用户说明', '2020-3-08 09:30:22');
INSERT INTO `roles` VALUES ('3', '测试角色1', '测试角色1说明', '2020-3-08 09:30:22');


-- ----------------------------
-- 用户与角色关系表
-- ----------------------------
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `rid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `rid` (`rid`),
  KEY `user_roles_ibfk_2` (`uid`),
  CONSTRAINT `user_roles_ibfk_1` FOREIGN KEY (`rid`) REFERENCES `roles` (`id`) ON DELETE CASCADE,
  CONSTRAINT `user_roles_ibfk_2` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

INSERT INTO `user_roles` VALUES ('1', '1', '1');
INSERT INTO `user_roles` VALUES ('2', '1', '2');
INSERT INTO `user_roles` VALUES ('3', '2', '2');
INSERT INTO `user_roles` VALUES ('4', '2', '3');
INSERT INTO `user_roles` VALUES ('5', '3', '3');


-- ----------------------------
-- 权限表
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
	`authorityName` varchar(64) NOT NULL,
	`explanation` varchar(255) NOT NULL,
	`mark` varchar(64) NOT NULL,
	`type` varchar(64) NOT NULL,
	`icon` varchar(64) DEFAULT NULL,
	`superiorId` int(11) NOT NULL,
	`api` varchar(64) DEFAULT NULL,
	`createTime` datetime DEFAULT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

INSERT INTO `authority` VALUES ('1', '用户管理', '用户管理说明', 'Users', 'menu', 'my-icon-yonghuguanli', '0', '', '2020-3-08 09:30:22');

INSERT INTO `authority` VALUES ('2', '用户列表', '用户列表说明', 'User', 'menu', 'my-icon-yonghuliebiao', '1', 'API_USER_GET', '2020-3-08 09:30:22');
INSERT INTO `authority` VALUES ('3', '增加用户', '增加用户说明', 'addUser', 'view', '', '2', 'API_USER_POST', '2020-3-08 09:30:22');
INSERT INTO `authority` VALUES ('4', '编辑用户', '编辑用户说明', 'editUser', 'view', '','2', 'API_USER_PUT', '2020-3-08 09:30:22');
INSERT INTO `authority` VALUES ('5', '删除用户', '删除用户说明', 'deleteUser', 'view', '', '2', 'API_USER_DELETE', '2020-3-08 09:30:22');
INSERT INTO `authority` VALUES ('6', '分配角色', '分配角色说明', 'allotRoles', 'view', '', '2', 'API_USER_ROLES_POST', '2020-3-08 09:30:22');

INSERT INTO `authority` VALUES ('7', '权限管理', '权限管理说明', 'Authoritys', 'menu', 'my-icon-quanxianguanli', '0', '', '2020-3-08 09:30:22');

INSERT INTO `authority` VALUES ('8', '角色管理', '角色管理说明', 'Roles', 'menu', 'my-icon-jiaoseguanli', '7', 'API_ROLES_GET', '2020-3-08 09:30:22');

INSERT INTO `authority` VALUES ('9', '增加角色', '增加角色说明', 'addRoles', 'view', '', '8', 'API_ROLES_POST', '2020-3-08 09:30:22');
INSERT INTO `authority` VALUES ('10', '编辑角色', '编辑角色说明', 'editRoles', 'view', '', '8', 'API_ROLES_PUT', '2020-3-08 09:30:22');
INSERT INTO `authority` VALUES ('11', '删除角色', '删除角色说明', 'deleteRoles', 'view', '', '8', 'API_ROLES_DELETE', '2020-3-08 09:30:22');
INSERT INTO `authority` VALUES ('12', '分配权限', '分配权限说明', 'allotAuthority', 'view', '', '8', 'API_ROLES_AUTHORITY_POST', '2020-3-08 09:30:22');

INSERT INTO `authority` VALUES ('13', '权限管理', '权限管理说明', 'Authority', 'menu', 'my-icon-quanxian my-icon', '7', 'API_AUTHORITY_GET', '2020-3-08 09:30:22');

INSERT INTO `authority` VALUES ('14', '歌曲管理', '歌曲管理说明', 'Songs', 'menu', 'my-icon-dianyingzhiye-gequbangdianjitai', '0', '', '2020-3-08 09:30:22');

INSERT INTO `authority` VALUES ('15', '歌曲列表', '歌曲列表说明', 'Song', 'menu', 'my-icon-dianyingzhiye-gequbang', '14', 'API_SONG_GET', '2020-3-08 09:30:22');

INSERT INTO `authority` VALUES ('16', '添加歌曲', '添加歌曲说明', 'addSong', 'view', '', '15', 'API_SONG_POST', '2020-3-08 09:30:22');
INSERT INTO `authority` VALUES ('17', '编辑歌曲', '编辑歌曲说明', 'editSong', 'view', '', '15', 'API_SONG_PUT', '2020-3-08 09:30:22');
INSERT INTO `authority` VALUES ('18', '删除歌曲', '删除歌曲说明', 'deleteSong', 'view', '', '15', 'API_SONG_DELETE', '2020-3-08 09:30:22');

INSERT INTO `authority` VALUES ('19', '歌单列表', '歌单列表说明', 'songLists', 'menu', 'my-icon-gedan', '0', '', '2020-3-08 09:30:22');

INSERT INTO `authority` VALUES ('20', '歌单列表', '歌单列表说明', 'songList', 'menu', 'my-icon-gedan1', '19', 'API_SONGLIST_GET', '2020-3-08 09:30:22');

INSERT INTO `authority` VALUES ('21', '添加歌单', '添加歌单说明', 'addSongList', 'view', '', '20', 'API_SONGLIST_POST', '2020-3-08 09:30:22');
INSERT INTO `authority` VALUES ('22', '删除歌单', '添加歌单说明', 'deleteSongList', 'view', '', '20', 'API_SONGLIST_DELETE', '2020-3-08 09:30:22');
INSERT INTO `authority` VALUES ('23', '编辑歌单', '编辑歌曲说明', 'editSongList', 'view', '', '20', 'API_SONGLIST_PUT', '2020-3-08 09:30:22');


-- ----------------------------
-- 角色与权限关系表
-- ----------------------------
DROP TABLE IF EXISTS `roles_authority`;
CREATE TABLE `roles_authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rid` int(11) NOT NULL,
  `aid` int(11) NOT NULL,	
  PRIMARY KEY (`id`),
  KEY `aid` (`aid`),
  KEY `roles_authority_ibfk_2` (`rid`),
  CONSTRAINT `roles_authority_ibfk_1` FOREIGN KEY (`aid`) REFERENCES `authority` (`id`) ON DELETE CASCADE,
  CONSTRAINT `roles_authority_ibfk_2` FOREIGN KEY (`rid`) REFERENCES `roles` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

INSERT INTO `roles_authority` VALUES ('1', '1', '1');
INSERT INTO `roles_authority` VALUES ('2', '1', '2');
INSERT INTO `roles_authority` VALUES ('3', '1', '3');
INSERT INTO `roles_authority` VALUES ('4', '1', '4');
INSERT INTO `roles_authority` VALUES ('5', '1', '5');
INSERT INTO `roles_authority` VALUES ('6', '1', '6');
INSERT INTO `roles_authority` VALUES ('7', '1', '7');
INSERT INTO `roles_authority` VALUES ('8', '1', '8');
INSERT INTO `roles_authority` VALUES ('9', '1', '9');
INSERT INTO `roles_authority` VALUES ('10', '1', '10');
INSERT INTO `roles_authority` VALUES ('11', '1', '11');
INSERT INTO `roles_authority` VALUES ('12', '1', '12');
INSERT INTO `roles_authority` VALUES ('13', '1', '13');
INSERT INTO `roles_authority` VALUES ('14', '1', '14');
INSERT INTO `roles_authority` VALUES ('15', '1', '15');
INSERT INTO `roles_authority` VALUES ('16', '1', '16');
INSERT INTO `roles_authority` VALUES ('17', '1', '17');
INSERT INTO `roles_authority` VALUES ('18', '1', '18');
INSERT INTO `roles_authority` VALUES ('19', '1', '19');
INSERT INTO `roles_authority` VALUES ('20', '1', '20');
INSERT INTO `roles_authority` VALUES ('21', '1', '21');
INSERT INTO `roles_authority` VALUES ('22', '1', '22');
INSERT INTO `roles_authority` VALUES ('23', '1', '23');
INSERT INTO `roles_authority` VALUES ('24', '2', '1');
INSERT INTO `roles_authority` VALUES ('25', '2', '2');
INSERT INTO `roles_authority` VALUES ('26', '2', '3');
INSERT INTO `roles_authority` VALUES ('27', '2', '4');
INSERT INTO `roles_authority` VALUES ('28', '2', '5');
INSERT INTO `roles_authority` VALUES ('29', '2', '6');
INSERT INTO `roles_authority` VALUES ('30', '2', '7');
INSERT INTO `roles_authority` VALUES ('31', '2', '8');
INSERT INTO `roles_authority` VALUES ('32', '2', '9');
INSERT INTO `roles_authority` VALUES ('33', '2', '10');
INSERT INTO `roles_authority` VALUES ('34', '2', '11');
INSERT INTO `roles_authority` VALUES ('35', '2', '12');
INSERT INTO `roles_authority` VALUES ('36', '2', '13');


-- ----------------------------
-- 歌曲表
-- ----------------------------
DROP TABLE IF EXISTS `song`;
CREATE TABLE `song` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
	`songName` varchar(255) NOT NULL,
	`singer` varchar(64) NOT NULL,
	`album` varchar(64) NOT NULL,
	`songUrl` varchar(255) DEFAULT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


-- ----------------------------
-- 歌单表
-- ----------------------------
DROP TABLE IF EXISTS `songList`;
CREATE TABLE `songList` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
	`songListName` varchar(255) NOT NULL,
	`introduction` varchar(255) NOT NULL,
	`createTime` datetime DEFAULT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- 歌曲与歌单关系表
-- ----------------------------
DROP TABLE IF EXISTS `song_songList`;
CREATE TABLE `song_songList` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` int(11) NOT NULL,
  `lid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `lid` (`lid`),
  KEY `song_songList_ibfk_2` (`sid`),
  CONSTRAINT `song_songList_ibfk_1` FOREIGN KEY (`lid`) REFERENCES `songList` (`id`) ON DELETE CASCADE,
  CONSTRAINT `song_songList_ibfk_2` FOREIGN KEY (`sid`) REFERENCES `song` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS=1;