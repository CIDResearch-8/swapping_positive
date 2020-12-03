package com.swappingpositive.login;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Objects;

public class RegisterForm {
    @Getter @Setter @NonNull
    private String userId;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String email;

    @Getter @Setter @NonNull
    private String password;

    private String emptyToNull(String value) {
        return value.isEmpty() ? null : value;
    }

    public void emptyToNullField() {
        this.userId = emptyToNull(this.userId);
        this.username = emptyToNull(this.username);
        this.email = emptyToNull(this.email);
        this.password = emptyToNull(this.password);
    }
}
