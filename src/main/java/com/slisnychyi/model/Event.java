package com.slisnychyi.model;

import java.util.Objects;

public class Event {
    private String uuid;
    private String username;
    private String task;
    private int duration;

    public Event() {
    }

    public Event(String id, String username, String task, int duration) {
        this.uuid = id;
        this.username = username;
        this.task = task;
        this.duration = duration;
    }

    public Event(String uuid, String username, EventRequest request) {
        this.uuid = uuid;
        this.username =username;
        this.task = request.getTask();
        this.duration = request.getDuration();
    }

    public String getUuid() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }

    public String getTask() {
        return task;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return duration == event.duration &&
                Objects.equals(uuid, event.uuid) &&
                Objects.equals(username, event.username) &&
                Objects.equals(task, event.task);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, username, task, duration);
    }
}
