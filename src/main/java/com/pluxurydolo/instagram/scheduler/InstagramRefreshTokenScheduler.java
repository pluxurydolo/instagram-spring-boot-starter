package com.pluxurydolo.instagram.scheduler;

import com.pluxurydolo.instagram.scheduler.handler.InstagramRefreshTokenSchedulerHandler;
import org.springframework.scheduling.annotation.Scheduled;

public class InstagramRefreshTokenScheduler {
    private final InstagramRefreshTokenSchedulerHandler instagramRefreshTokenSchedulerHandler;

    public InstagramRefreshTokenScheduler(InstagramRefreshTokenSchedulerHandler instagramRefreshTokenSchedulerHandler) {
        this.instagramRefreshTokenSchedulerHandler = instagramRefreshTokenSchedulerHandler;
    }

    @Scheduled(
        cron = "${instagram.refresh.token.scheduler.cron}",
        zone = "${instagram.refresh.token.scheduler.zone}"
    )
    public void schedule() {
        String jobName = getClass().getName();

        instagramRefreshTokenSchedulerHandler.handle(jobName)
            .subscribe();
    }
}
