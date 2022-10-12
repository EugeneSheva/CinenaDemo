package com.example.cinenademo.cinema.repository.banners;

import com.example.cinenademo.cinema.model.banners.MainTopBannerRotationSpeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MainTopBannersRotationSpeedRepository extends JpaRepository<MainTopBannerRotationSpeed, Integer> {
    Optional<MainTopBannerRotationSpeed> findFirstById(int id);
}
