package com.pluxurydolo.instagram.controller;

import com.pluxurydolo.instagram.base.AbstractControllerIntegrationTests;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InstagramOAuthControllerIntegrationTests extends AbstractControllerIntegrationTests {

    @Test
    void testLogin() {
        webTestClient.get()
            .uri("/app-name/v1/instagram/login")
            .exchange()
            .expectStatus().isFound()
            .expectHeader().location(locationHeader())
            .expectBody().isEmpty();
    }

    @Test
    void testRedirect() {
        webTestClient.get()
            .uri(uriBuilder -> uriBuilder.path("/app-name/v1/instagram/login/redirect")
                .queryParam("code", "code")
                .build())
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .value(response -> assertThat(response).isEqualTo("saveTokens"));
    }

    @Test
    void testRefresh() {
        webTestClient.get()
            .uri("/app-name/v1/instagram/refresh")
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .value(response -> assertThat(response).isEqualTo("saveTokens"));
    }

    private static String locationHeader() {
        return "https://www.facebook.com/v20.0/dialog/oauth?client_id=id&redirect_uri=http://localhost:8888$/app-name/v1/instagram/login/redirect&scope=instagram_basic,instagram_content_publish,business_management&response_type=code";
    }
}
