package cityrescue;

import cityrescue.exceptions.CapacityExceededException;
import cityrescue.exceptions.IDNotRecognisedException;
import cityrescue.exceptions.InvalidCapacityException;

public class Station {
    private int numberOfStations;
    private int stationID;
    private int maxCapacity;
    private int currentCapacity = 0;
    private int[] position;  //[x, y]
    private String stationName;
    private Unit[] carPark;

    public Station(int inMaxCapacity, String inStationName, int[] inPosition) 
    {
        stationID = ++numberOfStations;
        maxCapacity = inMaxCapacity;
        stationName = inStationName;
        carPark = new Unit[inMaxCapacity];
        position = inPosition;
    }

    public int getStationID()
    {
        return stationID;
    }

    public String getStationName()
    {
        return stationName;
    }

    public void setStationCapacity(int inCapacity) throws InvalidCapacityException
    {
        if (currentCapacity > inCapacity)
        {
            throw new InvalidCapacityException("There are more units than parking spaces to be allocated");
        }
        else
        {
            maxCapacity = inCapacity;

            Unit[] unitTempCarPark = new Unit[inCapacity];
            for (int i = 0; i < carPark.length; i++)
            {
                unitTempCarPark[i] = carPark[i];
            }
            carPark = unitTempCarPark;
        }
    }

    public boolean carParkFull()
    {
        return currentCapacity == maxCapacity;
    }
    
    public void addUnit(Unit unitToBeAdded) throws CapacityExceededException
    {   
        if (carParkFull() == true)
        {
            throw new CapacityExceededException("No more units can be added to this station");
        }
        else
        {
            currentCapacity++;
        
            for (int i = 0; i < carPark.length; i++)
            {
                if (carPark[i] == null)
                {
                    carPark[i] = unitToBeAdded;
                }
            }
        }
    }
    
    public void removeUnit(int unitIDtoBeRemoved) throws IDNotRecognisedException
    {
        boolean untiRemoved = false;

        for (int i = 0; i < carPark.length; i++)
        {
            if (carPark[i].getUnitID() == unitIDtoBeRemoved)
            {
                carPark[i] = null;
                untiRemoved = true;
            }
        }
        if (untiRemoved == false)
        {
            throw new IDNotRecognisedException("The unit was not present in the station car park");
        }
        else{
            currentCapacity--;
        }
    }
}
