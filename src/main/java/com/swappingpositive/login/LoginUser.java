package com.swappingpositive.login;

import java.util.*;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LoginUser extends User {

    private static final long serialVersionUID = 1L;

    @Getter @Setter @NonNull
    private String userId;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String email;

    @Getter @Setter @NonNull
    private String password;

    public LoginUser(Account account) {
        super(account.getUsername(), account.getPassword(), true, true, true, true, new ArrayList<GrantedAuthority>());
        username = account.getUsername();
        password = account.getPassword();
        userId = account.getUserId();
        email = account.getEmail();
    }
}
