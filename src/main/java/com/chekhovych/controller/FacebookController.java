package com.chekhovych.controller;

import com.chekhovych.dto.PostDto;
import com.chekhovych.service.PostService;
import io.swagger.annotations.Api;
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
import org.springframework.web.multipart.MultipartFile;

import static com.chekhovych.controller.FacebookController.PATH;

@Api(basePath = PATH, value = PATH, description = "Facebook Controller")
@Controller(value = PATH)
public class FacebookController {
    static final String PATH = "api/facebook";

    @Autowired
    private PostService postService;

    private Facebook facebook;
    private ConnectionRepository connectionRepository;

    public FacebookController(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String signInFacebook(Model model) {
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "/connect/facebookConnect";
        }
        String[] fields = {"id", "email", "name", "first_name", "last_name"};
        User userProfile = facebook.fetchObject("me", User.class, fields);
        PagedList<Post> feeds = facebook.feedOperations().getFeed();
        postService.saveAll(PostDto.convertAll(feeds, userProfile));
        model.addAttribute("facebookProfile", userProfile);
        model.addAttribute("feeds", feeds);

        return "hello";
    }

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    public String addPost() {;
        return "addpost";
    }

    @RequestMapping(value = "/posts", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public String savePost(
            @RequestParam("username") String username,
            @RequestParam("message") String message,
            @RequestParam("story") String story,
            @RequestParam("image") MultipartFile image,
            Model model) {

        PostDto dto = new PostDto();
        dto.userName = username;
        dto.message = message;
        dto.story = story;
        dto.pictureUrl = image.getOriginalFilename();

        postService.save(dto);
        model.addAttribute("posts", postService.findAll());

        return "success";
    }

    @RequestMapping(value = "/posts/{postId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deletePost(@PathVariable("postId") Long postId, Model model) {
        postService.delete(postId);
        model.addAttribute("posts", postService.findAll());

        return "hello";// todo
    }
}
