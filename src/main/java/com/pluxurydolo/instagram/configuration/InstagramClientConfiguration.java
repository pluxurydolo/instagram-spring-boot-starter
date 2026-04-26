package com.pluxurydolo.instagram.configuration;

import com.pluxurydolo.instagram.client.InstagramImageClient;
import com.pluxurydolo.instagram.client.InstagramVideoClient;
import com.pluxurydolo.instagram.step.image.InstagramImageUploader;
import com.pluxurydolo.instagram.step.video.InstagramVideoUploader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstagramClientConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public InstagramImageClient instagramImageClient(InstagramImageUploader instagramImageUploader) {
        return new InstagramImageClient(instagramImageUploader);
    }

    @Bean
    @ConditionalOnMissingBean
    public InstagramVideoClient instagramVideoClient(InstagramVideoUploader instagramVideoUploader) {
        return new InstagramVideoClient(instagramVideoUploader);
    }
}
