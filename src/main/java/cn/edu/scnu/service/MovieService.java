package cn.edu.scnu.service;

import cn.edu.scnu.entity.Movie;
import cn.edu.scnu.entity.MovieStaff;
import cn.edu.scnu.entity.Staff;
import cn.edu.scnu.mapper.MovieMapper;
import cn.edu.scnu.mapper.MovieStaffMapper;
import cn.edu.scnu.mapper.StaffMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService extends ServiceImpl<MovieMapper,Movie> {

    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    private MovieStaffMapper movieStaffMapper;
    @Autowired
    private StaffMapper staffMapper;


    public List<Movie> findByName(String name) {
        // Step 1: 根据员工name在staff表中找到对应的staff_id
        QueryWrapper<Staff> staffQueryWrapper = new QueryWrapper<>();
        staffQueryWrapper.eq("name", name);
        Staff staff = staffMapper.selectOne(staffQueryWrapper);

        if (staff == null) return Collections.emptyList();
        int staffId = staff.getStaffId();

        // Step 2: 根据staff_id在movie_staff表中找到所有的movie_id
        QueryWrapper<MovieStaff> movieStaffQueryWrapper = new QueryWrapper<>();
        movieStaffQueryWrapper.eq("staff_id", staffId);
        List<MovieStaff> movieStaffList = movieStaffMapper.selectList(movieStaffQueryWrapper);

        if (movieStaffList.isEmpty()) return Collections.emptyList();
        List<Integer> movieIds = movieStaffList.stream()
                .map(MovieStaff::getMovieId)
                .collect(Collectors.toList());

        // Step 3: 根据movie_id在movie表中找到所有的movie
        QueryWrapper<Movie> movieQueryWrapper = new QueryWrapper<>();
        movieQueryWrapper.in("movie_id", movieIds);

        return movieMapper.selectList(movieQueryWrapper);
    }

}
