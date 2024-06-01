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
    public String index(@RequestParam(name = "name", required = false) String name,
                        @RequestParam(name = "pageNo", required = false,defaultValue = "1") Integer pageNo,
                        Model model) {
        List<Movie> movies = null;

        List<Movie> movies1 = movieService.getMoviesByActorName(name);
        List<Movie> movies2 = movieService.getMoviesByDirectorName(name);

        Integer pageSize=3;//每页显示的记录数
        if(pageNo==null) pageNo=1;
        Integer pageCount = 0;
        if (movies1!=null) {
            movies = movieService.getMoviesByActorNameWithPagination(name, pageNo, pageSize);
            pageCount = (movies1.size() % pageSize == 0) ? (movies1.size() / pageSize) : (movies1.size() / pageSize + 1);
        }
        else if (movies2!=null) {
            movies = movieService.getMoviesByDirectorNameWithPagination(name, pageNo, pageSize);
            pageCount = (movies2.size() % pageSize == 0) ? (movies2.size() / pageSize) : (movies2.size() / pageSize + 1);
        }




        model.addAttribute("movies", movies);
        model.addAttribute("name", name);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("PageCount", pageCount);
        return "search";
    }
}

