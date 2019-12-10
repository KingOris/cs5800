package group11project.demo.controller;

import group11project.demo.annotation.onlyadmin;
import group11project.demo.entity.Course;
import group11project.demo.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Controller

@RequestMapping("/jdbc")
public class JdbcController {
    @onlyadmin
    @RequestMapping("/addCourse")
    public String addcourse(){
        return "addCourse";
    }
    @Resource
    private JdbcTemplate jdbcTemplate;

    @onlyadmin
    @RequestMapping("/user")
    public String getUserInfo(ModelMap map){
        String sql = "Select*FROM user.user";
        List<User>userList = jdbcTemplate.query(sql, new RowMapper<User>() {
            User user = null;
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                user = new User();

                user.setId(resultSet.getString("id"));
                System.out.println(user.getId());
                user.setPassword(resultSet.getString("password"));
                user.setInstructor(resultSet.getBoolean("instructor"));
                user.setInstructor(resultSet.getBoolean("Student"));
                return user;
            }
        });
        map.addAttribute("users",userList);
        return "admin-user";
    }

    @onlyadmin
    @RequestMapping("/course")
    public String courseInfo(ModelMap map){
        String sql = "Select*From user.course";
        List<Course>courseList = jdbcTemplate.query(sql, new RowMapper<Course>() {
            Course course = null;
            @Override
            public Course mapRow(ResultSet resultSet, int i) throws SQLException {
                course = new Course();

                course.setCourseCode(resultSet.getString("courseCode"));
                course.setCourseName(resultSet.getString("courseName"));
                course.setDepartment(resultSet.getString("department"));
                course.setNumOfSeats(resultSet.getInt("numOfSeats"));
                course.setPrimaryInstructor(resultSet.getString("primaryInstructor"));
                course.setSemesterHours(resultSet.getInt("semesterHours"));
                return course;
            }
        });
        map.addAttribute("courses",courseList);
        return "admin_course";
    }

    @onlyadmin
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id,
                         RedirectAttributes redirectAttributes){
        String sql = "DELETE FROM `user`.`user` WHERE (`id` = '"+id +"');";
        jdbcTemplate.update(sql);
        return "redirect:/jdbc/user";
    }

    @onlyadmin
    @GetMapping("/delete/course/{id}")
    public String delete_course(@PathVariable("id") String id,
                         RedirectAttributes redirectAttributes){
        String sql = "DELETE FROM `user`.`course` WHERE (`courseCode` = '"+id +"');";
        jdbcTemplate.update(sql);
        return "redirect:/jdbc/course";
    }
}
