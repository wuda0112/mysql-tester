/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE IF NOT EXISTS `foundation_commons`;
USE `foundation_commons`;

CREATE TABLE IF NOT EXISTS `email` (
                                     `email_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                     `address` varchar(20) NOT NULL COMMENT 'email address',
                                     `state` tinyint(3) unsigned NOT NULL COMMENT '邮箱状态。1比如：验证不通过，验证通过，未验证',
                                     `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                     `create_user_id` bigint(20) unsigned NOT NULL,
                                     `last_modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                     `last_modify_user_id` bigint(20) unsigned NOT NULL,
                                     `is_deleted` bigint(20) unsigned NOT NULL DEFAULT '0',
                                     PRIMARY KEY (`email_id`),
                                     UNIQUE KEY `idx_email_unique` (`address`,`is_deleted`)
) ENGINE=InnoDB AUTO_INCREMENT=314876056249048299 COMMENT='email信息';

CREATE TABLE IF NOT EXISTS `phone` (
                                     `phone_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                     `number` varchar(20) NOT NULL COMMENT '电话号码',
                                     `type` tinyint(3) unsigned NOT NULL COMMENT '电话类型。用于区分手机，-固话等。注意：不是用来区分【客服电话还是400电话】这种类型，这些属于业务，应该由具体的业务表关联到这个表。',
                                     `state` tinyint(3) unsigned NOT NULL COMMENT '手机状态',
                                     `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                     `create_user_id` bigint(20) unsigned NOT NULL,
                                     `last_modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                     `last_modify_user_id` bigint(20) unsigned NOT NULL,
                                     `is_deleted` bigint(20) unsigned NOT NULL DEFAULT '0',
                                     PRIMARY KEY (`phone_id`),
                                     UNIQUE KEY `idx_phone_number` (`number`,`is_deleted`)
) ENGINE=InnoDB AUTO_INCREMENT=314875826682205361 COMMENT='电话信息。';

CREATE TABLE IF NOT EXISTS `property_key` (
                                            `property_key_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                            `key` varchar(45) NOT NULL COMMENT '属性名',
                                            `type` tinyint(3) unsigned NOT NULL COMMENT '属性的类型，比如最常用的就是“字面量”类型；比如该属性表示图片，属性值保存图片的链接；比如该属性表示颜色，因为在一些应用中，可以使用调色盘选取颜色，或者在显示时，可以显示颜色，而不是白色这样的纯文本',
                                            `owner_type` tinyint(3) unsigned NOT NULL COMMENT '该属性的owner的类型',
                                            `owner_identifier` bigint(20) unsigned NOT NULL COMMENT '该属性的owner的id',
                                            `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                            `create_user_id` bigint(20) unsigned NOT NULL,
                                            `last_modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                            `last_modify_user_id` bigint(20) unsigned NOT NULL,
                                            `is_deleted` bigint(20) unsigned NOT NULL DEFAULT '0',
                                            PRIMARY KEY (`property_key_id`),
                                            UNIQUE KEY `idx_unique` (`owner_identifier`,`owner_type`,`key`,`is_deleted`)
) ENGINE=InnoDB AUTO_INCREMENT=314874986349204186 COMMENT='属性的key';

CREATE TABLE IF NOT EXISTS `property_value` (
                                              `property_value_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                              `property_key_id` bigint(20) unsigned NOT NULL COMMENT '所属的key',
                                              `value` varchar(45) NOT NULL COMMENT '属性值',
                                              `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                              `create_user_id` bigint(20) unsigned NOT NULL,
                                              `last_modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                              `last_modify_user_id` bigint(20) unsigned NOT NULL,
                                              `is_deleted` bigint(20) unsigned NOT NULL DEFAULT '0',
                                              PRIMARY KEY (`property_value_id`),
                                              UNIQUE KEY `idx_attr_value` (`property_key_id`,`value`,`is_deleted`)
) ENGINE=InnoDB AUTO_INCREMENT=314874986353399087 COMMENT='属性的值';

CREATE DATABASE IF NOT EXISTS `foundation_item`;
USE `foundation_item`;

