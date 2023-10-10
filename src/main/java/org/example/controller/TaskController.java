package org.example.controller;

import org.example.domain.Status;
import org.example.domain.Task;
import org.example.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TaskController {


    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String getHomePage(Model model){
        return findPaginated(1, "Description", "asc", model);
    }
    @PostMapping("/")
    public String saveTask(Model model,
                           @RequestParam(value = "desc") String desc,
                           @RequestParam(value = "status") String status){
        Task newTask = new Task();
        newTask.setDescription(desc);
        newTask.setStatus(Status.valueOf(status));
        taskService.saveTask(newTask);
        return "redirect:/";
    }

    @GetMapping("/showFromForUpdate/{id}")
    public String showFromForUpdate(@PathVariable(value = "id") int id, Model model){
        Task task = taskService.getTaskById(id);
        model.addAttribute("task", task);
        return "update_task";
    }
    @PostMapping("/updateTask")
    public String updateTask(@ModelAttribute(value = "task") Task task){
        taskService.saveTask(task);
        return "redirect:/";
    }
    @GetMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable(value = "id") int id){
        taskService.deleteTaskById(id);
        return "redirect:/";
    }

    @GetMapping("/page/{numberPage}")
    public String findPaginated(@PathVariable(value = "numberPage") int numberPage,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model){
        int pageSize = 10;
        Page<Task> taskServicePaginated = taskService.findPaginated(numberPage, pageSize, sortField, sortDir);
        model.addAttribute("currentPage", numberPage);
        model.addAttribute("totalPages", taskServicePaginated.getTotalPages());
        model.addAttribute("totalElements", taskServicePaginated.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equalsIgnoreCase("asc") ? "desc" : "asc");

        model.addAttribute("listTask", taskServicePaginated.getContent());

        return "index";
    }
}
