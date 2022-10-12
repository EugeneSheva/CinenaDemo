package com.example.cinenademo.cinema.repository.banners;

import com.example.cinenademo.cinema.model.banners.MainTopBanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface MainTopBannerRepository extends JpaRepository<MainTopBanner, Integer> {
    List<MainTopBanner> findAllByIdNotIn(Collection<Integer> id);
}
