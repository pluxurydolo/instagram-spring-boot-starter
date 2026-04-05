package com.pluxurydolo.instagram.client;

import com.pluxurydolo.instagram.dto.request.upload.UploadMediaRequest;
import com.pluxurydolo.instagram.step.image.InstagramImageSender;
import com.pluxurydolo.instagram.step.video.InstagramVideoSender;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class InstagramClient {
    private final InstagramImageSender instagramImageUploader;
    private final InstagramVideoSender instagramVideoUploader;

    public InstagramClient(InstagramImageSender instagramImageUploader, InstagramVideoSender instagramVideoUploader) {
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
