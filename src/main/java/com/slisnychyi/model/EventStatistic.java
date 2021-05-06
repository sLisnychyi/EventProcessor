package com.slisnychyi.model;

import java.util.Objects;
import java.util.StringJoiner;

public class EventStatistic {
    private final String username;
    private final int totalTasks;
    private final int totalDuration;

    public EventStatistic(String username, int totalTasks, int totalDuration) {
        this.username = username;
        this.totalTasks = totalTasks;
        this.totalDuration = totalDuration;
    }

    public String getUsername() {
        return username;
    }

    public int getTotalTasks() {
        return totalTasks;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventStatistic that = (EventStatistic) o;
        return totalTasks == that.totalTasks &&
                totalDuration == that.totalDuration &&
                Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, totalTasks, totalDuration);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", EventStatistic.class.getSimpleName() + "[", "]")
                .add("username='" + username + "'")
                .add("totalTasks=" + totalTasks)
                .add("totalDuration=" + totalDuration)
                .toString();
    }
}
