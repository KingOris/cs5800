package group11project.demo.entity;

public class Course {
    private final String courseCode;
    private String courseName;
    private String primaryInstructor;
    private int numOfSeats;
    private int semesterHours;
    private String department;

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setPrimaryInstructor(String primaryInstructor) {
        this.primaryInstructor = primaryInstructor;
    }

    public void setNumOfSeats(int numOfSeats) {
        this.numOfSeats = numOfSeats;
    }

    public void setSemesterHours(int semesterHours) {
        this.semesterHours = semesterHours;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getPrimaryInstructor() {
        return primaryInstructor;
    }

    public int getNumOfSeats() {
        return numOfSeats;
    }

    public int getSemesterHours() {
        return semesterHours;
    }

    public String getDepartment() {
        return department;
    }

    public Course(String courseCode, String courseName, String primaryInstructor, int numOfSeats, int semesterHours, String department) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.primaryInstructor = primaryInstructor;
        this.numOfSeats = numOfSeats;
        this.semesterHours = semesterHours;
        this.department = department;
    }
}
