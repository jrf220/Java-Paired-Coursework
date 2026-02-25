public abstract class Unit {
   private String unitType;
   private String unitStatus;
   private String canRespondTo;
   private int ticksToResolve;
   private int unitID;
   private int numberOfUnits;

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
   abstract void moveUnit();

}
