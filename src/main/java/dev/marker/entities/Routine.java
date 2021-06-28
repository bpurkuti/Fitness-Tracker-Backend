package dev.marker.entities;

public class Routine {
    
    private int routineId;
    private String username;
    private String routineName;
    private int dateScheduled;
    private int dateCompleted;

    public Routine() {
    }

    public Routine(int routineId, String username, String routineName, int dateScheduled, int dateCompleted) {
        this.routineId = routineId;
        this.username = username;
        this.routineName = routineName;

        this.dateScheduled = dateScheduled;
        this.dateCompleted = dateCompleted;
    }

    public int getRoutineId() {
        return routineId;
    }

    public void setRoutineId(int routineId) {

        this.routineId = routineId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoutineName() {
        return routineName;
    }

    public void setRoutineName(String routineName) {
        this.routineName = routineName;
    }

    public int getDateScheduled() {
        return dateScheduled;
    }

    public void setDateScheduled(int dateScheduled) {
        this.dateScheduled = dateScheduled;
    }

    public int getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(int dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    @Override
    public String toString() {
        return "Routine{" +
                "routineId='" + routineId + '\'' +
                ", username='" + username + '\'' +

                ", routineName='" + routineName + '\'' +

                ", dateScheduled=" + dateScheduled +
                ", dateCompleted=" + dateCompleted +
                '}';
    }
}
