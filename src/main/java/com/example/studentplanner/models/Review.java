package com.example.studentplanner.models;

import javax.persistence.*;

@Entity
@Table

public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = true)
    private String comment;

    @ManyToOne
    private Course course;

    @OneToOne
    private User student;

    public Review(String comment, Course course, User student) {
        this.comment = comment;
        this.course = course;
        this.student = student;
    }

    public Review() {

    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
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

}