package cityrescue;
import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitType;

class FireEngine extends Unit {
    private final UnitType unitType = UnitType.FIRE_ENGINE;
    private final IncidentType canRespondTo = IncidentType.FIRE;
    private final int ticksToResolve = 4;

    FireEngine(){
        // this is a constructor method
        super();
    }
}
