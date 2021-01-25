package com.swappingpositive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Component
public class CommentService {

    @Autowired
    private CommentDao dao;

    public void save(CommentForm form, String userId) {
        form.emptyToNullField();
        //ツイートをSQLに登録
        dao.insert(new Comment(
                userId,
                form.getComment()));
    }

    public Comment findById(int id) {
        return dao.selectByPrimaryKey(id);//タイムライン表示
    }

    public List<Comment> findByReplyParentId(int id) {
        return dao.selectByColumn("reply_parent_id",id);//タイムライン表示
    }

    public List<Comment> findAll() {
        return dao.selectAll();//タイムライン表示
    }

    public List<Comment> findByUser(String userId) {
        return dao.selectByColumn("user_id", userId);//ユーザーページのツイート
    }

    public void delete(int commentId) {
        dao.delete(commentId);//ツイートの削除
    }
}