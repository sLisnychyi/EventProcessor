package com.slisnychyi.model;

import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventRequest that = (EventRequest) o;
        return duration == that.duration &&
                Objects.equals(task, that.task);
    }

    @Override
    public int hashCode() {
        return Objects.hash(task, duration);
    }
}
