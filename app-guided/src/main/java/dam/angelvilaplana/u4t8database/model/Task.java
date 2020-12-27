package dam.angelvilaplana.u4t8database.model;

public class Task {

    private int id;

    private String toDo;

    private String toAccomplish;

    private String description;

    public Task(int id, String toDo, String toAccomplish, String description) {
        this.id = id;
        this.toDo = toDo;
        this.description = toAccomplish;
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

    public String getDescription() {
        return description;
    }

}
