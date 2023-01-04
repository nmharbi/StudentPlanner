package com.example.studentplanner.controllers;

import com.example.studentplanner.models.User;
import com.example.studentplanner.models.Task;
import com.example.studentplanner.service.UserService;
import com.example.studentplanner.service.TaskService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/task")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    private UserService studentService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping (path = "/getAll")
    public List<Task> getAll(){
        return taskService.getAll();
    }

    @GetMapping(path = "/get/{id}")
    public Task get(@PathVariable Long id){
        return taskService.get(id);
    }

    @PostMapping(path = "/create/{studentId}")
    public Task create(@RequestBody Task task, @PathVariable Long studentId){
        task.setStudent(studentService.get(studentId));
        return taskService.add(task);
    }

    @JsonFormat(pattern="yyyy-MM-dd")
    @GetMapping (path = "/getAllByStudent/{id}")
    public List<Task> getAllByStudent(@PathVariable Long id){
        User student = studentService.get(id);
        return taskService.getAllByStudent(student);
    }

    @PostMapping (path = "/delete/{id}")
    public void delete(@PathVariable Long id){
        taskService.delete(id);
    }

    @PostMapping  (path = "/update/{id}")
    public Task update(@RequestBody Task task , @PathVariable Long id){
        return taskService.update(task,id);
    }
}
