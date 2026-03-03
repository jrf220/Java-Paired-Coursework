package cityrescue;
import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitType;

class PoliceCar extends Unit {
    private final UnitType unitType = UnitType.POLICE_CAR;
    private final IncidentType canRespondTo = IncidentType.CRIME;
    private final int ticksToResolve = 3;

    PoliceCar(){
        // this is a constructor method
        super();
    }
}
