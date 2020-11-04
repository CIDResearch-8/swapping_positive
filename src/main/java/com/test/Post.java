package com.test;

public class Post {
    private static long staticPostId;
    private long postId;
    private String name;
    private String content;

    public Post() {
        setPostId(staticPostId);
    }

    public static long getStaticPostId() {
        return staticPostId;
    }

    public static void nextPostId() {
        staticPostId++;
    }

    public long getPostId() {
        return postId;
    }

    private void setPostId(long postId) {
        this.postId = postId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
