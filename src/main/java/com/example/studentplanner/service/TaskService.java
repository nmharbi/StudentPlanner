package com.example.studentplanner.service;

import com.example.studentplanner.models.User;
import com.example.studentplanner.models.Task;
import com.example.studentplanner.repositorys.TaskRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepo taskRepo;

    public TaskService(TaskRepo taskRepo){
        this.taskRepo = taskRepo;
    }

    public List<Task> getAll(){
        return taskRepo.findAll();
    }

    public List<Task> getAllByStudent(User user){
        return taskRepo.findByStudentOrderByDateDesc(user);
    }

    public Task get(Long id){
        return taskRepo.findById(id).orElseThrow(()->new IllegalStateException("task does not exist!"));
    }

    public Task add(Task task){
        return taskRepo.save(task);
    }

    public void delete(Long id ){
        taskRepo.deleteById(id);
    }

    @Transactional
    public Task update(Task task , Long id){
        Task t = taskRepo.findById(id).orElseThrow(()->new IllegalStateException("task does not exist!"));
        if(task.getTitle() != null )
            t.setTitle(task.getTitle());
        if(task.getDate() != null)
            t.setDate(task.getDate());
        if(task.getTime() != null)
            t.setTime(task.getTime());


        return t;
    }
}
