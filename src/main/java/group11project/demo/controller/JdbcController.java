package group11project.demo.controller;

import group11project.demo.entity.Course;
import group11project.demo.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/jdbc")
public class JdbcController {
    @Resource
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/user")
    public String getUserInfo(ModelMap map){
        String sql = "Select*FROM user";
        List<User>userList = jdbcTemplate.query(sql, new RowMapper<User>() {
            User user = null;
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                user = new User();
                user.setId(resultSet.getString("id"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        });
        map.addAttribute("users",userList);
        return "user";
    }

    @RequestMapping("/log")
    public String getCouseInfo(ModelMap map){
        String sql = "Select*FROM table";
        List<Course> courseList = jdbcTemplate.query(sql, new RowMapper<Course>() {
            Course course = null;
            @Override
            public Course mapRow(ResultSet resultSet, int i) throws SQLException {
                course = new Course();
                course.setCourseName(resultSet.getString("courseName"));
                course.setDepartment(resultSet.getString("department"));
                course.setNumOfSeats(resultSet.getInt("numOfSeats"));
                course.setPrimaryInstructor(resultSet.getString("primaryInstructor"));
                course.setSemesterHours(resultSet.getInt("semesterHours"));
                course.setCourseCode(resultSet.getString("courseCode"));
                return course;
            }
        });
        map.addAttribute("courses", courseList);
        return "mainPage";
    }
}
