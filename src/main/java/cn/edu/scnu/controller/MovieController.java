package cn.edu.scnu.controller;

import cn.edu.scnu.entity.Movie;
import cn.edu.scnu.entity.TbUser;
import cn.edu.scnu.service.MovieService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/rank")

    public String getMovies(@RequestParam(required = false) String genre,
                            @RequestParam(required = false) String region,
                            @RequestParam(required = false) String rank,
                            Model model) {

        List<Movie> movies = movieService.getMoviesByCriteria(genre, region);
        if (rank != null) {
            switch (rank) {
                case "rating" -> movies = movieService.getMoviesByLikes();
                case "week" -> movies = movieService.getMoviesByWeekLikes();
                case "month" -> movies = movieService.getMoviesByMonthlyLikes();
            }
        }
        model.addAttribute("movies", movies);
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

    @GetMapping("/searchByActor")
    @ResponseBody
    public List<Movie> getMoviesByActor(@RequestParam String actorName) {
        return movieService.getMoviesByActorName(actorName);
    }

    @GetMapping("/searchByDirector")
    @ResponseBody
    public List<Movie> getMoviesByDirector(@RequestParam String directorName) {
        return movieService.getMoviesByDirectorName(directorName);
    }

    @GetMapping("/movie")
    @ResponseBody
    public String getMoviePage(@RequestParam Long movieId, HttpSession session) {
        Movie movie = movieService.getById(movieId);
        TbUser user = (TbUser) session.getAttribute("user");

        if (movie.isNeedVip() && (user == null || user.getIsVip() != 1)) {
            return "不是vip";
        }

        return "可以进入";
    }

    @GetMapping("/movieLikesChart")
    public String movieLikesChart() {
        return "movieLikes";
    }

    @GetMapping("/movieViewChart")
    public String movieViewChart() {
        return "movieView";
    }

    @ResponseBody
    @GetMapping("/getMovieById")
    public Movie getMovieById(Integer id) {
        return movieService.GetMovieById(id);
    }

    @ResponseBody
    @GetMapping("/getActorsByMovieId")
    public List<String> getActorsByMovieId(Integer id) {
        return movieService.getActorsByMovieId(id);
    }

    @ResponseBody
    @GetMapping("/getDirectorByMovieId")
    public String getDirectorByMovieId(Integer id) {
        return movieService.getDirectorByMovieId(id);
    }


    @RequestMapping("/movieView")
    public String movieinfo(@RequestParam(required = false) Integer movieId, Model model) {

        Movie movie = movieService.GetMovieById(movieId);
        model.addAttribute("movie", movie);

        List<String> actors = movieService.getActorsByMovieId(movieId);
        String director = movieService.getDirectorByMovieId(movieId);
        model.addAttribute("actors", actors);
        model.addAttribute("director", director);
        return "movieView";
    }
}
