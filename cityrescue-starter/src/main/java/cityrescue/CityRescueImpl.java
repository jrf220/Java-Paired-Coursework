package cityrescue;

import cityrescue.enums.IncidentStatus;
import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitStatus;
import cityrescue.enums.UnitType;
import cityrescue.exceptions.CapacityExceededException;
import cityrescue.exceptions.IDNotRecognisedException;
import cityrescue.exceptions.InvalidCapacityException;
import cityrescue.exceptions.InvalidGridException;
import cityrescue.exceptions.InvalidLocationException;
import cityrescue.exceptions.InvalidNameException;
import cityrescue.exceptions.InvalidSeverityException;
import cityrescue.exceptions.InvalidUnitException;


/**
* This is the main implementation for this project, containing all the functions for the tests to run.
*
* @author Jacob Foot & Appsharan Chandrarajan
* @version 1.0
* @since 2026
*/
public class CityRescueImpl implements CityRescue {

    private CityMap cityMap;
    private Unit[] units = new Unit[50];
    private Station[] stations = new Station[20];
    private Incident[] incidents = new Incident[200];
    private final int MAX_STATIONS = 20;
    private final int MAX_UNITS = 50;
    private final int MAX_INCIDENTS = 200;
    private int currentTick = 0;
    
    /**
    * Start a fresh simulation. 
    *
    * @param width the width of the city grid
    * @param height the height of the city grid
    */
    @Override
    public void initialise(int width, int height) throws InvalidGridException {
        if ((width <= 0) || (height <=0)) {throw new InvalidGridException("Not valid grid size.");}
        this.cityMap = new CityMap(new int[] {width, height});
    }

    /**
    * Ask the city how big it is. 
    *
    * @return the grid size in the format {x, y}
    */
    @Override
    public int[] getGridSize() {
        return cityMap.getGridSize();
    }

    /**
    * Place a roadblock.
    *
    * @param x the x coordinate of the obstacle
    * @param y the y coordiate of the obstacle
    */
    @Override
    public void addObstacle(int x, int y) throws InvalidLocationException{
        try{
            cityMap.addBlockedTile(new int[] {x, y});
        } catch (InvalidLocationException e) {
            throw e;
        }
    }

    /**
    * Remove a roadblock.
    *
    * @param x the x coordinate of the obstacle
    * @param y the y coordiate of the obstacle
    */
    @Override
    public void removeObstacle(int x, int y) throws InvalidLocationException {
        try{
            cityMap.removeBlockedTile(new int[] {x, y});
        } catch (InvalidLocationException e) {
            throw e;
        }
    }

    /**
    * Build a station.
    *
    * @param name the name of the station (cannot be empty)
    * @param x the x coordinate of the station
    * @param y the y coordiate of the station
    */
    @Override
    public int addStation(String name, int x, int y) throws CapacityExceededException, InvalidNameException, InvalidLocationException {
        int[] location = new int[] {x, y};
        if (cityMap.isBlocked(location) || cityMap.checkInGrid(location)){
            throw new InvalidLocationException("Location Invalid");}
        if (name.equals("")) {throw new InvalidNameException("Name Invalid.");}
        int added = 0;
        Station newStation = new Station(3, name, location);
        for (int i = 0; i < stations.length; i++) {
            if (stations[i] == null){
                stations[i] = newStation;
                added += 1;
                break;
                }
        }
        if (added == 0) {throw new CapacityExceededException("Capacity Exceeded");}
        return newStation.getStationID();
    }

    /**
    * Remove a station (must be empty).
    *
    * @param stationId the Id of the station to be removed
    */
    @Override
    public void removeStation(int stationId) throws IDNotRecognisedException, IllegalStateException {
        int removed = 0;
        for (int i = 0; i < stations.length; i++){
            if (stations[i].getStationID() == stationId){
                if (stations[i].isEmpty()){
                    stations[i] = null;
                    removed += 1;
                } else {throw new IllegalStateException("State Illegal");}
            }
        }
        if (removed == 0) {throw new IDNotRecognisedException("ID not recognised");}
    }

    /**
    * Change station parking capacity.
    *
    * @param stationId the Id of the station
    * @param maxUnits the capacity to change to
    */
    @Override
    public void setStationCapacity(int stationId, int maxUnits) throws IDNotRecognisedException, InvalidCapacityException {
        int updated = 0;
        for (int i = 0; i < stations.length; i++){
            if (stations[i].getStationID() == stationId){
                if (maxUnits > stations[i].getMaxCapacity()){
                    stations[i] = null;
                    updated += 1;
                } else {throw new InvalidCapacityException("Invalid Capacity");}
            }
        }
        if (updated == 0) {throw new IDNotRecognisedException("ID not recognised");}
    }

