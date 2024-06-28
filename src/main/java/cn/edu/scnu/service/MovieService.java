package cn.edu.scnu.service;

import cn.edu.scnu.entity.Movie;
import cn.edu.scnu.mapper.MovieMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.stream.Collectors;

@Service
public class MovieService extends ServiceImpl<MovieMapper, Movie> {
    @Autowired
    private MovieMapper movieMapper;

    public Movie GetMovieById(Integer id) {
        return movieMapper.selectById(id);
    }

    public List<Movie> getMoviesByCriteria(List<Movie> movies, String genre, String region) {
        //首先从数据库中获取所有符合条件的电影，然后使用 filter 方法来筛选出那些在 movies 列表中的电影。
        //这将保留 movies 列表的顺序，因为是按照 movies 列表的顺序来筛选电影的。
        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();

        if (genre != null && !genre.isEmpty()) {
            queryWrapper.like("genre", genre);
        }

        if (region != null && !region.isEmpty()) {
            queryWrapper.like("region", region);
        }

        List<Movie> allMovies = movieMapper.selectList(queryWrapper);

        List<Movie> movies1 = new ArrayList<>();
        if (movies != null && !movies.isEmpty()) {
            for (Movie movie : movies) {
                if (allMovies.contains(movie)) {
                    movies1.add(movie);
                }
            }
        }

        return movies1;
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

    public List<Movie> getMoviesByActorNameWithPagination(String name, Integer pageNo, Integer pageSize) {
        int start = (pageNo - 1) * pageSize;
        return movieMapper.selectMoviesByActorNameWithPagination(name, start, pageSize);
    }

    public List<Movie> getMoviesByDirectorNameWithPagination(String name, Integer pageNo, Integer pageSize) {
        int start = (pageNo - 1) * pageSize;
        return movieMapper.selectMoviesByDirectorNameWithPagination(name, start, pageSize);
    }

    public List<Movie> getMoviesByViews() {
        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("view");
        return movieMapper.selectList(queryWrapper);
    }

    public List<Movie> AllMovies() {
        // 获取所有电影后 随机排序
        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("RAND()");
        return movieMapper.selectList(queryWrapper);
    }

    public List<Movie> getAllMoviesByID() {
        // 获取所有电影后 随机排序
        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("movie_id");
        return movieMapper.selectList(queryWrapper);
    }
}
