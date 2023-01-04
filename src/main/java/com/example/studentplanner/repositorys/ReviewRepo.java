package com.example.studentplanner.repositorys;


import com.example.studentplanner.models.Course;
import com.example.studentplanner.models.Review;
import com.example.studentplanner.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepo extends JpaRepository<Review,Long> {

    List<Review> findAllByCourse(Course course);

    List<Review> findAllByStudent(User student);

    void deleteAllByStudent_Id(Long student_id);

    void deleteAllByCourse(Course course);


}
