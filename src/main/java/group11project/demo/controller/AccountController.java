package group11project.demo.controller;

import group11project.demo.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

// TODO: controller for account settings page
public class AccountController {
    @Resource
    private JdbcTemplate jdbcTemplate;

    // todo request mapping
    public String getUserInfo(ModelMap map){
        String sql = "Select*FROM user";
        List<User> userList = jdbcTemplate.query(sql, new RowMapper<User>() {
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
        return "account";
    }
}
