package org.lpw.carousel.process;

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
@Component(ProcessModel.NAME + ".model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Entity(name = ProcessModel.NAME)
@Table(name = "t_process")
public class ProcessModel extends ModelSupport {
    static final String NAME = "carousel.process";

    private String config; // 配置ID值
    private int step; // 执行步骤
    private String in; // 输入数据
    private String out; // 输出数据
    private Timestamp start; // 开始时间
    private Timestamp end; // 结束时间
    private int times; // 重复执行次数
    private int state; // 状态
    private int failure; // 失败次数

    @Jsonable
    @Column(name = "c_config")
    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    @Jsonable
    @Column(name = "c_step")
    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    @Jsonable
    @Column(name = "c_in")
    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    @Jsonable
    @Column(name = "c_out")
    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    @Jsonable
    @Column(name = "c_start")
    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    @Jsonable
    @Column(name = "c_end")
    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
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
    @Column(name = "c_state")
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Jsonable
    @Column(name = "c_failure")
    public int getFailure() {
        return failure;
    }

    public void setFailure(int failure) {
        this.failure = failure;
    }
}
