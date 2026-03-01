public abstract class Unit {
   private String unitType;
   private String unitStatus = "IDLE";
   private String canRespondTo;
   private int ticksToResolve;
   private int unitID;
   private int numberOfUnits;
   private int[] position;  //[x, y]
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
      if (!(this.unitStatus.equals("EN_ROUTE"))) {return CityMap;}
      int[] targetPos = this.targetIncident.getPosition();
      int distanceY = Math.abs(targetPos[1] - position[1]);
      int distanceX = Math.abs(targetPos[0] - position[0]);

      boolean[] blockedSpaces = CityMap.checkAround(this.position);
      if (!(Arrays.asList(blockedSpaces).contains(false))) {return CityMap;}  // nowhere to move and so CityMap is returned.
      validDir = new String[2]
      for (int i = 0; i < movementCandidates.length; i++){
         if (!(blockedSpaces[i])) {validDir[i] = movementCandidates[i];}
      }

      directions = new String[2]
      if (targetPos[0] > position[0]) {directions[0] = "EAST";}
      else if (targetPos[0] < position[0]) {directions[0] = "WEST";}
      else {directions[0] = "NEITHER";}
      if (targetPos[1] > position[1]) {directions[1] = "SOUTH";}
      else if (targetPos[1] < position[1]) {directions[1] = "NORTH";}
      else {directions[1] = "NEITHER";}

      for (int i = 0; i < movementCandidates.length; i++){
         if (Arrays.asList(directions).contains(movementCandidates[i])) {
            String closest = movementCandidates[i]
            break;
            }
      }

      // remove item from current city map position

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

      // add item to new position in city map

      if (Arrays.equals(position, targetPos)) {this.unitStatus = "AT_SCENE"}
      return CityMap
   }

}








