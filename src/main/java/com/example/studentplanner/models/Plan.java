package com.example.studentplanner.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ElementCollection
    private List<String> passedCourses;

    @ElementCollection
    private List<String> currentCourses;

    @OneToOne
    private User user;

    public Plan(List<String> passedCourses, List<String> currentCourses, User user) {
        this.passedCourses = passedCourses;
        this.currentCourses = currentCourses;
        this.user = user;
    }

    public Plan(User user) {
        this.user = user;
    }

    public Plan() {
    }

    public List<String> getPassedCourses() {
        return passedCourses;
    }

    public void setPassedCourses(List<String> passedCourses) {
        this.passedCourses = passedCourses;
    }

    public List<String> getCurrentCourses() {
        return currentCourses;
    }

    public void setCurrentCourses(List<String> currentCourses) {
        this.currentCourses = currentCourses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}