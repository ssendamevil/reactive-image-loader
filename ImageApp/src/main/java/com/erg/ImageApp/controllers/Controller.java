package com.erg.ImageApp.controllers;

import com.erg.ImageApp.model.Image;
import com.erg.ImageApp.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class Controller {

    private final ImageService service;

    @PostMapping("/image/add")
    public String storeImage(@RequestParam(name="image")MultipartFile image){
        String id = service.storeImageToDb(image);
        return "Image "+ id +" stored successfully!";
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable String id, @RequestParam(name = "width") int width, @RequestParam(name = "height") int height) throws Exception {
        Image image = service.  getImage(id, width, height);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getFiletype() ))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFilename()+"\"")
                .body(new ByteArrayResource(image.getImageData()));
    }

}
