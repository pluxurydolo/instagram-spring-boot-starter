package com.pluxurydolo.instagram.base;

import com.pluxurydolo.instagram.TestApplication;
import com.pluxurydolo.instagram.configuration.SchedulerTestConfiguration;
import com.pluxurydolo.instagram.configuration.TokensTestConfiguration;
import com.pluxurydolo.instagram.configuration.ValidatorTestConfiguration;
import com.pluxurydolo.instagram.configuration.WebTestConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@SpringBootTest(classes = {
    TestApplication.class,
    TokensTestConfiguration.class,
    ValidatorTestConfiguration.class,
    SchedulerTestConfiguration.class,
    WebTestConfiguration.class
})
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public abstract class AbstractIntegrationTests {
}
