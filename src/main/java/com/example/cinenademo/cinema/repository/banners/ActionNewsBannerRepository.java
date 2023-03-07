package com.example.cinenademo.cinema.repository.banners;

import com.example.cinenademo.cinema.model.banners.ActionNewsBanner;
import com.example.cinenademo.cinema.model.banners.MainTopBanner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ActionNewsBannerRepository extends JpaRepository<ActionNewsBanner, Long> {
    List<ActionNewsBanner> findAllByIdNotIn(Collection<Long> id);
}
