package com.example.cinenademo.cinema.model;

import lombok.Data;

import javax.persistence.*;
import java.io.File;

@Data
@Entity
@Table(name = "bannermain")
public class Bannermain {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idbannermain")
    Long idbannermain;
    String picture;
    String url;
    String text;
    Integer speed;
    Boolean status;
    File filetmp;

    @Override
    public String toString() {
        return "Bannermain{" +
                "id=" + idbannermain +
                ", name='" + picture + '\'' +
                ", url='" + url + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}

