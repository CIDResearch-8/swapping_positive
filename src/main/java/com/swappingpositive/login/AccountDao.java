package com.swappingpositive.login;

import java.util.*;

import com.swappingpositive.abstracts.AbstractDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountDao implements AbstractDao {
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    /**
     * userIdをもとに、accountテーブルからuserIdと同じアカウントを探査し、最初に見つけたものを返します。
     * なお、user_idは主キーのため、必ず一つが返ってきます。
     * @param id 探したいユーザーID
     * @return userIdと一致したアカウント　なければnull
     */
    @Override
    public Account getById(Object id) {
        if (!(id instanceof String)) {
            return null;
        }
        String userId = (String) id;

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
        Account account = getById(userId);
        //パスワードの確認
        //パスワードが違う/ユーザー情報がなければnullを返す
        if (account == null) {
            return null;
        }
        return passwordEncoder.matches(password, account.getPassword()) ?
                account : null;
    }

    public boolean insert(Object account) {
        //そもそもAccountインスタンスでない場合はfalseを返す
        if (!(account instanceof Account)) {
            return false;
        }
        Account certainAccount = (Account) account;

        certainAccount.setPassword(passwordEncoder.encode(certainAccount.getPassword()));
        try {
            jdbcTemplate.update("INSERT INTO account VALUES (?, ?, ?, ?)",
                    certainAccount.getUserId(),
                    certainAccount.getUsername(),
                    certainAccount.getPassword(),
                    certainAccount.getEmail());
        }
        catch (DuplicateKeyException e) {
            return false;
        }
        return true;
    }

    @Override
    public List<Account> getAll() {
        return jdbcTemplate.query("SELECT * FROM account", new BeanPropertyRowMapper<>(Account.class));
    }
}
