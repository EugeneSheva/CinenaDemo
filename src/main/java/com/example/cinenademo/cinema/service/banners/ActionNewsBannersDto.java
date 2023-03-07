package com.example.cinenademo.cinema.service.banners;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ActionNewsBannersDto {
    List<Long> ids;
    List<MultipartFile> images;
    List<String> urls;
    Integer rotationSpeed;
    Boolean status;
}
