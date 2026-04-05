package com.pluxurydolo.instagram.config;

import com.pluxurydolo.instagram.client.InstagramClient;
import com.pluxurydolo.instagram.step.image.InstagramImageSender;
import com.pluxurydolo.instagram.step.video.InstagramVideoSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstagramClientConfiguration {

    @Bean
    public InstagramClient instagramClient(
        InstagramImageSender instagramImageSender,
        InstagramVideoSender instagramVideoSender
    ) {
        return new InstagramClient(instagramImageSender, instagramVideoSender);
    }
}
