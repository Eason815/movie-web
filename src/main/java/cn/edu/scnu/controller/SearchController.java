package cn.edu.scnu.controller;

import cn.edu.scnu.entity.Movie;
import cn.edu.scnu.entity.Staff;
import cn.edu.scnu.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private MovieService movieService;

//    @RequestMapping("/search")
//    public String toSearchPage(){
//        return "search";
//    }

    @GetMapping("/search")
    public String index(@RequestParam(name = "name", required = false) String name, Model model) {
        List<Movie> movies = movieService.findByName(name);
        model.addAttribute("movies", movies);
        model.addAttribute("name", name);
        return "search";
    }


}
