package com.egovorushkin.logiweb.config;

import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.Locale;
import java.util.Properties;

public final class WebTestConfig {

    private static final String ERROR_VALUE = "error/error";

    private WebTestConfig() {
    }

    public static SimpleMappingExceptionResolver exceptionResolver() {


        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();

        Properties exceptionMappings = new Properties();

        exceptionMappings.put("com.egovorushkin.logiweb.exceptions.EntityNotFoundException", "error/404");
        exceptionMappings.put("java.lang.Exception", ERROR_VALUE);
        exceptionMappings.put("java.lang.RuntimeException", ERROR_VALUE);

        exceptionResolver.setExceptionMappings(exceptionMappings);

        Properties statusCodes = new Properties();

        statusCodes.put("error/404", "404");
        statusCodes.put(ERROR_VALUE, "500");

        exceptionResolver.setStatusCodes(statusCodes);

        return exceptionResolver;

    }

    public static FixedLocaleResolver fixedLocalResolver() {
        return new FixedLocaleResolver(Locale.ENGLISH);
    }

    public static ViewResolver jspViewResolver() {
        InternalResourceViewResolver viewResolver = new
                InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp");
        viewResolver.setPrefix(".jsp");
        return viewResolver;
    }
}
