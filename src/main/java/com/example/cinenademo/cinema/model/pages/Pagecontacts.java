package com.example.cinenademo.cinema.model.pages;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "pagecontacts")
public class Pagecontacts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idpage;
    String name;
    String description;
    String coordinates;
    String mainpicture;
    String status;


}
