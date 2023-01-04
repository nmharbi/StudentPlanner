package com.example.studentplanner.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Time;
import java.util.Collection;
import java.util.List;


@Entity
@Table
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false,unique = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String credit;

    @Column(nullable = true)
    private Time startTime;

    @Column(nullable = true)
    private Time endTime;

    @Column(nullable = false)
    private String days;

    @Column(nullable = false)
    private String color;

    @Column(unique = true )
    private long section;

    @Column(nullable = true )
    private String whatsapp;

    @Column(nullable = true )
    private String telegram;

    @ElementCollection
    private List<String> prerequisite;



    @JsonIgnore
    @JsonIgnoreProperties({"hirbernateLazyInitializer"})
    @ManyToMany(mappedBy =  "courses")
    private List<Schedule> schedules;

    public Course(Long id, String code, String name, String credit, Time startTime, Time endTime,
                  String days, String color, List<Schedule> schedules, long section) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.credit = credit;
        this.startTime = startTime;
        this.endTime = endTime;
        this.days = days;
        this.color = color;
        this.schedules = schedules;
        this.section = section;
    }

    public Course() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public Collection<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public long getSection() {
        return section;
    }

    public void setSection(long section) {
        this.section = section;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public List<String> getPrerequisite() {
        return prerequisite;
    }

    public void setPrerequisite(List<String> prerequisite) {
        this.prerequisite = prerequisite;
    }
    public void addPrerequisite(String prerequisite) {
        this.prerequisite.add(prerequisite);
    }

}