    /**
    * List station Ids
    *
    * @return a list of the station ids
    */
    @Override
    public int[] getStationIds() {
        int[] stationIds = new int[stations.length];
        for (int i = 0; i < stations.length; i++){
            if (stations[i] != null){
                stationIds[i] = stations[i].getStationID();
            }
        }
        return stationIds;
    }

    /**
    * Add a vehicle to a station. 
    *
    * @param stationId the Id of the station for the unit to be added to
    * @param type the type of unit to be added to the station
    * @return the Id of the new unit
    */
    @Override
    public int addUnit(int stationId, UnitType type) throws IDNotRecognisedException, InvalidUnitException, IllegalStateException, CapacityExceededException {
        Unit newUnit;
        switch (type) {
            case AMBULANCE:
                newUnit = new Ambulance(stationId);
                break;
            case FIRE_ENGINE:
                newUnit = new FireEngine(stationId);
                break;
            case POLICE_CAR:
                newUnit = new PoliceCar(stationId);
                break;
            default:
                throw new InvalidUnitException("Unit type Invalid");
        }

        int exist = 0;
        for (int i = 0; i < stations.length; i++){
            if (stations[i].getStationID() == stationId){
                try {
                    stations[i].addUnit(newUnit);
                } catch (CapacityExceededException e) {
                    throw e;
                } finally {
                    exist += 1;
                    int[] pos = stations[i].getPosition();
                    newUnit.setPosition(pos);
                    }
            }
        }
        if (exist == 0){throw new IDNotRecognisedException("ID not recognised");}

        int added = 0;
        for (int i = 0; i < units.length; i++) {
            if (units[i] == null){
                units[i] = newUnit;
                added += 1;
                break;
                }
        }
        if (added == 0) {throw new CapacityExceededException("Capacity Exceeded");}
        return newUnit.getUnitID();
    }

    /**
    * Retire a unit (only when free).  
    *
    * @param unitId the Id of the unit to be decomissioned
    */
    @Override
    public void decommissionUnit(int unitId) throws IDNotRecognisedException, IllegalStateException {
        int exist = 0;
        for (int i = 0; i < units.length; i++) {
            if (units[i].getUnitID() == unitId){
                if (units[i].getUnitStatus().equals(UnitStatus.IDLE)) {throw new IllegalStateException("State Illegal");}
                exist += 1;
                setUnitOutOfService(unitId, true);
                break;
                }
        }
        if (exist == 0) {throw new IDNotRecognisedException("ID not recognised");}
    }

    /**
    * Move a unit to a new home station. 
    *
    * @param unitId the Id of the unit to be transferred
    * @param newStationId the Id of the station for that unit to be transferred to
    */
    @Override
    public void transferUnit(int unitId, int newStationId) throws IDNotRecognisedException, IllegalStateException {
        int unitIndex = -1;
        int stationIndex = -1;
        int exist = 0;
        for (int i = 0; i < units.length; i++) {
            if (units[i].getUnitID() == unitId){
                unitIndex = i;
                exist += 1;
                }
        }
        for (int i = 0; i < stations.length; i++) {
            if (units[i].getUnitID() == newStationId){
                stationIndex = i;
                exist += 1;
                }
        }
        if (exist < 2) {throw new IDNotRecognisedException("ID not recognised");}
        if ((units[unitIndex].getUnitStatus() != UnitStatus.IDLE) && (stations[stationIndex].carParkFull())){
            throw new IllegalStateException("State Illegal");
            }
        units[unitIndex].setHomeStationId(newStationId);
        int[] newPos = stations[stationIndex].getPosition();
        units[unitIndex].setPosition(newPos);
        Unit unitToTransfer = units[unitIndex];
        stations[stationIndex].addUnit(unitToTransfer);
    }

