package com.chekhovych.service;

import com.chekhovych.model.Post;

import java.util.List;

public interface PostService {

    Post save(Post post);

    List<Post> findAll();

    void delete(Long postId);
}
