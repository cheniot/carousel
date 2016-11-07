CREATE SCHEMA d_carousel;
USE d_carousel;

DROP TABLE IF EXISTS t_config;
CREATE TABLE t_config
(
  c_id CHAR(36) NOT NULL COMMENT '主键',
  c_name VARCHAR(255) NOT NULL COMMENT '名称',
  c_description VARCHAR(255) DEFAULT NULL COMMENT '描述',
  c_delay INT DEFAULT 0 COMMENT '延迟执行时间',
  c_interval INT DEFAULT 0 COMMENT '重复执行间隔',
  c_times INT DEFAULT 0 COMMENT '重复执行次数',
  c_wait INT DEFAULT 0 COMMENT '配置值',
  c_value TEXT NOT NULL COMMENT '配置值',
  c_time DATETIME NOT NULL COMMENT '更新时间',

  PRIMARY KEY pk_config(c_id),
  UNIQUE KEY uk_config_name(c_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS t_process;
CREATE TABLE t_process
(
  c_id CHAR(36) NOT NULL COMMENT '主键',
  c_config CHAR(36) NOT NULL COMMENT '配置ID值',
  c_step INT DEFAULT 0 COMMENT '执行步骤',
  c_in TEXT DEFAULT NULL COMMENT '输入数据',
  c_out TEXT DEFAULT NULL COMMENT '输出数据',
  c_start DATETIME NOT NULL COMMENT '开始时间',
  c_end DATETIME DEFAULT NULL COMMENT '结束时间',
  c_times INT DEFAULT 0 COMMENT '重复执行次数',
  c_state INT DEFAULT 0 COMMENT '状态',
  c_failure INT DEFAULT 0 COMMENT '失败次数',

  PRIMARY KEY pk_process(c_id),
  KEY k_process_config(c_config,c_start),
  KEY k_process_state(c_state,c_start)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS t_process_step;
CREATE TABLE t_process_step
(
  c_id CHAR(36) NOT NULL COMMENT '主键',
  c_process CHAR(36) NOT NULL COMMENT '配置ID值',
  c_serial INT DEFAULT 0 COMMENT '流水号',
  c_index INT DEFAULT 0 COMMENT '执行步骤',
  c_data TEXT DEFAULT NULL COMMENT '数据',
  c_time DATETIME DEFAULT NULL COMMENT '时间',

  PRIMARY KEY pk_process_step(c_id),
  KEY k_process_step_process(c_process,c_serial)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS t_discovery;
CREATE TABLE t_discovery
(
  c_id CHAR(36) NOT NULL COMMENT '主键',
  c_key VARCHAR(255) NOT NULL COMMENT '服务key',
  c_service VARCHAR(255) NOT NULL COMMENT '服务URL地址',
  c_validate VARCHAR(255) NOT NULL COMMENT '验证URL地址',
  c_success VARCHAR(255) NOT NULL COMMENT '验证成功标识',
  c_state INT DEFAULT 0 COMMENT '状态：0-正常，>0-异常',
  c_register DATETIME NOT NULL COMMENT '注册时间',

  PRIMARY KEY pk_discovery(c_id),
  UNIQUE KEY uk_discovery_key_service(c_key,c_service)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS m_discovery;
CREATE TABLE m_discovery
(
  c_id CHAR(36) NOT NULL COMMENT '主键',
  c_key VARCHAR(255) NOT NULL COMMENT '服务key',
  c_service VARCHAR(255) NOT NULL COMMENT '服务URL地址',
  c_validate VARCHAR(255) NOT NULL COMMENT '验证URL地址',
  c_success VARCHAR(255) NOT NULL COMMENT '验证成功标识',
  c_state INT DEFAULT 0 COMMENT '状态：0-正常，>0-异常',
  c_register DATETIME NOT NULL COMMENT '注册时间',

  PRIMARY KEY pk_discovery(c_id),
  UNIQUE KEY uk_discovery_key_service(c_key,c_service)
) ENGINE=Memory DEFAULT CHARSET=utf8;
