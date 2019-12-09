package group11project.demo.entity;

import java.util.Date;

public class CalendarDate {
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public CalendarEvent getCalendarEvent() {
        return calendarEvent;
    }

    public void setCalendarEvent(CalendarEvent calendarEvent) {
        this.calendarEvent = calendarEvent;
    }

    private Date date;
    private CalendarEvent calendarEvent;
}
