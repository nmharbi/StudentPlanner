package com.example.studentplanner.repositorys;

import com.example.studentplanner.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepo extends JpaRepository<Course,Long> {
    Optional<Course> findBySection(long section);
    Optional<Course> findTopByCode(String code);
}
