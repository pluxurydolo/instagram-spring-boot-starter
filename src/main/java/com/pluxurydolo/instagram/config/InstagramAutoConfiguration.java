package com.pluxurydolo.instagram.config;

import com.pluxurydolo.instagram.properties.InstagramPollingProperties;
import com.pluxurydolo.instagram.properties.InstagramProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@EnableConfigurationProperties({
    InstagramProperties.class,
    InstagramPollingProperties.class
})
@Import({
    InstagramOAuthConfiguration.class,
    InstagramWebConfiguration.class,
    InstagramClientConfiguration.class,
    InstagramUploadStepConfiguration.class,
    InstagramSchedulingConfiguration.class,
    InstagramFilterConfiguration.class
})
public class InstagramAutoConfiguration {
}
