package cn.edu.scnu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("movie_staff")
public class MovieStaff {

    @TableId(type= IdType.AUTO)
    private Long id;
    private Long movieId;
    private Long staffId;
}
