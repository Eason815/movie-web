package cn.edu.scnu.controller;

import cn.edu.scnu.entity.Movie;
import cn.edu.scnu.entity.TbUser;
import cn.edu.scnu.service.MovieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MovieController {
    @Autowired
    private MovieService movieService;

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

    @GetMapping("/checkMembership")
    @ResponseBody
    public ResponseEntity<Map<String, Boolean>> checkMembership(HttpSession session) {
        TbUser user = (TbUser) session.getAttribute("user");
        Map<String, Boolean> response = new HashMap<>();
        if (user != null && user.getIsVip()==1) {
            response.put("isVip", true);
        } else {
            response.put("isVip", false);
        }
        return ResponseEntity.ok(response);
    }


    @GetMapping("/movieLikesChart")
    public String movieLikesChart() {
        return "echarts1";
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
    public String movieView(@RequestParam(required = false) Integer movieId,
                            HttpSession session,
                            HttpServletRequest request,
                            Model model) {

        Movie movie = movieService.GetMovieById(movieId);
        model.addAttribute("movie", movie);

        List<String> actors = movieService.getActorsByMovieId(movieId);
        String director = movieService.getDirectorByMovieId(movieId);
        model.addAttribute("actors", actors);
        model.addAttribute("director", director);
        model.addAttribute("httpServletRequest", request);


        TbUser user = (TbUser) session.getAttribute("user");
        model.addAttribute("user", user);

        boolean needVip = movie.isNeedVip();
        model.addAttribute("needVip", needVip);
        return "movieView";
    }
}
