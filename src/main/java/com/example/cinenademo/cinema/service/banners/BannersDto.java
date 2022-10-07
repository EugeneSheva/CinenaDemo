package com.example.cinenademo.cinema.service.banners;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class BannersDto {
    List<MultipartFile> image;
    List<String> url;
    List<String> text;
}
