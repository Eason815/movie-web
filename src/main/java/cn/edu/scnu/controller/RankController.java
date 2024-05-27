package cn.edu.scnu.controller;

import cn.edu.scnu.entity.Movie;
import cn.edu.scnu.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class RankController {

    @Autowired
    private RankService rankService;

    @GetMapping("/rank")
    public String getMovies(Model model,
                            @RequestParam(required = false) String type,
                            @RequestParam(required = false) String region,
                            @RequestParam(required = false) String rank) {
        List<Movie> movies = rankService.list();  // 这里可以根据过滤条件和排行方式查询电影
        model.addAttribute("movies", movies);
        return "rank";
    }
}
