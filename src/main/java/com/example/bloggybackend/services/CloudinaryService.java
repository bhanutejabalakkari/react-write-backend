package com.example.bloggybackend.services;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;


@Service
@RequiredArgsConstructor
public class CloudinaryService {


    private final Cloudinary cloudinary;

    public String uploadImage(MultipartFile file, String folderName) {

        try {
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", folderName);
            var uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);
            System.out.println("The uploaded file is --> "+ uploadedFile);
            String publicId = (String) uploadedFile.get("public_id");
            return cloudinary.url().secure(true).generate(publicId);

        } catch (Exception e) {
            return null;
        }

    }

//    public void deleteImage(String imageUrl) {
//        try {
//            cloudinary.uploader().destroy(imageUrl);
//        }
//    }

}
