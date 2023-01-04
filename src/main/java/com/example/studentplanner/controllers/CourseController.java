package com.example.studentplanner.controllers;

import com.example.studentplanner.models.Course;
import com.example.studentplanner.service.CourseService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/course")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping(path = "/get/{id}")
    public Course get(@PathVariable Long id){
        return courseService.get(id);
    }

    @GetMapping(path = "/getAll")
    public List<Course> getAll(){
        return courseService.getAll();
    }

    @GetMapping(path = "/getFor/{userId}")
    public List<Course> getFor(@PathVariable Long userId){
        return courseService.getFor(userId);
    }

    @PostMapping(path = "/add")
    public Course add(@RequestBody Course course){
        return courseService.add(course);
    }

    @PostMapping(path = "/delete/{courseId}")
    public void delete(@PathVariable Long courseId){
        courseService.delete(courseId);
    }


    @PostMapping(path = "/update/{id}")
    public Course update(@RequestBody Course course, @PathVariable Long id){
        return courseService.update(course,id);
    }

}
