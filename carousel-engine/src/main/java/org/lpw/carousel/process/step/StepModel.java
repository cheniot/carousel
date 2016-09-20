package org.lpw.carousel.process.step;

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
@Component("carousel.process.step.model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Entity(name = "carousel.process.step")
@Table(name = "t_carousel_process_step")
public class StepModel extends ModelSupport {
    private String process;
    private int serial;
    private int index;
    private String data;
    private Timestamp time;

    @Jsonable
    @Column(name = "c_process")
    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    @Jsonable
    @Column(name = "c_serial")
    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    @Jsonable
    @Column(name = "c_index")
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Jsonable
    @Column(name = "c_data")
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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
