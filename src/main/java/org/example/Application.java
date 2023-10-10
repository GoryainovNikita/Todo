package org.example;


import org.example.domain.Task;
import org.example.service.TaskService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        TaskService taskService = context.getBean(TaskService.class);

        Task task = taskService.getTaskById(1);
        System.out.println(task);
    }
}