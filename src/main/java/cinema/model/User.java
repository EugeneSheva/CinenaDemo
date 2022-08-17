package cinema.model;

import lombok.Data;

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
    String passwerd;
    Integer cardnumer;
    Language language;
    Gender gender;
    Integer phonenumber;
    Date dateofbirdth;
    String city;
    String repassword;




    public enum Gender {
        man,
        woman
    }
    public enum Language {
        russian,
        ukrainian
    }
}

