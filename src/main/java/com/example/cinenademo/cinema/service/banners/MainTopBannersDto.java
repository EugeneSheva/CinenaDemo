package com.example.cinenademo.cinema.service.banners;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class MainTopBannersDto {
    List<Integer> ids;
    List<MultipartFile> images;
    List<String> urls;
    List<String> texts;
    Integer rotationSpeed;
}
