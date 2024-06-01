package cn.edu.scnu.service;

import cn.edu.scnu.entity.Movie;
import cn.edu.scnu.mapper.MovieMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService extends ServiceImpl<MovieMapper, Movie> {
    @Autowired
    private MovieMapper movieMapper;

    public Movie GetMovieById(Integer id) {
        return movieMapper.selectById(id);
    }

    public List<Movie> getMoviesByCriteria(String genre, String region) {
        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();

        if (genre != null && !genre.isEmpty()) {
            queryWrapper.like("genre", genre);
        }

        if (region != null && !region.isEmpty()) {
            queryWrapper.like("region", region);
        }

        queryWrapper.orderByDesc("view");

        return movieMapper.selectList(queryWrapper);
    }

    public List<Movie> getMoviesByLikes() {
        return movieMapper.selectMoviesByLikes();
    }

    public List<Movie> getMoviesByWeekLikes() {
        return movieMapper.selectMoviesByWeekLikes();
    }

    public List<Movie> getMoviesByMonthlyLikes() {
        return movieMapper.selectMoviesByMonthlyLikes();
    }

    public List<Movie> getMoviesByActorName(String actorName) {
        return movieMapper.selectMoviesByActorName(actorName);
    }

    public List<Movie> getMoviesByDirectorName(String directorName) {
        return movieMapper.selectMoviesByDirectorName(directorName);
    }

    public List<String> getActorsByMovieId(Integer id) {
        return movieMapper.selectActorsByMoiveId(id);
    }

    public String getDirectorByMovieId(Integer id) {
        return movieMapper.selectDirectorByMoiveId(id);
    }

}
