package com.swappingpositive.login;

import java.util.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class LoginUser extends User {
    @Getter @Setter
    private Long userId;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private String password;

    public LoginUser(Account account) {
        super(account.getUsername(), account.getPassword(), true, true, true, true, new ArrayList<GrantedAuthority>());
        username = account.getUsername();
        password = account.getPassword();
        userId = account.getUserId();
        email = account.getEmail();
    }
}
