package group11project.demo.controller;

import com.mysql.cj.jdbc.admin.MiniAdmin;
import group11project.demo.entity.MailVo;
import group11project.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Controller
public class PasswordController {
    @Resource
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/forgetPassword")
    public String forgetPassword(){
        return "forgetPassword";
    }

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/forgetpassword")
    public String forgetPassword(@RequestParam("username") String username,
                               @RequestParam("email") String email,
                               HttpSession session,
                               RedirectAttributes redirectAttributes){
        System.out.println(1);
        String sql = "Select*FROM user Where id = '" + username + "'";
        System.out.println(sql);
        List<User> userList = jdbcTemplate.query(sql, new RowMapper<User>() {
            User user = null;
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                user = new User();
                user.setId(resultSet.getString("id"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                return user;
            }
        });

        if (userList.isEmpty()){
            redirectAttributes.addFlashAttribute("warning","Username/Password incorrect");
            return "redirect:/forget";
        }else if (userList.get(0).getId().equals(username) && userList.get(0).getEmail().equals(email)){
            System.out.println(1);
            MailVo mailVo = new MailVo();
            mailVo.setFrom("Bicon");
            mailVo.setTo(email);
            mailVo.setText(userList.get(0).getPassword());
            sendMymail(mailVo);
            return "redirect:/login";
        }else {
            redirectAttributes.addFlashAttribute("warning","Username/Password incorrect");
            return "redirect:/forget";
        }
    }


    @RequestMapping("/Findpassword/{id}")
    public String find_password(@PathVariable("id") String id,
                                ModelMap map){
        String sql = "Select*FROM user Where id = '" + id + "'";
        List<User> userList = jdbcTemplate.query(sql, new RowMapper<User>() {
            User user = null;
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                user = new User();
                user.setId(resultSet.getString("id"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                return user;
            }
        });
        map.addAttribute("users",userList);
        return "findPassword";
    }
    private void sendMymail(MailVo mailVo){
        try{
            System.out.println(1);
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(),true);
            messageHelper.setFrom(mailVo.getFrom());
            messageHelper.setTo(mailVo.getTo());
            messageHelper.setSubject("Your password");
            messageHelper.setText(mailVo.getText());
            mailSender.send(messageHelper.getMimeMessage());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
