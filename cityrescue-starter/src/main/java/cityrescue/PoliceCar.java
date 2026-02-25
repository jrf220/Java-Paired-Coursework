class PoliceCar extends Unit {
    private final String unitType = "POLICE_CAR";
    private final String canRespondTo = "CRIME";
    private final int ticksToResolve = 3;

    PoliceCar(){
        // this is a constructor method
        super();
    }

    public String getUnitType(){
        return unitType;
    }
    public String getUnitStatus(){
        return unitStatus;
    }
    public String getCanRespondTo(){
        return canRespondTo;
    }
    public int getUnitID(){
        return unitID;
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