CREATE TABLE IF NOT EXISTS `item` (
                                    `item_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                    `store_id` bigint(20) unsigned NOT NULL COMMENT '所属店铺ID',
                                    `type` tinyint(3) unsigned NOT NULL COMMENT '商品类型 . 不同类型的商品, 保存到各自不同的表中. 参考 https://learnwoo.com/woocommerce-different-product-types/',
                                    `state` tinyint(3) unsigned NOT NULL COMMENT '状态',
                                    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    `create_user_id` bigint(20) unsigned NOT NULL,
                                    `last_modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                    `last_modify_user_id` bigint(20) unsigned NOT NULL,
                                    `is_deleted` bigint(20) unsigned NOT NULL DEFAULT '0',
                                    PRIMARY KEY (`item_id`),
                                    KEY `fk_store_id` (`store_id`)
) ENGINE=InnoDB AUTO_INCREMENT=314874986730886848 COMMENT='代表所有的物品，之前有把用户ID放进来，表示该物品所属的用户，但是考虑到如果有子账号的情况，物品难道属于这个子账号所属的用户吗？而且记录了创建人用户ID，考虑这两个因素，因此不设置用户ID列';

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
) ENGINE=InnoDB AUTO_INCREMENT=314874986345009285 COMMENT='item的描述信息，通常作为详情的一个字段，但是，由于描述信息通常内容较多，很多orm框架都是select *，分开了可以避免查询出来（有时候根本就没用到），而且大数据量的字段更新性能较差，如果需要更新，会影响到核心item表，因此单独作为一个表保存。也可以表示物品某个规格的描述信息，如果variation id不等于0';

