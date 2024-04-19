package com.erg.reactiveimage.loader.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Image {
    @Id
    private String id;
    private String filename;
    private byte[] imageByte;

}
