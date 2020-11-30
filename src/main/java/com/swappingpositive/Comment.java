package com.swappingpositive;
import lombok.*;
public class Comment {
    @Getter @Setter
    private String message;

    @Getter @Setter
    private String userID;

    @Getter @Setter
    private int date;
}
