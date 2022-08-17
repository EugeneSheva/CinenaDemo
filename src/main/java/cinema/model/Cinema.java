package cinema.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cinema")
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idcinema;
    String name;
    String description;
    String condition;
    byte[] logo;
    byte[] bunner;
    byte[] picture1;
    byte[] picture2;
    byte[] picture3;
    byte[] picture4;
    byte[] picture5;
    Integer hall;
    String url;
    String title;
    String keywords;
    String descript;
}
