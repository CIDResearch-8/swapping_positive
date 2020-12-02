package com.swappingpositive.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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

        Account account = Optional.ofNullable(accountDao.selectByPrimaryKey(userId))
                .orElseThrow(() -> new UsernameNotFoundException("ユーザーが見つかりませんでした。"));

        return new LoginUser(account);
    }

    public void createAccount(RegisterForm registerForm) {
        if (!registerForm.getUserId().matches("^[a-zA-Z][a-zA-Z0-9_-]*")) {
            throw new IllegalArgumentException("ユーザーIDが不適切です");
        }
        if (!accountDao.insert(new Account(registerForm.getUserId(),
                registerForm.getUsername(),
                registerForm.getEmail(),
                registerForm.getPassword()))) {
            throw new DuplicateKeyException("このユーザーIDは既に存在しています");
        }
        System.out.println(registerForm.getUserId() + registerForm.getPassword());
        accountDao.insert(null);
    }
}