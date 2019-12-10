package group11project.demo.controller;

import group11project.demo.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Controller
public class SignUpController {
    @RequestMapping("/signUp")
    public String signUp(){
        return "signUp";
    }

    @Resource
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/signup")
    public String Log_in(@RequestParam("username") String username,
                         @RequestParam("password") String password,
                         @RequestParam("Type") String Type,
                         @RequestParam("email") String email,
                         RedirectAttributes redirectAttributes){
        int admin= 0;
        int student=0;
        int instructor=0;
        if(Type.equals("admin")){
            admin=1;
        }

        if(Type.equals("student")){
            student=1;
        }

        if(Type.equals("instructor")){
            instructor=1;
        }
        String sql = "INSERT INTO user (id,password,locked,student,instructor,email,admin) VALUES ('" + username +"','"+ password+"','"+"0"+"','"+student+"','"+instructor+"','"+email+"','"+admin+"')";
        System.out.println(sql);
        try{
            jdbcTemplate.update(sql);
            return "admin_homepage";
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("warning","Sign up failed");
            return "redirect:/signUp";
        }

}
}
