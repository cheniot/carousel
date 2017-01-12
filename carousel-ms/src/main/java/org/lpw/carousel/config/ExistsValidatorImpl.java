package org.lpw.carousel.config;

import org.lpw.tephra.ctrl.validate.ValidateWrapper;
import org.lpw.tephra.ctrl.validate.ValidatorSupport;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;

/**
 * @author lpw
 */
@Controller(ConfigService.VALIDATOR_EXISTS)
public class ExistsValidatorImpl extends ValidatorSupport {
    @Inject
    private ConfigService configService;

    @Override
    public boolean validate(ValidateWrapper validate, String parameter) {
        return configService.findById(parameter) != null;
    }

    @Override
    protected String getDefaultFailureMessageKey() {
        return "carousel.config.id.not-exists";
    }
}
