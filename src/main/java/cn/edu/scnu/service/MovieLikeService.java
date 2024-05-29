package cn.edu.scnu.service;

import cn.edu.scnu.entity.MovieLike;
import cn.edu.scnu.mapper.MovieLikeMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieLikeService extends ServiceImpl<MovieLikeMapper, MovieLike> {
    @Autowired
    private MovieLikeMapper movieLikeMapper;

    public List<MovieLike> getAllMovieLikes() {
        return movieLikeMapper.selectList(null);
    }
}