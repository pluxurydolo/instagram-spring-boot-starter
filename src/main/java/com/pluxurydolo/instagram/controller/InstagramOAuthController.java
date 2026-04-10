package com.pluxurydolo.instagram.controller;

import com.pluxurydolo.instagram.service.InstagramOAuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
public class InstagramOAuthController {
    private final InstagramOAuthService instagramOAuthService;

    public InstagramOAuthController(InstagramOAuthService instagramOAuthService) {
        this.instagramOAuthService = instagramOAuthService;
    }

    @GetMapping("${instagram.login.url}")
    public Mono<Void> login(ServerWebExchange serverWebExchange) {
        return instagramOAuthService.login(serverWebExchange);
    }

    @GetMapping("${instagram.redirect.url}")
    public Mono<String> callback(@RequestParam("code") String code) {
        return instagramOAuthService.callback(code);
    }

    @GetMapping("${instagram.refresh.url}")
    public Mono<String> refreshToken() {
        return instagramOAuthService.refreshToken();
    }
}
