package org.lpw.carousel.config;

import org.lpw.tephra.dao.model.Jsonable;
import org.lpw.tephra.dao.model.ModelSupport;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.sql.Timestamp;

/**
 * @author lpw
 */
@Component(ConfigModel.NAME + ".model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Entity(name = ConfigModel.NAME)
@Table(name = "t_config")
public class ConfigModel extends ModelSupport {
    static final String NAME = "carousel.config";

    private String name; // 名称
    private String description; // 描述
    private int delay; // 延迟执行时间
    private int interval; // 重复执行间隔
    private int times; // 重复执行次数
    private int wait; // 配置值
    private String value; // 配置值
    private Timestamp time; // 更新时间

    @Jsonable
    @Column(name = "c_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Jsonable
    @Column(name = "c_description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Jsonable
    @Column(name = "c_delay")
    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    @Jsonable
    @Column(name = "c_interval")
    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    @Jsonable
    @Column(name = "c_times")
    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    @Jsonable
    @Column(name = "c_wait")
    public int getWait() {
        return wait;
    }

    public void setWait(int wait) {
        this.wait = wait;
    }

    @Jsonable
    @Column(name = "c_value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Jsonable
    @Column(name = "c_time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
