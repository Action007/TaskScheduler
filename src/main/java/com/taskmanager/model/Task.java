package main.java.com.taskmanager.model;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Task implements Comparable<Task> {
  private String id;
  private String title;
  private Priority priority;
  private LocalDateTime deadline;
  private Status status;
  private Set<String> tags;
  public static final Comparator<Task> BY_DEADLINE = Comparator.comparing(Task::getDeadline)
      .thenComparing(Task::getId);

  public Task(String id, String title, Priority priority, LocalDateTime deadline, Status status, Set<String> tags) {
    this.id = id;
    this.title = title;
    this.priority = priority;
    this.deadline = deadline;
    this.status = status;
    this.tags = new HashSet<>(tags);
  }

  public LocalDateTime getDeadline() {
    return deadline;
  }

  public String getId() {
    return id;
  }

  public Priority getPriority() {
    return priority;
  }

  public Status getStatus() {
    return status;
  }

  public Set<String> getTags() {
    return new HashSet<>(tags);
  }

  public String getTitle() {
    return title;
  }

  @Override
  public int compareTo(Task otherTask) {
    int priorityOrder = otherTask.priority.compareTo(this.priority);

    if (priorityOrder != 0) {
      return priorityOrder;
    }

    return this.deadline.compareTo(otherTask.deadline);
  }

  @Override
  public String toString() {
    return "Task{" +
        "id='" + id + '\'' +
        ", title='" + title + '\'' +
        ", priority=" + priority +
        ", deadline=" + deadline +
        ", status=" + status +
        ", tags=" + tags +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Task task = (Task) o;
    return Objects.equals(id, task.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
