package cinema.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "film")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idfilm;
    String name;
    String description;
    byte[] mainpicture;
    byte[] picture1;
    byte[] picture2;
    byte[] picture3;
    byte[] picture4;
    byte[] picture5;
    String video;
    Type type;
    String url;
    String title;
    String keywords;
    String descript;

    public enum Type {
        d2,
        d3,
        IMAX
    }
}
