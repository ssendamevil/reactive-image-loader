package com.erg.reactiveimage.loader.controllers;
import com.erg.reactiveimage.loader.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class Controller {

    private final ImageService imageService;

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Mono<ResponseEntity<String>> uploadImage(@RequestPart("file") Mono<FilePart> filePart){
        return imageService.uploadImage(filePart)
                .map((id) -> ok().body(id.toString()));
    }

    @GetMapping(value = "/download/{id}")
    public Mono<Void> downloadImage(@PathVariable(value = "id") String id, ServerWebExchange serverWebExchange){
        return imageService.downloadImage(id).flatMap(
                resource -> {
                    ServerHttpResponse response = serverWebExchange.getResponse();
                    response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + resource.getFilename() + "\"");
                    return response.writeWith(resource.getDownloadStream());
                }
        );
    }

    @GetMapping(value = "/download/resized/{id}")
    public Mono<Void> downloadImage(
            @PathVariable(value = "id") String id,
            @RequestParam("h") int newHeight, ServerWebExchange serverWebExchange){
        ServerHttpResponse response = serverWebExchange.getResponse();
//                    response.getHeaders().set(HttpHeaders.CONTENT_TYPE, resource.getOptions().getMetadata().getString("webp"));
        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + "resource.getFilename()" + "\"");
        return response.writeWith(imageService.downloadImage(id, newHeight));
    }
}
