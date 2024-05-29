package cn.edu.scnu.controller;

import cn.edu.scnu.entity.Movie;
import cn.edu.scnu.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class RankController {

    @Autowired
    private MovieService movieService;

//    @RequestMapping("/rank")
//    public String toRankPage() {
//        return "rank";
//    }


    @RequestMapping("/rank")

    public String index(Model model,
                        @RequestParam(value = "type", required = false) String type,
                        @RequestParam(value = "region", required = false) String region,
                        @RequestParam(value = "sort", required = false) String sort) {

        List<Movie> movies;

        if ("hot".equals(sort))
            movies = movieService.getHotRankedMovies();
        else if ("weekly".equals(sort))
             movies = movieService.getWeeklyRankedMovies();
        else if ("monthly".equals(sort))
            movies = movieService.getMonthlyRankedMovies();
        else if ("best".equals(sort))
            movies = movieService.getBestRatedRankedMovies();
        else
            movies = movieService.searchMovies(type, region);


        model.addAttribute("movies", movies);
        model.addAttribute("type", type);
        model.addAttribute("region", region);
        model.addAttribute("sort", sort);
        return "rank";
    }
}
