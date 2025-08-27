package main.java.com.taskmanager.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import main.java.com.taskmanager.model.Priority;
import main.java.com.taskmanager.model.Task;

public class TaskService {
  private final Map<String, Task> taskStore = new HashMap<>();
  private final NavigableSet<Task> urgentTasks = new TreeSet<>(Task.BY_DEADLINE);
  private final Map<LocalDateTime, List<Task>> deadlineIndex = new TreeMap<>();
  private final Set<String> allKnownTags = new HashSet<>();

  public void addTask(Task task) {
    taskStore.put(task.getId(), task);
    allKnownTags.addAll(task.getTags());

    if (task.getPriority() == Priority.HIGH || task.getPriority() == Priority.URGENT) {
      urgentTasks.add(task);
    }
  }

  public NavigableSet<Task> getUrgentTasksForTesting() {
    return Collections.unmodifiable(urgentTasks);
  }

  public Set<String> getAllKnownTagsForTesting() {
    return Collections.unmodifiable(allKnownTags);
  }
}
