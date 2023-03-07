package com.example.cinenademo.cinema.model.banners;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "news_actions_banners_rotation_speed")
@Data
public class ActionNewsBannerRotationSpeed {
    @Id
    private Long id = 1L;
    private int rotationSpeed = 1;
    private Boolean status = true;
}
