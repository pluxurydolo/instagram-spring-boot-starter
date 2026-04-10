package com.pluxurydolo.instagram.load;

import com.pluxurydolo.instagram.base.AbstractLoadTests;
import com.pluxurydolo.instagram.util.LoadTestingResult;
import com.pluxurydolo.instagram.util.RequestExecutor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InstagramOAuthControllerLoadTests extends AbstractLoadTests {

    @Test
    void testLoginWithLoad() {
        RequestExecutor requestExecutor = requestExecutor("/app-name/v1/instagram/login");

        LoadTestingResult result = runConcurrent(
            1000,
            requestExecutor
        );
        long successful = result.successful();
        long failed = result.failed();

        assertThat(successful)
            .isGreaterThan(0L);
        assertThat(failed)
            .isGreaterThan(0L);
    }

    @Test
    void testRedirectWithLoad() {
        RequestExecutor requestExecutor = requestExecutor("/app-name/v1/instagram/login/redirect?code=code");

        LoadTestingResult result = runConcurrent(
            1000,
            requestExecutor
        );
        long successful = result.successful();
        long failed = result.failed();

        assertThat(successful)
            .isGreaterThan(0L);
        assertThat(failed)
            .isGreaterThan(0L);
    }

    @Test
    void testRefreshWithLoad() {
        RequestExecutor requestExecutor = requestExecutor("/app-name/v1/instagram/refresh");

        LoadTestingResult result = runConcurrent(
            1000,
            requestExecutor
        );
        long successful = result.successful();
        long failed = result.failed();

        assertThat(successful)
            .isGreaterThan(0L);
        assertThat(failed)
            .isGreaterThan(0L);
    }

    private static RequestExecutor requestExecutor(String uri) {
        return client -> client.get()
            .uri(uri)
            .exchange()
            .returnResult()
            .getStatus();
    }
}
