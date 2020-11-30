package com.swappingpositive.login;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class LoginForm {

    @Getter @Setter @NonNull
    private String userId;

    @Getter @Setter @NonNull
    private String password;
}
