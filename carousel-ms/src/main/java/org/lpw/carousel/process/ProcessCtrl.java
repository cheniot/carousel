package org.lpw.carousel.process;

import org.lpw.carousel.engine.Engine;
import org.lpw.tephra.ctrl.context.Request;
import org.lpw.tephra.ctrl.execute.Execute;
import org.lpw.tephra.ctrl.validate.Validate;
import org.lpw.tephra.ctrl.validate.Validators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author lpw
 */
@Controller(ProcessModel.NAME + ".ctrl")
@Execute(name = "/process/", key = ProcessModel.NAME, code = "10")
public class ProcessCtrl {
    @Autowired
    protected Request request;
    @Autowired
    protected Engine engine;

    @Execute(name = "execute", validates = {@Validate(validator = Validators.TRUSTFUL_IP),
            @Validate(validator = Validators.NOT_EMPTY, parameter = "name", failureCode = 1),
            @Validate(validator = Validators.NOT_EMPTY, parameter = "data", failureCode = 2)
    })
    public Object execute() {
        Object object = engine.execute(request.get("name"), request.getAsInt("delay"), request.get("data"));

        return object == null ? "" : object;
    }
}
