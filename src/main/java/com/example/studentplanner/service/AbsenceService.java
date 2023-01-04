package com.example.studentplanner.service;

import com.example.studentplanner.models.Absence;
import com.example.studentplanner.models.Course;
import com.example.studentplanner.models.User;
import com.example.studentplanner.repositorys.AbsenceRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AbsenceService {
    private final AbsenceRepo absenceRepo;

    public AbsenceService(AbsenceRepo absenceRepo){
        this.absenceRepo = absenceRepo;
    }

    public Absence create(Course course , User student){
        Absence absence = new Absence(course,student , 0L);
        return absenceRepo.save(absence);
    }

    public Absence getByCourseAndStudent(Course course, User student){
        return absenceRepo.findByCourseAndStudent(course, student);
    }

    public void deleteByCourseAndStudent(Course course, User student){
         absenceRepo.deleteByCourseAndStudent(course,student);
    }

    public void deleteAllByCourse(Course course){
        absenceRepo.deleteAllByCourse(course);
    }

    @Transactional
    public void addAbsence(Long absenceId){
        Absence absence = absenceRepo.getById(absenceId);
        absence.addAbsence();
    }

    @Transactional
    public void deleteAbsence(Long absenceId){
        Absence absence = absenceRepo.getById(absenceId);
        absence.deleteAbsence();
    }

    @Transactional
    public void resetAbsence(Long absenceId){
        Absence absence = absenceRepo.getById(absenceId);
        absence.resetAbsence();
    }

}
