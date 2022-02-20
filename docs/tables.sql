CREATE TABLE `t_security_config`
(
    `source`      varchar(20)   NOT NULL,
    `token`       varchar(1024) NOT NULL,
    `secure_key`  varchar(2048) NOT NULL,
    `sign_key`    varchar(2048) NOT NULL,
    `url_pattern` varchar(100) DEFAULT NULL,
    `state`       tinyint(1) NOT NULL DEFAULT '1',
    PRIMARY KEY (`source`) USING BTREE
) ENGINE=InnoDB;

CREATE TABLE `t_flow_log`
(
    `apply_id`     varchar(64) NOT NULL,
    `name`         varchar(1024) DEFAULT NULL,
    `name_decrypt` varchar(50)   DEFAULT NULL,
    `mobile`       varchar(11)   DEFAULT NULL,
    `id_no`        varchar(18)   DEFAULT NULL,
    `apply_time`   timestamp NULL DEFAULT current_timestamp (),
    `update_time`  timestamp NULL DEFAULT current_timestamp () ON UPDATE current_timestamp (),
    PRIMARY KEY (`apply_id`) USING BTREE
) ENGINE=InnoDB;
CREATE TABLE `t_flow_log_0` like t_flow_log;
CREATE TABLE `t_flow_log_1` like t_flow_log;

CREATE TABLE `t_business_log`
(
    `apply_id` varchar(64) NOT NULL,
    `name`     varchar(50) NOT NULL,
    `value`    varchar(11) NULL,
    PRIMARY KEY (`apply_id`, `name`) USING BTREE
) ENGINE=InnoDB;
