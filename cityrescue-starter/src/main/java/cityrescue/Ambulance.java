class Ambulance extends Unit {
    private final String unitType = "AMBULANCE";
    private String unitStatus = "IDLE";
    private final String canRespondTo = "MEDICAL";
    private final int ticksToResolve = 2;
    private int unitID;

    Ambulance(){
        // this is a constructor method
        super();
    }
    public String getUnitType(){
        return unitType;
    }
    public String getUnitStatus(){
        return unitStatus;
    }
    public int getUnitID(){
        return unitID;
    }
    public String getCanRespondTo(){
        return canRespondTo;
    }
    public void setUnitStatus(String unitStatus){
        this.unitStatus = unitStatus;
    }
    public boolean canHandle(Incident type){
        return (type.getIncidentType().equals(canRespondTo));
    }
    public int getTicksToResolve(){
        return ticksToResolve;
    }
    public void moveUnit(){
        
    }
}