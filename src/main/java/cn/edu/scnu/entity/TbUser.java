package cn.edu.scnu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
@Data
@TableName("tb_user")
public class TbUser {
    @TableId(type= IdType.AUTO)
    private Integer userId;
    private String username;
    private String password;
    private String nickname;
    private String headPic;
    private String sex;
    private String phone;
    private String email;
    private int type;
}

