package org.lpw.carousel.discovery;

import org.lpw.tephra.dao.model.Jsonable;
import org.lpw.tephra.dao.model.Memory;
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
@Component(DiscoveryModel.NAME + ".model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Entity(name = DiscoveryModel.NAME)
@Table(name = "t_discovery")
@Memory(name = "m_discovery")
public class DiscoveryModel extends ModelSupport {
    static final String NAME = "carousel.discovery";

    private String key; // 服务key
    private String service; // 服务URL地址
    private String validate; // 验证URL地址
    private String success; // 验证成功标识
    private int state; // 状态：0-正常，>0-异常
    private Timestamp register; // 注册时间

    @Jsonable
    @Column(name = "c_key")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Jsonable
    @Column(name = "c_service")
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @Jsonable
    @Column(name = "c_validate")
    public String getValidate() {
        return validate;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }

    @Jsonable
    @Column(name = "c_success")
    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
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
    @Column(name = "c_register")
    public Timestamp getRegister() {
        return register;
    }

    public void setRegister(Timestamp register) {
        this.register = register;
    }
}
