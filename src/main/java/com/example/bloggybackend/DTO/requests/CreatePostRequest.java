package com.example.bloggybackend.DTO.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;




import org.springframework.web.multipart.MultipartFile;



@AllArgsConstructor
@Data
public class CreatePostRequest {

    @JsonProperty(value = "title")
    @NotBlank(message = "Please provide a valid title")
    private String title;


    @NotBlank(message = "Please provide a valid slug")
    @JsonProperty(value = "slug")
    private String slug;


    @NotBlank(message = "Please provide a valid content")
    @JsonProperty(value = "content")
    private String content;

    @JsonProperty(value = "active")
    @NotNull(message = "Please provide active")
    private Boolean active;

    @JsonProperty(value = "image")
    @NotNull(message = "Please provide image")
    private MultipartFile image;


}
