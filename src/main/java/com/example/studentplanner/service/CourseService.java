package com.example.studentplanner.service;

import com.example.studentplanner.models.Course;
import com.example.studentplanner.models.Plan;
import com.example.studentplanner.repositorys.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CourseService {
    private final CourseRepo courseRepo;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private AbsenceService absenceService;

    @Autowired
    private PlanService planService;

    public CourseService(CourseRepo courseRepo){
        this.courseRepo = courseRepo;
    }

    public Course get(Long courseid){
        return courseRepo.findById(courseid).orElseThrow(()->new IllegalStateException("course does not exist!"));
    }

    public Course add(Course course){
        Course course1 = courseRepo.findTopByCode(course.getCode()).orElseThrow(()->new IllegalStateException("There is no course with the same code!"));
        List<String> prerequisites =  course1.getPrerequisite();
        for(int i = 0 ; i < prerequisites.size() ; i++)
            course.addPrerequisite(prerequisites.get(i));


        if(course1.getWhatsapp() != null && course1.getWhatsapp().length() > 1)
            course.setWhatsapp(course1.getWhatsapp());
        if(course1.getTelegram() != null && course1.getTelegram().length() > 1)
            course.setTelegram(course1.getTelegram());
        course.setSection(generate());
        course.setName(course1.getName());
        course.setCredit(course1.getCredit());
        course.setColor("");
        return courseRepo.save(course);
    }

    public Course getByCode(String code){
        return courseRepo.findTopByCode(code).orElseThrow(()->new IllegalStateException("There is no course with the same code!"));
    }

    @Transactional
    public void delete(Long courseId){
        Course course = get(courseId);
        scheduleService.deleteAllByCourse(course);
        absenceService.deleteAllByCourse(course);
        reviewService.deleteAllByCourse(course);
        courseRepo.deleteById(courseId);
    }

    public List<Course> getAll(){
        return courseRepo.findAll();
    }

    public List<Course> getFor(Long studentId){
        Plan plan = planService.get(studentId);
        return getfor(courseRepo.findAll(),plan.getPassedCourses(),plan.getCurrentCourses(),plan);
    }

    private List<Course> getfor(List<Course> courses ,List<String> passedCourses,List<String> currentCourses,Plan plan ){
        for(int i = 0 ; i < courses.size() ;i++)
            if(passedCourses.contains(courses.get(i).getCode()))
                courses.remove(i--);
            else if(currentCourses.contains(courses.get(i).getCode()))
                courses.remove(i--);
            else if(checkPrerequisite(courses.get(i),plan))
                courses.remove(i--);
        return courses;
    }

    @Transactional
    public Course update(Course course , Long courseid){
        Course c = courseRepo.findById(courseid).orElseThrow(()->new IllegalStateException("course does not exist!"));
        if(course.getCode() != null && course.getCode().length() > 0)
            c.setCode(course.getCode());

        if(course.getCredit() != null && course.getCredit().length() > 0)
            c.setCredit(course.getCredit());

        if(course.getName() != null && course.getName().length() > 0)
            c.setName(course.getName());

        if(course.getDays() != null && course.getDays().length() > 0)
            c.setDays(course.getDays());

        if(course.getWhatsapp() != null && course.getWhatsapp().length() > 0)
            c.setWhatsapp(course.getWhatsapp());

        if(course.getTelegram() != null && course.getTelegram().length() > 0)
            c.setTelegram(course.getTelegram());

        if(course.getPrerequisite() != null && course.getPrerequisite().size() > 0 )
            c.setPrerequisite(course.getPrerequisite());

        if(course.getSection() > 0  && courseRepo.findBySection(course.getSection()).isEmpty())
            c.setSection(course.getSection());

        if(course.getStartTime() != null && course.getEndTime() != null && course.getStartTime().before(course.getEndTime())){
            c.setStartTime(course.getStartTime());
            c.setEndTime(course.getEndTime());
        }
        return c;
    }

    private boolean checkPrerequisite(Course course , Plan plan){
        if(course.getPrerequisite().size() != 0) {
            List<String> prerequisite = course.getPrerequisite();
            List<String> passedCourses = plan.getPassedCourses();
            for(int i = 0 ; i < prerequisite.size() ; i++)
                if(passedCourses.contains(prerequisite.get(i)))
                    return false;
        }else return false;
        return true;
    }

    public int generate() {
        Random r = new Random( System.currentTimeMillis() );
        return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
    }

}
