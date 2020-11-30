package com.swappingpositive.login;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class Account {
    @Getter @Setter @NonNull
    private String userId;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String email;

    @Getter @Setter @NonNull
    private String password;

    public Account(String userId, String username, String email, String password) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public Account() {
    }
}
