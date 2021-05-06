package com.slisnychyi.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

public class EventRequest {
    @NotBlank
    private String task;
    @PositiveOrZero
    private int duration;

    public EventRequest() {
    }

    public EventRequest(String task, int duration) {
        this.task = task;
        this.duration = duration;
    }

    public String getTask() {
        return task;
    }

    public int getDuration() {
        return duration;
    }
}
