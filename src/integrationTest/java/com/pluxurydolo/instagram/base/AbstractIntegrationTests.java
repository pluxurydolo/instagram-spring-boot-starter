package com.pluxurydolo.instagram.base;

import com.pluxurydolo.instagram.TestApplication;
import com.pluxurydolo.instagram.config.SchedulerTestConfig;
import com.pluxurydolo.instagram.config.TokensTestConfig;
import com.pluxurydolo.instagram.config.ValidatorTestConfig;
import com.pluxurydolo.instagram.config.WebTestConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@SpringBootTest(classes = {
    TestApplication.class,
    TokensTestConfig.class,
    ValidatorTestConfig.class,
    SchedulerTestConfig.class,
    WebTestConfig.class
})
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public abstract class AbstractIntegrationTests {
}
