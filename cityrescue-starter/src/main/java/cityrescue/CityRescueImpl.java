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
    int currentTick = 0;
    

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
            cityMap.removedBlockedTile(new int[] {x, y});
        } catch (InvalidLocationException e) {
            throw e;
        }
    }

    @Override
    public int addStation(String name, int x, int y) throws CapacityExceededException, InvalidNameException, InvalidLocationException {
        int[] location = new int[] {x, y};
        if (cityMap.isBlocked(location) || cityMap.checkInGrid(location)){
            throw InvalidLocationException;}
        if (name.equals("")) {throw InvalidNameException;}
        int added = 0;
        Station newStation = new Station(3, name, location);
        for (int i = 0; i < stations.length; i++) {
            if (stations[i] == null){
                stations[i] = newStation;
                added += 1;
                break;
                }
        }
        if (added == 0) {throw CapacityExceededException;}
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
                } else {throw IllegalStateException;}
            }
        }
        if (removed == 0) {throw IDNotRecognisedException;}
    }

    @Override
    public void setStationCapacity(int stationId, int maxUnits) throws IDNotRecognisedException, InvalidCapacityException {
        int updated = 0;
        for (int i = 0; i < stations.length; i++){
            if (stations[i].getStationID() == stationId){
                if (maxUnits > stations[i].getMaxCapacity()){
                    stations[i] = null;
                    updated += 1;
                } else {throw InvalidCapacityException;}
            }
        }
        if (updated == 0) {throw IDNotRecognisedException;}
    }

    @Override
    public int[] getStationIds() {
        int[] stationIds = new int[stations.length];
        for (int i = 0; i < stations.length; i++){
            if (stations[i] != null){
                stationIds[i] = stations[i].getStationID();
            }
        }
    }

    @Override
    public int addUnit(int stationId, UnitType type) throws IDNotRecognisedException, InvalidUnitException, IllegalStateException, CapacityExceededException {
        switch (type) {
            case AMBULANCE:
                Ambulance newUnit = new Ambulance();
            case FIRE_ENGINE:
                FireEngine newUnit = new FireEngine();
            case POLICE_CAR:
                PoliceCar newUnit = new PoliceCar();
            default:
                throw InvalidUnitException;
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
        if (exist == 0){throw IDNotRecognisedException;}

        int added = 0;
        for (int i = 0; i < units.length; i++) {
            if (units[i] == null){
                units[i] = newUnit;
                added += 1;
                break;
                }
        }
        if (added == 0) {throw CapacityExceededException;}
        return newUnit.getUnitID();
    }

    @Override
    public void decommissionUnit(int unitId) throws IDNotRecognisedException, IllegalStateException {
        int exist = 0;
        for (int i = 0; i < units.length; i++) {
            if (units[i].getUnitID() == unitId){
                if (units[i].getUnitStatus().equals(UnitStatus.IDLE)) {throw IllegalStateException;}
                exist += 1;
                units[i].setUnitStatus(UnitStatus.OUT_OF_SERVICE);
                break;
                }
        }
        if (exist == 0) {throw IDNotRecognisedException;}
    }

    @Override
    public void transferUnit(int unitId, int newStationId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setUnitOutOfService(int unitId, boolean outOfService) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getUnitIds() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String viewUnit(int unitId) throws IDNotRecognisedException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
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
