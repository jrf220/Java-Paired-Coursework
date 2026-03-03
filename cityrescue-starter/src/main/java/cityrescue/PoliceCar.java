package cityrescue;
import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitType;

class PoliceCar extends Unit {
    private final UnitType unitType = UnitType.POLICE_CAR;
    private final IncidentType canRespondTo = IncidentType.CRIME;
    private static final int ticksToResolve = 3;

    PoliceCar(int homeStationId){
        // this is a constructor method
        super(homeStationId);
    }
}
