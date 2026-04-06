package com.pluxurydolo.instagram.config;

import com.pluxurydolo.instagram.client.InstagramClient;
import com.pluxurydolo.instagram.step.image.InstagramImageUploader;
import com.pluxurydolo.instagram.step.video.InstagramVideoUploader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstagramClientConfiguration {

    @Bean
    public InstagramClient instagramClient(
        InstagramImageUploader instagramImageUploader,
        InstagramVideoUploader instagramVideoUploader
    ) {
        return new InstagramClient(instagramImageUploader, instagramVideoUploader);
    }
}
