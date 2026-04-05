package com.pluxurydolo.instagram.controller;

import com.pluxurydolo.instagram.service.InstagramOAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class InstagramOAuthController {
    private final InstagramOAuthService instagramOAuthService;

    public InstagramOAuthController(InstagramOAuthService instagramOAuthService) {
        this.instagramOAuthService = instagramOAuthService;
    }

    @GetMapping("${instagram.login.url}")
    public Mono<ResponseEntity<Void>> login() {
        return instagramOAuthService.login();
    }

    @GetMapping("${instagram.redirect.url}")
    public Mono<String> callback(@RequestParam("code") String code) {
        return instagramOAuthService.callback(code);
    }
}
