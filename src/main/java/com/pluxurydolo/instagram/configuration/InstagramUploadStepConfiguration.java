package com.pluxurydolo.instagram.configuration;

import com.pluxurydolo.instagram.properties.InstagramAuthProperties;
import com.pluxurydolo.instagram.properties.InstagramPollingProperties;
import com.pluxurydolo.instagram.token.AbstractTokenRetriever;
import com.pluxurydolo.instagram.step.InstagramContainerPublisher;
import com.pluxurydolo.instagram.step.InstagramContainerStatusPoller;
import com.pluxurydolo.instagram.step.image.InstagramImageContainerCreator;
import com.pluxurydolo.instagram.step.image.InstagramImageUploader;
import com.pluxurydolo.instagram.step.video.InstagramVideoContainerCreator;
import com.pluxurydolo.instagram.step.video.InstagramVideoUploader;
import com.pluxurydolo.instagram.web.InstagramUploadWebClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstagramUploadStepConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public InstagramImageUploader instagramImageUploader(
        InstagramImageContainerCreator instagramImageContainerCreator,
        InstagramContainerStatusPoller instagramContainerStatusPoller,
        InstagramContainerPublisher instagramContainerPublisher,
        AbstractTokenRetriever abstractTokenRetriever,
        InstagramAuthProperties instagramAuthProperties
    ) {
        return new InstagramImageUploader(
            instagramImageContainerCreator,
            instagramContainerStatusPoller,
            instagramContainerPublisher,
            abstractTokenRetriever,
            instagramAuthProperties
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public InstagramVideoUploader instagramVideoUploader(
        InstagramVideoContainerCreator instagramVideoContainerCreator,
        InstagramContainerStatusPoller instagramContainerStatusPoller,
        InstagramContainerPublisher instagramContainerPublisher,
        AbstractTokenRetriever abstractTokenRetriever,
        InstagramAuthProperties instagramAuthProperties
    ) {
        return new InstagramVideoUploader(
            instagramVideoContainerCreator,
            instagramContainerStatusPoller,
            instagramContainerPublisher,
            abstractTokenRetriever,
            instagramAuthProperties
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public InstagramImageContainerCreator instagramImageContainerCreator(
        InstagramUploadWebClient instagramUploadWebClient
    ) {
        return new InstagramImageContainerCreator(instagramUploadWebClient);
    }

    @Bean
    @ConditionalOnMissingBean
    public InstagramVideoContainerCreator instagramVideoContainerCreator(
        InstagramUploadWebClient instagramUploadWebClient
    ) {
        return new InstagramVideoContainerCreator(instagramUploadWebClient);
    }

    @Bean
    @ConditionalOnMissingBean
    public InstagramContainerStatusPoller instagramImageContainerStatusPoller(
        InstagramUploadWebClient instagramUploadWebClient,
        InstagramPollingProperties instagramPollingProperties
    ) {
        return new InstagramContainerStatusPoller(instagramUploadWebClient, instagramPollingProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public InstagramContainerPublisher instagramImageContainerPublisher(
        InstagramUploadWebClient instagramUploadWebClient
    ) {
        return new InstagramContainerPublisher(instagramUploadWebClient);
    }
}
