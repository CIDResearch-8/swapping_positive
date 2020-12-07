package com.swappingpositive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class CommentService {

    @Autowired
    private CommentDao dao;

    public void save(CommentForm form, String userId) {
        //ツイートをSQLに登録
        dao.insert(new Comment(
                userId,
                form.getComment()));
    }

    public List<Comment> getTimeline() {
        return dao.selectAll();//タイムライン表示
    }

    public Comment getUser(String userId) {
        return new Comment();//ユーザーページのツイート
    }

    public void delete(int comment_id) {
        dao.delete(comment_id);//ツイートの削除
    }
}