package com.chekhovych.controller;

import com.chekhovych.dto.PostDto;
import com.chekhovych.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class FacebookController {

    @Autowired
    private PostService postService;

    private Facebook facebook;
    private ConnectionRepository connectionRepository;

    public FacebookController(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String signInFacebook(Model model) {
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "/connect/facebookConnect";
        }
        String[] fields = {"id", "email", "name", "first_name", "last_name"};
        User userProfile = facebook.fetchObject("me", User.class, fields);
        PagedList<Post> feed = facebook.feedOperations().getFeed();
        model.addAttribute("facebookProfile", userProfile);
        model.addAttribute("feed", feed);

        return "hello";
    }

    @RequestMapping(value = "/posts", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public String savePost(@RequestBody PostDto dto, Model model) {
        postService.save(dto);
        model.addAttribute("posts", postService.findAll());

        return "hello";// todo
    }

    @RequestMapping(value = "/posts/{postId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deletePost(@PathVariable("postId") Long postId, Model model) {
        postService.delete(postId);
        model.addAttribute("posts", postService.findAll());

        return "hello";// todo
    }
}
