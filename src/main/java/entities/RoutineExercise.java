package entities;

public class RoutineExercise {
    private String exerciseName;
    private int routineId;
    private int duration;
    private int reps;
    private int weight;

    public RoutineExercise() {
    }

    public RoutineExercise(String exerciseName, int routineId, int duration, int reps, int weight) {
        this.exerciseName = exerciseName;
        this.routineId = routineId;
        this.duration = duration;
        this.reps = reps;
        this.weight = weight;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public int getRoutineId() {
        return routineId;
    }

    public void setRoutineId(int routineId) {
        this.routineId = routineId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "RoutineExercise{" +
                "exerciseName='" + exerciseName + '\'' +
                ", routineId=" + routineId +
                ", duration=" + duration +
                ", reps=" + reps +
                ", weight=" + weight +
                '}';
    }
}
