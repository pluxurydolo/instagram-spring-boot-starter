package com.pluxurydolo.instagram.configuration;

import com.pluxurydolo.instagram.client.InstagramClient;
import com.pluxurydolo.instagram.step.image.InstagramImageUploader;
import com.pluxurydolo.instagram.step.video.InstagramVideoUploader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstagramClientConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public InstagramClient instagramClient(
        InstagramImageUploader instagramImageUploader,
        InstagramVideoUploader instagramVideoUploader
    ) {
        return new InstagramClient(instagramImageUploader, instagramVideoUploader);
    }
}
