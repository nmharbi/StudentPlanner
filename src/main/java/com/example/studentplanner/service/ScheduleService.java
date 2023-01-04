package com.example.studentplanner.service;

import com.example.studentplanner.models.Course;
import com.example.studentplanner.models.Plan;
import com.example.studentplanner.models.Schedule;
import com.example.studentplanner.repositorys.ScheduleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepo scheduleRepo;
    
    @Autowired
    private AbsenceService absenceService;

    @Autowired
    private PlanService planService;

    public ScheduleService(ScheduleRepo scheduleRepo) {
        this.scheduleRepo = scheduleRepo;
    }

    public Schedule getSecludeByStudentId(Long id){
        return scheduleRepo.findByStudent_Id(id).orElseThrow(()->new IllegalStateException("schedule does not exist!"));
    }

    public Schedule create(Schedule schedule){
        return scheduleRepo.save(schedule);
    }

    public Schedule get(Long id){
        return scheduleRepo.findById(id).orElseThrow(()->new IllegalStateException("schedule does not exist!"));
    }

    class SortByName implements Comparator<Course> {
        @Override
        public int compare(Course a, Course b) {
            return a.getId().compareTo(b.getId());
        }
    }

    public List<Course> getCoursesByDay(Long id,String day){
        List<String> colors = getColors();
        List<Course> courses = scheduleRepo.findByStudent_Id(id).get().getCourses();
        Collections.sort(courses, new SortByName());
        for (int i=0;i<courses.size();i++)
            courses.get(i).setColor(colors.get(i));
        List<Course> courses2= new ArrayList<>();
        for(int i = 0 ; i < courses.size() ; i++){
            List<String> strings = new ArrayList<>();
            strings = List.of(courses.get(i).getDays().split("-"));
            for(int j = 0 ; j < strings.size() ; j++)
                if(strings.get(j).equalsIgnoreCase(day))
                    courses2.add(courses.get(i));
        }
        return courses2;
    }

    public List<String> getColors(){
        List<String> colors= new ArrayList<>();
        colors.add("00FF00");
        colors.add("FFFF00");
        colors.add("FF00FF");
        colors.add("FF00FF");
        colors.add("FF00FF");
        colors.add("FF00FF");
        colors.add("FF00FF");
        colors.add("FF00FF");
        colors.add("FF00FF");
        colors.add("FF00FF");
        return colors;
    }

    @Transactional
    public void addCourseToSchedule(Schedule s,Course course){
        //Check if the student has passed this course prerequisite's
        if(checkPrerequisite(course,planService.get(s.getStudent().getId())))
            if(s!= null && scheduleRepo.findById(s.getId()).isPresent()){
                List<String> day = List.of(course.getDays().split("-"));
                //check if this course overlaps with any other course in this student schedule
                for(int i = 0; i < s.getCourses().size() ;i++)
                    for(int j = 0; j < day.size() ;j++)
                        if(s.getCourses().get(i).getDays().contains(day.get(j)))
                            if(overlap(s.getCourses().get(i),course ))
                                throw new IllegalStateException("course overlaps with :" + s.getCourses().get(i).getCode()+": " +s.getCourses().get(i).getName());
                Schedule schedule = s;
                List<Course> courses = schedule.getCourses();
                courses.add(course);
                schedule.setCourses(courses);
                //create absence object for this student and this course
                absenceService.create(course , s.getStudent());
                //Add this course to the current courses in the user plan
                planService.addCurrentCourses(schedule.getStudent().getId(),course.getCode());
            }
    }

    @Transactional
    public void deleteCourseFromSchedule(Schedule s,Course course){
        Schedule schedule = scheduleRepo.findById(s.getId()).get();
        //check if this schedule exists
        if(schedule!= null ){
                List<Course> courses = schedule.getCourses();
                if(courses.remove(course)){
                    schedule.setCourses(courses);
                    //delete absence object for this student and this course
                    absenceService.deleteByCourseAndStudent(course , schedule.getStudent());
                    planService.deleteCurrentCourses(schedule.getStudent().getId(),course.getCode());
                }
        }else
            throw new IllegalStateException("schedule does not exist!");
    }

    public void deleteAllByCourse(Course course){
        List<Schedule> schedules = scheduleRepo.findAll();
        for(int i = 0 ; i < schedules.size() ; i++)
            schedules.get(i).getCourses().remove(course);
    }

    private boolean overlap(Course c1 , Course c2){
        return c1.getStartTime().compareTo(c2.getEndTime()) <= 0 && c1.getEndTime().compareTo(c2.getStartTime()) >= 0;
    }

    private boolean checkPrerequisite(Course course , Plan plan){
        if(course.getPrerequisite().size() > 0) {
            List<String> prerequisite = course.getPrerequisite();
            List<String> passedCourses = plan.getPassedCourses();
            for(int i = 0 ; i < prerequisite.size() ; i++)
                if(!passedCourses.contains(prerequisite.get(i)))
                    throw new IllegalStateException("Cannot add this course since prerequisite :"+prerequisite.get(i) + " haven't been studied before ");
        }
        return true;
    }
}
