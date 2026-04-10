package com.pluxurydolo.instagram.config;

import com.pluxurydolo.instagram.dto.response.TokenResponse;
import com.pluxurydolo.instagram.web.InstagramApiWebClient;
import com.pluxurydolo.instagram.web.InstagramUploadWebClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import java.time.Clock;

import static java.time.Clock.systemUTC;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfiguration
public class WebTestConfig {

    @Bean
    public InstagramApiWebClient instagramApiWebClient() {
        InstagramApiWebClient instagramApiWebClient = mock(InstagramApiWebClient.class);

        when(instagramApiWebClient.getExchangeToken(any()))
            .thenReturn(Mono.just(tokenResponse()));

        when(instagramApiWebClient.getAccessToken(any()))
            .thenReturn(Mono.just(tokenResponse()));

        return instagramApiWebClient;
    }

    @Bean
    public InstagramUploadWebClient instagramUploadWebClient() {
        return mock(InstagramUploadWebClient.class);
    }

    @Bean
    public Clock clock() {
        return systemUTC();
    }

    private static TokenResponse tokenResponse() {
        return new TokenResponse("accessToken", "tokenType", 1L);
    }
}
