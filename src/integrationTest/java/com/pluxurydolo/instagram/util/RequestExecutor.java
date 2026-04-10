package com.pluxurydolo.instagram.util;

import org.springframework.http.HttpStatusCode;
import org.springframework.test.web.reactive.server.WebTestClient;

@FunctionalInterface
public interface RequestExecutor {
    HttpStatusCode execute(WebTestClient webTestClient);
}
