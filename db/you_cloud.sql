/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : localhost:3306
 Source Schema         : you_cloud

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 11/08/2023 16:24:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `menu_id`    varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '菜单id',
    `menu_name`  varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '菜单名称',
    `path`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '对应前台页面地址',
    `icon`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT '' COMMENT 'icon',
    `perm_token` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限字符串',
    `parent_id`  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT 'parent id',
    `order_num`  int(11)                                                       NULL     DEFAULT NULL COMMENT '排序',
    `menu_type`  tinyint(1)                                                    NULL     DEFAULT NULL COMMENT '1菜单 2按钮',
    `status`     tinyint(1)                                                    NOT NULL DEFAULT 1 COMMENT 'is enable,0 no,1 yes',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu`
VALUES ('01', '系统管理', 'system', '', 'manage:system:open', '-1', 1, 1, 1);
INSERT INTO `sys_menu`
VALUES ('0101', '系统管理', 'system', 'el-icon-s-help', 'manage:system:open', '01', 1, 1, 1);
INSERT INTO `sys_menu`
VALUES ('010101', '用户管理', 'user', 'el-icon-user-solid', 'b:user:open', '0101', 1, 1, 1);
INSERT INTO `sys_menu`
VALUES ('01010101', '用户新增', NULL, '', 'b:user:add', '010101', 1, 2, 1);
INSERT INTO `sys_menu`
VALUES ('01010102', '用户编辑', NULL, '', 'b:user:edit', '010101', 2, 2, 1);
INSERT INTO `sys_menu`
VALUES ('01010103', '重置密码', NULL, '', 'b:user:resetPassword', '010101', 3, 2, 1);
INSERT INTO `sys_menu`
VALUES ('01010104', '用户授权', NULL, '', 'b:user:authorization', '010101', 4, 2, 1);
INSERT INTO `sys_menu`
VALUES ('01010105', '导出', NULL, '', 'b:user:download', '010101', 5, 2, 1);
INSERT INTO `sys_menu`
VALUES ('01010106', '导入', NULL, '', 'b:user:importExcel', '010101', 6, 2, 1);
INSERT INTO `sys_menu`
VALUES ('010102', '角色管理', 'role', '', 'b:role:open', '0101', 2, 1, 1);
INSERT INTO `sys_menu`
VALUES ('01010201', '角色新增', NULL, '', 'b:role:add', '010102', 1, 2, 1);
INSERT INTO `sys_menu`
VALUES ('01010202', '角色编辑', NULL, '', 'b:role:edit', '010102', 2, 2, 1);
INSERT INTO `sys_menu`
VALUES ('01010203', '角色删除', NULL, '', 'b:role:delete', '010102', 3, 2, 1);
INSERT INTO `sys_menu`
VALUES ('01010204', '角色授权', NULL, '', 'b:role:authorize', '010102', 4, 2, 1);
INSERT INTO `sys_menu`
VALUES ('010103', '部门管理', 'organization', '', 'b:organization:open', '0101', 3, 1, 1);
INSERT INTO `sys_menu`
VALUES ('01010301', '新增', NULL, '', 'b:organization:add', '010103', 1, 2, 1);
INSERT INTO `sys_menu`
VALUES ('01010302', '编辑', NULL, '', 'b:organization:update', '010103', 2, 2, 1);
INSERT INTO `sys_menu`
VALUES ('01010303', '删除', NULL, '', 'b:organization:delete', '010103', 3, 2, 1);
INSERT INTO `sys_menu`
VALUES ('010104', '数据字典', 'dict', 'el-icon-s-grid', 'b:dict:open', '0101', 5, 1, 1);
INSERT INTO `sys_menu`
VALUES ('01010401', '数据字典新增', NULL, '', 'b:dict:add', '010104', 1, 2, 1);
INSERT INTO `sys_menu`
VALUES ('01010402', '数据字典编辑', NULL, '', 'b:dict:edit', '010104', 2, 2, 1);
INSERT INTO `sys_menu`
VALUES ('01010403', '数据字典删除', NULL, '', 'b:dict:delete', '010104', 3, 2, 1);
INSERT INTO `sys_menu`
VALUES ('01010404', '数据字典详情编辑', NULL, '', 'b:dict:infoEdit', '010104', 4, 2, 1);
INSERT INTO `sys_menu`
VALUES ('01010405', '数据字典详情删除', NULL, '', 'b:dict:infoDelete', '010104', 5, 2, 1);
INSERT INTO `sys_menu`
VALUES ('01010406', '数据字典详情', NULL, '', 'b:dict:info', '010104', 6, 2, 1);
INSERT INTO `sys_menu`
VALUES ('01010407', '数据字典详情新增', NULL, '', 'b:dict:infoAdd', '010104', 7, 2, 1);
INSERT INTO `sys_menu`
VALUES ('010105', '全局参数设置', 'globalParam', 'el-icon-s-opportunity', 'b:globalParam:open', '0101', 9, 1, 1);
INSERT INTO `sys_menu`
VALUES ('01010501', '全局参数编辑', NULL, '', 'b:globalParam:edit', '010105', 1, 2, 1);
INSERT INTO `sys_menu`
VALUES ('010106', '日志', 'log', '', 'b:log:open', '0101', 10, 1, 1);
INSERT INTO `sys_menu`
VALUES ('01010601', '日志导出', NULL, '', 'b:log:excelOut', '010106', 1, 2, 1);
INSERT INTO `sys_menu`
VALUES ('010107', '菜单管理', 'menu', '', 'b:menu:open', '0101', 8, 1, 1);
INSERT INTO `sys_menu`
VALUES ('01010701', '菜单新增', NULL, '', 'b:menu:add', '010107', 1, 2, 1);
INSERT INTO `sys_menu`
VALUES ('01010702', '菜单编辑', NULL, '', 'b:menu:edit', '010107', 2, 2, 1);
INSERT INTO `sys_menu`
VALUES ('01010703', '菜单删除', NULL, '', 'b:menu:delete', '010107', 3, 2, 1);
INSERT INTO `sys_menu`
VALUES ('010108', '通知公告', 'notice', 'el-icon-message-solid', 'b:notice:open', '0101', 4, 1, 1);
INSERT INTO `sys_menu`
VALUES ('01010801', '通知公共新增', NULL, '', 'b:notice:add', '010108', 1, 2, 1);
INSERT INTO `sys_menu`
VALUES ('01010802', '通知公告编辑', NULL, '', 'b:notice:edit', '010108', 2, 2, 1);
INSERT INTO `sys_menu`
VALUES ('01010803', '通知公告查看', NULL, '', 'b:notice:view', '010108', 3, 2, 1);
INSERT INTO `sys_menu`
VALUES ('01010804', '通知公告删除', NULL, '', 'b:notice:delete', '010108', 4, 2, 1);
INSERT INTO `sys_menu`
VALUES ('0102', '系统监控', 'monitor', 'el-icon-monitor', 'manage:monitor:open', '01', 2, 1, 1);
INSERT INTO `sys_menu`
VALUES ('010201', '数据监控', 'druid', 'el-icon-coin', 'b:druid:open', '0102', 1, 1, 1);
INSERT INTO `sys_menu`
VALUES ('010202', '服务器监控', 'server', 'el-icon-data-analysis', 'b:server:open', '0102', 2, 1, 1);
INSERT INTO `sys_menu`
VALUES ('0103', '开发人员工具', 'develop', 'el-icon-setting', 'system:develop:open', '01', 3, 1, 1);
INSERT INTO `sys_menu`
VALUES ('010301', '前台表单构建', 'formGenerator', 'el-icon-film', 'b:formGenerator:open', '0103', 1, 1, 1);
INSERT INTO `sys_menu`
VALUES ('010302', '代码生成', 'generate', '', 'b:generate:open', '0103', 2, 1, 1);
INSERT INTO `sys_menu`
VALUES ('010304', '文件上传', 'upload', 'el-icon-upload', 'b:upload:open', '0103', 4, 1, 1);
INSERT INTO `sys_menu`
VALUES ('010305', '模拟数据', 'dataCreate', 'el-icon-document', 'b:dataCreate:open', '0103', 3, 1, 1);
INSERT INTO `sys_menu`
VALUES ('010306', '词库', 'lexicon', 'el-icon-notebook-1', 'b:lexicon:open', '0103', 6, 1, 1);
INSERT INTO `sys_menu`
VALUES ('01030601', '词库新增', NULL, '', 'b:lexicon:add', '010306', 1, 2, 1);
INSERT INTO `sys_menu`
VALUES ('01030602', '词库编辑', NULL, '', 'b:lexicon:edit', '010306', 2, 2, 1);
INSERT INTO `sys_menu`
VALUES ('01030603', '词库删除', NULL, '', 'b:lexicon:delete', '010306', 3, 2, 1);
INSERT INTO `sys_menu`
VALUES ('01030604', '词库查看', NULL, '', 'b:lexicon:view', '010306', 4, 2, 1);
INSERT INTO `sys_menu`
VALUES ('0104', '工作流示例', 'workflow', 'el-icon-ship', 'b:workflow:manage', '01', 4, 1, 1);
INSERT INTO `sys_menu`
VALUES ('010402', '请假流程', 'leaveProcess', '', 'b:leaveProcess:manage', '0104', NULL, 1, 1);
INSERT INTO `sys_menu`
VALUES ('010403', '待办列表', 'todoList', '', 'b:todoList:open', '0104', NULL, 1, 1);
INSERT INTO `sys_menu`
VALUES ('010404', '流程部署', 'processDeployment', '', 'b:processDeployment:open', '0104', NULL, 1, 1);
INSERT INTO `sys_menu`
VALUES ('010405', '我的申请', 'myApplication', '', 'b:myApplication:open', '0104', NULL, 1, 1);
INSERT INTO `sys_menu`
VALUES ('010406', '已办事项', 'workflowAalready', '', 'workflow:already:open', '0104', 5, 1, 1);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '角色名',
  `type`        tinyint(1)                                                    NULL DEFAULT 2 COMMENT '角色类型：1内置角色，2新增角色',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role`
