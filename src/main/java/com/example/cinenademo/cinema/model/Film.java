package com.example.cinenademo.cinema.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "film")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idfilm;
    String name;
    String description;
    String mainpicture;
    String picture1;
    String picture2;
    String picture3;
    String picture4;
    String picture5;
    String video;
    String type;
    String url;
    String title;
    String keywords;
    String descript;

    public enum Type {
        d2,
        d3,
        IMAX
    }
}
