package duke.logic;

import duke.ModelStub;
import duke.commons.enumerations.Constraint;
import duke.commons.exceptions.FileLoadFailException;
import duke.model.locations.Venue;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PathFinderTest {
    @Test
    void execute() throws FileLoadFailException {
        ModelStub modelStub = new ModelStub();
        PathFinder pathFinder = new PathFinder(modelStub.getMap());
        Venue buonaVistaMrt = new Venue("Buona Vista",1.3073, 103.8077,0,0);
        Venue yewTeeMrt = new Venue("yewtee", 1.3973, 103.7475,0,0);
        Venue bloxhomeDrBusStop = new Venue("Opp Bloxhome Dr", 1.36412138937997,
                103.86103467229529,0,0);
        Venue westCoastCcBusStop = new Venue("West Coast CC", 1.30232494247638,
                103.7647147428673,0,0);

        assertNotNull(pathFinder.execute(buonaVistaMrt, yewTeeMrt, Constraint.MRT));
        assertNotNull(pathFinder.execute(westCoastCcBusStop, bloxhomeDrBusStop, Constraint.MIXED));
        assertNotNull(pathFinder.execute(buonaVistaMrt, bloxhomeDrBusStop, Constraint.MIXED));
        assertNotNull(pathFinder.execute(bloxhomeDrBusStop, yewTeeMrt, Constraint.MIXED));
        assertNotNull(pathFinder.execute(buonaVistaMrt, yewTeeMrt, Constraint.BUS));
        assertNotNull(pathFinder.execute(buonaVistaMrt, yewTeeMrt, Constraint.MIXED));


    }
}