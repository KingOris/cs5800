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
                         @RequestParam("password") String password){
        String sql = "Select*FROM user Where id = '" + username + "'";
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
        if (userList.get(0).getId().equals(username) && userList.get(0).getPassword().equals(password)){
            return "index";
        }else {
            return "login";
        }
    }
}


