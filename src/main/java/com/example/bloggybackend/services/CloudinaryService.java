package com.example.bloggybackend.services;

import com.cloudinary.Cloudinary;
import com.example.bloggybackend.exception.CustomRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class CloudinaryService {


    private final Cloudinary cloudinary;

    public String generateSecureUrl(String publicId) {
        if (publicId == null) return null;
        return cloudinary.url().secure(true).generate(publicId);
    }

    public String uploadImage(MultipartFile file, String folderName) {

        try {
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", folderName);
            var uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);
            System.out.println(uploadedFile);
            System.out.println("The uploaded file is --> "+ uploadedFile);
            return (String) uploadedFile.get("public_id");
        } catch (Exception e) {
            return null;
        }

    }

    public void deleteImage(String imageUrl) {
        try {
            Map<Object, Object> options = new HashMap<>();
            options.put("folder", "react-write");
            cloudinary.uploader().destroy(imageUrl, options);
        } catch (Exception e) {
            throw new CustomRuntimeException("Could not destroy image in cloudinary", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
