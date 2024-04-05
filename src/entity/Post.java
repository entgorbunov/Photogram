package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private long id;
    private User user;
    private String caption;
    private Timestamp postTime;
    private String imageUrl;

}
