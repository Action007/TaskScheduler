package com.example;

import java.time.LocalDateTime;
import java.util.Set;

import main.java.com.taskmanager.model.Priority;
import main.java.com.taskmanager.model.Status;
import main.java.com.taskmanager.model.Task;
import main.java.com.taskmanager.service.TaskService;
import main.java.com.taskmanager.util.CollectionUtils;

/**
 * Hello world!
 *
 */
public class Main {
    public static void main(String[] args) {
        TaskService service = new TaskService();

        Task t1 = new Task("1", "Fix bug", Priority.URGENT,
                LocalDateTime.now().plusHours(2),
                Status.IN_PROGRESS,
                Set.of("bug", "critical"));

        Task t2 = new Task("2", "Write doc", Priority.LOW,
                LocalDateTime.now().plusDays(1),
                Status.TODO,
                Set.of("docs"));

        service.addTask(t1);
        service.addTask(t2);

        System.out.println("Urgent tasks: " + service.getUrgentTasksForTesting());
        System.out.println("All known tags: " + service.getAllKnownTagsForTesting());
    }
}
