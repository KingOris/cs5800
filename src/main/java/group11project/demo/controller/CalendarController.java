package group11project.demo.controller;

import group11project.demo.entity.CalendarDate;
import group11project.demo.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.ui.ModelMap;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

// TODO controller for calendar page
public class CalendarController {
    @Resource
    private JdbcTemplate jdbcTemplate;

    // todo request mapping
    public String getCalendarInfo(ModelMap map){
        String sql = "Select*FROM calendar";
        List<CalendarDate> calendar = jdbcTemplate.query(sql, new RowMapper<CalendarDate>() {
            CalendarDate calendarDate = null;
            @Override
            public CalendarDate mapRow(ResultSet resultSet, int i) throws SQLException {
                calendarDate = new CalendarDate();

                // todo figure out how to store the event and date in mySQL
                //calendarDate.setCalendarEvent(resultSet.getString("calendarEvent"));
                calendarDate.setDate(resultSet.getDate("date"));
                return calendarDate;
            }
        });
        map.addAttribute("calendar", calendar);
        return "calendar";
    }
}
