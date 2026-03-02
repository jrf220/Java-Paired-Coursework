import java.util.Arrays;
/**
* The Unit class is an abstract class that is the parent class for the
* Ambulance, FireEngine and PoliceCar classes.
*
* @author Jacob Foot
* @version 1.0
* @since 2026
*/
public abstract class Unit {
    private String unitType;
    private String unitStatus = "IDLE";
    private String canRespondTo;
    private int ticksToResolve;
    private int unitID;
    private int numberOfUnits;
    private int[] position;  //[x, y]
    private final String[] movementCandidates = {"NORTH", "EAST", "SOUTH", "WEST"};
    private Incident targetIncident;

    public Unit() {unitID = ++numberOfUnits;}
    public String getUnitType() {return unitType;}
    public String getCanRespondTo() {return canRespondTo;}
    public int getUnitID() {return unitID;}
    public int getTicksToResolve() {return ticksToResolve;}
    public int[] getPosition() {return position;}
    public int getManhattanDistance(int[] targetPos){
        if (!(this.unitStatus.equals("IDLE"))) {return -1;}
        return (Math.abs(targetPos[0] - position[0]) + Math.abs(targetPos[1] - position[1]));
    }
    
    public String getUnitStatus() {return unitStatus;}
    public Incident getTargetIncident(){return targetIncident;}
    
    public void setUnitStatus(String unitStatus) {this.unitStatus = unitStatus;}
    public void setTargetIncident(Incident targetIncident) {this.targetIncident = targetIncident;}
    
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
    * @return the cityMap
    */
    public CityMap moveUnit(CityMap cityMap){
        if (!(this.unitStatus.equals("EN_ROUTE"))) {return cityMap;}
        int[] targetPos = this.targetIncident.getPosition();

        boolean[] validDirections = cityMap.checkAround(this.position);

        if (targetPos[0] >= position[0]) {validDirections[3] = false;}
        if (targetPos[0] <= position[0]) {validDirections[1] = false;}
        if (targetPos[1] >= position[1]) {validDirections[0] = false;}
        if (targetPos[1] <= position[1]) {validDirections[2] = false;}

        int checkTrue = 0;
        for (int i = 0; i < validDirections.length; i++){
            if (validDirections[i]) {checkTrue += 1;}
        }  
        if (checkTrue == 0){return cityMap;} // nowhere to move and so CityMap is returned.

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
                return cityMap;
        }

        if (Arrays.equals(this.position, targetPos)) {this.unitStatus = "AT_SCENE";}
        return cityMap;
    }
}
