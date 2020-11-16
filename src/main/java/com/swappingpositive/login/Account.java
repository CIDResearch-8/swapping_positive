package com.swappingpositive.login;

import lombok.Getter;
import lombok.Setter;

public class Account {
    @Getter @Setter
    private Long userId;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private String password;
}
