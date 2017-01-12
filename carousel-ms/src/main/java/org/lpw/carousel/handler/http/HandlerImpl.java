package org.lpw.carousel.handler.http;

import net.sf.json.JSONObject;
import org.lpw.carousel.handler.Handler;
import org.lpw.carousel.handler.Parameter;
import org.lpw.tephra.bean.BeanFactory;
import org.lpw.tephra.ctrl.context.Header;
import org.lpw.tephra.util.Http;
import org.lpw.tephra.util.Validator;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author lpw
 */
@Service("carousel.handler.http")
public class HandlerImpl implements Handler {
    @Inject
    private Validator validator;
    @Inject
    private Http http;
    @Inject
    private Header header;

    @Override
    public String getName() {
        return "carousel.handler.http";
    }

    @Override
    public boolean isValid(JSONObject parameter) {
        return parameter != null && parameter.has("url");
    }

    @Override
    public Parameter newParameter() {
        return BeanFactory.getBean("carousel.handler.http.parameter", Parameter.class);
    }

    @Override
    public Object execute(Parameter parameter, Object object) {
        return http.post(parameter.get("url"), header.getMap(), object.toString());
    }

    @Override
    public boolean isSuccess(Parameter parameter, Object object) {
        if (object == null)
            return false;

        String success = parameter.get("success");
        if (success.equals(""))
            return true;

        return object.toString().contains(success);
    }
}
