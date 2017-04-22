package com.chekhovych.repository;

import com.chekhovych.model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

    @Query("select p from Post p where p.story=:story")
    List<Post> findByStory(@Param("story") String story);
}
