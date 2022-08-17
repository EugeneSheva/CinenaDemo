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
    byte[] mainpicture;
    byte[] picture1;
    byte[] picture2;
    byte[] picture3;
    byte[] picture4;
    byte[] picture5;
    String video;
    String url;
    String title;
    String keywords;
    String descript;
}
