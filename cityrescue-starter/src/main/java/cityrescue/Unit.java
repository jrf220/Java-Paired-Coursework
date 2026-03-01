public abstract class Unit {
    private String unitType;
    private String unitStatus = "IDLE";
    private String canRespondTo;
    private int ticksToResolve;
    private int unitID;
    private int numberOfUnits;
    private int[] position;  //[x, y]
    private String[] movementCandidates = {"NORTH", "EAST", "SOUTH", "WEST"};
    private Incident targetIncident;

    public Unit() {unitID = ++numberOfUnits;}
    public String getUnitType() {return unitType;}
    public String getCanRespondTo() {return canRespondTo;}
    public int getUnitID() {return unitID;}
    public int getTicksToResolve() {return ticksToResolve;}
    public int[] getPosition() {return position;}
    
    public String getUnitStatus() {return unitStatus;}
    public Incident getTargetIncident(){return targetIncident;}
    
    public void setUnitStatus(String unitStatus) {this.unitStatus = unitStatus;}
    public void setTargetIncident(Incident targetIncident) {this.targetIncident = targetIncident;}
    
    public boolean canHandle(Incident type) {return (type.getIncidentType().equals(canRespondTo));}
    public Object moveUnit(CityMap cityMap){
        if (!(this.unitStatus.equals("EN_ROUTE"))) {return cityMap;}
        int[] targetPos = this.targetIncident.getPosition();
        int distanceY = Math.abs(targetPos[1] - position[1]);
        int distanceX = Math.abs(targetPos[0] - position[0]);

        boolean[] validDirections = cityMap.checkAround(this.position);

        if (targetPos[0] >= position[0]) {validDirections[3] = false;}
        if (targetPos[0] <= position[0]) {validDirections[1] = false;}
        if (targetPos[1] >= position[1]) {validDirections[0] = false;}
        if (targetPos[1] =< position[1]) {validDirections[2] = false;}

        if (!(Arrays.asList(validDirections).contains(true))) {return cityMap;}  // nowhere to move and so CityMap is returned.

        for (int i = 0; i < movementCandidates.length; i++){
            if (validDirections[i]) {
                String closest = movementCandidates[i];
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
        }

        if (Arrays.equals(position, targetPos)) {this.unitStatus = "AT_SCENE";}
        return cityMap;
    }

}
