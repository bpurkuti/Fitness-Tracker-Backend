package entities;

public class Routine {
    private String routineId;
    private String username;
    private String workoutName;
    private int dateScheduled;
    private int dateCompleted;

    public Routine() {
    }

    public Routine(String routineId, String username, String workoutName, int dateScheduled, int dateCompleted) {
        this.routineId = routineId;
        this.username = username;
        this.workoutName = workoutName;
        this.dateScheduled = dateScheduled;
        this.dateCompleted = dateCompleted;
    }

    public String getRoutineId() {
        return routineId;
    }

    public void setRoutineId(String routineId) {
        this.routineId = routineId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
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
                ", workoutName='" + workoutName + '\'' +
                ", dateScheduled=" + dateScheduled +
                ", dateCompleted=" + dateCompleted +
                '}';
    }
}
