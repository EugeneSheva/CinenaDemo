package cinema.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Data
@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idnews;
    String name;
    Date date;
    String description;
    byte[] mainpicture;
    byte[] picture1;
    byte[] picture2;
    byte[] picture3;
    byte[] picture4;
    byte[] picture5;
    String video;
    String url;
    String title;
    String keywords;
    String descript;
}
