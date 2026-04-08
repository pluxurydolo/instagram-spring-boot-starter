package com.pluxurydolo.instagram.config;

import com.pluxurydolo.instagram.dto.response.TokenResponse;
import com.pluxurydolo.instagram.web.InstagramApiWebClient;
import com.pluxurydolo.instagram.web.InstagramUploadWebClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfiguration
public class WebTestConfig {

    @Bean
    public InstagramApiWebClient instagramApiWebClient() {
        InstagramApiWebClient mock = mock(InstagramApiWebClient.class);

        when(mock.getAccessToken(any()))
            .thenReturn(Mono.just(tokenResponse()));

        return mock;
    }

    @Bean
    public InstagramUploadWebClient instagramUploadWebClient() {
        return mock(InstagramUploadWebClient.class);
    }

    private static TokenResponse tokenResponse() {
        return new TokenResponse("accessToken", "tokenType", 1L);
    }
}
