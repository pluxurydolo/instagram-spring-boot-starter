package com.pluxurydolo.instagram.controller;

import com.pluxurydolo.instagram.service.InstagramOAuthService;
import org.springframework.resilience.annotation.ConcurrencyLimit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.resilience.annotation.ConcurrencyLimit.ThrottlePolicy.REJECT;

@RestController
public class InstagramOAuthController {
    private final InstagramOAuthService instagramOAuthService;

    public InstagramOAuthController(InstagramOAuthService instagramOAuthService) {
        this.instagramOAuthService = instagramOAuthService;
    }

    @GetMapping("${instagram.endpoint.login}")
    @ConcurrencyLimit(limit = 1, policy = REJECT)
    public Mono<Void> login(ServerWebExchange serverWebExchange) {
        return instagramOAuthService.login(serverWebExchange);
    }

    @GetMapping("${instagram.endpoint.redirect}")
    @ConcurrencyLimit(limit = 1, policy = REJECT)
    public Mono<String> redirect(@RequestParam("code") String code) {
        return instagramOAuthService.redirect(code);
    }

    @GetMapping("${instagram.endpoint.refresh-token}")
    @ConcurrencyLimit(limit = 1, policy = REJECT)
    public Mono<String> refreshToken() {
        return instagramOAuthService.refreshToken();
    }
}
