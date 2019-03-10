package com.wuda.tester.mysql;

import org.springframework.context.ApplicationContext;

/**
 * spring application context.
 *
 * @author wuda
 */
public class SpringApplicationContextHolder {

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringApplicationContextHolder.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return SpringApplicationContextHolder.applicationContext;
    }

}
