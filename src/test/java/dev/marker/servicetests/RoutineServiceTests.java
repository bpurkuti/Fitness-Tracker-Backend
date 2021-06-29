package dev.marker.servicetests;
import dev.marker.services.RoutineService;

import dev.marker.daos.RoutineDao;
import dev.marker.services.RoutineServiceImpl;
import org.mockito.Mockito;

public class RoutineServiceTests {

    RoutineDao routineDao = Mockito.mock((RoutineDao.class));
    RoutineService routineService = new RoutineServiceImpl(routineDao);

}
