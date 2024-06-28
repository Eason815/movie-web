package cn.edu.scnu.controller;

import cn.edu.scnu.entity.Movie;
import cn.edu.scnu.entity.MovieLike;
import cn.edu.scnu.service.MovieLikeService;
import cn.edu.scnu.service.MovieService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
public class MovieLikeController {
    @Autowired
    private MovieLikeService movieLikeService;

    @Autowired
    public MovieService movieService;

//    @GetMapping("/movieLikes")
//    public List<MovieLike> getAllMovieLikes() {
//        return movieLikeService.getAllMovieLikes();
//    }

    @RequestMapping("/movieLikes")
    public String getAllMovieLikes(Model model, @RequestParam("returnUrl") String returnUrl) {
        // 这里可以添加获取电影点赞数据的逻辑
        model.addAttribute("returnUrl", returnUrl);
        return "print1";
    }

    @GetMapping("/downloadMovieLikes")
    public ResponseEntity<InputStreamResource> downloadMovieLikes(@RequestParam("returnUrl") String returnUrl) throws IOException {
        List<MovieLike> movieLikes = movieLikeService.getAllMovieLikes();

        // 创建工作簿和工作表
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Movie Likes");

        // 创建表头
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Like ID");
        headerRow.createCell(1).setCellValue("Movie ID");
        headerRow.createCell(2).setCellValue("Like Time");

        // 填充数据
        int rowNum = 1;
        for (MovieLike movieLike : movieLikes) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(movieLike.getLikeId());
            row.createCell(1).setCellValue(movieLike.getMovieId());
            row.createCell(2).setCellValue(movieLike.getLikeTime());
        }

        // 将工作簿写入输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        // 创建输入流资源
        ByteArrayInputStream inputStream = new ByteArrayInputStream(out.toByteArray());

        // 设置HTTP头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=movieLikes.xlsx");

        // 返回响应实体
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(new InputStreamResource(inputStream));
    }

    @GetMapping("/downloadMovieViews")
    public ResponseEntity<InputStreamResource> downloadMovieViews(@RequestParam("returnUrl") String returnUrl) throws IOException {
        List<Movie> movies = movieService.getMoviesByViews();


        // 创建工作簿和工作表
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Movie Views");


        // 创建表头
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Movie ID");
        headerRow.createCell(1).setCellValue("Movie Name");
        headerRow.createCell(2).setCellValue("View Count");

        // 填充数据
        int rowNum = 1;
        for (Movie movie : movies) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(movie.getMovieId());
            row.createCell(1).setCellValue(movie.getName());
            row.createCell(2).setCellValue(movie.getView());
        }

        // 将工作簿写入输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        // 创建输入流资源
        ByteArrayInputStream inputStream = new ByteArrayInputStream(out.toByteArray());

        // 设置HTTP头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=movieViews.xlsx");

        // 返回响应实体
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(new InputStreamResource(inputStream));


    }
}