    /**
    * Toggle a unit to/from OUT_OF_SERVICE  
    *
    * @param unitId the Id of the unit to be decomissioned
    * @param outOfService whether the unit is already OOS or not
    */
    @Override
    public void setUnitOutOfService(int unitId, boolean outOfService) throws IDNotRecognisedException, IllegalStateException {
        int exist = 0;
        for (int i = 0; i < units.length; i++) {
            if (units[i].getUnitID() == unitId){
                if (outOfService) {units[i].setUnitStatus(UnitStatus.IDLE);}
                else {
                    if (units[i].getUnitStatus().equals(UnitStatus.IDLE)){
                        units[i].setUnitStatus(UnitStatus.OUT_OF_SERVICE);
                    } else {throw new IllegalStateException("State Ilegeal");}
                }
                exist += 1;
            }
        }
        if (exist == 0) {throw new IDNotRecognisedException("ID not recognised");}
    }

    /**
    * List unit Ids
    *
    * @return a list of the unit ids
    */
    @Override
    public int[] getUnitIds() {
        int[] unitIds = new int[units.length];
        for (int i = 0; i < units.length; i++){
            if (units[i] != null){
                unitIds[i] = units[i].getUnitID();
            }
        }
        return unitIds;
    }

    /**
    * Describe one unit.
    *
    * @param unitId the Id of the unit you want to describe
    * @return a deterministic string describing that unit
    */
    @Override
    public String viewUnit(int unitId) throws IDNotRecognisedException {
        int exist = 0;
        String unitString = "";
        for (int i = 0; i < units.length; i++) {
            if (units[i].getUnitID() == unitId){
                int[] position = units[i].getPosition();
                String extra = "";
                if (units[i].getTargetIncident() != null) {extra = " INCIDENT="+ units[i].getTargetIncident().getIncidentID() +" WORK=2 ";}
                unitString = "\nU"+unitId+" TYPE="+ units[i].getUnitType() +" HOME="+ units[i].getHomeStationId() +" LOC=("+ position[0] +","+ position[1] +") STATUS="+ units[i].getUnitStatus() + extra;
                exist += 1;
            }
        }
        if (exist == 0) {throw new IDNotRecognisedException("ID not recognised");}
        return unitString;
    }

    /**
    * Log a new incident.
    *
    * @param type the type of incident to log
    * @param severity a value 1-5 representing the severity of the incident
    * @param x the x coordinate of the incident
    * @param y the y coordinate of the incident
    * @return the Id of the incident just logged
    */
    @Override
    public int reportIncident(IncidentType type, int severity, int x, int y) throws InvalidSeverityException, InvalidLocationException {
        int[] coords = {x, y};
        if (type == null) {throw new InvalidLocationException("type == null");}
        if ((severity < 1) || (severity > 5)) {throw new InvalidSeverityException("Severity Invalid");}
        if (cityMap.checkInGrid(coords) || (cityMap.isBlocked(coords))) {throw new InvalidLocationException("Location Invalid");}
        Incident newIncident = new Incident(type, severity, x, y);
        int added = 0;
        for (int i = 0; i < incidents.length; i++) {
            if (incidents[i] == null){
                incidents[i] = newIncident;
                added += 1;
                break;
                }
        }
        if (added == 0) {throw new CapacityExceededException("Capacity Exceeded");}
        return newIncident.getIncidentID();
    }

