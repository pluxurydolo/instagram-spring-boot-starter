package com.pluxurydolo.instagram.base;

import com.pluxurydolo.instagram.util.LoadTestingResult;
import com.pluxurydolo.instagram.util.RequestExecutor;
import org.springframework.http.HttpStatusCode;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newVirtualThreadPerTaskExecutor;
import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.IntStream.range;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

public abstract class AbstractLoadTests extends AbstractControllerIntegrationTests {
    protected LoadTestingResult runConcurrent(int requestCount, RequestExecutor requestExecutor) {
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(requestCount);
        List<HttpStatusCode> statusCodes = new CopyOnWriteArrayList<>();

        Runnable runnable = () -> {
            try {
                startSignal.await();
                doneSignal.countDown();

                HttpStatusCode httpStatusCode = requestExecutor.execute(webTestClient);
                statusCodes.add(httpStatusCode);
            } catch (InterruptedException _) {
                statusCodes.add(INTERNAL_SERVER_ERROR);
            }
        };

        try (ExecutorService executorService = newVirtualThreadPerTaskExecutor()) {
            range(0, requestCount)
                .forEach(_ -> executorService.submit(runnable));

            doneSignal.await(5, SECONDS);

            startSignal.countDown();
            executorService.shutdown();
            executorService.awaitTermination(10, SECONDS);

            long success = countSuccessfulStatuses(statusCodes);
            long fail = countFailedStatuses(statusCodes);

            return new LoadTestingResult(success, fail);
        } catch (InterruptedException _) {
            return new LoadTestingResult(0, 0);
        }
    }

    private static long countSuccessfulStatuses(List<HttpStatusCode> statusCodes) {
        Set<HttpStatusCode> successfulStatuses = Set.of(OK, FOUND);
        return countByStatuses(statusCodes, successfulStatuses);
    }

    private static long countFailedStatuses(List<HttpStatusCode> statusCodes) {
        Set<HttpStatusCode> failedStatuses = Set.of(TOO_MANY_REQUESTS, INTERNAL_SERVER_ERROR);
        return countByStatuses(statusCodes, failedStatuses);
    }

    private static long countByStatuses(List<HttpStatusCode> statusCodes, Set<HttpStatusCode> target) {
        return statusCodes.stream()
            .filter(target::contains)
            .count();
    }
}
