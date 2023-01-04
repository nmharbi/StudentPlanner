package com.example.studentplanner.controllers;

import com.example.studentplanner.models.Course;
import com.example.studentplanner.models.User;
import com.example.studentplanner.service.AbsenceService;
import com.example.studentplanner.service.CourseService;
import com.example.studentplanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/absence")
public class AbsenceController {
    private final AbsenceService absenceService;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    public AbsenceController(AbsenceService absenceService){
        this.absenceService = absenceService;
    }

    @GetMapping(path = "get/{userId}/{courseId}")
    public Long get(@PathVariable Long userId, @PathVariable Long courseId){
        User student = userService.get(userId);
        Course course = courseService.get(courseId);
        return absenceService.getByCourseAndStudent(course, student).getNumberOfAbsence();
    }

    @PostMapping(path = "addAbsence/{userId}/{courseId}")
    public Long addAbsence(@PathVariable Long userId, @PathVariable Long courseId){
        absenceService.addAbsence(absenceService.getByCourseAndStudent( courseService.get(courseId) , userService.get(userId) ).getId());
        return get(userId,courseId);
    }

    @PostMapping(path = "deleteAbsence/{userId}/{courseId}")
    public Long deleteAbsence(@PathVariable Long userId, @PathVariable Long courseId){
        absenceService.deleteAbsence(absenceService.getByCourseAndStudent( courseService.get(courseId) , userService.get(userId) ).getId());
        return get(userId,courseId);
    }

    @PostMapping(path = "resetAbsence/{userId}/{courseId}")
    public Long resetAbsence(@PathVariable Long userId, @PathVariable Long courseId){
        absenceService.resetAbsence(absenceService.getByCourseAndStudent( courseService.get(courseId) , userService.get(userId) ).getId());
        return get(userId,courseId);
    }

}
