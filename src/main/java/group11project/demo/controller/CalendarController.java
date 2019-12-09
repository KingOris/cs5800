package group11project.demo.controller;

import group11project.demo.entity.CalendarEvent;
import group11project.demo.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CalendarController {
    @Resource
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/calendar")
    public String getCalendarInfo(ModelMap map){
        String sql = "Select*FROM calendarevent";
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
}
