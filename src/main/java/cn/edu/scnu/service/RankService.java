package cn.edu.scnu.service;

import cn.edu.scnu.entity.Movie;
import cn.edu.scnu.mapper.MovieMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class RankService extends ServiceImpl<MovieMapper, Movie>  {
}