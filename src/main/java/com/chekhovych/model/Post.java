package com.chekhovych.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "POST")
public class Post implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "message")
    private String message;

    @Column(name = "story")
    private String story;

    @Column(name = "picture_url")
    private String pictureUrl;

    public Post() {
        super();
    }

    public Post(Long id) {
       this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
