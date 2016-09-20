ALTER TABLE t_carousel_config ADD COLUMN c_interval INT DEFAULT 0 COMMENT '重复执行间隔' AFTER c_delay;
ALTER TABLE t_carousel_config ADD COLUMN c_times INT DEFAULT 0 COMMENT '重复执行次数' AFTER c_interval;
ALTER TABLE t_carousel_process ADD COLUMN c_times INT DEFAULT 0 COMMENT '重复执行次数' AFTER c_end;
