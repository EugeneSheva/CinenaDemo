package com.example.cinenademo.cinema.model.banners;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "main_top_banners_rotation_speed")
@Data
public class MainTopBannerRotationSpeed {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id = 1;
    private int rotationSpeed = 1;
}
