package com.example.cinenademo.cinema.model.banners;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "news_actions_banners")
public class ActionNewsBanner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imagePath;
    private String url;
}
