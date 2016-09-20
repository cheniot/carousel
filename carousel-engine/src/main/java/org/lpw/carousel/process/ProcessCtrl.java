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
@Controller("carousel.process.ctrl")
@Execute(name = "/carousel/process/")
public class ProcessCtrl {
    @Autowired
    protected Request request;
    @Autowired
    protected Engine engine;

    @Execute(name = "execute", validates = {@Validate(validator = Validators.NOT_EMPTY, parameter = "name", failureCode = 1001, failureArgKeys = {"name"}),
            @Validate(validator = Validators.NOT_EMPTY, parameter = "data", failureCode = 1002, failureArgKeys = {"data"})})
    public Object execute() {
        Object object = engine.execute(request.get("name"), request.getAsInt("delay"), request.get("data"));

        return object == null ? "" : object;
    }
}
