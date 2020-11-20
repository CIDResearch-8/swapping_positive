package com.swappingpositive.login;

import lombok.Getter;
import lombok.Setter;

public class Account {
    @Getter @Setter
    private String userId;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private String password;
}
