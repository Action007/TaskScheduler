package com.taskmanager.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.taskmanager.model.Task;
import com.taskmanager.util.CollectionUtils;

public class TagService {
  private final Set<String> systemTags = new HashSet<>();
  private final Map<String, Set<String>> userPreferredTags = new HashMap<>();

  public Set<String> getSuggestedTagsForUser(String userId) {
    Set<String> userTags = userPreferredTags.getOrDefault(userId, Set.of());
    Set<String> commonTags = CollectionUtils.intersection(systemTags, userTags);
    return new TreeSet<>(commonTags);
  }

  public Set<String> getOverlappingTags(Collection<Task> tasks) {
    return tasks.stream()
        .map(Task::getTags)
        .reduce((s1, s2) -> CollectionUtils.intersection(s1, s2))
        .orElse(Set.of());
  }
}
