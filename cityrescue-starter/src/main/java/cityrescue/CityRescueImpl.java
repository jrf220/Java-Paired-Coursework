package cityrescue;

import cityrescue.enums.*;
import cityrescue.exceptions.*;
import cityrescue.Unit;
import cityrescue.CityMap;
import cityrescue.Incident;
import cityrescue.Station;

/**
 * CityRescueImpl (Starter)
 *
 * Your task is to implement the full specification.
 * You may add additional classes in any package(s) you like.
 */
public class CityRescueImpl implements CityRescue {

    // TODO: add fields (map, arrays for stations/units/incidents, counters, tick, etc.)
    private CityMap cityMap;
    private Unit[] units = new Unit[50];
    private Station[] stations = new Station[20];
    private Incident[] incidents = new Incident[200];
    private final int MAX_STATIONS = 20;
    private final int MAX_UNITS = 50;
    private final int MAX_INCIDENTS = 200;
    private int currentTick = 0;
    

    @Override
    public void initialise(int width, int height) throws InvalidGridException {
        if ((width <= 0) || (height <=0)) {throw new InvalidGridException("Not valid grid size.");}
        this.cityMap = new CityMap(new int[] {width, height});
    }

    @Override
    public int[] getGridSize() {
        return cityMap.getGridSize();
    }

    @Override
    public void addObstacle(int x, int y) throws InvalidLocationException{
        try{
            cityMap.addBlockedTile(new int[] {x, y});
        } catch (InvalidLocationException e) {
            throw e;
        }
    }

    @Override
    public void removeObstacle(int x, int y) throws InvalidLocationException {
        try{
            cityMap.removeBlockedTile(new int[] {x, y});
        } catch (InvalidLocationException e) {
            throw e;
        }
    }

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

    @Override
    public int addUnit(int stationId, UnitType type) throws IDNotRecognisedException, InvalidUnitException, IllegalStateException, CapacityExceededException {
        switch (type) {
            case AMBULANCE:
                Ambulance newUnit = new Ambulance(stationId);
            case FIRE_ENGINE:
                FireEngine newUnit = new FireEngine(stationId);
            case POLICE_CAR:
                PoliceCar newUnit = new PoliceCar(stationId);
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
                } finally {exist += 1;}
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

    @Override
    public void setUnitOutOfService(int unitId, boolean outOfService) throws IDNotRecognisedException, IllegalStateException {
        int exist = 0;
        for (int i = 0; i < units.length; i++) {
            if (units[i].getUnitID() == unitId){
                units[i].setUnitStatus(UnitStatus.OUT_OF_SERVICE);
                int stationId = units[i].getHomeStationId();
                for (int j = 0; j < units.length; j++){
                    if (stations[i].getStationID() == stationId){
                        stations[i].removeUnit(unitId);
                    }
                }
                exist += 1;
            }
        }
        if (exist == 0) {throw new IDNotRecognisedException("ID not recognised");}
    }

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

    @Override
    public String viewUnit(int unitId) throws IDNotRecognisedException {
        int exist = 0;
        String unitString = "";
        for (int i = 0; i < units.length; i++) {
            if (units[i].getUnitID() == unitId){
                int[] position = units[i].getPosition();
                String extra = "";
                if (units[i].getTargetIncident() != null) {extra = " INCIDENT="+ units[i].getTargetIncident().getIncidentID() +" WORK=2 ";}
                unitString = "U"+unitId+" TYPE="+ units[i].getUnitType() +" HOME="+ units[i].getHomeStationId() +" LOC=("+ position[0] +","+ position[1] +") STATUS="+ units[i].getUnitStatus();
                exist += 1;
            }
        }
        if (exist == 0) {throw new IDNotRecognisedException("ID not recognised");}
        return unitString;
    }

    @Override
    public int reportIncident(IncidentType type, int severity, int x, int y) throws InvalidSeverityException, InvalidLocationException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void cancelIncident(int incidentId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void escalateIncident(int incidentId, int newSeverity) throws IDNotRecognisedException, InvalidSeverityException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getIncidentIds() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String viewIncident(int incidentId) throws IDNotRecognisedException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void dispatch() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void tick() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String getStatus() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
