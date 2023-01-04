package com.example.studentplanner.repositorys;

import com.example.studentplanner.models.Absence;
import com.example.studentplanner.models.Course;
import com.example.studentplanner.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AbsenceRepo extends JpaRepository<Absence,Long> {
    <Optional> Absence findByCourseAndStudent(Course course, User student);
    <Optional> void deleteByCourseAndStudent(Course course, User student);

    @Transactional
    @Modifying
    @Query("delete from Absence a where a.course = ?1")
    int deleteAllByCourse(Course course);

}
