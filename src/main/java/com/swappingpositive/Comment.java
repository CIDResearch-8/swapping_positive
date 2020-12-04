package com.swappingpositive;
import lombok.*;
public class Comment {
    @Getter
    @Setter
    private String comment;

    @Getter
    @Setter
    private String userId;

    @Getter
    @Setter
    private int commentId;

    @Getter
    @Setter
    private int date;
}
