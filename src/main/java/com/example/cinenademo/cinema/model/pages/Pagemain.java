package com.example.cinenademo.cinema.model.pages;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "pagemain")
public class Pagemain {
    @Id
    Long idpage;
    String name;
    Integer phonenumber1;
    Integer phonenumber2;
    String seotext;
    String url;
    String title;
    String keywords;
    String descript;
    String status;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date date;

    public Pagemain() {
    }

    public Pagemain(Long idpage, String name) {
        this.idpage = idpage;
        this.name = name;
        this.date = new Date();
    }
}
