package com.example.cinenademo.cinema.model.pages;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "pageuser")
public class Pageuser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idpage;
    String name;
    String description;
    String mainpicture;
    String picture1;
    String picture2;
    String picture3;
    String picture4;
    String picture5;
    String url;
    String title;
    String keywords;
    String descript;
    String status;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date date;

    public Pageuser() {
    }

    public Pageuser(String name) {
        this.name = name;
        this.date = new Date();
    }
}
