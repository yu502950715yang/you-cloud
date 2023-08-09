/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 5.7.34-log : Database - you_nacos_config
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`you_nacos_config` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `you_nacos_config`;

/*Table structure for table `config_info` */

DROP TABLE IF EXISTS `config_info`;

CREATE TABLE `config_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `content` longtext COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text COLLATE utf8_bin COMMENT 'source user',
  `src_ip` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `c_use` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `effect` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `type` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `c_schema` text COLLATE utf8_bin,
  `encrypted_data_key` text COLLATE utf8_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_info';

/*Data for the table `config_info` */

insert  into `config_info`(`id`,`data_id`,`group_id`,`content`,`md5`,`gmt_create`,`gmt_modified`,`src_user`,`src_ip`,`app_name`,`tenant_id`,`c_desc`,`c_use`,`effect`,`type`,`c_schema`,`encrypted_data_key`) values 
(1,'application-dev.yaml','DEFAULT_GROUP','# feign 配置\r\nfeign:\r\n  sentinel:\r\n    enabled: true\r\n  okhttp:\r\n    enabled: true\r\n  httpclient:\r\n    enabled: false\r\n  client:\r\n    config:\r\n      default:\r\n        connectTimeout: 10000\r\n        readTimeout: 10000\r\n  compression:\r\n    request:\r\n      enabled: true\r\n    response:\r\n      enabled: true','0616ec8d6b88afa4f408dabaea88d05a','2023-08-09 01:38:18','2023-08-09 01:38:18','nacos','127.0.0.1','','','公共配置',NULL,NULL,'yaml',NULL,''),
(2,'you-auth-dev.yaml','DEFAULT_GROUP','spring:\r\n  redis:\r\n    host: localhost\r\n    port: 6379\r\n    password:\r\n','7679126e553fb99f877a98715a35f8ef','2023-08-09 01:39:23','2023-08-09 01:39:23','nacos','127.0.0.1','','','认证中心模块配置',NULL,NULL,'yaml',NULL,''),
(3,'you-gateway-dev.yaml','DEFAULT_GROUP','spring:\r\n  redis:\r\n    host: localhost\r\n    port: 6379\r\n    password:\r\n  cloud:\r\n    gateway:\r\n      discovery:\r\n        locator:\r\n          lowerCaseServiceId: true\r\n          enabled: true\r\n      routes:\r\n        # 认证中心\r\n        - id: you-auth\r\n          uri: lb://you-auth\r\n          predicates:\r\n            - Path=/auth/**\r\n          filters:\r\n            - StripPrefix=1\r\n        # 系统模块\r\n        - id: you-system\r\n          uri: lb://you-system\r\n          predicates:\r\n            - Path=/system/**\r\n          filters:\r\n            - StripPrefix=1\r\nauth:\r\n  ignore:\r\n    whiteList:\r\n      - /auth/verifyCode','d5ead612bd895144c93bc2453ed3ad21','2023-08-09 01:40:03','2023-08-09 01:40:03','nacos','127.0.0.1','','','网关配置',NULL,NULL,'yaml',NULL,''),
(4,'you-system-dev.yaml','DEFAULT_GROUP','# spring配置\r\nspring:\r\n  redis:\r\n    host: localhost\r\n    port: 6379\r\n    password:\r\n  datasource:\r\n    druid:\r\n      # 数据监控\r\n      stat-view-servlet:\r\n        enabled: true\r\n        loginUsername: admin\r\n        loginPassword: 123456\r\n    dynamic:\r\n      druid:\r\n        initial-size: 5\r\n        min-idle: 5\r\n        maxActive: 20\r\n        maxWait: 60000\r\n        timeBetweenEvictionRunsMillis: 60000\r\n        minEvictableIdleTimeMillis: 300000\r\n        validationQuery: SELECT 1 FROM DUAL\r\n        testWhileIdle: true\r\n        testOnBorrow: false\r\n        testOnReturn: false\r\n        poolPreparedStatements: true\r\n        maxPoolPreparedStatementPerConnectionSize: 20\r\n        filters: stat,slf4j\r\n        connectionProperties: druid.stat.mergeSql\\=true;druid.stat.slowSqlMillis\\=5000\r\n      datasource:\r\n        # 主库数据源\r\n        master:\r\n          driver-class-name: com.mysql.cj.jdbc.Driver\r\n          url: jdbc:mysql://localhost:3306/you-cloud?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\r\n          username: root\r\n          password: root\r\n          # 从库数据源\r\n          # slave:\r\n          # username:\r\n          # password:\r\n          # url:\r\n          # driver-class-name:','0cd48e4bc5e2fa84496cc36ce08d654b','2023-08-09 01:41:36','2023-08-09 01:41:36','nacos','127.0.0.1','','','系统模块配置',NULL,NULL,'yaml',NULL,'');

/*Table structure for table `config_info_aggr` */

DROP TABLE IF EXISTS `config_info_aggr`;

CREATE TABLE `config_info_aggr` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
  `content` longtext COLLATE utf8_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfoaggr_datagrouptenantdatum` (`data_id`,`group_id`,`tenant_id`,`datum_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='增加租户字段';

/*Data for the table `config_info_aggr` */

/*Table structure for table `config_info_beta` */

DROP TABLE IF EXISTS `config_info_beta`;

CREATE TABLE `config_info_beta` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext COLLATE utf8_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) COLLATE utf8_bin DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text COLLATE utf8_bin COMMENT 'source user',
  `src_ip` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text COLLATE utf8_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfobeta_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_info_beta';

/*Data for the table `config_info_beta` */

/*Table structure for table `config_info_tag` */

DROP TABLE IF EXISTS `config_info_tag`;

CREATE TABLE `config_info_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text COLLATE utf8_bin COMMENT 'source user',
  `src_ip` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfotag_datagrouptenanttag` (`data_id`,`group_id`,`tenant_id`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_info_tag';

/*Data for the table `config_info_tag` */

/*Table structure for table `config_tags_relation` */

DROP TABLE IF EXISTS `config_tags_relation`;

CREATE TABLE `config_tags_relation` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `tag_name` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`),
  UNIQUE KEY `uk_configtagrelation_configidtag` (`id`,`tag_name`,`tag_type`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_tag_relation';

/*Data for the table `config_tags_relation` */

/*Table structure for table `group_capacity` */

DROP TABLE IF EXISTS `group_capacity`;

CREATE TABLE `group_capacity` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_group_id` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='集群、各Group容量信息表';

/*Data for the table `group_capacity` */

/*Table structure for table `his_config_info` */

DROP TABLE IF EXISTS `his_config_info`;

CREATE TABLE `his_config_info` (
  `id` bigint(20) unsigned NOT NULL,
  `nid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL,
  `group_id` varchar(128) COLLATE utf8_bin NOT NULL,
  `app_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext COLLATE utf8_bin NOT NULL,
  `md5` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user` text COLLATE utf8_bin,
  `src_ip` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `op_type` char(10) COLLATE utf8_bin DEFAULT NULL,
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text COLLATE utf8_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`nid`),
  KEY `idx_gmt_create` (`gmt_create`),
  KEY `idx_gmt_modified` (`gmt_modified`),
  KEY `idx_did` (`data_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='多租户改造';

/*Data for the table `his_config_info` */

insert  into `his_config_info`(`id`,`nid`,`data_id`,`group_id`,`app_name`,`content`,`md5`,`gmt_create`,`gmt_modified`,`src_user`,`src_ip`,`op_type`,`tenant_id`,`encrypted_data_key`) values 
(0,1,'application-dev.yaml','DEFAULT_GROUP','','# feign 配置\r\nfeign:\r\n  sentinel:\r\n    enabled: true\r\n  okhttp:\r\n    enabled: true\r\n  httpclient:\r\n    enabled: false\r\n  client:\r\n    config:\r\n      default:\r\n        connectTimeout: 10000\r\n        readTimeout: 10000\r\n  compression:\r\n    request:\r\n      enabled: true\r\n    response:\r\n      enabled: true','0616ec8d6b88afa4f408dabaea88d05a','2023-08-09 09:38:17','2023-08-09 01:38:18','nacos','127.0.0.1','I','',''),
(0,2,'you-auth-dev.yaml','DEFAULT_GROUP','','spring:\r\n  redis:\r\n    host: localhost\r\n    port: 6379\r\n    password:\r\n','7679126e553fb99f877a98715a35f8ef','2023-08-09 09:39:23','2023-08-09 01:39:23','nacos','127.0.0.1','I','',''),
(0,3,'you-gateway-dev.yaml','DEFAULT_GROUP','','spring:\r\n  redis:\r\n    host: localhost\r\n    port: 6379\r\n    password:\r\n  cloud:\r\n    gateway:\r\n      discovery:\r\n        locator:\r\n          lowerCaseServiceId: true\r\n          enabled: true\r\n      routes:\r\n        # 认证中心\r\n        - id: you-auth\r\n          uri: lb://you-auth\r\n          predicates:\r\n            - Path=/auth/**\r\n          filters:\r\n            - StripPrefix=1\r\n        # 系统模块\r\n        - id: you-system\r\n          uri: lb://you-system\r\n          predicates:\r\n            - Path=/system/**\r\n          filters:\r\n            - StripPrefix=1\r\nauth:\r\n  ignore:\r\n    whiteList:\r\n      - /auth/verifyCode','d5ead612bd895144c93bc2453ed3ad21','2023-08-09 09:40:02','2023-08-09 01:40:03','nacos','127.0.0.1','I','',''),
(0,4,'you-system-dev.yaml','DEFAULT_GROUP','','# spring配置\r\nspring:\r\n  redis:\r\n    host: localhost\r\n    port: 6379\r\n    password:\r\n  datasource:\r\n    druid:\r\n      # 数据监控\r\n      stat-view-servlet:\r\n        enabled: true\r\n        loginUsername: admin\r\n        loginPassword: 123456\r\n    dynamic:\r\n      druid:\r\n        initial-size: 5\r\n        min-idle: 5\r\n        maxActive: 20\r\n        maxWait: 60000\r\n        timeBetweenEvictionRunsMillis: 60000\r\n        minEvictableIdleTimeMillis: 300000\r\n        validationQuery: SELECT 1 FROM DUAL\r\n        testWhileIdle: true\r\n        testOnBorrow: false\r\n        testOnReturn: false\r\n        poolPreparedStatements: true\r\n        maxPoolPreparedStatementPerConnectionSize: 20\r\n        filters: stat,slf4j\r\n        connectionProperties: druid.stat.mergeSql\\=true;druid.stat.slowSqlMillis\\=5000\r\n      datasource:\r\n        # 主库数据源\r\n        master:\r\n          driver-class-name: com.mysql.cj.jdbc.Driver\r\n          url: jdbc:mysql://localhost:3306/you-cloud?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\r\n          username: root\r\n          password: root\r\n          # 从库数据源\r\n          # slave:\r\n          # username:\r\n          # password:\r\n          # url:\r\n          # driver-class-name:','0cd48e4bc5e2fa84496cc36ce08d654b','2023-08-09 09:41:36','2023-08-09 01:41:36','nacos','127.0.0.1','I','','');

/*Table structure for table `permissions` */

DROP TABLE IF EXISTS `permissions`;

CREATE TABLE `permissions` (
  `role` varchar(50) NOT NULL,
  `resource` varchar(255) NOT NULL,
  `action` varchar(8) NOT NULL,
  UNIQUE KEY `uk_role_permission` (`role`,`resource`,`action`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `permissions` */

/*Table structure for table `roles` */

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `username` varchar(50) NOT NULL,
  `role` varchar(50) NOT NULL,
  UNIQUE KEY `idx_user_role` (`username`,`role`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `roles` */

insert  into `roles`(`username`,`role`) values 
('nacos','ROLE_ADMIN');

/*Table structure for table `tenant_capacity` */

DROP TABLE IF EXISTS `tenant_capacity`;

CREATE TABLE `tenant_capacity` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数',
  `max_aggr_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='租户容量信息表';

/*Data for the table `tenant_capacity` */

/*Table structure for table `tenant_info` */

DROP TABLE IF EXISTS `tenant_info`;

CREATE TABLE `tenant_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint(20) NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`,`tenant_id`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='tenant_info';

/*Data for the table `tenant_info` */

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(500) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `users` */

insert  into `users`(`username`,`password`,`enabled`) values 
('nacos','$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
