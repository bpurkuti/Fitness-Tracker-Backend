package dev.marker.exceptions;

public class RoutineDoesntExist extends RuntimeException{
    public RoutineDoesntExist(String message){
        super(message);
    }
}