VALUES (1, '系统管理员', 1, '系统管理员（内置角色）');
INSERT INTO `sys_role`
VALUES (2, '普通用户', 2, '普通用户');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`
(
    `role_id`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL,
    `perm_token` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    PRIMARY KEY (`role_id`, `perm_token`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:dataCreate:open');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:dict:add');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:dict:delete');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:dict:edit');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:dict:info');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:dict:infoAdd');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:dict:infoDelete');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:dict:infoEdit');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:dict:open');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:druid:open');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:formGenerator:open');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:generate:open');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:globalParam:edit');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:globalParam:open');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:leaveProcess:manage');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:lexicon:add');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:lexicon:delete');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:lexicon:edit');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:lexicon:open');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:lexicon:view');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:log:excelOut');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:log:open');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:menu:add');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:menu:delete');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:menu:edit');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:menu:open');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:myApplication:open');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:notice:add');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:notice:delete');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:notice:edit');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:notice:open');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:notice:view');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:organization:add');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:organization:delete');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:organization:open');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:organization:update');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:processDeployment:open');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:role:add');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:role:authorize');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:role:delete');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:role:edit');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:role:open');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:server:open');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:todoList:open');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:upload:open');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:user:add');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:user:authorization');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:user:download');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:user:edit');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:user:importExcel');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:user:open');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:user:resetPassword');
INSERT INTO `sys_role_permission`
VALUES ('1', 'b:workflow:manage');
INSERT INTO `sys_role_permission`
VALUES ('1', 'manage:monitor:open');
INSERT INTO `sys_role_permission`
VALUES ('1', 'manage:system:open');
INSERT INTO `sys_role_permission`
VALUES ('1', 'system:develop:open');
INSERT INTO `sys_role_permission`
VALUES ('1', 'workflow:already:open');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `user_id`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL,
    `username`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '登录账号',
    `real_name`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '真实姓名',
    `password`    varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
    `phone`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '电话号',
    `email`       varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '邮箱',
    `status`      tinyint(2)                                                    NOT NULL DEFAULT 0 COMMENT '用户状态：0禁用；1启用',
    `create_time` datetime                                                      NULL     DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user`
VALUES ('06e7eca9784276247f671718a5c8783a', 'admin', 'Administrator',
        'dc1fd00e3eeeb940ff46f457bf97d66ba7fcc36e0b20802383de142860e76ae6', NULL, NULL, 1, '2023-01-23 14:50:40');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role_id` int(11) NOT NULL,
    PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '用户角色对应表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role`
VALUES ('06e7eca9784276247f671718a5c8783a', 1);

SET FOREIGN_KEY_CHECKS = 1;
