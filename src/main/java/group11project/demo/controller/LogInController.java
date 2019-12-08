package group11project.demo.controller;

import group11project.demo.entity.Course;
import group11project.demo.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;



@Controller
public class LogInController {
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @Resource
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/log")
    public String Log_in(@RequestParam("username") String username,
                         @RequestParam("password") String password,
                         HttpSession session,
                         RedirectAttributes redirectAttributes,
                         ModelMap map){
        String sql = "Select*FROM user Where id = '" + username + "'";
        List<User>userList = jdbcTemplate.query(sql, new RowMapper<User>() {
            User user = null;
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                user = new User();
                user.setId(resultSet.getString("id"));
                user.setPassword(resultSet.getString("password"));
                user.setInstructor(resultSet.getBoolean("instructor"));
                user.setAdmin(resultSet.getBoolean("admin"));
                user.setStudent(resultSet.getBoolean("student"));
                return user;
            }
        });



        if (userList.isEmpty()){
            redirectAttributes.addFlashAttribute("warning","Username/Password incorrect");
            return "redirect:/login";
        }else if (userList.get(0).getId().equals(username) && userList.get(0).getPassword().equals(password)){
            System.out.println(userList.get(0).isAdmin());
            if(userList.get(0).isAdmin()==true){
                session.setMaxInactiveInterval(30*60);
                session.setAttribute("id",userList.get(0));
                return "admin_homepage";
            }

                String sqlcourse = "Select*FROM user.course";
                List<Course> courseList = jdbcTemplate.query(sqlcourse, new RowMapper<Course>() {
                    Course course = null;
                    @Override
                    public Course mapRow(ResultSet resultSet, int i) throws SQLException {
                        course = new Course();
                        course.setCourseName(resultSet.getString("courseName"));
                        System.out.println(course.getCourseName());
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
        }else {
            redirectAttributes.addFlashAttribute("warning","Username/Password incorrect");
            return "redirect:/login";
        }
    }

    @RequestMapping("/logout")
    public String Log_out(HttpSession session){
        session.invalidate();
        return "index";
    }
}


