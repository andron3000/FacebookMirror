package com.chekhovych.controller;

import com.chekhovych.dto.PostDto;
import com.chekhovych.service.GmailService;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.chekhovych.controller.FacebookController.PATH;

@Api(basePath = "/", value = "/", description = "Facebook Controller")
@Controller(value = "/")
public class FacebookController {
    static final String PATH = "/";

    @Autowired
    private PostService postService;

    @Autowired
    private GmailService gmailService;

    private Facebook facebook;
    private ConnectionRepository connectionRepository;

    public FacebookController(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String index(Model model) {
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
    public String allPosts(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "success";
    }

    @RequestMapping(value = "/posts/create", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    public String addPost() {
        return "addpost";
    }

    @RequestMapping(value = "/posts/create", method = RequestMethod.POST)
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
    public void deletePost(@PathVariable("postId") Long postId,
                           HttpServletResponse response) throws IOException {
        postService.delete(postId);
        gmailService.sendEmail(String.format("Post with id = %d, was successfully deleted !!!", postId));
        response.sendRedirect("/api/facebook/posts");
    }
}
