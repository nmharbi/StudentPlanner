package com.example.studentplanner.service;

import com.example.studentplanner.models.Course;
import com.example.studentplanner.models.Review;
import com.example.studentplanner.models.User;
import com.example.studentplanner.repositorys.ReviewRepo;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepo reviewRepo;

    public ReviewService(ReviewRepo reviewRepo){
        this.reviewRepo = reviewRepo;
    }

    public Review save(Review review){
        return reviewRepo.save(review);
    }

    public List<Review> getAll(){
        return reviewRepo.findAll();
    }

    public List<Review> getAllByCourse(Course course){
        return reviewRepo.findAllByCourse(course);
    }

    public List<Review> getAllByUser(User student){
        return reviewRepo.findAllByStudent(student);
    }

    public void delete(Long id){
        reviewRepo.deleteById(id);
    }

    public void deleteAllStudentComments(Long studentID){
        reviewRepo.deleteAllByStudent_Id(studentID);
    }

    public void deleteAllByCourse(Course course){
        reviewRepo.deleteAllByCourse(course);
    }

}
