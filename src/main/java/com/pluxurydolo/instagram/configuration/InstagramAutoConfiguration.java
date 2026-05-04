package com.pluxurydolo.instagram.configuration;

import com.pluxurydolo.instagram.properties.InstagramAuthProperties;
import com.pluxurydolo.instagram.properties.InstagramPollingProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@EnableConfigurationProperties({
    InstagramAuthProperties.class,
    InstagramPollingProperties.class
})
@Import({
    InstagramOAuthConfiguration.class,
    InstagramWebConfiguration.class,
    InstagramClientConfiguration.class,
    InstagramUploadStepConfiguration.class,
    InstagramSchedulingConfiguration.class
})
public class InstagramAutoConfiguration {
}
