package com.example.cinenademo.cinema.model;

import javax.persistence.*;
@Entity
@Table(name = "bannermiddle")
public class Bannermiddle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idbannermiddle;
    String picture;

}
