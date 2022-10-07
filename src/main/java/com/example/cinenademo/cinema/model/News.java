package com.example.cinenademo.cinema.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
@Data
@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idnews;
    String name;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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
