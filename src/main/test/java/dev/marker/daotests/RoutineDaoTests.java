package dev.marker.daotests;

import daos.RoutineDao;

import entities.Routine;

public class RoutineDaoTests {

    static RoutineDao routineDao = null;

    //Not sure about routineId being a string||Change to Int
    static Routine testRoutine = new Routine("0", "Mack", "Legs Day", 0, 0);

}
