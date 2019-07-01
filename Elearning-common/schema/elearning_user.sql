CREATE SCHEMA `elearning_user`;

use elearning_user;

CREATE TABLE `user_info`
(
    `id`             int(11)     NOT NULL AUTO_INCREMENT,
    `name`           varchar(64) NOT NULL DEFAULT '',
    `gender`         tinyint(1)  NOT NULL DEFAULT '0' COMMENT '1代表男性 2代表女性',
    `age`            int(11)     NOT NULL,
    `telphone`       varchar(45) NOT NULL,
    `register_mode`  varchar(45) NOT NULL COMMENT 'byphone bywechat byalipay',
    `third_party_id` varchar(64) NOT NULL DEFAULT '',
    PRIMARY KEY (`id`),
    UNIQUE KEY `telphone_UNIQUE` (`telphone`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 1
    DEFAULT CHARSET = utf8mb4
    COMMENT '用户表基本信息表';


CREATE TABLE `user_password`
(
    `id`              int(11)      NOT NULL AUTO_INCREMENT,
    `encrypt_password` varchar(128) NOT NULL,
    `user_id`         int(11)      NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4
    COMMENT '用户密码权限表';

CREATE TABLE `user_publisher`
(
    `id`           int(11)     NOT NULL AUTO_INCREMENT,
    `user_id`      int(11)     NOT NULL COMMENT '用户id',
    `id_card`      varchar(18) NOT NULL COMMENT '身份证号码',
    `cost_percent` double      NOT NULL DEFAULT '0' COMMENT '收费比例',
    `room_id`      int(11)     NOT NULL COMMENT '房间号',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT ='用户_发布者表';
