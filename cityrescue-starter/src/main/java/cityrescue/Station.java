package cityrescue;

import java.util.ArrayList;
import java.util.HashMap;

public class Station {
    private int numberOfStations;
    private int stationID;
    private int maxCapacity;
    private int currentCapacity = 0;
    private int[] position;  //[x, y]
    private int stationName;
    private HashMap <Integer, Unit> carPark;

    public Station(int inMaxCapacity) 
    {
        stationID = ++numberOfStations;
        maxCapacity = inMaxCapacity;
    }

    public void setStationCapacity(int inCapacity)
    {
        maxCapacity = inCapacity;
    }

    public boolean carParkFull()
    {
        return currentCapacity == maxCapacity;
    }
    
    public void addUnit(Unit unitToBeAdded)
    {  
        carPark.put(unitToBeAdded.getUnitID(), unitToBeAdded);
        currentCapacity++;
    }
    
    public void removeUnit(Unit unitIDtoBeRemoved)
    {
        carPark.remove(unitIDtoBeRemoved.getUnitID());
        currentCapacity--;
    }
}
