package com.swappingpositive.login;

import lombok.Getter;
import lombok.Setter;

public class LoginUser {
    @Getter @Setter
    private Long userId;

    @Getter @Setter
    private String userName;

    @Getter @Setter
    private String mailAdress;

    @Getter @Setter
    private String password;
}
