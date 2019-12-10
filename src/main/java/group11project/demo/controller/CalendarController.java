package group11project.demo.controller;


import group11project.demo.entity.CalendarEvent;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Controller
public class CalendarController {
    @Resource
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/calendar")
    public String getCalendarInfo(ModelMap map){
        String sql = "Select*FROM user.calendarevent";
        List<CalendarEvent> calendar = jdbcTemplate.query(sql, new RowMapper<CalendarEvent>() {
            CalendarEvent calendarEvent = null;
            @Override
            public CalendarEvent mapRow(ResultSet resultSet, int i) throws SQLException {
                calendarEvent = new CalendarEvent();
                calendarEvent.setDate(resultSet.getInt("date"));
                calendarEvent.setMonth(resultSet.getInt("month"));
                calendarEvent.setEventName(resultSet.getString("eventName"));
                calendarEvent.setEventID(resultSet.getInt("eventID"));
                return calendarEvent;
            }
        });
        map.addAttribute("calendar", calendar);
        return "calendar";
    }

    @PostMapping("/addEvent")
    public String add_event(@RequestParam("eventID") int eventID,
                            @RequestParam("date") int date,
                            @RequestParam("month") int month,
                            @RequestParam("eventName") String eventName,
                            RedirectAttributes redirectAttributes){
        String sql = "INSERT INTO `user`.`calendarevent` (`eventID`, `eventName`, `date`, `month`) VALUES ('" + eventID
                +"','"+eventName+"','"+date+"','"+month+ "')";

        try{
            jdbcTemplate.update(sql);
            return "index";
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("warning","Sign up failed");
            return "redirect:/signUp";
        }
    }
}
