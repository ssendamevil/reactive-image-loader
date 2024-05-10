package com.erg.reactiveimage.loader.services;

import org.bson.types.ObjectId;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsResource;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public interface ImageService {

    Mono<ObjectId> uploadImage(Mono<FilePart> filePart);

    Flux<DataBuffer> downloadImage(String id, int newH);

    Mono<ReactiveGridFsResource> downloadImage(String id);
}
