package com.pluxurydolo.instagram.configuration;

import com.pluxurydolo.instagram.properties.InstagramAuthProperties;
import com.pluxurydolo.instagram.properties.InstagramEndpointProperties;
import com.pluxurydolo.instagram.properties.InstagramPollingProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@EnableConfigurationProperties({
    InstagramAuthProperties.class,
    InstagramEndpointProperties.class,
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
