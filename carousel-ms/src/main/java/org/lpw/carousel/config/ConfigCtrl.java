package org.lpw.carousel.config;

import org.lpw.tephra.ctrl.context.Request;
import org.lpw.tephra.ctrl.execute.Execute;
import org.lpw.tephra.ctrl.template.Templates;
import org.lpw.tephra.ctrl.validate.Validate;
import org.lpw.tephra.ctrl.validate.Validators;
import org.lpw.tephra.dao.model.ModelHelper;
import org.lpw.tephra.util.Message;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;

/**
 * @author lpw
 */
@Controller(ConfigModel.NAME + ".ctrl")
@Execute(name = "/config/", key = ConfigModel.NAME, code = "21")
public class ConfigCtrl {
    @Inject
    private Message message;
    @Inject
    private ModelHelper modelHelper;
    @Inject
    private Request request;
    @Inject
    private Templates templates;
    @Inject
    private ConfigService configService;

    @Execute(name = "update", validates = {@Validate(validator = Validators.TRUSTFUL_IP)})
    public Object update() {
        ConfigService.Update update = configService.update(request.getFromInputStream());

        return update == ConfigService.Update.Success ? templates.get().success(null, ConfigModel.NAME + ".update.success")
                : templates.get().failure(2101, message.get(ConfigModel.NAME + ".update.failure"), update.name(), null);
    }

    @Execute(name = "query", validates = {@Validate(validator = Validators.TRUSTFUL_IP)})
    public Object query() {
        return configService.query(request.get("name"), request.getAsInt("pageSize"), request.getAsInt("pageNum"));
    }

    @Execute(name = "get", validates = {
            @Validate(validator = Validators.TRUSTFUL_IP),
            @Validate(validator = ConfigService.VALIDATOR_EXISTS, parameter = "id", failureCode = 2)
    })
    public Object get() {
        return modelHelper.toJson(configService.findById(request.get("id")));
    }

    @Execute(name = "help", type = Templates.STRING, validates = {@Validate(validator = Validators.TRUSTFUL_IP)})
    public Object help() {
        return configService.help(request.get("handler"));
    }
}
