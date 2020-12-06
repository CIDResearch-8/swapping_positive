package com.swappingpositive;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class CommentForm {
    @Getter @Setter @NonNull
    private String comment;

    @Getter @Setter @NonNull
    private String userId;
}

