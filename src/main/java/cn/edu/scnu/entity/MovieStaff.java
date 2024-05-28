package cn.edu.scnu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("movie_staff")
public class MovieStaff {
    public static final long serialVersionUID = -1L;

    @TableId(type= IdType.AUTO)
    private int id;
    private int movieId;
    private int staffId;
}
