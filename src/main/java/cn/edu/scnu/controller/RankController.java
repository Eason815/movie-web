package cn.edu.scnu.controller;

import cn.edu.scnu.entity.Movie;
import cn.edu.scnu.service.MovieService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RankController {

    @Autowired
    private MovieService movieService;

    @RequestMapping("/rank")
    public String getMovies(@RequestParam(required = false) String genre,
                            @RequestParam(required = false) String region,
                            @RequestParam(required = false) String rank,
                            @RequestParam(name = "pageNo", required = false,defaultValue = "1") Integer pageNo,
                            HttpServletRequest request,
                            Model model) {

        List<Movie> movies = null;


        Integer pageSize=18;//每页显示的记录数
        if(pageNo==null) pageNo=1;
        Integer pageCount = 0;

        if (rank != null) {
            switch (rank) {

                case "hot" -> movies = movieService.getMoviesByViews();
                case "rating" -> movies = movieService.getMoviesByLikes();
                case "week" -> movies = movieService.getMoviesByWeekLikes();
                case "month" -> movies = movieService.getMoviesByMonthlyLikes();

                default -> movies = movieService.AllMovies();
            }
        }
        else
            movies = movieService.AllMovies();

        List<Movie> movies1 = movieService.getMoviesByCriteria(movies, genre, region);
        pageCount = (movies1.size() % pageSize == 0) ? (movies1.size() / pageSize) : (movies1.size() / pageSize + 1);

        Integer pageStart = (pageNo-1)*pageSize;
        Integer pageEnd =  movies1.size() < pageNo*pageSize ? movies1.size() : pageNo*pageSize;
        List<Movie> pageMovies = movies1.subList(pageStart, pageEnd); // 对movies1[pageStart, pageEnd]

        model.addAttribute("genre", genre);
        model.addAttribute("region", region);
        model.addAttribute("rank", rank);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("PageCount", pageCount);
        model.addAttribute("movies", pageMovies);
        model.addAttribute("httpServletRequest", request);


        return "rank";
    }

    @GetMapping("/rankByLikes")
    @ResponseBody
    public List<Movie> getMoviesByLikes() {
        return movieService.getMoviesByLikes();
    }

    @GetMapping("/rankByWeekLikes")
    @ResponseBody
    public List<Movie> getMoviesByWeekLikes() {
        return movieService.getMoviesByWeekLikes();
    }

    @GetMapping("/rankByMonthlyLikes")
    @ResponseBody
    public List<Movie> getMoviesByMonthlyLikes() {
        return movieService.getMoviesByMonthlyLikes();
    }
}
