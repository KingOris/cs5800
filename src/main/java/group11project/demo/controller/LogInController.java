package group11project.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.Map;



@Controller
public class LogInController {
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/log")
    public String Log_in(@RequestParam("username") String username, @RequestParam("password") String password){
        testDB databaseRef = new testDB();
        if ("user".equals(username) && "password".equals(password)){
            return "index";
        }else {
            return "login";
        }
    }
}


