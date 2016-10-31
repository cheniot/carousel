package org.lpw.carousel.config;

import org.lpw.carousel.handler.Parameter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author lpw
 */
@Component(ConfigModel.NAME + ".action")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ActionImpl implements Action {
    private String name;
    private String handler;
    private Parameter parameter;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getHandler() {
        return handler;
    }

    @Override
    public void setHandler(String handler) {
        this.handler = handler;
    }

    @Override
    public Parameter getParameter() {
        return parameter;
    }

    @Override
    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }
}
