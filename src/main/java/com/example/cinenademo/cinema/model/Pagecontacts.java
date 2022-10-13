package com.example.cinenademo.cinema.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "pagecontacts")
public class Pagecontacts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idpage;
    String name;
    String description;
    Integer coordinates;
    String mainpicture;
    Integer page;



}
