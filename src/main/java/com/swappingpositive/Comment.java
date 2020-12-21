package com.swappingpositive;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Timestamp;

public class Comment {
    @Getter @Setter
    private int commentId;

    @Getter @Setter
    private String userId;

    @Getter @Setter
    private String comment;

    @Getter @Setter
    private Timestamp date;

    @Getter @Setter
    private Integer replyParentId;

    public Comment(String userId, String comment) {
        this.date = new Timestamp(new java.util.Date().getTime());
        this.userId = userId;
        this.comment = comment;
        this.replyParentId = -1;
    }

    public Comment() {}

}

