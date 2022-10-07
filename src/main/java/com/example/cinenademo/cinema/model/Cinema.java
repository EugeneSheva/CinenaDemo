package com.example.cinenademo.cinema.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "cinema")
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idcinema;
    String name;
    String description;
    String cinemacondition;
    String mainpicture;
    String bunner;
    String picture1;
    String picture2;
    String picture3;
    String picture4;
    String picture5;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cinema", fetch = FetchType.EAGER)
    List<Hall>halls;
    String url;
    String title;
    String keywords;
    String descript;

    public void addHallToCinema (Hall hall) {
        if (halls == null) {
            halls=new ArrayList<>();
        } halls.add(hall);
        hall.setCinema(this);
    }
    public void delHallFromCinema (Hall hall) {
        halls.remove(hall);
        if (hall!=null) {
            hall.setCinema(null);
        }
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "idcinema=" + idcinema +
                ", name='" + name + '\'' +
                '}';
    }
}
