package com.erg.ImageApp.repository;

import com.erg.ImageApp.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageRepository extends MongoRepository<Image, String> {
}
