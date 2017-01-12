package org.lpw.carousel.discovery;

import org.lpw.tephra.ctrl.context.Header;
import org.lpw.tephra.ctrl.context.Request;
import org.lpw.tephra.ctrl.execute.Execute;
import org.lpw.tephra.ctrl.template.Templates;
import org.lpw.tephra.ctrl.validate.Validate;
import org.lpw.tephra.ctrl.validate.Validators;
import org.lpw.tephra.util.Message;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;

/**
 * @author lpw
 */
@Controller(DiscoveryModel.NAME + ".ctrl")
@Execute(name = "/discovery/", key = DiscoveryModel.NAME, code = "23")
public class DiscoveryCtrl {
    @Inject
    private Message message;
    @Inject
    private Header header;
    @Inject
    private Request request;
    @Inject
    private Templates templates;
    @Inject
    private DiscoveryService discoveryService;

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
            return templates.get().failure(2311, message.get(DiscoveryModel.NAME + ".key.not-exists"), null, null);

        return string;
    }

    @Execute(name = "query", validates = {@Validate(validator = Validators.TRUSTFUL_IP)})
    public Object query() {
        return discoveryService.query(request.get("key"), request.get("service"), request.getAsInt("pageSize"), request.getAsInt("pageNum"));
    }
}
