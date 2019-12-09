package group11project.demo.entity;

import java.util.Date;

public class CalendarEvent {
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    private String eventName;
    private String eventID;
    private Date date;
}
