package com.taskmanager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import com.taskmanager.model.Priority;
import com.taskmanager.model.Status;
import com.taskmanager.model.Task;
import com.taskmanager.service.DeadlineScheduler;
import com.taskmanager.service.TaskService;
import com.taskmanager.util.CollectionUtils;

public class Main {
    public static void main(String[] args) {
        TaskService taskService = new TaskService();
        DeadlineScheduler scheduler = new DeadlineScheduler();
        // TagService tagService = new TagService(); // uncomment when implemented

        LocalDateTime now = LocalDateTime.now();

        // === CREATE TASKS ===
        Task t1 = new Task(
                "1",
                "Fix login bug",
                Priority.URGENT,
                now.plusHours(2),
                Status.TODO,
                Set.of("bug", "urgent", "auth"));

        Task t2 = new Task(
                "2",
                "Write API docs",
                Priority.MEDIUM,
                now.plusDays(1),
                Status.IN_PROGRESS,
                Set.of("docs", "api"));

        Task t3 = new Task(
                "3",
                "Refactor service layer",
                Priority.HIGH,
                now.plusHours(5),
                Status.TODO,
                Set.of("refactor", "urgent"));

        Task t4 = new Task(
                "4",
                "Design dashboard",
                Priority.LOW,
                now.minusHours(1),
                Status.TODO,
                Set.of("design", "ui"));

        // === ADD TASKS ===
        for (Task task : Arrays.asList(t1, t2, t3, t4)) {
            taskService.addTask(task);
            scheduler.scheduleTask(task); // assuming you have this method
        }

        // === TEST COLLECTION UTILS ===
        Set<String> tagsA = new HashSet<>(Set.of("work", "urgent"));
        Set<String> tagsB = new TreeSet<>(Set.of("personal", "urgent", "work"));

        Set<String> union = CollectionUtils.union(tagsA, tagsB);
        System.out.println("Union: " + union);

        Set<String> intersection = CollectionUtils.intersection(tagsA, tagsB);
        System.out.println("Intersection: " + intersection);

        // PECS test: add to List<Object>
        List<Object> objectList = new ArrayList<>();
        List<Task> tasksToAdd = Arrays.asList(t1, t2, t3, t4); // can't get from service yet
        CollectionUtils.addAllTo(objectList, tasksToAdd);
        System.out.println("PECS succeeded: " + objectList.size() + " tasks added");

        // Filter urgent/high
        List<Task> filtered = new ArrayList<>();
        CollectionUtils.filterAndCopyTo(
                tasksToAdd,
                task -> task.getPriority() == Priority.URGENT || task.getPriority() == Priority.HIGH,
                filtered);
        System.out.println("Filtered urgent/high: " + filtered.size());

        // === TEST TAGS VIA TASK SERVICE ===
        System.out.println("All known tags: " + taskService.getAllKnownTagsForTesting());

        // === TEST SCHEDULER ===
        List<Task> imminent = scheduler.getImminentTasks(Duration.ofHours(24));
        System.out.println("Imminent tasks (next 24h): " + imminent.size());

        List<Task> overdue = scheduler.getTasksBefore(now);
        System.out.println("Overdue tasks: " + overdue.size());

        Optional<LocalDateTime> nextDeadline = scheduler.getNextDeadline();
        System.out.println("Next deadline: " + nextDeadline.orElse(null));

        // === TEST URGENT TASKS NAVIGATION ===
        NavigableSet<Task> urgentView = taskService.getUrgentTasksForTesting();

        System.out.println("Urgent tasks (sorted by deadline):");
        for (Task task : urgentView) {
            System.out.println("  🔥 " + task.getTitle() + " @ " + task.getDeadline());
        }

        // Probe for higher()
        Task probe = new Task(
                "0", "Probe", Priority.URGENT, now.plusHours(3), Status.TODO, Set.of());

        Task nextAfter = urgentView.higher(probe);
        System.out.println("First task after probe: " +
                (nextAfter != null ? nextAfter.getTitle() : "None"));

        Set<Task> beforeProbe = urgentView.headSet(probe, false);
        System.out.println("Tasks before probe: " + beforeProbe.size());
    }
}