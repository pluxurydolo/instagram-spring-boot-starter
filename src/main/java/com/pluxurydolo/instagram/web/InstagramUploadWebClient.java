package com.pluxurydolo.instagram.web;

import com.pluxurydolo.instagram.dto.request.upload.CreateContainerRequest;
import com.pluxurydolo.instagram.dto.request.upload.PublishContainerRequest;
import com.pluxurydolo.instagram.dto.response.ContainerResponse;
import com.pluxurydolo.instagram.dto.response.ContainerStatusResponse;
import com.pluxurydolo.instagram.exception.InstagramCreateImageContainerException;
import com.pluxurydolo.instagram.exception.InstagramImageContainerStatusException;
import com.pluxurydolo.instagram.exception.InstagramPublishImageContainerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.web.reactive.function.BodyInserters.fromFormData;

public class InstagramUploadWebClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(InstagramUploadWebClient.class);

    private final WebClient webClient;

    public InstagramUploadWebClient() {
        this.webClient = WebClient.builder()
            .baseUrl("https://graph.facebook.com")
            .codecs(configurer -> configurer
                .defaultCodecs()
                .maxInMemorySize(16 * 1024 * 1024))
            .build();
    }

    public Mono<ContainerResponse> createImageContainer(CreateContainerRequest request) {
        String imageUrl = request.mediaUrl();
        String caption = request.caption();
        String userId = request.userId();
        String accessToken = request.accessToken();

        return webClient.post()
            .uri("/v20.0/{userId}/media", userId)
            .body(fromFormData("image_url", imageUrl)
                .with("access_token", accessToken)
                .with("caption", caption))
            .retrieve()
            .bodyToMono(ContainerResponse.class)
            .doOnSuccess(_ -> LOGGER.info("erhs [instagram-starter] Контейнер изображения {} успешно создан", imageUrl))
            .onErrorResume(throwable -> {
                LOGGER.error("dhwr [instagram-starter] Произошла ошибка при создании контейнера изображения {}", imageUrl);
                return Mono.error(new InstagramCreateImageContainerException(throwable));
            });
    }

    public Mono<ContainerResponse> createVideoContainer(CreateContainerRequest request) {
        String videoUrl = request.mediaUrl();
        String caption = request.caption();
        String userId = request.userId();
        String accessToken = request.accessToken();

        return webClient.post()
            .uri("/v20.0/{userId}/media", userId)
            .contentType(APPLICATION_FORM_URLENCODED)
            .body(fromFormData("media_type", "REELS")
                .with("video_url", videoUrl)
                .with("access_token", accessToken)
                .with("caption", caption)
                .with("thumb_offset", "0")
                .with("share_to_feed", "true"))
            .retrieve()
            .bodyToMono(ContainerResponse.class)
            .doOnSuccess(_ -> LOGGER.info("xznj [instagram-starter] Контейнер видео {} успешно создан", videoUrl))
            .onErrorResume(throwable -> {
                LOGGER.error("plei [instagram-starter] Произошла ошибка при создании контейнера видео {}", videoUrl);
                return Mono.error(new InstagramCreateImageContainerException(throwable));
            });
    }

    public Mono<ContainerResponse> publishContainer(PublishContainerRequest request) {
        String containerId = request.containerId();
        String userId = request.userId();
        String accessToken = request.accessToken();

        return webClient.post()
            .uri("/v20.0/{userId}/media_publish", userId)
            .body(fromFormData("creation_id", containerId)
                .with("access_token", accessToken))
            .retrieve()
            .bodyToMono(ContainerResponse.class)
            .doOnSuccess(_ -> LOGGER.info("ynup [instagram-starter] Контейнер {} успешно опубликован", containerId))
            .onErrorResume(throwable -> {
                LOGGER.error("ervb [instagram-starter] Произошла ошибка при публикации контейнера {}", containerId);
                return Mono.error(new InstagramPublishImageContainerException(throwable));
            });
    }

    public Mono<ContainerStatusResponse> getContainerStatus(String containerId, String accessToken) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/v20.0/{containerId}")
                .queryParam("fields", "status_code,status")
                .queryParam("access_token", accessToken)
                .build(containerId))
            .retrieve()
            .bodyToMono(ContainerStatusResponse.class)
            .doOnSuccess(_ -> LOGGER.info("jvkg [instagram-starter] Статус контейнера {} успешно получен", containerId))
            .onErrorResume(throwable -> {
                LOGGER.error("ykyn [instagram-starter] Произошла ошибка при проверке статуса контейнера {}", containerId);
                return Mono.error(new InstagramImageContainerStatusException(throwable));
            });
    }
}
