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

    public Account findByNo(String username) {
        Map<String, Object> map = jdbcTemplate
                .queryForMap("SELECT * FROM account WHERE user_id = ?", username);

        //user_idが一致すればその行を返す
        if (!map.isEmpty()) {
            return (Account) map.get(username);
        }
        //なければnullを返す
        else {
            return null;
        }
    }
    
    public Account authAccount(String username, String password) {
        Map<String, Object> map = jdbcTemplate
                .queryForMap("SELECT * FROM account WHERE user_id = ?", username);

        //パスワードの確認
        if (!map.isEmpty() && map.get("password").equals(password)) {
            return (Account) map.get(username);
        }
        //パスワードが違う/ユーザー情報がなければnullを返す
        else {
            return null;
        }
    }
}
