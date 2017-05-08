package com.chekhovych.service.impl;

import com.chekhovych.dto.PostDto;
import com.chekhovych.model.Post;
import com.chekhovych.repository.PostRepository;
import com.chekhovych.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Post save(PostDto postDto) {
        Post post = new Post();

        post.setMessage(postDto.message);
        post.setPictureUrl(postDto.pictureUrl);
        if(postDto.story == null){
            postDto.story = "";
        }
        post.setStory(postDto.story);
        post.setUserName(postDto.userName);

        if (postRepository.findByStory(postDto.story).size() < 1) {
            return postRepository.save(post);
        }

        return postRepository.findByStory(postDto.story).get(0);
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public List<Post> saveAll(List<PostDto> postDtoList) {
        List<Post> posts = new ArrayList<>();

        for (PostDto dto : postDtoList) {
            posts.add(save(dto));
        }
        return posts;
    }

    @Override
    public List<Post> findAll() {
        return (List<Post>) postRepository.findAll();
    }

    @Override
    public Post findOne(Long id) {
        return postRepository.findOne(id);
    }

    @Override
    public void delete(Long postId) {
        postRepository.delete(postId);
    }
}
