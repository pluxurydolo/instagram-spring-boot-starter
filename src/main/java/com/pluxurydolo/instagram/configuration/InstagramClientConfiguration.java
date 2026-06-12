package com.pluxurydolo.instagram.configuration;

import com.pluxurydolo.instagram.client.InstagramImageClient;
import com.pluxurydolo.instagram.client.InstagramVideoClient;
import com.pluxurydolo.instagram.flow.upload.image.InstagramImagePublisher;
import com.pluxurydolo.instagram.flow.upload.video.InstagramVideoPublisher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstagramClientConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public InstagramImageClient instagramImageClient(InstagramImagePublisher instagramImagePublisher) {
        return new InstagramImageClient(instagramImagePublisher);
    }

    @Bean
    @ConditionalOnMissingBean
    public InstagramVideoClient instagramVideoClient(InstagramVideoPublisher instagramVideoPublisher) {
        return new InstagramVideoClient(instagramVideoPublisher);
    }
}
