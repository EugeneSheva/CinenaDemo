package com.example.cinenademo.cinema.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "hall")
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idhall;
    String name;
    String description;
    String hallschema;
    String bunner;
    String picture1;
    String picture2;
    String picture3;
    String picture4;
    String picture5;
    @ManyToOne(fetch = FetchType.EAGER)
            @JoinColumn (name = "cinemaid")
    Cinema cinema;
    String url;
    String title;
    String keywords;
    String descript;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date dateofregistry;
}
