package com.pluxurydolo.instagram.configuration;

import com.pluxurydolo.instagram.validator.RequestParamValidator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import static com.pluxurydolo.instagram.validator.ValidationResult.SUCCESS;

@TestConfiguration
public class ValidatorTestConfiguration {

    @Bean
    public RequestParamValidator requestParamValidator() {
        return _ -> Mono.just(SUCCESS);
    }
}
