package dam.angelvilaplana.u4t8database.model;

import dam.angelvilaplana.u4t8database.AppResources;

import java.io.Serializable;

public class Task implements Serializable {

    private int id;

    private String toDo;

    private String toAccomplish;

    private int priority;

    private int status;

    private String description;

    public Task(int id, String toDo, String toAccomplish, int priority, int status, String description) {
        this.id = id;
        this.toDo = toDo;
        this.toAccomplish = toAccomplish;
        this.priority = priority;
        this.status = status;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getToDo() {
        return toDo;
    }

    public String getToAccomplish() {
        return toAccomplish;
    }

    public String getPriority() {
        return AppResources.getPriority(priority);
    }

    public String getStatus() {
        return AppResources.getStatus(status);
    }

    public String getDescription() {
        return description;
    }

    public int getSelectionPriority() {
        return priority;
    }

    public int getSelectionStatus() {
        return status;
    }

}
