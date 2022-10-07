package com.example.cinenademo.cinema.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Data
@Entity
@Table(name = "action")
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idaction;
    String name;
    Date date;
    String description;
    String mainpicture;
    String picture1;
    String picture2;
    String picture3;
    String picture4;
    String picture5;
    String video;
    String url;
    String title;
    String keywords;
    String descript;
}
