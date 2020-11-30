package com.swappingpositive.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@Component
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountDao accountDao;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        Account account = Optional.ofNullable(accountDao.findById(userId))
                .orElseThrow(() -> new UsernameNotFoundException("ユーザーが見つかりませんでした。"));

        return new LoginUser(account);
    }

    public void createAccount(ResisterForm resisterForm) {
        if (!resisterForm.getUserId().matches("^[a-zA-Z][a-zA-Z0-9_-]*")) {
            throw new IllegalArgumentException("ユーザーIDが不適切です");
        }

        accountDao.create(new Account(resisterForm.getUserId(),
                resisterForm.getUsername(),
                resisterForm.getEmail(),
                resisterForm.getPassword()));
        System.out.println(resisterForm.getUserId() + resisterForm.getPassword());
    }
}