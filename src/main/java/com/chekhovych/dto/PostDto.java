package com.chekhovych.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.User;

import java.util.ArrayList;
import java.util.List;

public class PostDto {

    @JsonProperty(value = "user_name")
    public String userName;

    @JsonProperty(value = "message")
    public String message;

    @JsonProperty(value = "story")
    public String story;

    @JsonProperty(value = "picture_url")
    public String pictureUrl;

    public static PostDto convert(Post post, User userProfile) {
        PostDto dto = new PostDto();

        dto.userName = userProfile.getName();
        dto.message = post.getMessage();
        dto.story = post.getStory();
        dto.pictureUrl = post.getPicture();
        return dto;
    }

    public static List<PostDto> convertAll(List<Post> posts, User userProfile) {
        List<PostDto> postDtoList = new ArrayList<>();

        for(Post post: posts){
            postDtoList.add(convert(post, userProfile));
        }
        return postDtoList;
    }
}
