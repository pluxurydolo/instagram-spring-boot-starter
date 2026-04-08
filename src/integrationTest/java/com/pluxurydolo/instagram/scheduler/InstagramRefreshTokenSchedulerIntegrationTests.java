package com.pluxurydolo.instagram.scheduler;

import com.pluxurydolo.instagram.base.AbstractIntegrationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class InstagramRefreshTokenSchedulerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private InstagramRefreshTokenScheduler scheduler;

    @Test
    void testSchedule() {
        assertDoesNotThrow(scheduler::schedule);
    }
}
