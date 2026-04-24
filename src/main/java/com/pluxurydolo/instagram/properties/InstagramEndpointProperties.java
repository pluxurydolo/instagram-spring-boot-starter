package com.pluxurydolo.instagram.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Name;

@ConfigurationProperties(prefix = "instagram.endpoint")
public record InstagramEndpointProperties(

    @Name("login")
    String loginUrl,

    @Name("redirect")
    String redirectUrl,

    @Name("refresh-token")
    String refreshTokenUrl
) {
}
