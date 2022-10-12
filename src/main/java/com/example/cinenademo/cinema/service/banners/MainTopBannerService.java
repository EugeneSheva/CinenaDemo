package com.example.cinenademo.cinema.service.banners;

import com.example.cinenademo.cinema.model.banners.MainTopBanner;
import com.example.cinenademo.cinema.model.banners.MainTopBannerRotationSpeed;

import java.util.List;

public interface MainTopBannerService {
    List<MainTopBanner> findAll();
    MainTopBannerRotationSpeed findRotationSpeed();
    void saveAllAndRotationSpeed(MainTopBannersDto dto);
}
