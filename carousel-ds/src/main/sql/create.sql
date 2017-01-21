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

  PRIMARY KEY pk_discovery(c_id) USING HASH,
  UNIQUE KEY uk_discovery_key_service(c_key,c_service) USING HASH
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

  PRIMARY KEY pk_discovery(c_id) USING HASH,
  UNIQUE KEY uk_discovery_key_service(c_key,c_service) USING HASH
) ENGINE=Memory DEFAULT CHARSET=utf8;
