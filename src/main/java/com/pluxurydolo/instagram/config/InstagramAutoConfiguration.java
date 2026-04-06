package com.pluxurydolo.instagram.config;

import com.pluxurydolo.instagram.properties.InstagramProperties;
import com.pluxurydolo.instagram.properties.PollingProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@ConditionalOnProperty(prefix = "instagram", name = "enabled", havingValue = "true")
@EnableConfigurationProperties({
    InstagramProperties.class,
    PollingProperties.class
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
