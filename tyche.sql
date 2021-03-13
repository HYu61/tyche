DROP DATABASE IF EXISTS `tyche`;
CREATE DATABASE `tyche`;
USE `tyche`;

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for bgm
-- ----------------------------
DROP TABLE IF EXISTS `bgm`;
CREATE TABLE `bgm` (
	`id` VARCHAR(64) NOT NULL,
	`author` VARCHAR(50) NOT NULL,
	`name` VARCHAR(50) NOT NULL,
	`path` VARCHAR(255) NOT NULL COMMENT 'the path of the background music',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='table of background music';



-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments` (
	`id` VARCHAR(64) NOT NULL,
	`parenet_comment_id` VARCHAR(64) DEFAULT NULL,
	`to_user_id` VARCHAR(64) DEFAULT NULL,
	`video_id` VARCHAR(64) NOT NULL,
	`from_user_id` VARCHAR(64) NOT NULL COMMENT 'the commenter',
	`comment` TEXT NOT NULL,
	`create_time` DATETIME NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- ----------------------------
-- Table structure for search_records
-- ----------------------------
DROP TABLE IF EXISTS `search_records`;
CREATE TABLE `search_records` (
	`id` VARCHAR(64) NOT NULL,
	`content` VARCHAR(50) NOT NULL COMMENT 'the content that need to be searched',
	`create_time` DATETIME NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='the table of record for the video searched';


-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
	`id` VARCHAR(64) NOT NULL,
	`username` VARCHAR(20) NOT NULL,
	`password` VARCHAR(256) NOT NULL,
	`profile_photo` VARCHAR(255) DEFAULT NULL COMMENT 'if the user does not upload the profile, provide one by default',
	`nickname` VARCHAR(20) NOT NULL,
	`follower_counts` INT(11) DEFAULT '0' COMMENT 'the num of how many followers this user has', 
	`follow_counts` INT(11) DEFAULT '0' COMMENT 'the num of people that the user have followed',
	`received_like_counts` INT(11) DEFAULT '0',
	`status` TINYINT(1) NOT NULL DEFAULT '0' COMMENT 'the status of the user: -1、inactive, 0、active, 1 vip_user',
	`security_answer` VARCHAR(20) NOT NULL COMMENT 'the answer for reset password',
	PRIMARY KEY (`id`),
	UNIQUE KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- ----------------------------
-- Table structure for users_followers
-- ----------------------------
DROP TABLE IF EXISTS `users_followers`;
CREATE TABLE `users_followers` (
	`id` VARCHAR(64) NOT NULL,
	`user_id` VARCHAR(64) NOT NULL COMMENT 'the user posted the videos',
	`follower_id` VARCHAR(64) NOT NULL COMMENT 'the follower',
	PRIMARY KEY (`id`),
	UNIQUE KEY `user_id` (`user_id`, `follower_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='the relation table of the users and the followers';


-- ----------------------------
-- Table structure for users_like_videos
-- ----------------------------
DROP TABLE IF EXISTS `users_liked_videos`;
CREATE TABLE `users_liked_videos` (
	`id` VARCHAR(64) NOT NULL,
	`user_id` VARCHAR(64) NOT NULL,
	`video_id` VARCHAR(64) NOT NULL,
	PRIMARY KEY (`id`),
	UNIQUE KEY `user_like_video_rel` (`user_id`, `video_id`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- ----------------------------
-- Table structure for users_report
-- ----------------------------
DROP TABLE IF EXISTS `users_report`;
CREATE TABLE `users_report` (
	`id` VARCHAR(64) NOT NULL,
	`reported_user_id` VARCHAR(64) NOT NULL COMMENT 'the user who uploaded the video that may contain illegal content',
	`reported_video_id` VARCHAR(64) NOT NULL COMMENT 'the video that contains illegal content',
	`title` VARCHAR(128) NOT NULL COMMENT 'the title of the illegal type; the user will choose from the enum',
	`content` VARCHAR(255) DEFAULT NULL COMMENT 'the detail of the report',
	`user_id` VARCHAR(64) NOT NULL COMMENT 'the user who report this illegal video',
	`create_time` DATETIME NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for videos
-- ----------------------------
DROP TABLE IF EXISTS `videos`;
CREATE TABLE `videos` (
	`id` VARCHAR(64) NOT NULL,
  	`user_id` VARCHAR(64) NOT NULL COMMENT 'the user who upload the video',
  	`bmg_id` VARCHAR(64) DEFAULT NULL COMMENT 'the background music that added by the user',
  	`video_desc` VARCHAR(128) DEFAULT NULL,
  	`video_path` VARCHAR(255) NOT NULL COMMENT 'the path of the video stored',
  	`video_seconds` Float(6,2) DEFAULT NULL,
  	`video_width` INT(6) DEFAULT NULL,
  	`video_height` INT(6) DEFAULT NULL,
  	`cover_path` VARCHAR(255) DEFAULT NULL COMMENT 'the path of the cover of the video',
  	`like_counts` INT(11) NOT NULL DEFAULT '0',
  	`status` TINYINT(1) NOT NULL DEFAULT '0' COMMENT 'the status of the video: -1、forbidden, 0、active, 1 for vip_video that only vip user can access',
  	`create_time` DATETIME NOT NULL,
  	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- ------------------------------------
-- Table structure for management users
-- ------------------------------------
DROP TABLE IF EXISTS `admins`;
CREATE TABLE `admins` (
  	`id` VARCHAR(64) NOT NULL COMMENT 'the management user',
  	`username` VARCHAR(20) NOT NULL,
	`password` VARCHAR(256) NOT NULL,
  	`status` TINYINT(1) NOT NULL DEFAULT '0' COMMENT 'the status of the admin: 0、forbidden, 1、active, 2 for limited function',
  	PRIMARY KEY (`id`),
	UNIQUE KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
