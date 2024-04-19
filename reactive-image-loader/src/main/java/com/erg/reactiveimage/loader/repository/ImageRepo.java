package com.erg.reactiveimage.loader.repository;

import com.erg.reactiveimage.loader.model.Image;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ImageRepo extends ReactiveMongoRepository<Image, String> {
}
