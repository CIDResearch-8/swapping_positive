package com.swappingpositive.login;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class ResisterForm {
    @Getter @Setter @NonNull
    private String userId;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String email;

    @Getter @Setter @NonNull
    private String password;
}
