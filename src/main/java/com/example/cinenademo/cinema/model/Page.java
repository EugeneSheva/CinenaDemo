package com.example.cinenademo.cinema.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "page")
public class Page {
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
    Integer phonenumber1;
    Integer phonenumber2;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "page")
    List<Pagecontacts> cinemacontacts;
    String status;
    Boolean notremovable;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date date;
    Boolean ismain;
    Boolean iscontact;

}
