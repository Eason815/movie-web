package cn.edu.scnu.mapper;

import cn.edu.scnu.entity.MovieStaff;
import cn.edu.scnu.entity.Staff;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StaffMapper extends BaseMapper<Staff> {
}
