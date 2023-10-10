package org.example.service;

import org.example.domain.Task;
import org.example.repository.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepo taskRepo;

    @Autowired
    public TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    public List<Task> getAllTask(){
        return taskRepo.findAll();
    }

    public void saveTask(Task task){
        taskRepo.save(task);
    }

    public void deleteTaskById(Integer id){
        taskRepo.deleteById(id);
    }

    public Task getTaskById(Integer id){
        Optional<Task> optionalTask = taskRepo.findById(id);
        if(optionalTask.isPresent()){
            return optionalTask.get();
        }
        throw new RuntimeException("Task not found for id : " + id);
    }

    public Page<Task> findPaginated(int numberPage, int sizePage, String sortField, String sortDirection){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(numberPage - 1, sizePage, sort);
        return taskRepo.findAll(pageable);
    }




}
