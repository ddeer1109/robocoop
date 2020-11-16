package org.masteukodeu.robocoop.web;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class CurrentRoundInterceptorAppConfig implements WebMvcConfigurer {

    private final CurrentRoundInterceptor currentRoundInterceptor;

    public CurrentRoundInterceptorAppConfig(CurrentRoundInterceptor currentRoundInterceptor) {
        this.currentRoundInterceptor = currentRoundInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(currentRoundInterceptor);
    }
}