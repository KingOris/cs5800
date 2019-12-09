package group11project.demo.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

@Controller
public class CourseController {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/jdbc/addcourse")
    public String Log_in(@RequestParam("courseCode") String courseCode,
                         @RequestParam("courseName") String courseName,
                         @RequestParam("Instructor") String instructor,
                         @RequestParam("Num of Seats") String Number,
                         @RequestParam("Semester Hours") String sh,
                         @RequestParam("department") String department,
                         RedirectAttributes redirectAttributes){
        String sql = "INSERT INTO `user`.`course` (`courseCode`, `courseName`, `primaryInstructor`, `numOfSeats`, `semesterHours`, `department`) VALUES('"+courseCode+"', '"+courseName+"','"+ instructor+"','"+Number+"','"+ sh+"', '"+department+"')";
        System.out.println(sql);
        try{
            jdbcTemplate.update(sql);
            return "redirect:/jdbc/course";
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("warning","Sign up failed");
            return "redirect:/jdbc/course";
        }
}}
