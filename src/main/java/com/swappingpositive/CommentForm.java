package com.swappingpositive;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class CommentForm {
    @Getter @Setter @NonNull
    private String comment;

    public CommentForm(String text) {
        this.comment = text;
    }
    public CommentForm() {}

    //空文字をnullに変換する
    private String emptyToNull(String value) {
        return value.isEmpty() ? null : value;
    }

    public void emptyToNullField() {
        this.comment = emptyToNull(comment);
    }
}

