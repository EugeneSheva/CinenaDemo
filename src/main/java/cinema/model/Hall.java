package cinema.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "hall")
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idhall;
    String name;
    String description;
    byte[] schema;
    byte[] bunner;
    byte[] picture1;
    byte[] picture2;
    byte[] picture3;
    byte[] picture4;
    byte[] picture5;
    Integer cinema;
    String url;
    String title;
    String keywords;
    String descript;
}
