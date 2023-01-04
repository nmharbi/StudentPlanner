package com.example.studentplanner.controllers;

import com.example.studentplanner.models.Review;
import com.example.studentplanner.service.CourseService;
import com.example.studentplanner.service.ReviewService;
import com.example.studentplanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/review")
public class ReviewController {
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    @PostMapping(path = "/save/{courseId}/{userId}/{comment}")
    public Review save(@PathVariable Long courseId, @PathVariable Long userId, @PathVariable String comment){
        if(userService.get(userId).getCommentCertified()){
            Review review = new Review(comment , courseService.get(courseId), userService.get(userId));
            return reviewService.save(review);
        }else
            throw new IllegalStateException("User is blocked from commenting");
    }

    @GetMapping(path = "/getAll")
    public List<Review> getAll(){
        return reviewService.getAll();
    }

    @GetMapping(path = "/getAllByCourse/{courseId}")
    public List<Review> getAllByCourse(@PathVariable Long courseId){
        return reviewService.getAllByCourse(courseService.get(courseId));
    }

    @GetMapping(path = "/getAllByUser/{userId}")
    public List<Review> getAllByUser(@PathVariable Long userId){
        return reviewService.getAllByUser(userService.get(userId));
    }

    @PostMapping(path = "/delete/{reviewId}")
    public void delete(@PathVariable Long reviewId){
        reviewService.delete(reviewId);
    }

}
