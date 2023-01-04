package com.example.studentplanner.service;

import com.example.studentplanner.models.Course;
import com.example.studentplanner.models.Plan;
import com.example.studentplanner.models.User;
import com.example.studentplanner.repositorys.PlanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PlanService {
    private final PlanRepo planRepo;

    @Lazy
    @Autowired
    private ScheduleService scheduleService;

    @Lazy
    @Autowired
    private CourseService courseService;
    public PlanService(PlanRepo planRepo) {
        this.planRepo = planRepo;
    }

    public Plan save(User user){
        Plan plan = new Plan(user);
        return planRepo.save(plan);
    }

    public Plan get(Long id){
        return planRepo.findByUser_Id(id);
    }

    public Plan getPlan(Long id){
        return planRepo.findById(id).orElseThrow(()->new IllegalStateException("plan does not exist!") );
    }

    @Transactional
    public Plan addCurrentCourses(Long id,String courseCode){
        Plan plan = planRepo.findByUser_Id(id);
        List<String> currentCourses = plan.getCurrentCourses();
        currentCourses.add(courseCode);
        plan.setCurrentCourses(currentCourses);
        return plan;
    }

    @Transactional
    public Plan deleteCurrentCourses(Long id,String courseCode){
        Plan plan = planRepo.findByUser_Id(id);
        List<String> currentCourses = plan.getCurrentCourses();
        currentCourses.remove(courseCode);
        plan.setCurrentCourses(currentCourses);
        return plan;
    }

    @Transactional
    public Plan addPassedCourses(Long id,String courseCode){
        Plan plan = planRepo.findByUser_Id(id);
        List<String> passedCourses = plan.getPassedCourses();
        Course course = courseService.getByCode(courseCode);

        List<String> prerequisites= course.getPrerequisite();
        for(int i = 0 ; i < prerequisites.size() ; i++)
            if(!passedCourses.contains(prerequisites.get(i)))
                throw new IllegalStateException("prerequisite  " + prerequisites.get(i) + " is not passed:");
        passedCourses.add(courseCode);
        plan.setPassedCourses(passedCourses);
        deleteCurrentCourses(id,courseCode);
        scheduleService.deleteCourseFromSchedule(scheduleService.getSecludeByStudentId(id),courseService.getByCode(courseCode));

        return plan;
    }

    @Transactional
    public Plan deletePassedCourses(Long id,String courseCode){
        Plan plan = planRepo.findByUser_Id(id);
        List<String> passedCourses = plan.getPassedCourses();
        passedCourses.remove(courseCode);
        plan.setPassedCourses(passedCourses);
        return plan;
    }

}
