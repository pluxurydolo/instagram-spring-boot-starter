package com.pluxurydolo.instagram.configuration;

import com.pluxurydolo.instagram.dto.response.ContainerResponse;
import com.pluxurydolo.instagram.dto.response.ContainerStatusResponse;
import com.pluxurydolo.instagram.dto.response.TokenResponse;
import com.pluxurydolo.instagram.web.InstagramApiWebClient;
import com.pluxurydolo.instagram.web.InstagramUploadWebClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfiguration
public class WebTestConfiguration {

    @Bean
    public InstagramApiWebClient instagramApiWebClient() {
        InstagramApiWebClient mock = mock(InstagramApiWebClient.class);

        when(mock.getExchangeToken(any()))
            .thenReturn(Mono.just(tokenResponse()));
        when(mock.getAccessToken(any()))
            .thenReturn(Mono.just(tokenResponse()));
        return mock;
    }

    @Bean
    public InstagramUploadWebClient instagramUploadWebClient() {
        InstagramUploadWebClient mock = mock(InstagramUploadWebClient.class);

        when(mock.createImageContainer(any()))
            .thenReturn(Mono.just(containerResponse()));
        when(mock.createVideoContainer(any()))
            .thenReturn(Mono.just(containerResponse()));
        when(mock.publishContainer(any()))
            .thenReturn(Mono.just(containerResponse()));
        when(mock.getContainerStatus(anyString(), anyString()))
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
