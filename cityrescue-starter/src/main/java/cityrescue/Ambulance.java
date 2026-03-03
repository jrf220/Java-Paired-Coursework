package cityrescue;
import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitType;

class Ambulance extends Unit {
    private final UnitType unitType = UnitType.AMBULANCE;
    private final IncidentType canRespondTo = IncidentType.MEDICAL;
    private final int ticksToResolve = 2;

    Ambulance(int homeStationId){
        // this is a constructor method
        super(homeStationId);
    }
}
