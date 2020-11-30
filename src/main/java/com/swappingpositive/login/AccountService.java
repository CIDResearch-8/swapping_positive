package com.swappingpositive.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    }
}