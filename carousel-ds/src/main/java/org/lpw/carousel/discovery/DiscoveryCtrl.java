package org.lpw.carousel.discovery;

import org.lpw.tephra.ctrl.context.Header;
import org.lpw.tephra.ctrl.context.Request;
import org.lpw.tephra.ctrl.execute.Execute;
import org.lpw.tephra.ctrl.template.Templates;
import org.lpw.tephra.ctrl.validate.Validate;
import org.lpw.tephra.ctrl.validate.Validators;
import org.lpw.tephra.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author lpw
 */
@Controller(DiscoveryModel.NAME + ".ctrl")
@Execute(name = "/discovery/", key = DiscoveryModel.NAME, code = "20")
public class DiscoveryCtrl {
    @Autowired
    protected Message message;
    @Autowired
    protected Header header;
    @Autowired
    protected Request request;
    @Autowired
    protected Templates templates;
    @Autowired
    protected DiscoveryService discoveryService;

    @Execute(name = "register", validates = {
            @Validate(validator = Validators.TRUSTFUL_IP),
            @Validate(validator = Validators.NOT_EMPTY, parameter = "key", failureCode = 1),
            @Validate(validator = Validators.MAX_LENGTH, parameter = "key", number = {100}, failureCode = 2),
            @Validate(validator = Validators.NOT_EMPTY, parameter = "service", failureCode = 3),
            @Validate(validator = Validators.MAX_LENGTH, parameter = "service", number = {100}, failureCode = 4),
            @Validate(validator = Validators.NOT_EMPTY, parameter = "validate", failureCode = 5),
            @Validate(validator = Validators.MAX_LENGTH, parameter = "validate", number = {100}, failureCode = 6),
            @Validate(validator = Validators.MAX_LENGTH, parameter = "success", number = {100}, failureCode = 7)
    })
    public Object register() {
        discoveryService.register(request.get("key"), request.get("service"), request.get("validate"), request.get("success"));

        return "";
    }

    @Execute(name = "execute", validates = {@Validate(validator = Validators.TRUSTFUL_IP)})
    public Object execute() {
        String string = discoveryService.execute(header.getMap(), request.getMap());
        if (string == null)
            return templates.get().failure(2001, message.get(DiscoveryModel.NAME + ".key.not-exists"), null, null);

        return string;
    }
}
