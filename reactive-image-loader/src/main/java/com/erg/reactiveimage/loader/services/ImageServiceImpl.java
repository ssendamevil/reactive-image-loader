package com.erg.reactiveimage.loader.services;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.imgscalr.Scalr;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.*;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{

    private final ReactiveGridFsTemplate reactiveGridFsTemplate;
    private final ReactiveGridFsOperations reactiveGridFsOperations;
    @Override
    public Mono<ObjectId> uploadImage(Mono<FilePart> filePart) {
        return filePart
                .flatMap(part -> reactiveGridFsTemplate.store(part.content(), part.filename()));
    }

    @Override
    public Flux<DataBuffer> downloadImage(String id, int newH) {

        Mono<GridFSFile> gridFSFileMono = reactiveGridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));

        return gridFSFileMono.flatMapMany(gridFSFile -> reactiveGridFsOperations.getResource(gridFSFile)
                .flatMap(gridFsResource -> gridFsResource.getInputStream()
                        .publishOn(Schedulers.boundedElastic())
                        .handle((inputStream, sink) -> {
                            try {
                                BufferedImage originalImage = ImageIO.read(inputStream);
                                int newW = (newH*originalImage.getWidth())/originalImage.getHeight();
                                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                BufferedImage output = Scalr.resize(originalImage, Scalr.Method.ULTRA_QUALITY, newW, newH);
                                ImageIO.write(output, "webp", outputStream);
                                byte[] resizedBytes = outputStream.toByteArray();
                                outputStream.close();
                                sink.next(new DefaultDataBufferFactory().wrap(resizedBytes));
                            } catch (IOException e) {
                                sink.error(new RuntimeException(e));
                            }
                        })
                ));
    }

    @Override
    public Mono<ReactiveGridFsResource> downloadImage(String id) {
        return reactiveGridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)))
                .flatMap(reactiveGridFsOperations::getResource);
    }
}
