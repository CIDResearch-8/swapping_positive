package com.swappingpositive.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountDao accountDao;

    @Override
    public UserDetails loadUserByUsername(String userId)
            throws UsernameNotFoundException {

        Account account = accountDao.findById(userId);
        if (account == null) {
            throw new UsernameNotFoundException("ユーザーが見つかりませんでした。");
        }

        return new LoginUser(account);
    }
}