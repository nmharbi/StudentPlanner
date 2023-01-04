package com.example.studentplanner.models;

import javax.persistence.*;

@Entity
@Table

public class Absence{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = true)
    private Long numberOfAbsence;

    @OneToOne
    private Course course;

    @OneToOne
    private User student;

    public Absence(Course course, User student, Long numberOfAbsence) {
        this.course = course;
        this.student = student;
        this.numberOfAbsence = numberOfAbsence;
    }

    public Absence() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Long getNumberOfAbsence() {
        return numberOfAbsence;
    }

    public void setNumberOfAbsence(Long numberOfAbsence) {
        if(numberOfAbsence >= 0)
            this.numberOfAbsence = numberOfAbsence;
    }

    public void addAbsence(){
        numberOfAbsence++;
    }

    public void deleteAbsence(){
        if(numberOfAbsence > 0)
            numberOfAbsence--;
    }

    public void resetAbsence(){
        numberOfAbsence = 0L;
    }

}
