package com.example.studentplanner.repositorys;

import com.example.studentplanner.models.Schedule;
import com.example.studentplanner.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScheduleRepo extends JpaRepository<Schedule,Long> {
    Optional<Schedule> findByStudent(User student);
    Optional<Schedule> findByStudent_Id(Long id);

    @Query("select s from Schedule s inner join s.courses courses where courses.code like concat('%', ?1, '%')")
    Optional<Schedule> findByCourses_CodeContains(String code);

    @Query("select s from Schedule s inner join s.courses courses where courses.code = ?1")
    Optional<Schedule> findByCourses_Code(String code);

    Optional<Schedule> findTopByCourses_Code(String code);




}
