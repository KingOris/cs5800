package group11project.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Instructor {
    private final String universityID;
    private final String name;

    public String getUniversityID() {
        return universityID;
    }

    public String getName() {
        return name;
    }

    public Instructor(@JsonProperty("id") String universityID,
                      @JsonProperty("name") String name) {
        this.universityID = universityID;
        this.name = name;
    }
}
