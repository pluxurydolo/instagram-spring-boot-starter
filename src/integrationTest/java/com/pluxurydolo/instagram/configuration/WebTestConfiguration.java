package com.pluxurydolo.instagram.configuration;

import com.pluxurydolo.instagram.dto.response.ContainerResponse;
import com.pluxurydolo.instagram.dto.response.ContainerStatusResponse;
import com.pluxurydolo.instagram.dto.response.TokenResponse;
import com.pluxurydolo.instagram.web.InstagramApiHttpClient;
import com.pluxurydolo.instagram.web.InstagramUploadHttpClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfiguration
public class WebTestConfiguration {

    @Bean
    public InstagramApiHttpClient instagramApiHttpClient() {
        InstagramApiHttpClient mock = mock(InstagramApiHttpClient.class);

        when(mock.getExchangeToken(anyString(), anyString(), anyString(), anyString()))
            .thenReturn(Mono.just(tokenResponse()));
        when(mock.getAccessToken(anyString(), anyString(), anyString(), anyString()))
            .thenReturn(Mono.just(tokenResponse()));
        return mock;
    }

    @Bean
    public InstagramUploadHttpClient instagramUploadHttpClient() {
        InstagramUploadHttpClient mock = mock(InstagramUploadHttpClient.class);

        when(mock.createImageContainer(anyString(), anyString(), anyString(), anyString()))
            .thenReturn(Mono.just(containerResponse()));
        when(mock.createVideoContainer(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString()))
            .thenReturn(Mono.just(containerResponse()));
        when(mock.publishContainer(anyString(), anyString(), anyString()))
            .thenReturn(Mono.just(containerResponse()));
        when(mock.getContainerStatus(anyString(), anyString(), anyString()))
            .thenReturn(Mono.just(containerStatusResponse()));

        return mock;
    }

    private static TokenResponse tokenResponse() {
        return new TokenResponse("accessToken", "tokenType", 1L);
    }

    private static ContainerResponse containerResponse() {
        return new ContainerResponse("id");
    }

    private static ContainerStatusResponse containerStatusResponse() {
        return new ContainerStatusResponse("id", "FINISHED", "status");
    }
}
