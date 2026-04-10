package com.pluxurydolo.instagram.config;

import com.pluxurydolo.instagram.token.AbstractTokenRetriever;
import com.pluxurydolo.instagram.token.AbstractTokenSaver;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import java.util.Map;

import static java.time.Clock.systemUTC;

@TestConfiguration
public class TokensTestConfig {

    @Bean
    public AbstractTokenRetriever abstractTokensRetriever() {
        return new AbstractTokenRetriever() {

            @Override
            public Mono<Map<String, String>> retrieveTokens() {
                return Mono.just(
                    Map.of(
                        "exchange_token", "exchangeToken",
                        "access_token", "accessToken"
                    )
                );
            }
        };
    }

    @Bean
    public AbstractTokenSaver abstractTokensSaver() {
        return new AbstractTokenSaver(systemUTC()) {

            @Override
            public Mono<String> saveTokens(Map<String, String> tokens) {
                return Mono.just("saveTokens");
            }
        };
    }
}
