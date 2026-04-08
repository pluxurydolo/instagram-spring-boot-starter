package com.pluxurydolo.instagram.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Name;

@ConfigurationProperties(prefix = "instagram")
public record InstagramProperties(

    @Name("app.id")
    String appId,

    @Name("app.secret")
    String appSecret,

    @Name("user.id")
    String userId,

    @Name("redirect.uri")
    String redirectUri,

    @Name("login.url")
    String loginUrl,

    @Name("redirect.url")
    String redirectUrl,

    @Name("refresh.url")
    String refreshUrl
) {
}
