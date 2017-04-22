package com.chekhovych.service;

import com.chekhovych.dto.PostDto;
import com.chekhovych.model.Post;

import java.util.List;

public interface PostService {

    Post save(PostDto postDto);

    List<Post> saveAll(List<PostDto> postDtoList);

    List<Post> findAll();

    void delete(Long postId);
}
