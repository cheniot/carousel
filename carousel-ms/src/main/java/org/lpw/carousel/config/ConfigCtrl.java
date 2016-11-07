package org.lpw.carousel.config;

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
@Controller(ConfigModel.NAME + ".ctrl")
@Execute(name = "/config/", key = ConfigModel.NAME, code = "10")
public class ConfigCtrl {
    @Autowired
    protected Message message;
    @Autowired
    protected Request request;
    @Autowired
    protected Templates templates;
    @Autowired
    protected ConfigService configService;

    @Execute(name = "update", validates = {@Validate(validator = Validators.TRUSTFUL_IP)})
    public Object update() {
        ConfigService.Update update = configService.update(request.getFromInputStream());

        return update == ConfigService.Update.Success ? templates.get().success(null, ConfigModel.NAME + ".update.success")
                : templates.get().failure(1001, message.get(ConfigModel.NAME + ".update.failure"), update.name(), null);
    }

    @Execute(name = "help", type = Templates.STRING, validates = {@Validate(validator = Validators.TRUSTFUL_IP)})
    public Object help() {
        return configService.help(request.get("handler"));
    }
}
