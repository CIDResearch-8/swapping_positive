package com.swappingpositive;


import com.swappingpositive.fizzy.Dao;
import com.swappingpositive.login.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class CommentDao implements Dao<Comment> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean insert(Comment comment) {
        try {
            jdbcTemplate.update("INSERT INTO comment(user_id, date, comment) VALUES ( ?, ?, ?)",
                    comment.getUserId(),
                    comment.getDate(),
                    comment.getComment());
        } catch (DuplicateKeyException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Object commentId) {
        jdbcTemplate.update("DELETE FROM comment WHERE comment_id = ?", commentId);
        return selectByPrimaryKey(commentId) == null;
    }

    public void deleteUserComment(String userId) {
        jdbcTemplate.update("DELETE FROM comment WHERE user_id = ?", userId);
    }

    @Override
    public Comment selectByPrimaryKey(Object commentId) {
        List<Comment> list = selectByColumn("comment_id", commentId);
        //userIdが一致すればその行を返す(なければnullを返す)
        return list.stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Comment> selectByColumn(String columnName, Object value) {
        return jdbcTemplate
                .query(String.format("SELECT * FROM comment WHERE %s = ?", columnName),
                        //BeanPropertyRowMapperで自動的に
                        //データベースのカラムとJavaのフィールドを一致させる
                        new BeanPropertyRowMapper<>(Comment.class), value);
    }

    @Override
    public List<Comment> selectAll() {
        return jdbcTemplate.query("SELECT * FROM comment", new BeanPropertyRowMapper<>(Comment.class));
    }

    @Override
    public boolean updateByPrimaryKey (String columnName, Object source, Object key){
        try{
            jdbcTemplate.update(String.format("UPDATE comment SET %s = ? WHERE comment_id = ?", columnName), source, key);
            return true;
        }
        catch (DataAccessException e){
            return false;
        }
    }
    public List<Comment> selectUserComment(String userId) {
        return jdbcTemplate.query("SELECT * FROM comment WHERE user_id = ?", new BeanPropertyRowMapper<>(Comment.class), userId);
    }
}