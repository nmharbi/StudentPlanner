package com.example.studentplanner.controllers;

import com.example.studentplanner.models.Plan;
import com.example.studentplanner.service.PlanService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/plan")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping(path = "/get/{id}")
    public Plan get(@PathVariable Long id){
        return planService.get(id);
    }


    @PostMapping(path = "/addPassedCourses/{id}/{courseCode}")
    public Plan addPassedCourses(@PathVariable Long id,@PathVariable String courseCode){
        return planService.addPassedCourses(id,courseCode);
    }

    @PostMapping(path = "/deletePassedCourses/{id}/{courseCode}")
    public Plan deletePassedCourses(@PathVariable Long id,@PathVariable String courseCode){
        return planService.deletePassedCourses(id,courseCode);
    }

}
