package com.swappingpositive.login;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountDao {
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    /**
     * userIdをもとに、accountテーブルからuserIdと同じアカウントを探査し、最初に見つけたものを返します。
     * なお、user_idは主キーのため、必ず一つが返ってきます。
     * @param userId 探したいユーザーID
     * @return userIdと一致したアカウント　なければnull
     */
    public Account findById(String userId) {
        List<Account> list = jdbcTemplate
                .query("SELECT * FROM account WHERE user_id = ?",
                        //BeanPropertyRowMapperで自動的に
                        //データベースのカラムとJavaのフィールドを一致させる
                        new BeanPropertyRowMapper<>(Account.class), userId);

        //userIdが一致すればその行を返す(なければnullを返す)
        return list.stream()
                .findFirst()
                    .orElse(null);
    }
    
    public Account authenticateAccount(String userId, String password) {
        Account account = findById(userId);
        //パスワードの確認
        //パスワードが違う/ユーザー情報がなければnullを返す
        if (account == null) {
            return null;
        }
        return passwordEncoder.matches(password, account.getPassword()) ?
                account : null;
    }

    public boolean create(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        jdbcTemplate.update("INSERT INTO account VALUES (?, ?, ?, ?)",
                account.getUserId(),
                account.getUsername(),
                account.getPassword(),
                account.getEmail());

        return findById(account.getUserId()) != null;
    }
}
