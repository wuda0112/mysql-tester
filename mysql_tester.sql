/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE IF NOT EXISTS `mysql_tester` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `mysql_tester`;

CREATE TABLE IF NOT EXISTS `individual_user_general` (
                                                       `individual_user_general_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                                       `user_id` bigint(20) unsigned NOT NULL,
                                                       `nickname` varchar(20) DEFAULT NULL COMMENT '昵称',
                                                       `biography` varchar(128) DEFAULT NULL COMMENT '个人简介。简短的介绍',
                                                       `picture` varchar(45) DEFAULT NULL COMMENT '用户图像。保存的是图片地址。命名来源：github',
                                                       `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                                       `create_user_id` bigint(20) unsigned NOT NULL,
                                                       `last_modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                                       `last_modify_user_id` bigint(20) unsigned NOT NULL,
                                                       `is_deleted` bigint(20) unsigned NOT NULL DEFAULT '0',
                                                       PRIMARY KEY (`individual_user_general_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='个人用户－基本信息';

CREATE TABLE IF NOT EXISTS `item` (
                                    `item_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                    `store_id` bigint(20) unsigned NOT NULL COMMENT '所属店铺ID',
                                    `type` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '商品类型 . 不同类型的商品, 保存到各自不同的表中.',
                                    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    `create_user_id` bigint(20) unsigned NOT NULL,
                                    `last_modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                    `last_modify_user_id` bigint(20) unsigned NOT NULL,
                                    `is_deleted` bigint(20) unsigned NOT NULL DEFAULT '0',
                                    PRIMARY KEY (`item_id`),
                                    KEY `fk_store_id` (`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='代表所有的物品';

CREATE TABLE IF NOT EXISTS `item_description` (
                                                `item_description_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                                `item_id` bigint(20) unsigned NOT NULL,
                                                `item_variation_id` bigint(20) unsigned NOT NULL DEFAULT '0',
                                                `content` mediumtext COMMENT '描述内容',
                                                `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                                `create_user_id` bigint(20) unsigned NOT NULL,
                                                `last_modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                                `last_modify_user_id` bigint(20) unsigned NOT NULL,
                                                `is_deleted` bigint(20) unsigned NOT NULL DEFAULT '0',
                                                PRIMARY KEY (`item_description_id`),
                                                KEY `fk_item_id` (`item_id`),
                                                KEY `fk_item_variation_id` (`item_variation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='item的描述信息，通常作为详情的一个字段，但是，由于描述信息通常内容较多，很多orm框架都是select *，分开了可以避免查询出来（有时候根本就没用到），而且大数据量的字段更新性能较差，如果需要更新，会影响到核心item表，因此单独作为一个表保存。也可以表示物品某个规格的描述信息，如果variation id不等于0';

CREATE TABLE IF NOT EXISTS `item_general` (
                                            `item_general_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                            `item_name` varchar(45) NOT NULL,
                                            `item_id` bigint(20) unsigned NOT NULL,
                                            `item_variation_id` bigint(20) unsigned NOT NULL DEFAULT '0',
                                            `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                            `create_user_id` bigint(20) unsigned NOT NULL,
                                            `last_modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                            `last_modify_user_id` bigint(20) unsigned NOT NULL,
                                            `is_deleted` bigint(20) unsigned NOT NULL DEFAULT '0',
                                            PRIMARY KEY (`item_general_id`),
                                            KEY `fk_item_id` (`item_id`),
                                            KEY `fk_item_variation_id` (`item_variation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='物品基本信息，也可以表示物品某个规格的基本信息，如果variation id不等于0';

CREATE TABLE IF NOT EXISTS `store` (
                                     `store_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                     `user_id` bigint(20) unsigned NOT NULL COMMENT '所属用户ID',
                                     `type` tinyint(3) unsigned NOT NULL COMMENT '店铺类型',
                                     `status` tinyint(3) unsigned NOT NULL,
                                     `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                     `create_user_id` bigint(20) unsigned NOT NULL,
                                     `last_modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                     `last_modify_user_id` bigint(20) unsigned NOT NULL,
                                     `is_deleted` bigint(20) unsigned NOT NULL DEFAULT '0',
                                     PRIMARY KEY (`store_id`),
                                     KEY `fk_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='店铺信息';

CREATE TABLE IF NOT EXISTS `store_general` (
                                             `store_general_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                             `store_id` bigint(20) unsigned NOT NULL COMMENT '主键',
                                             `store_name` varchar(45) NOT NULL COMMENT '店铺名称',
                                             `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                             `create_user_id` bigint(20) unsigned NOT NULL,
                                             `last_modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                             `last_modify_user_id` bigint(20) unsigned NOT NULL,
                                             `is_deleted` bigint(20) unsigned NOT NULL DEFAULT '0',
                                             PRIMARY KEY (`store_general_id`),
                                             KEY `fk_store_id` (`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='店铺基本信息';

CREATE TABLE IF NOT EXISTS `user` (
                                    `user_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                    `type` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '用户类型',
                                    `status` tinyint(3) unsigned NOT NULL COMMENT '用户状态',
                                    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    `create_user_id` bigint(20) unsigned NOT NULL,
                                    `last_modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                    `last_modify_user_id` bigint(20) unsigned NOT NULL,
                                    `is_deleted` bigint(20) unsigned NOT NULL DEFAULT '0',
                                    PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户有很多类型，比如一种分类方法是把用户分成个人用户和企业用户，而不同类型的用户需要的字段不一样，但是他们都是用户，即 is-a user。这个表属于所有用户的基本信息，其他不同类型的用户有自己专属的表，然后用用户ID关联回这个表。这样做还有一个好处，那就是其他表中的用户ID都统一关联回这个表，这样用户ID就不会有歧义了。';

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
