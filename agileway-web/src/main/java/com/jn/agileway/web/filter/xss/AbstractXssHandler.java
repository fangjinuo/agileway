package com.jn.agileway.web.filter.xss;

import com.jn.langx.lifecycle.AbstractInitializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXssHandler extends AbstractInitializable implements XssHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String apply(String value) {
        if (isAttack(value)) {
            logger.warn("maybe XSS attack: {}", value);
            return "";
        }
        return value;
    }

    protected abstract boolean isAttack(String value);
}
