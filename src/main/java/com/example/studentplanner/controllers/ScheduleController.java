package com.example.studentplanner.controllers;

import com.example.studentplanner.models.Course;
import com.example.studentplanner.models.Schedule;
import com.example.studentplanner.models.User;
import com.example.studentplanner.service.CourseService;
import com.example.studentplanner.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    private CourseService courseService;

    public ScheduleController(ScheduleService scheduleService){this.scheduleService = scheduleService;}


    @GetMapping(path = "/get/{id}")
    public Schedule get(@PathVariable Long id){
        return scheduleService.get(id);
    }

    @GetMapping(path = "/getCoursesByStudentIdWithDay/{id}/{day}")
    public List<Course> getCoursesByStudentIdWithDay(@PathVariable Long id,@PathVariable String day){
        return scheduleService.getCoursesByDay(id,day);
    }

    @PostMapping(path = "/create/{id}")
    public Schedule create(@PathVariable Long id) {
        User student = new User();
        student.setId(id);
        Schedule s = new Schedule();
        s.setStudent(student);
        return scheduleService.create(s);
    }

    @PostMapping(path = "/addCourseToSchedule/{courseId}/{usertId}")
    public void addCourseToSchedule(@PathVariable Long courseId , @PathVariable Long usertId){
        Schedule schedule =  scheduleService.getSecludeByStudentId(usertId);
        Course course = courseService.get(courseId);
        scheduleService.addCourseToSchedule(schedule,course);
    }

    @PostMapping(path = "/deleteCourseFromSchedule/{courseId}/{usertId}")
    public void deleteCourseFromSchedule(@PathVariable Long courseId , @PathVariable Long usertId){
        Schedule schedule = scheduleService.getSecludeByStudentId(usertId);
        Course course = courseService.get(courseId);
        scheduleService.deleteCourseFromSchedule(schedule,course);
    }

}
