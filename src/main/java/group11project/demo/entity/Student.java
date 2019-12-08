package group11project.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Student {
    private final String universityID;
    private final String name;

    public String getUniversityID() {
        return universityID;
    }

    public String getName() {
        return name;
    }

    /*{
        "name": "Yuhan Jin",
        "id": "01270225"
    }*/
    // use json format POST requests to use this constructor
    public Student(@JsonProperty("id") String universityID,
                   @JsonProperty("name") String name) {
        this.universityID = universityID;
        this.name = name;
    }
}

