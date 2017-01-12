package org.lpw.carousel.process;

import org.lpw.carousel.engine.Engine;
import org.lpw.carousel.process.step.StepService;
import org.lpw.tephra.ctrl.context.Request;
import org.lpw.tephra.ctrl.execute.Execute;
import org.lpw.tephra.ctrl.validate.Validate;
import org.lpw.tephra.ctrl.validate.Validators;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;

/**
 * @author lpw
 */
@Controller(ProcessModel.NAME + ".ctrl")
@Execute(name = "/process/", key = ProcessModel.NAME, code = "22")
public class ProcessCtrl {
    @Inject
    private Request request;
    @Inject
    private Engine engine;
    @Inject
    private ProcessService processService;
    @Inject
    private StepService stepService;

    @Execute(name = "execute", validates = {
            @Validate(validator = Validators.TRUSTFUL_IP),
            @Validate(validator = Validators.NOT_EMPTY, parameter = "name", failureCode = 1),
            @Validate(validator = Validators.NOT_EMPTY, parameter = "data", failureCode = 2)
    })
    public Object execute() {
        Object object = engine.execute(request.get("name"), request.getAsInt("delay"), request.get("data"));

        return object == null ? "" : object;
    }

    @Execute(name = "query", validates = {
            @Validate(validator = Validators.TRUSTFUL_IP)
    })
    public Object query() {
        return processService.query(request.get("config"), request.getAsInt("pageSize"), request.getAsInt("pageNum"));
    }

    @Execute(name = "steps", validates = {
            @Validate(validator = Validators.TRUSTFUL_IP)
    })
    public Object steps() {
        return stepService.query(request.get("process"));
    }
}
