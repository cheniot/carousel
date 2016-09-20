package org.lpw.carousel.handler.xml2json;

import net.sf.json.JSONObject;
import org.lpw.carousel.handler.Handler;
import org.lpw.carousel.handler.Parameter;
import org.lpw.tephra.util.Xml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lpw
 */
@Service("carousel.handler.xml2json")
public class HandlerImpl implements Handler {
    @Autowired
    protected Xml xml;

    @Override
    public String getName() {
        return "carousel.handler.xml2json";
    }

    @Override
    public boolean isValid(JSONObject parameter) {
        return true;
    }

    @Override
    public Parameter newParameter() {
        return null;
    }

    @Override
    public Object execute(Parameter parameter, Object object) {
        return xml.toJson(object.toString());
    }

    @Override
    public boolean isSuccess(Parameter parameter, Object object) {
        return true;
    }
}
