package com.swappingpositive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentDao dao;

    @Autowired
    public CommentServiceImpl(CommentDao dao) {
        this.dao = dao;
    }
    @Override
    public void save(Comment comment) {
        dao.create(comment);//ツイートをSQLに登録
    }
    @Override
    public List<Comment> getTimeline() {
        return dao.getTimeline();//タイムライン表示
    }
    @Override
    public Comment getUser(int user_id) {
        return dao.getUser(user_id);//ユーザーページのツイート
    }
    @Override
    public void delete(int user_id) {
        dao.delete(user_id);//ツイートの削除
    }
}