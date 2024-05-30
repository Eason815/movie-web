package cn.edu.scnu.mapper;

import cn.edu.scnu.entity.Movie;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MovieMapper extends BaseMapper<Movie> {
    @Select("SELECT m.* FROM movie m " +
            "JOIN (SELECT movie_id FROM movie_like GROUP BY movie_id ORDER BY COUNT(*) DESC) ml " +
            "ON m.movie_id = ml.movie_id")
    List<Movie> selectMoviesByLikes();



    @Select("SELECT m.* " +
            "FROM movie m " +
            "JOIN (SELECT movie_id " +
            "FROM movie_like " +
            "WHERE like_time >= DATE_SUB('2024-07-01 00:00:00', INTERVAL 7 DAY) " +
            "GROUP BY movie_id " +
            "ORDER BY COUNT(*) DESC) ml " +
            "ON m.movie_id = ml.movie_id " +
            "ORDER BY (SELECT COUNT(*) " +
            "FROM movie_like " +
            "WHERE movie_id = m.movie_id AND like_time >= DATE_SUB('2024-07-01 00:00:00', INTERVAL 7 DAY)) DESC")
    List<Movie> selectMoviesByWeekLikes();


    @Select("SELECT m.* " +
            "FROM movie m " +
            "JOIN (SELECT movie_id " +
            "FROM movie_like " +
            "WHERE like_time >= DATE_SUB('2024-07-01 00:00:00', INTERVAL 1 MONTH) " +
            "GROUP BY movie_id " +
            "ORDER BY COUNT(*) DESC) ml " +
            "ON m.movie_id = ml.movie_id " +
            "ORDER BY (SELECT COUNT(*) " +
            "FROM movie_like " +
            "WHERE movie_id = m.movie_id AND like_time >= DATE_SUB('2024-07-01 00:00:00', INTERVAL 1 MONTH)) DESC")
    List<Movie> selectMoviesByMonthlyLikes();

    @Select("SELECT m.* FROM movie m " +
            "JOIN movie_staff ms ON m.movie_id = ms.movie_id " +
            "JOIN staff s ON s.staff_id = ms.staff_id " +
            "WHERE s.is_director = 0 AND s.name = #{actorName}")
    List<Movie> selectMoviesByActorName(String actorName);

    @Select("SELECT m.* FROM movie m " +
            "JOIN movie_staff ms ON m.movie_id = ms.movie_id " +
            "JOIN staff s ON s.staff_id = ms.staff_id " +
            "WHERE s.is_director = 1 AND s.name = #{directorName}")
    List<Movie> selectMoviesByDirectorName(String directorName);

    @Select(("select s.name\n" +
            "\tfrom movie m, movie_staff ms, staff s\n" +
            "\twhere m.movie_id = ms.movie_id and s.staff_id  = ms.staff_id and s.is_director = 0 and m.movie_id = #{id}"))
    List<String> selectActorsByMoiveId(Integer id);

    @Select(("select s.name\n" +
            "\tfrom movie m, movie_staff ms, staff s\n" +
            "\twhere m.movie_id = ms.movie_id and s.staff_id  = ms.staff_id and s.is_director = 1 and m.movie_id = #{id}"))
    String selectDirectorByMoiveId(Integer id);
}
