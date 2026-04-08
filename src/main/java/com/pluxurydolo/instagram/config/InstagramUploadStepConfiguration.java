package com.pluxurydolo.instagram.config;

import com.pluxurydolo.instagram.properties.InstagramProperties;
import com.pluxurydolo.instagram.properties.PollingProperties;
import com.pluxurydolo.instagram.token.AbstractTokenRetriever;
import com.pluxurydolo.instagram.step.InstagramContainerPublisher;
import com.pluxurydolo.instagram.step.InstagramContainerStatusPoller;
import com.pluxurydolo.instagram.step.image.InstagramImageContainerCreator;
import com.pluxurydolo.instagram.step.image.InstagramImageUploader;
import com.pluxurydolo.instagram.step.video.InstagramVideoContainerCreator;
import com.pluxurydolo.instagram.step.video.InstagramVideoUploader;
import com.pluxurydolo.instagram.web.InstagramUploadWebClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstagramUploadStepConfiguration {

    @Bean
    public InstagramImageUploader instagramImageUploader(
        InstagramImageContainerCreator instagramImageContainerCreator,
        InstagramContainerStatusPoller instagramContainerStatusPoller,
        InstagramContainerPublisher instagramContainerPublisher,
        AbstractTokenRetriever abstractTokenRetriever,
        InstagramProperties instagramProperties
    ) {
        return new InstagramImageUploader(
            instagramImageContainerCreator,
            instagramContainerStatusPoller,
            instagramContainerPublisher,
            abstractTokenRetriever,
            instagramProperties
        );
    }

    @Bean
    public InstagramVideoUploader instagramVideoUploader(
        InstagramVideoContainerCreator instagramVideoContainerCreator,
        InstagramContainerStatusPoller instagramContainerStatusPoller,
        InstagramContainerPublisher instagramContainerPublisher,
        AbstractTokenRetriever abstractTokenRetriever,
        InstagramProperties instagramProperties
    ) {
        return new InstagramVideoUploader(
            instagramVideoContainerCreator,
            instagramContainerStatusPoller,
            instagramContainerPublisher,
            abstractTokenRetriever,
            instagramProperties
        );
    }

    @Bean
    public InstagramImageContainerCreator instagramImageContainerCreator(
        InstagramUploadWebClient instagramUploadWebClient
    ) {
        return new InstagramImageContainerCreator(instagramUploadWebClient);
    }

    @Bean
    public InstagramVideoContainerCreator instagramVideoContainerCreator(
        InstagramUploadWebClient instagramUploadWebClient
    ) {
        return new InstagramVideoContainerCreator(instagramUploadWebClient);
    }

    @Bean
    public InstagramContainerStatusPoller instagramImageContainerStatusPoller(
        InstagramUploadWebClient instagramUploadWebClient,
        PollingProperties pollingProperties
    ) {
        return new InstagramContainerStatusPoller(instagramUploadWebClient, pollingProperties);
    }

    @Bean
    public InstagramContainerPublisher instagramImageContainerPublisher(
        InstagramUploadWebClient instagramUploadWebClient
    ) {
        return new InstagramContainerPublisher(instagramUploadWebClient);
    }
}
