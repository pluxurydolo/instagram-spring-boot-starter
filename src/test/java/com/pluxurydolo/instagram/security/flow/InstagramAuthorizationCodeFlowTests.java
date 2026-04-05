package com.pluxurydolo.instagram.security.flow;

import com.pluxurydolo.instagram.properties.InstagramProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InstagramAuthorizationCodeFlowTests {

    @Mock
    private InstagramProperties instagramProperties;

    @InjectMocks
    private InstagramAuthorizationCodeFlow instagramAuthorizationCodeFlow;

    @Test
    void testGetAuthorizationUrl() {
        when(instagramProperties.appId())
            .thenReturn("appId");
        when(instagramProperties.redirectUri())
            .thenReturn("redirectUri");

        String result = instagramAuthorizationCodeFlow.getAuthorizationUrl();

        assertThat(result)
            .isEqualTo("https://www.facebook.com/v20.0/dialog/oauth?client_id=appId&redirect_uri=redirectUri&scope=instagram_basic,instagram_content_publish,business_management&response_type=code");
    }
}
