package com.example.studentplanner.repositorys;

import com.example.studentplanner.models.Task;
import com.example.studentplanner.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task,Long> {
    <Optional> List<Task> findAllByStudent(User student);

    @Query("select t from Task t where t.student = ?1 order by t.date DESC")
    List<Task> findByStudentOrderByDateDesc(User student);




}
