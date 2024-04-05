package entity;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class Comment {
    private int id;
    private int postId;
    private int userId;
    private String text;
    private Timestamp commentTime;
}
