package cinema.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "page")
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idpage;
    String name;
    String description;
    byte[] mainpicture;
    byte[] picture1;
    byte[] picture2;
    byte[] picture3;
    byte[] picture4;
    byte[] picture5;
    String url;
    String title;
    String keywords;
    String descript;
}
