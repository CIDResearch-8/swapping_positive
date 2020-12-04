package com.swappingpositive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class CommentService {

    private CommentDao dao;

    @Autowired
    public CommentService(CommentDao dao) {
        this.dao = dao;
    }

    public void save(Comment comment) {
        dao.insert(comment);//ツイートをSQLに登録
    }

    public List<Comment> getTimeline() {
        return dao.selectAll();//タイムライン表示
    }

    public Comment getUser(String userId) {
        return dao.selectUserComment(userId);//ユーザーページのツイート
    }

    public void delete(int comment_id) {
        dao.delete(comment_id);//ツイートの削除
    }
}