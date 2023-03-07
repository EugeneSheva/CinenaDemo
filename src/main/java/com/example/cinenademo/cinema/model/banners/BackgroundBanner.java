package com.example.cinenademo.cinema.model.banners;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "background_banners")
public class BackgroundBanner {

    @Id
    private Long id;
    private String imagePath;

}
