package cityrescue;
import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitType;

/**
* This is a child of the Unit class used to represent a Police Car.
* It can respond to CRIME incidents and takes 3 ticks to resolve.
*
* @author Jacob Foot
* @version 1.0
* @since 2026
*/
class PoliceCar extends Unit {
    private final UnitType unitType = UnitType.POLICE_CAR;
    private final IncidentType canRespondTo = IncidentType.CRIME;
    private static final int ticksToResolve = 3;

    PoliceCar(int homeStationId){
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


