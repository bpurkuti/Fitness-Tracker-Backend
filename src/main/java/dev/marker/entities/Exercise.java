package dev.marker.entities;

public class Exercise {
    private String exerciseName;
    private String description;
    private String type;
    private String videoLink;

    public Exercise() {
    }

    public Exercise(String exerciseName, String description, String type, String videoLink) {
        this.exerciseName = exerciseName;
        this.description = description;
        this.type = type;
        this.videoLink = videoLink;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "exerciseName='" + exerciseName + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", videoLink='" + videoLink + '\'' +
                '}';
    }
}
