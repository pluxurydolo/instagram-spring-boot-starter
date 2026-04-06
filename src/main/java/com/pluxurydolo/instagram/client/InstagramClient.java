package com.pluxurydolo.instagram.client;

import com.pluxurydolo.instagram.dto.request.upload.UploadMediaRequest;
import com.pluxurydolo.instagram.step.image.InstagramImageUploader;
import com.pluxurydolo.instagram.step.video.InstagramVideoUploader;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class InstagramClient {
    private final InstagramImageUploader instagramImageUploader;
    private final InstagramVideoUploader instagramVideoUploader;

    public InstagramClient(
        InstagramImageUploader instagramImageUploader,
        InstagramVideoUploader instagramVideoUploader
    ) {
        this.instagramImageUploader = instagramImageUploader;
        this.instagramVideoUploader = instagramVideoUploader;
    }

    public Mono<String> uploadImage(UploadMediaRequest request) {
        return instagramImageUploader.upload(request)
            .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<String> uploadVideo(UploadMediaRequest request) {
        return instagramVideoUploader.upload(request)
            .subscribeOn(Schedulers.boundedElastic());
    }
}
