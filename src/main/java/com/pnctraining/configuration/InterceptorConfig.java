package com.pnctraining.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import com.pnctraining.security.ValidateUserInterceptor;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    @Value("${base.url}")
    private String baseUrl;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        List<String> exludedPatterns = new ArrayList<>();
        exludedPatterns.add("/**/registration");
        exludedPatterns.add("/**/login");
//        exludedPatterns.add("/**/tags");

        registry.addInterceptor(getValidateUserInterceptor()).addPathPatterns("/**").excludePathPatterns(exludedPatterns);
    }

    @Bean
    public ValidateUserInterceptor getValidateUserInterceptor(){
        return new ValidateUserInterceptor();
    }
}
