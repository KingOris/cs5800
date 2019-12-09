package group11project.demo.controller;

import group11project.demo.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Controller
public class AccountController {
    @Resource
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/account")
    public String getUserInfo(ModelMap map,
                              HttpServletRequest request,
                              HttpSession session){
        User user =(User)request.getSession().getAttribute("id");
        String sql = "Select*FROM user Where id = '" + user.getId() + "'";
        System.out.println(sql);
        List<User> userList = jdbcTemplate.query(sql, new RowMapper<User>() {
            User user = null;
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                user = new User();
                user.setId(resultSet.getString("id"));
                user.setPassword(resultSet.getString("password"));
                user.setInstructor(resultSet.getBoolean("instructor"));
                user.setInstructor(resultSet.getBoolean("Student"));
                return user;
            }
        });
        map.addAttribute("users",userList);
        return "account";
    }
}
