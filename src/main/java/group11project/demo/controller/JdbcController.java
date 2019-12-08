package group11project.demo.controller;

import com.sun.org.apache.bcel.internal.generic.Select;
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
                user.setInstructor(resultSet.getBoolean("instructor"));
                user.setInstructor(resultSet.getBoolean("Student"));
                return user;
            }
        });
        map.addAttribute("users",userList);
        return "user";
    }
}
