public abstract class Unit {
   private String unitType;
   private String unitStatus = "IDLE";
   private String canRespondTo;
   private int ticksToResolve;
   private int unitID;
   private int numberOfUnits;
   private int[] currentPos;
   private String[] movementCandidates = {"NORTH", "EAST", "SOUTH", "WEST"}
   private Object targetIncident;

   public Unit(){
      unitID = ++numberOfUnits;
   }

   abstract String getUnitType();
   abstract String getUnitStatus();
   abstract int getUnitID();
   abstract String setUnitStatus();
   abstract String getCanRespondTo();
   abstract boolean canHandle(Incident type);
   abstract int getTicksToResolve();
   public void setTargetIncident;
   public Object getTargetIncident;
   public Object moveUnit(Object CityMap){
      // check manhattan distance to target
         // pythogras with target incident
      return CityMap
   }

}



