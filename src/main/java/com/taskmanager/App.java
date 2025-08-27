package com.taskmanager;

import java.time.LocalDateTime;
import java.util.Set;

import com.taskmanager.model.Priority;
import com.taskmanager.model.Status;
import com.taskmanager.model.Task;
import com.taskmanager.service.TaskService;

public class App {
  public static void main(String[] args) {
    TaskService service = new TaskService();

    Task t1 =
        new Task(
            "1",
            "Fix bug",
            Priority.URGENT,
            LocalDateTime.now().plusHours(2),
            Status.IN_PROGRESS,
            Set.of("bug", "critical"));

    Task t2 =
        new Task(
            "2",
            "Write doc",
            Priority.LOW,
            LocalDateTime.now().plusDays(1),
            Status.TODO,
            Set.of("docs"));

    service.addTask(t1);
    service.addTask(t2);

    System.out.println("Urgent tasks: " + service.getUrgentTasksForTesting());
    System.out.println("All known tags: " + service.getAllKnownTagsForTesting());
  }
}
