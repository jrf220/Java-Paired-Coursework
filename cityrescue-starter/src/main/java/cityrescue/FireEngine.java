package cityrescue;
import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitType;

class FireEngine extends Unit {
    private final UnitType unitType = UnitType.FIRE_ENGINE;
    private final IncidentType canRespondTo = IncidentType.FIRE;
    private static final int ticksToResolve = 4;

    FireEngine(int homeStationId){
        // this is a constructor method
        super(homeStationId);
    }

    /**
    * Checks if this unit can handle a specific incident.
    *
    * @param incident the incident to check
    * @return boolean true/false whether this unit can handle that incident
    */
    @Override
    public boolean canHandle(Incident incident) {return (incident.getIncidentType().equals(canRespondTo));}

    @Override
    public int getTicksToResolve(){return ticksToResolve;};
}
