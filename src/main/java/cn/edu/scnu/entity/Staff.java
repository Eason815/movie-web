package cn.edu.scnu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("staff")
public class Staff {

    @TableId(type= IdType.AUTO)
    private int staffId;
    private String name;
    private int isDirector;
}
