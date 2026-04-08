package com.pluxurydolo.instagram.validator;

import reactor.core.publisher.Mono;

public interface RequestParamValidator {
    Mono<ValidationResult> validate(String accessToken);
}
