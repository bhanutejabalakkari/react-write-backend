package com.example.bloggybackend.dtos.auth.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;



@AllArgsConstructor
@Data
public class CreatePostRequest {

    @JsonProperty(value = "title")
    @NotNull
    @NotBlank
    @NotEmpty
    private String title;

    @NotNull
    @NotBlank
    @NotEmpty
    @JsonProperty(value = "slug")
    private String slug;

    @NotNull
    @NotBlank
    @NotEmpty
    @JsonProperty(value = "content")
    private String content;

    @JsonProperty(value = "active")
    @NonNull
    @NotEmpty
    private Boolean active;

    @JsonProperty(value = "image")
    @NonNull
    private MultipartFile image;


}
