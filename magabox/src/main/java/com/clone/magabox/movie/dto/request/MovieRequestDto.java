package com.clone.magabox.movie.dto.request;

import org.springframework.web.multipart.MultipartFile;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class MovieRequestDto {
    private String title;
    private String summary;
    private MultipartFile file;
    private int runtime;
}
