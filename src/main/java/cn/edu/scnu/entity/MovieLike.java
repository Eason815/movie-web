package cn.edu.scnu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("movie_like")
public class MovieLike {

    @TableId(type = IdType.AUTO)
    private Long likeId;
    private Long movieId;
    private LocalDateTime likeTime;
}
