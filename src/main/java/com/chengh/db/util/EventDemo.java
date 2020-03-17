package com.chengh.db.util;

import org.springframework.context.ApplicationEvent;

/**
 * @program: chenghTest
 * @description:
 * @author: chengh
 * @create: 2019-09-12 17:24
 */
public class EventDemo extends ApplicationEvent {
    private String message;
    public EventDemo(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}