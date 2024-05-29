package cn.edu.scnu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("movie_like")
public class MovieLike {
    private int likeId;
    private Date likeTime;
    private int movieId;
}
