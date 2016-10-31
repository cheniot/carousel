package org.lpw.carousel.config;

import org.lpw.tephra.ctrl.context.Request;
import org.lpw.tephra.ctrl.execute.Execute;
import org.lpw.tephra.ctrl.template.Templates;
import org.lpw.tephra.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author lpw
 */
@Controller(ConfigModel.NAME + ".ctrl")
@Execute(name = "/config/")
public class ConfigCtrl {
    @Autowired
    protected Message message;
    @Autowired
    protected Request request;
    @Autowired
    protected Templates templates;
    @Autowired
    protected ConfigService configService;

    @Execute(name = "update")
    public Object update() {
        ConfigService.Update update = configService.update(request.getFromInputStream());

        return update == ConfigService.Update.Success ? templates.get().success(null, "carousel.config.update.success")
                : templates.get().failure(1001, message.get("carousel.config.update.failure"), update.name(), null);
    }

    @Execute(name = "help", type = Templates.STRING)
    public Object help() {
        return configService.help(request.get("handler"));
    }
}
