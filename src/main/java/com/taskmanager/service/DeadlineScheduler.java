package com.taskmanager.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

import com.taskmanager.model.Task;

public class DeadlineScheduler {

  private final TreeMap<LocalDateTime, List<Task>> schedule = new TreeMap<>();

  public void scheduleTask(Task task) {
    schedule.computeIfAbsent(task.getDeadline(), k -> new ArrayList<>()).add(task);
  }

  public List<Task> getImminentTasks(Duration within) {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime deadline = now.plus(within);
    return schedule.subMap(now, true, deadline, false).values().stream()
        .flatMap(List::stream)
        .toList();
  }

  public List<Task> getTasksBefore(LocalDateTime time) {
    return schedule.headMap(time, false).values().stream()
        .flatMap(List::stream)
        .toList();
  }

  public List<Task> getTasksAfter(LocalDateTime time) {
    return schedule.tailMap(time, true).values().stream()
        .flatMap(List::stream)
        .toList();
  }

  public Optional<LocalDateTime> getNextDeadline() {
    return Optional.ofNullable(schedule.ceilingKey(LocalDateTime.now()));
  }
}