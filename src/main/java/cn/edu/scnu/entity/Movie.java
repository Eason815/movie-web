package cn.edu.scnu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("movies")
public class Movie {

    @TableId(type= IdType.AUTO)
    private Long id;
    private String name;
    private String type;//类型
    private String region;//地区
    private String director;
    private String actors;
    private String description;
    private double rating;
    private long playCount;
    private boolean isVip;
}
