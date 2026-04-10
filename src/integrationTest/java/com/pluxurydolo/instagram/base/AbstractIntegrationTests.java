package com.pluxurydolo.instagram.base;

import com.pluxurydolo.instagram.TestApplication;
import com.pluxurydolo.instagram.config.SchedulerTestConfig;
import com.pluxurydolo.instagram.config.TokensTestConfig;
import com.pluxurydolo.instagram.config.ValidatorTestConfig;
import com.pluxurydolo.instagram.config.WebTestConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@SpringBootTest(classes = {
    TestApplication.class,
    TokensTestConfig.class,
    ValidatorTestConfig.class,
    SchedulerTestConfig.class,
    WebTestConfig.class
})
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties = {
    "instagram.enabled=true",
    "instagram.app.id=id",
    "instagram.app.secret=secret",
    "instagram.user.id=id",
    "instagram.login.url=/app-name/v1/instagram/login",
    "instagram.redirect.uri=http://localhost:8888$/app-name/v1/instagram/login/redirect",
    "instagram.redirect.url=/app-name/v1/instagram/login/redirect",
    "instagram.refresh.url=/app-name/v1/instagram/refresh",
    "instagram.refresh.token.scheduler.cron=0 0 0 * * SUN",
    "instagram.refresh.token.scheduler.zone=Europe/Moscow",
    "instagram.rate-limit.threshold=5",
    "instagram.polling.max-repeat=20",
    "instagram.polling.delay=10s"
})
public abstract class AbstractIntegrationTests {
}
