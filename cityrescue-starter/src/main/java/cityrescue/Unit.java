public abstract class Unit {
   private String unitType;
   private String unitStatus = "IDLE";
   private String canRespondTo;
   private int ticksToResolve;
   private int unitID;
   private int numberOfUnits;
   private int[] position;
   private String[] movementCandidates = {"NORTH", "EAST", "SOUTH", "WEST"}
   private Object targetIncident;

   public Unit() {unitID = ++numberOfUnits;}
   public String getUnitType() {return unitType;}
   public String getCanRespondTo() {return canRespondTo;}
   public int getUnitID() {return unitID;}
   public int getTicksToResolve() {return ticksToResolve;}
   public int[] getPosition() {return position;}
   
   public String getUnitStatus() {return unitStatus;}
   public Object getTargetIncident{return targetIncident}
   
   public void setUnitStatus(String unitStatus) {this.unitStatus = unitStatus;}
   public void setTargetIncident(Object targetIncident) {this.targetIncident = targetIncident;}
   
   public boolean canHandle(Incident type) {return (type.getIncidentType().equals(canRespondTo));}
   public Object moveUnit(Object CityMap){
      int[] targetPos = this.targetIncident.getPosition()
      // check manhattan distance to target
         // pythogras with target incident
            // check which neighbouring spaces are blocked
      switch (closest) {
         case "NORTH":
            // travel north
            break
         case "EAST":
            // travel east
            break
         case "SOUTH":
            // travel south
            break
         case "WEST":
            // travel west
            break
      }
      return CityMap
   }

}







