package com.pluxurydolo.instagram.advice;

import org.springframework.http.ProblemDetail;
import org.springframework.resilience.InvocationRejectedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Clock;

import static java.time.Instant.now;
import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;
import static org.springframework.http.ProblemDetail.forStatusAndDetail;

@RestControllerAdvice
public class ConcurrencyLimitAdvice {
    private final Clock clock;

    public ConcurrencyLimitAdvice(Clock clock) {
        this.clock = clock;
    }

    @ExceptionHandler(InvocationRejectedException.class)
    @ResponseStatus(TOO_MANY_REQUESTS)
    public ProblemDetail handleConcurrencyLimit() {
        String timestamp = now(clock)
            .toString();

        ProblemDetail problemDetail = forStatusAndDetail(TOO_MANY_REQUESTS, "Лимит запросов исчерпан");
        problemDetail.setTitle("Too Many Requests");
        problemDetail.setProperty("timestamp", timestamp);
        return problemDetail;
    }
}
