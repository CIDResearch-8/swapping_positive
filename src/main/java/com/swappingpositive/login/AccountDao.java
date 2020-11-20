package com.swappingpositive.login;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class AccountDao {
    protected final JdbcTemplate jdbcTemplate;

    @Autowired
    public AccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Account findById(String userId) {
        Map<String, Object> map = jdbcTemplate
                .queryForMap("SELECT * FROM account WHERE username = ?", userId);

        //usernameが一致すればその行を返す(なければnullを返す)
        return (Account) map.get(userId);
    }
    
    public Account authAccount(String userId, String password) {
        Map<String, Object> map = jdbcTemplate
                .queryForMap("SELECT * FROM account WHERE user_id = ?", userId);

        //パスワードの確認
        //パスワードが違う/ユーザー情報がなければnullを返す
        return map.get("password").equals(password) ?
                    (Account) map.get(userId) : null;
    }
}
