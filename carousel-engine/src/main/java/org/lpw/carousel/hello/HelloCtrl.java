package org.lpw.carousel.hello;

import org.lpw.tephra.ctrl.context.Request;
import org.lpw.tephra.ctrl.execute.Execute;
import org.lpw.tephra.ctrl.template.Templates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author lpw
 */
@Controller("carousel.hello.ctrl")
@Execute(name = "/carousel/hello/")
public class HelloCtrl {
    @Autowired
    protected Request request;

    @Execute(name = "hi", type = Templates.STRING)
    public Object hi() {
        return "hello " + request.getFromInputStream();
    }
}