    /**
    * Cancel an incident.
    *
    * @param incidentId the Id of the incident to cancel
    */
    @Override
    public void cancelIncident(int incidentId) throws IDNotRecognisedException, IllegalStateException {
        int exist = 0;
        for (int i = 0; i < incidents.length; i++) {
            if (incidents[i].getIncidentID() == incidentId){
                exist += 1;
                try{
                    incidents[i].cancelIncident();
                } catch (IllegalStateException e) {throw e;
                } finally {
                    if (incidents[i].getIncidentStatus().equals(IncidentStatus.DISPATCHED)) {
                        for (int j = 0; j < incidents.length; j++){
                        if (units[j].getTargetIncident() == incidents[i]) {
                            units[j].setUnitStatus(UnitStatus.IDLE);
                            }
                        }
                    }
                }
            }
        }
        if (exist == 0) {throw new IDNotRecognisedException("ID not recognised");}
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
    * Change the severity of an incident
    *
    * @param incidentId the Id of the incident you want to change
    * @param newSeverity the new severity for the incident
    */
    @Override
    public void escalateIncident(int incidentId, int newSeverity) throws IDNotRecognisedException, InvalidSeverityException, IllegalStateException {
        if ((newSeverity < 1) || (newSeverity > 5)) {throw new InvalidSeverityException("Severity Invalid");}
        int exist = 0;
        for (int i = 0; i < incidents.length; i++) {
            if (incidents[i].getIncidentID() == incidentId){
                exist += 1;
                IncidentStatus status = incidents[i].getIncidentStatus();
                switch (status){
                    case REPORTED:
                        break;
                    case DISPATCHED:
                        break;
                    case IN_PROGRESS:
                        break;
                    default:
                        throw new IllegalStateException("State Illegal");
                }
                incidents[i].setSeverity(newSeverity);
            }
        }
        if (exist == 0) {throw new IDNotRecognisedException("ID not recognised");}
    }

    /**
    * List incident Ids.
    *
    * @return a list of the incident ids
    */
    @Override
    public int[] getIncidentIds() {
        int[] incidentIds = new int[incidents.length];
        for (int i = 0; i < incidents.length; i++){
            if (incidents[i] != null){
                incidentIds[i] = incidents[i].getIncidentID();
            }
        }
        return incidentIds;
    }

    /**
    * Describe one incident.
    *
    * @param incidentId the Id of the incident you want to describe
    * @return a deterministic string describing that incident
    */
    @Override
    public String viewIncident(int incidentId) throws IDNotRecognisedException {
        int exist = 0;
        String incidentString = "";
        String extra = "";
        for (int i = 0; i < incidents.length; i++) {
            if (incidents[i].getIncidentID() == incidentId){
                int[] position = incidents[i].getPosition();
                for (int j = 0; j < incidents.length; j++){
                    if (units[j].getTargetIncident() == incidents[i]) {
                        extra = "" + units[j].getUnitID();
                        }
                }
                incidentString = "\nI#"+ incidentId +" TYPE="+ incidents[i].getIncidentType() +" SEV="+ incidents[i].getSeverity() +" LOC=("+ position[0] +","+ position[1] +") STATUS="+ incidents[i].getIncidentStatus() + extra;
                exist += 1;
            }
        }
        if (exist == 0) {throw new IDNotRecognisedException("ID not recognised");}
        return incidentString;
    }

    /**
    * Assign units to waiting incidents.
    */
    @Override
    public void dispatch() {
        for (Incident i: incidents){
            if (i.getIncidentStatus().equals(IncidentStatus.REPORTED)) {
                int closestUnitId = i.closestUnit(this.units);
                for (Unit u: units){
                    if (u.getUnitID() == closestUnitId){
                        u.setTargetIncident(i);
                        u.setUnitStatus(UnitStatus.EN_ROUTE);
                        i.setIncidentStatus(IncidentStatus.DISPATCHED);
                    }
                }
            }
        }
    }

    /**
    * Advance the time by one tick.
    */
    @Override
    public void tick() {
        this.currentTick++;
        for (Unit u : units){
            if(u.getUnitStatus().equals(UnitStatus.EN_ROUTE)){
                try{
                    u.moveUnit(cityMap);
                } catch(InvalidLocationException e){
                    System.err.println("moveUnit method has an error");
                }
            }
        }

        for (Unit u: units){
            if (u.getUnitStatus().equals(UnitStatus.AT_SCENE)){
                u.setWork(u.getTicksToResolve());
                if (u.getWork() == 0){
                    for (Incident i: incidents){
                        if (i.equals(u.getTargetIncident())){
                            i.setIncidentStatus(IncidentStatus.RESOLVED);
                            u.setUnitStatus(UnitStatus.IDLE);
                            u.removeTargetIncident();
                        }
                    }
                }
            }
        }
    }


    /**
    * Produce a full snapshot for the UI/tests.
    * 
    * @return a deterministic string of the state of the City Map
    */
    @Override
    public String getStatus() {
        String output = "TICK=" + currentTick;
        output+= "\nSTATIONS=" + Station.getNumberOfStations() + " UNITS=" + Unit.getNumberOfUnits() + " INCIDENTS=" + Incident.getNumberOfIncidents() + " OBSTACLES=" + CityMap.getBlockedCount();
        output+= "\nINCIDENTS";
        
        int[] incidentIds = getIncidentIds();
        int[] untitIDs = getUnitIds();

        for (int i = 0; i < incidentIds.length; i++){
            try{
                output+= viewIncident(incidentIds[i]);
            } catch(IDNotRecognisedException e){
                output+=("\nID not Found");
            } 
        }

        for (int i = 0; i < untitIDs.length; i++){
            try{
                output+= viewUnit(untitIDs[i]);
            } catch(IDNotRecognisedException e){
                output+=("\nID not Found");
            }
        }
        return output;
    }
}

