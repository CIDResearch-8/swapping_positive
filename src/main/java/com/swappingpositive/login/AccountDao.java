package com.swappingpositive.login;

import java.util.*;

import com.swappingpositive.fizzy.Dao;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountDao implements Dao<Account> {
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Override
    public List<Account> selectByColumn(String columnName, Object value) {
        final String FORMATTED_COLUMN_NAME = String.format("%s", columnName);
        return jdbcTemplate
                .query("SELECT * FROM account WHERE " + FORMATTED_COLUMN_NAME + " = ?",
                        //BeanPropertyRowMapperで自動的に
                        //データベースのカラムとJavaのフィールドを一致させる
                        new BeanPropertyRowMapper<>(Account.class), value);
    }

    /**
     * userIdをもとに、accountテーブルからuserIdと同じアカウントを探査し、最初に見つけたものを返します。
     * なお、user_idは主キーのため、必ず一つが返ってきます。
     * @param key 探したいユーザーID
     * @return userIdと一致したアカウント　なければnull
     */
    @Override
    public Account selectByPrimaryKey(Object key) {
        List<Account> list = selectByColumn("user_id", key);

        //userIdが一致すればその行を返す(なければnullを返す)
        return list.stream()
                .findFirst()
                    .orElse(null);
    }

    public Account authenticateAccount(String userId, String password) {
        Account account = selectByPrimaryKey(userId);

        //パスワードの確認
        //パスワードが違う/ユーザー情報がなければnullを返す
        if (account == null) {
            return null;
        }
        return passwordEncoder.matches(password, account.getPassword()) ?
                account : null;
    }

    public boolean insert(@NonNull Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        try {
            jdbcTemplate.update("INSERT INTO account VALUES (?, ?, ?, ?)",
                    account.getUserId(),
                    account.getUsername(),
                    account.getPassword(),
                    account.getEmail(),
                    account.getIconUri());
        }
        catch (DuplicateKeyException e) {
            return false;
        }
        return true;
    }

    public boolean delete(Object id) {
        jdbcTemplate.update("DELETE FROM account WHERE user_id = ?", id);

        return selectByPrimaryKey(id) == null;
    }

    @Override
    public List<Account> selectAll() {
        return jdbcTemplate.query("SELECT * FROM account", new BeanPropertyRowMapper<>(Account.class));
    }

    //更新
    public boolean update(String columnName,String date,Object id) {
        jdbcTemplate.update("UPDATE account SET ? = ? WHERE user_id = ?", columnName,date,id);

        return selectByPrimaryKey(id) == null;
    }


}
