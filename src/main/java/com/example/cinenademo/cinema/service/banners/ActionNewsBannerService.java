package com.example.cinenademo.cinema.service.banners;

import com.example.cinenademo.cinema.model.banners.ActionNewsBanner;
import com.example.cinenademo.cinema.model.banners.ActionNewsBannerRotationSpeed;
import com.example.cinenademo.cinema.model.banners.MainTopBanner;
import com.example.cinenademo.cinema.model.banners.MainTopBannerRotationSpeed;

import java.util.List;

public interface ActionNewsBannerService {
    List<ActionNewsBanner> findAll();

    ActionNewsBannerRotationSpeed findRotationSpeed();

    void saveAllAndRotationSpeed(ActionNewsBannersDto dto);
}
