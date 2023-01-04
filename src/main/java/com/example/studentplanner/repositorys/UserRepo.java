package com.example.studentplanner.repositorys;

import com.example.studentplanner.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    User findByVerificationCode(String verificationCode);

//    @Query("select u from User u where u.commentCertified = false")
//    List<User> findByCommentCertifiedFalse();

    @Query("select u from User u where u.commentCertified = false")
    List<User> findByCommentCertifiedFalse();





}
