package com.example.cinenademo.cinema.model.banners;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "main_top_banners")
@Data
public class MainTopBanner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String imagePath;
    private String url;
    private String text;
}
