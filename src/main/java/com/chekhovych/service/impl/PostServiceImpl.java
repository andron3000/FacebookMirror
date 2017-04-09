package com.chekhovych.service.impl;

import com.chekhovych.model.Post;
import com.chekhovych.repository.PostRepository;
import com.chekhovych.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public List<Post> findAll() {
        return (List<Post>) postRepository.findAll();
    }

    @Override
    public void delete(Long postId) {
        postRepository.delete(postId);
    }
}
