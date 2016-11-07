package org.lpw.carousel.discovery;

import org.lpw.tephra.ctrl.context.Header;
import org.lpw.tephra.ctrl.context.Request;
import org.lpw.tephra.ctrl.execute.Execute;
import org.lpw.tephra.ctrl.template.Templates;
import org.lpw.tephra.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author lpw
 */
@Controller(DiscoveryModel.NAME + ".ctrl")
@Execute(name = "/discovery/", key = DiscoveryModel.NAME)
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

    @Execute(name = "register")
    public Object register() {
        discoveryService.register(request.get("key"), request.get("service"), request.get("validate"));

        return "";
    }

    @Execute(name = "execute")
    public Object execute() {
        String string = discoveryService.execute(header.getMap(), request.getMap());
        if (string == null)
            return templates.get().failure(2001, message.get(DiscoveryModel.NAME + ".key.not-exists"), null, null);

        return string;
    }
}
