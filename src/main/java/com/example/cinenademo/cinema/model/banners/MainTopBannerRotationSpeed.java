package com.example.cinenademo.cinema.model.banners;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "main_top_banners_rotation_speed")
@Data
public class MainTopBannerRotationSpeed {
    @Id
    private Long id = 1L;
    private int rotationSpeed = 1;
    private Boolean status = true;
}
