package cityrescue;
import cityrescue.enums.IncidentStatus;
import cityrescue.enums.IncidentType;
/**
* The Incident class represents the three types of incidents that can exist
* in this city map, Fire, Medical and Crime, and how to handle these.
*
* @author Jacob Foot
* @version 1.0
* @since 2026
*/
public class Incident{
    private IncidentType incidentType;
    private int incidentID;
    private static int numberOfIncidents = 0;
    private int severity = 1;
    private IncidentStatus incidentStatus;
    private int[] position;

    public Incident(IncidentType incidentType){
        this.incidentType = incidentType;
        this.incidentStatus = IncidentStatus.REPORTED;
        incidentID = ++numberOfIncidents;
    }

    public IncidentType getIncidentType() {return this.incidentType;}
    public int getIncidentID() {return this.incidentID;}
    public static int getNumberOfIncidents() {return numberOfIncidents;}
    public IncidentStatus getIncidentStatus() {return incidentStatus;}
    public int[] getPosition() {return position;}
    public void setIncidentStatus(IncidentStatus incidentStatus) {this.incidentStatus = incidentStatus;}
    public void setPosition(int[] position) {this.position = position;}
    
    /**
    * Cancels a reported or dispatched unit
    *
    */
    public void cancelIncident(){
        if (incidentStatus.equals(IncidentStatus.REPORTED) || incidentStatus.equals(IncidentStatus.DISPATCHED)){
            this.setIncidentStatus(IncidentStatus.CANCELLED);
        }
    }

    /**
    * Checks which unit is closest to this incident
    *
    * @param units a list of all valid units
    * @return the closest instance of the Unit class
    */
    public Unit closestUnit(Unit[] units){
        Unit lowestUnit = units[0];
        int lowestMan = 0;
        for (int i = 0; i < units.length; i++){
            int currentMan = units[i].getManhattanDistance(this.position);
            if ((currentMan < lowestMan) && (units[i].canHandle(this))){
                lowestMan = currentMan;
                lowestUnit = units[i];
                }
        }
        return lowestUnit;
    }
}
