package com.swappingpositive.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationProviderImpl.class);

    @Autowired
    private AccountDao accountDao;

    @Override
    public Authentication authenticate(Authentication auth)
            throws AuthenticationException {

        String id = auth.getName();
        String password = auth.getCredentials().toString();

        if (id.isEmpty() || password.isEmpty()) {
            // 例外はSpringSecurityにあったものを適当に使用
            throw new AuthenticationCredentialsNotFoundException("ログイン情報に不備があります。");
        }

        Account account = Optional.ofNullable(accountDao.authenticateAccount(id, password))
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("ログイン情報が存在しません。"));

        return new UsernamePasswordAuthenticationToken(new LoginUser(account), password, auth.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> token) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(token);
    }
}
