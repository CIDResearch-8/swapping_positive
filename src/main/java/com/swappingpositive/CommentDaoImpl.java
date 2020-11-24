package com.swappingpositive;


public class CommentDaoImpl implements CommentDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CommentDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public void create(Comment comment) {
        //タイムスタンプの生成

        //協定世界時のUTC 1970年1月1日深夜零時との差をミリ秒で取得
        long millis = System.currentTimeMillis();

        //ミリ秒を引数としてTimestampオブジェクトを作成
        Timestamp timestamp = new Timestamp(millis);

        //Timestampオブジェクトの値
        System.out.println(timestamp.toString());
        jdbcTemplate.update("INSERT INTO comment (userID, date, massage) VALUES (?, ?, ?)",
                comment.getUserID(), timestamp.toString(), comment.getmassage());
    }
    //ユーザーページのツイート情報
    @Override
    public Comment getUser(int userID) {
        Map<String, Object> map = jdbcTemplate.queryForMap("SELECT * FROM comment WHERE userID = ?", userID);
        Comment comment = new Comment();

        comment.setUserId((int) map.get("userID"));

        comment.setDate(((Timestamp) map.get("date")).toLocalDateTime());

        comment.setMassage((String) map.get("massage"));

        return comment;
    }
    //タイムラインのツイート情報
    @Override
    public  List<Comment> getAll() {
        List<Comment> comments = new ArrayList<>();
        for (Map<String, Object> row : jdbcTemplate.queryForList("SELECT * FROM comment ORDER BY id")) {
            Comment comment = new Comment();
            comment.setUserID((int) row.get("userID"));
            comment.setDate(((Timestamp) row.get("date")).toLocalDateTime());
            comment.setMassage((String) row.get("massage"));
            comments.add(comment);
        }
        return comments;
    }

    //ツイートの削除
    @Override
    public void delete(int userID) {
        jdbcTemplate.update("DELETE  FROM comment WHERE userID = ?", userID);
    }

}