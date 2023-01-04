package com.example.studentplanner.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Table
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(nullable = true)
    private Date date;

    @Column(nullable = true)
    private Time time;

    @ManyToOne
    private User student;

    public Task(String title, Date date, User student, Time time) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.student = student;
    }

    public Task() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date deadline) {
        this.date = deadline;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

}
