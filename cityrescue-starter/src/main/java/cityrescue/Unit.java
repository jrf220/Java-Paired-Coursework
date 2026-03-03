package cityrescue;
import cityrescue.enums.IncidentStatus;
import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitStatus;
import cityrescue.enums.UnitType;
import cityrescue.exceptions.InvalidLocationException;
/**
* The Unit class is an abstract class that is the parent class for the
* Ambulance, FireEngine and PoliceCar classes.
*
* @author Jacob Foot
* @version 1.0
* @since 2026
*/
public abstract class Unit {
    private UnitType unitType;
    private UnitStatus unitStatus = UnitStatus.IDLE;
    private IncidentType canRespondTo;
    private int ticksToResolve;
    private int unitID;
    private int numberOfUnits;
    private int homeStationId;
    private int[] position;  //[x, y]
    private final String[] movementCandidates = {"NORTH", "EAST", "SOUTH", "WEST"};
    private Incident targetIncident;

    public Unit() {unitID = ++numberOfUnits;}
    public UnitType getUnitType() {return unitType;}
    public IncidentType getCanRespondTo() {return canRespondTo;}
    public int getUnitID() {return unitID;}
    public int getTicksToResolve() {return ticksToResolve;}
    public int[] getPosition() {return position;}
    public int getHomeStationId() {return homeStationId;}
    public UnitStatus getUnitStatus() {return unitStatus;}
    public Incident getTargetIncident(){return targetIncident;}
    public int getManhattanDistance(int[] targetPos){
        if (!(this.unitStatus.equals("IDLE"))) {return -1;}
        return (Math.abs(targetPos[0] - position[0]) + Math.abs(targetPos[1] - position[1]));
    }
    
    public void setUnitStatus(UnitStatus unitStatus) {this.unitStatus = unitStatus;}
    public void setTargetIncident(Incident targetIncident) {this.targetIncident = targetIncident;}
    public void setHomeStationId(int id) {this.homeStationId = id;}

    /**
    * Checks if this unit can handle a specific incident.
    *
    * @param incident the incident to check
    * @return boolean true/false whether this unit can handle that incident
    */
    public boolean canHandle(Incident incident) {return (incident.getIncidentType().equals(canRespondTo));}
    
    /**
    * Moves the unit closer to the incident it is responding to
    *
    * @param cityMap the cityMap to be updated
    */
    public void moveUnit(CityMap cityMap) throws InvalidLocationException{
        if (!(this.unitStatus.equals(UnitStatus.EN_ROUTE))) {return;}
        int[] targetPos = this.targetIncident.getPosition();

        boolean[] validDirections;

        try{
            validDirections = cityMap.checkAround(this.position);
        } catch (InvalidLocationException e) {throw e;}

        if (targetPos[0] >= position[0]) {validDirections[3] = false;}
        if (targetPos[0] <= position[0]) {validDirections[1] = false;}
        if (targetPos[1] >= position[1]) {validDirections[0] = false;}
        if (targetPos[1] <= position[1]) {validDirections[2] = false;}

        int checkTrue = 0;
        for (int i = 0; i < validDirections.length; i++){
            if (validDirections[i]) {checkTrue += 1;}
        }  
        if (checkTrue == 0){return;} // nowhere to move and so function is returned.

        String closest = "";
        for (int i = 0; i < movementCandidates.length; i++){
            if (validDirections[i]) {
                closest = movementCandidates[i];
                break;
                }
        }

        switch (closest) {
            case "NORTH":
                this.position[1] -= 1;
                break;
            case "EAST":
                this.position[0] += 1;
                break;
            case "SOUTH":
                this.position[1] += 1;
                break;
            case "WEST":
                this.position[0] -= 1;
                break;
            default:
                return;
        }

        if ((targetPos[0] == this.position[0]) && (targetPos[1] == this.position[1])){
            this.unitStatus = UnitStatus.AT_SCENE;
            this.targetIncident.setIncidentStatus(IncidentStatus.IN_PROGRESS);
            }
    }
}
