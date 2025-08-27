package com.taskmanager.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

public final class CollectionUtils {
  private CollectionUtils() {
    throw new AssertionError("No instances");
  }

  public static <T> Set<T> union(Set<T> set1, Set<T> set2) {
    Objects.requireNonNull(set1, "set1 is required");
    Objects.requireNonNull(set2, "set2 is required");

    Set<T> result = new HashSet<>(set1);
    result.addAll(set2);

    return result;
  }

  public static <T> boolean addAllTo(Collection<? super T> target, Collection<? extends T> source) {
    Objects.requireNonNull(source, "source is required");
    Objects.requireNonNull(target, "target is required");

    return target.addAll(source);
  }

  public static <T> void filterAndCopyTo(
      Collection<T> source, Predicate<T> predicate, Collection<? super T> target) {
    Objects.requireNonNull(source, "source is required");
    Objects.requireNonNull(target, "target is required");
    Objects.requireNonNull(predicate, "predicate is required");

    source.stream().filter(predicate).forEach(target::add);
  }

  public static <T extends Comparable<T>> List<T> sortedCopy(Collection<T> unsortedCollection) {
    Objects.requireNonNull(unsortedCollection, "unsortedCollection is required");

    List<T> copy = new ArrayList<>(unsortedCollection);
    Collections.sort(copy);

    return copy;
  }

  // Note: Serializable is not used — constraint is for practicing
  public static <T extends Comparable<T> & Serializable>
      List<T> sortedCopyWithSerializableConstraint(List<T> unsortedList) {
    Objects.requireNonNull(unsortedList, "unsortedList is required");

    List<T> copy = new ArrayList<>(unsortedList);
    Collections.sort(copy);

    return copy;
  }
}
