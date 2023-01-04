package com.example.studentplanner.repositorys;

import com.example.studentplanner.models.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepo extends JpaRepository<Plan,Long> {
    <Optional> Plan findByUser_Id(Long userId);
}
