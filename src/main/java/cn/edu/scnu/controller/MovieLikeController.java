package cn.edu.scnu.controller;

import cn.edu.scnu.entity.MovieLike;
import cn.edu.scnu.service.MovieLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MovieLikeController {
    @Autowired
    private MovieLikeService movieLikeService;

    @GetMapping("/movieLikes")
    public List<MovieLike> getAllMovieLikes() {
        return movieLikeService.getAllMovieLikes();
    }
}