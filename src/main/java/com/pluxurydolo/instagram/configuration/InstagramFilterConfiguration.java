package com.pluxurydolo.instagram.configuration;

import com.pluxurydolo.instagram.filter.InstagramRequestParamValidationFilter;
import com.pluxurydolo.instagram.properties.InstagramEndpointProperties;
import com.pluxurydolo.instagram.validator.RequestParamValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstagramFilterConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public InstagramRequestParamValidationFilter instagramRequestParamValidationFilter(
        RequestParamValidator requestParamValidator,
        InstagramEndpointProperties instagramEndpointProperties
    ) {
        return new InstagramRequestParamValidationFilter(requestParamValidator, instagramEndpointProperties);
    }
}
