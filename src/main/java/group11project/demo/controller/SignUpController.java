package group11project.demo.controller;

import group11project.demo.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
                         @RequestParam("password") String password){
        String sql = "INSERT INTO user(id,password)\n VALUES (" + username +","+ password+ ")";
        jdbcTemplate.update(sql);
        return "index";
}
}