CREATE TABLE IF NOT EXISTS `item_general` (
                                            `item_general_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                            `item_id` bigint(20) unsigned NOT NULL,
                                            `name` varchar(45) NOT NULL,
                                            `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                            `create_user_id` bigint(20) unsigned NOT NULL,
                                            `last_modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                            `last_modify_user_id` bigint(20) unsigned NOT NULL,
                                            `is_deleted` bigint(20) unsigned NOT NULL DEFAULT '0',
                                            PRIMARY KEY (`item_general_id`),
                                            KEY `fk_item_id` (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=314874986571504463 COMMENT='物品基本信息，也可以表示物品某个规格的基本信息，如果variation id不等于0';

CREATE TABLE IF NOT EXISTS `item_variation` (
                                              `item_variation_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                              `item_id` bigint(20) unsigned NOT NULL,
                                              `name` varchar(45) NOT NULL DEFAULT '' COMMENT '规格名称。方便管理。',
                                              `state` tinyint(3) unsigned NOT NULL COMMENT '状态',
                                              `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                              `create_user_id` bigint(20) unsigned NOT NULL,
                                              `last_modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                              `last_modify_user_id` bigint(20) unsigned NOT NULL,
                                              `is_deleted` bigint(20) unsigned NOT NULL DEFAULT '0',
                                              PRIMARY KEY (`item_variation_id`),
                                              KEY `fk_item_id` (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=314874986575699859 COMMENT='规格。比如一件衣服，有红色，白色两种规格。具体的属性和值保存在MongoDB. 不能用属性ID关联, 而是要具体的属性名称和值, 避免关联的属性修改. 注意和SKU之间的区别.';

CREATE DATABASE IF NOT EXISTS `foundation_store`;
USE `foundation_store`;

CREATE TABLE IF NOT EXISTS `store` (
                                     `store_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                     `type` tinyint(3) unsigned NOT NULL COMMENT '店铺类型',
                                     `state` tinyint(3) unsigned NOT NULL COMMENT '店铺状态',
                                     `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                     `create_user_id` bigint(20) unsigned NOT NULL,
                                     `last_modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                     `last_modify_user_id` bigint(20) unsigned NOT NULL,
                                     `is_deleted` bigint(20) unsigned NOT NULL DEFAULT '0',
                                     PRIMARY KEY (`store_id`)
) ENGINE=InnoDB AUTO_INCREMENT=314875818503314715 COMMENT='店铺信息';

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
) ENGINE=InnoDB AUTO_INCREMENT=314875810760626565 COMMENT='店铺基本信息';

CREATE TABLE IF NOT EXISTS `store_user_relationship` (
                                                       `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                                       `store_id` bigint(20) unsigned NOT NULL COMMENT 'store id',
                                                       `user_id` bigint(20) unsigned NOT NULL COMMENT 'user id',
                                                       `is_store_owner` bit(1) NOT NULL COMMENT '该用户是否店铺的owner。一个店铺只能有一个owner，就好像在store表中放入user id字段，表明一对一的关系一样。',
                                                       `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                                       `create_user_id` bigint(20) unsigned NOT NULL,
                                                       `is_deleted` bigint(20) unsigned NOT NULL DEFAULT '0',
                                                       PRIMARY KEY (`id`),
                                                       KEY `fk_store_id` (`store_id`),
                                                       KEY `fk_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=314875816137724979 COMMENT='如果把用户ID字段放在store表中，表明店铺属于某个用户，但是如果有多个用户可以管理这个店铺呢？有种做法是一个用户作为另一个用户的子账号；也可以建立用户与店铺的关联关系，这样感觉更符合逻辑。把用户IID放在store表，可以很明确的表明店铺的owner，如果是用关联关系表的话，就需要明确的标明哪个用户是owner，哪些用户只是管理这个店铺的。';

CREATE DATABASE IF NOT EXISTS `foundation_user` ;
USE `foundation_user`;

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
                                                       PRIMARY KEY (`individual_user_general_id`),
                                                       KEY `fk_user_id` (`user_id`)
) ENGINE=InnoDB  COMMENT='个人用户－基本信息';

CREATE TABLE IF NOT EXISTS `user` (
                                    `user_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                    `type` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '用户类型',
                                    `state` tinyint(3) unsigned NOT NULL COMMENT '用户状态',
                                    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    `create_user_id` bigint(20) unsigned NOT NULL,
                                    `last_modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                    `last_modify_user_id` bigint(20) unsigned NOT NULL,
                                    `is_deleted` bigint(20) unsigned NOT NULL DEFAULT '0',
                                    PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=314876148024612889 COMMENT='用户有很多类型，比如一种分类方法是把用户分成个人用户和企业用户，而不同类型的用户需要的字段不一样，但是他们都是用户，即 is-a user。这个表属于所有用户的基本信息，其他不同类型的用户有自己专属的表，然后用用户ID关联回这个表。这样做还有一个好处，那就是其他表中的用户ID都统一关联回这个表，这样用户ID就不会有歧义了。';

CREATE TABLE IF NOT EXISTS `user_account` (
                                            `user_account_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                            `user_id` bigint(20) unsigned NOT NULL COMMENT '用户ID',
                                            `username` varchar(45) NOT NULL COMMENT '只能是英文模式下的字母，数字，下划线，中划线，必须明确检查保证不是邮箱。设置以后不能修改(github可以修改)，可用作用户主页URL的一部分，参考github。注意和昵称的区别',
                                            `password` varchar(45) NOT NULL COMMENT '只能是ASCII表中的可打印字符',
                                            `state` tinyint(3) unsigned NOT NULL COMMENT '账号的状态',
                                            `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                            `create_user_id` bigint(20) unsigned NOT NULL,
                                            `last_modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                            `last_modify_user_id` bigint(20) unsigned NOT NULL,
                                            `is_deleted` bigint(20) unsigned NOT NULL DEFAULT '0',
                                            PRIMARY KEY (`user_account_id`),
                                            UNIQUE KEY `idx_username` (`username`,`is_deleted`)
) ENGINE=InnoDB AUTO_INCREMENT=314876128948918487 COMMENT='用户账号信息，适用各种类型的用户';

CREATE TABLE IF NOT EXISTS `user_email` (
                                          `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                          `user_id` bigint(20) unsigned NOT NULL,
                                          `email_id` bigint(20) unsigned NOT NULL,
                                          `use` tinyint(3) unsigned NOT NULL COMMENT '用途。比如用于登录',
                                          `state` tinyint(3) unsigned NOT NULL COMMENT '状态，每种用途的email状态可能不同，比如如果用于登录的email，状态可以是禁止登录状态',
                                          `description` varchar(45) DEFAULT NULL COMMENT '简单描述',
                                          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                          `create_user_id` bigint(20) unsigned NOT NULL,
                                          `last_modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                          `last_modify_user_id` bigint(20) unsigned NOT NULL,
                                          `is_deleted` bigint(20) unsigned NOT NULL DEFAULT '0',
                                          PRIMARY KEY (`id`),
                                          UNIQUE KEY `idx_unique` (`email_id`,`use`,`is_deleted`),
                                          KEY `fk_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=314875924648564415 COMMENT='用户的email';

CREATE TABLE IF NOT EXISTS `user_phone` (
                                          `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                          `user_id` bigint(20) unsigned NOT NULL,
                                          `phone_id` bigint(20) unsigned NOT NULL,
                                          `use` tinyint(3) unsigned NOT NULL COMMENT '电话的用途。比如用于400电话。也就是电话使用的业务场景。',
                                          `state` tinyint(3) unsigned NOT NULL COMMENT '状态，每种用途的phone的il状态可能不同，比如如果用于登录的phone，状态可以是禁止登录状态',
                                          `description` varchar(45) DEFAULT NULL COMMENT '简单描述',
                                          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                          `create_user_id` bigint(20) unsigned NOT NULL,
                                          `last_modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                          `last_modify_user_id` bigint(20) unsigned NOT NULL,
                                          `is_deleted` bigint(20) unsigned NOT NULL DEFAULT '0',
                                          PRIMARY KEY (`id`),
                                          UNIQUE KEY `idx_unique` (`phone_id`,`use`,`is_deleted`),
                                          KEY `fk_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=314875826682205861 COMMENT='用户的电话';

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;