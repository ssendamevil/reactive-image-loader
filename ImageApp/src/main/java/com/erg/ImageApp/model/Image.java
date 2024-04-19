package com.erg.ImageApp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="images")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Image {
    @Id
    private String id;
    private String filename;
    private String filetype;
    private long fileSize;
    private byte[] imageData;
}
