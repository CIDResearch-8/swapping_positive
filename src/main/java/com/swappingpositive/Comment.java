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

    public Comment(String userId, String comment) {
        this.date = new Timestamp(new java.util.Date().getTime());
        this.userId = userId;
        this.comment = comment;
    }

    public Comment() {}

}

class AjaxComment {
    @JsonProperty("userId")
    @Getter @Setter
    private String userId;

    @JsonProperty("inputText")
    @Getter @Setter
    private String inputText;
}
