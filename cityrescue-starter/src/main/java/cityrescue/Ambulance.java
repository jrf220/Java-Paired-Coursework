package cityrescue;
import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitType;

/**
* This is a child of the Unit class used to represent an Ambulance.
* It can respond to MEDICAL incidents and takes 2 ticks to resolve.
*
* @author Jacob Foot
* @version 1.0
* @since 2026
*/
class Ambulance extends Unit {
    private final UnitType unitType = UnitType.AMBULANCE;
    private final IncidentType canRespondTo = IncidentType.MEDICAL;
    private static final int ticksToResolve = 2;

    Ambulance(int homeStationId){
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
    public boolean canHandle(Incident incident){return (incident.getIncidentType().equals(canRespondTo));}

    @Override
    public int getTicksToResolve(){return ticksToResolve;};
}


