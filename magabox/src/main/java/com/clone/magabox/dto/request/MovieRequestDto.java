package com.clone.magabox.dto.request;

import org.springframework.web.multipart.MultipartFile;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class MovieRequestDto {
    private String title;
    private String desc;
    private MultipartFile file;
    private int runtime;
}