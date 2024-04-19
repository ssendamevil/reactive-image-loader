package com.erg.ImageApp.services;

import com.erg.ImageApp.model.Image;
import com.erg.ImageApp.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public String storeImageToDb(MultipartFile file){
        try{
            byte[] imageData  = file.getBytes();

            Image image = Image.builder()
                    .filename(file.getOriginalFilename())
                    .filetype(file.getContentType())
                    .fileSize(file.getSize())
                    .imageData(imageData)
                    .build();
            imageRepository.save(image);
            return image.getId();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public Image getImage(String id, int width, int height) throws Exception {
        Optional<Image> optionalImage = imageRepository.findById(id);
        if(optionalImage.isPresent()){
            Image image = optionalImage.get();

            try{
                byte[] resizedImageData = resizeImage(image.getImageData(), width, height);
                image.setImageData(resizedImageData);
                return image;
            }catch (IOException e){
                e.printStackTrace();
                return null;
            }



        }else{
            throw new Exception("Image not found with id: " + id);
        }
    }

    private byte[] resizeImage(byte[] imageData, int width, int height) throws IOException {
        System.out.println(Arrays.toString(imageData));
        InputStream inputStream = new ByteArrayInputStream(imageData);

        BufferedImage originalImage = ImageIO.read(inputStream);

        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = resizedImage.createGraphics();

        graphics.drawImage(originalImage.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH), 0, 0, width, height, null);
        graphics.dispose();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "webp", outputStream);
        return outputStream.toByteArray();
    }
}
