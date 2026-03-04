package cityrescue;
import cityrescue.enums.IncidentStatus;
import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitStatus;

/**
* The Incident class represents the three types of incidents that can exist
* in this city map, FIRE, MEDICAL and CRIME, and how to handle these.
*
* @author Jacob Foot
* @version 1.0
* @since 2026
*/
public class Incident{
    private IncidentType incidentType;
    private int incidentID;
    private static int numberOfIncidents = 0;
    private int severity;
    private IncidentStatus incidentStatus = IncidentStatus.REPORTED;
    private int[] position;

    public Incident(IncidentType incidentType, int severity, int x, int y){
        this.incidentType = incidentType;
        this.severity = severity;
        this.incidentStatus = IncidentStatus.REPORTED;
        this.incidentID = ++numberOfIncidents;
        this.position = new int[] {x, y};
    }

    public IncidentType getIncidentType() {return this.incidentType;}
    public int getIncidentID() {return this.incidentID;}
    public static int getNumberOfIncidents() {return numberOfIncidents;}
    public IncidentStatus getIncidentStatus() {return incidentStatus;}
    public int[] getPosition() {return position;}
    public int getSeverity() {return severity;}

    public void setSeverity(int severity) {this.severity = severity;}
    public void setIncidentStatus(IncidentStatus incidentStatus) {this.incidentStatus = incidentStatus;}
    public void setPosition(int[] position) {this.position = position;}
    
    /**
    * Cancels a reported or dispatched incident.
    *
    * @throws IllegalStateException if the incident is not in the correct state to be cancelled.
    */
    public void cancelIncident() throws IllegalStateException{
        if (incidentStatus.equals(IncidentStatus.REPORTED) || incidentStatus.equals(IncidentStatus.DISPATCHED)){
            this.setIncidentStatus(IncidentStatus.CANCELLED);
        } else {throw new IllegalStateException("Illegal State");}
    }

    /**
    * Checks which unit is closest to this incident. In order to make the function determinsistic,
    * the rules are as follows: Among all eligible units (correct type, not OUT_OF_SERVICE, 
    * and not already assigned), they are assigned by Shortest manhattan distance, then lowest 
    * unitId if tied, then lowestHomeStationId if tied again.
    *
    * @param units a list of all valid units
    * @return the id of the closest instance of the Unit class
    */
    public int closestUnit(Unit[] units){
        int lowestUnitId = -1;
        int lowestMan = 0;
        for (int i = 0; i < units.length; i++){
            int currentMan = units[i].getManhattanDistance(this.position);
            if ((units[i].canHandle(this) && units[i].getUnitStatus().equals(UnitStatus.IDLE))){
                if (currentMan < lowestMan){
                    lowestMan = currentMan;
                    lowestUnitId = units[i].getUnitID();
                } else if (currentMan == lowestMan){
                    if (units[i].getUnitID() < lowestUnitId){
                        lowestMan = currentMan;
                        lowestUnitId = units[i].getUnitID();
                        /* due to the way we have coded, it is impossible for two units to have the same
                        *  unit ID and so we do not need to handle the cases where they do. */
                    }
                }
            }
        }
        return lowestUnitId;
    }
}
