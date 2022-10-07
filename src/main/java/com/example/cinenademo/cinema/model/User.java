package com.example.cinenademo.cinema.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idusers;
    String firstname;
    String lastname;
    String nickname;
    String email;
    String adress;
    String password;
    Integer cardnumber;
    String language;
    String gender;
    Integer phonenumber;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date dateofbirth;
    String city;
    String repassword;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date dateofregistry;




    public enum Gender {
        man,
        woman
    }
    public enum Language {
        russian,
        ukrainian
    }
}

