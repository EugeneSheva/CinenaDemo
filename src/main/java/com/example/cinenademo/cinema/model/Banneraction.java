package com.example.cinenademo.cinema.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "banneraction")
public class Banneraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idbanneraction;
    String picture;
    Integer speed;
    Boolean status;
    }
