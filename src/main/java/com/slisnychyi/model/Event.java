package com.slisnychyi.model;

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
}
