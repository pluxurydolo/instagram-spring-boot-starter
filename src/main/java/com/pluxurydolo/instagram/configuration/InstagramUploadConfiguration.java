package com.pluxurydolo.instagram.configuration;

import com.pluxurydolo.instagram.flow.upload.InstagramContainerPublisher;
import com.pluxurydolo.instagram.flow.upload.InstagramContainerStatusPoller;
import com.pluxurydolo.instagram.flow.upload.image.InstagramImageContainerCreator;
import com.pluxurydolo.instagram.flow.upload.image.InstagramImageUploader;
import com.pluxurydolo.instagram.flow.upload.video.InstagramVideoContainerCreator;
import com.pluxurydolo.instagram.flow.upload.video.InstagramVideoUploader;
import com.pluxurydolo.instagram.properties.InstagramAuthProperties;
import com.pluxurydolo.instagram.properties.InstagramPollingProperties;
import com.pluxurydolo.instagram.token.AbstractTokenRetriever;
import com.pluxurydolo.instagram.web.InstagramUploadHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstagramUploadConfiguration {

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
        InstagramUploadHttpClient instagramUploadHttpClient
    ) {
        return new InstagramImageContainerCreator(instagramUploadHttpClient);
    }

    @Bean
    @ConditionalOnMissingBean
    public InstagramVideoContainerCreator instagramVideoContainerCreator(
        InstagramUploadHttpClient instagramUploadHttpClient
    ) {
        return new InstagramVideoContainerCreator(instagramUploadHttpClient);
    }

    @Bean
    @ConditionalOnMissingBean
    public InstagramContainerStatusPoller instagramContainerStatusPoller(
        InstagramUploadHttpClient instagramUploadHttpClient,
        InstagramPollingProperties instagramPollingProperties
    ) {
        return new InstagramContainerStatusPoller(instagramUploadHttpClient, instagramPollingProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public InstagramContainerPublisher instagramContainerPublisher(
        InstagramUploadHttpClient instagramUploadHttpClient
    ) {
        return new InstagramContainerPublisher(instagramUploadHttpClient);
    }
}
