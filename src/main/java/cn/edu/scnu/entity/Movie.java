package cn.edu.scnu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("movie")
public class Movie {

    @TableId(type= IdType.AUTO)
    private Long movieId;
    private String name;
    private String genre;//类型
    private String region;//地区
    private String introduce;
    private boolean needVip;
    private String image;
}
