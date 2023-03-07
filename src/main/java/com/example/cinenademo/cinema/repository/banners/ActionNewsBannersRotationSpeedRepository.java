package com.example.cinenademo.cinema.repository.banners;

import com.example.cinenademo.cinema.model.banners.ActionNewsBannerRotationSpeed;
import com.example.cinenademo.cinema.model.banners.MainTopBannerRotationSpeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActionNewsBannersRotationSpeedRepository extends JpaRepository<ActionNewsBannerRotationSpeed, Long> {
    Optional<ActionNewsBannerRotationSpeed> findFirstById(Long id);
}
