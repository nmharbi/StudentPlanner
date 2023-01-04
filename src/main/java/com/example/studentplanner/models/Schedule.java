package com.example.studentplanner.models;


import javax.persistence.*;
import java.util.List;


@Entity
@Table
public class Schedule implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User student;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Course> courses;

    public Schedule(User Student, List<Course> courses) {
        this.student = Student;
        this.courses = courses;
    }

    public Schedule() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> coureses) {
        this.courses = coureses;
    }

}
