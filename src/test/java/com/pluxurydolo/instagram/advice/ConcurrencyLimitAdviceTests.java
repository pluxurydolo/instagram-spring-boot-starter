package com.pluxurydolo.instagram.advice;

import org.junit.jupiter.api.Test;
import org.springframework.http.ProblemDetail;

import java.time.Clock;
import java.time.Instant;

import static java.time.ZoneOffset.UTC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

class ConcurrencyLimitAdviceTests {
    private static final ConcurrencyLimitAdvice ADVICE = new ConcurrencyLimitAdvice(clock());

    @Test
    void testHandleConcurrencyLimit() {
        ProblemDetail result = ADVICE.handleConcurrencyLimit();

        assertThat(result)
            .usingRecursiveComparison()
            .isEqualTo(problemDetail());
    }

    private static ProblemDetail problemDetail() {
        ProblemDetail problemDetail = ProblemDetail.forStatus(TOO_MANY_REQUESTS);
        problemDetail.setDetail("Лимит запросов исчерпан");
        problemDetail.setTitle("Too Many Requests");
        problemDetail.setProperty("timestamp", instant().toString());
        return problemDetail;
    }

    private static Clock clock() {
        return Clock.fixed(instant(), UTC);
    }

    private static Instant instant() {
        return Instant.parse("1970-01-01T12:00:00Z");
    }
}
