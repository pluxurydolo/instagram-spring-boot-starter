package com.pluxurydolo.instagram.config;

import com.pluxurydolo.instagram.validator.RequestParamValidator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import static com.pluxurydolo.instagram.validator.ValidationResult.SUCCESS;

@TestConfiguration
public class ValidatorTestConfig {

    @Bean
    public RequestParamValidator requestParamValidator() {
        return _ -> Mono.just(SUCCESS);
    }
}
