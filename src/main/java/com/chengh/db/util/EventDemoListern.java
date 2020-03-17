package com.chengh.db.util;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @program: chenghTest
 * @description:
 * @author: chengh
 * @create: 2019-09-12 17:25
 */
@Component
public class EventDemoListern implements ApplicationListener<EventDemo> {

    @Override
    public void onApplicationEvent(EventDemo event) {
        System.out.println("receiver: " + event.getMessage());
    }
}
