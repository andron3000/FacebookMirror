package com.chekhovych.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostDto {
    
    @JsonProperty(value = "user_name")
    public String userName;

    @JsonProperty(value = "message")
    public String message;

    @JsonProperty(value = "story")
    public String story;

    @JsonProperty(value = "picture_url")
    public String pictureUrl;
}
