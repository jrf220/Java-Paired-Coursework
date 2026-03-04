package cityrescue;

import cityrescue.exceptions.CapacityExceededException;
import cityrescue.exceptions.IDNotRecognisedException;
import cityrescue.exceptions.InvalidCapacityException;

/**
* The Station Class is a class used to keep track of a particular stations status.
* This includes its position, parking lot and the capcaity of it and whether it changes or not.
* 
* @author Appsharan Chandrarajan
* @version 1.0
* @since 2026
*/
public class Station {
    static private int numberOfStations;
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

    public int getStationID() {return stationID;}
    public String getStationName() {return stationName;}
    public int[] getPosition() {return position;}
    public static int getNumberOfStations() {return numberOfStations;}
    public int getCurrentStationCapacity() {return currentCapacity;}
    public int getMaxCapacity() {return maxCapacity;}

    /**
    * Checks if the Car park empty.
    * 
    * @return Returns a boolean value specifyin when the car park of a station is empty
    */
    public boolean isEmpty() {return currentCapacity == 0;}

    /**
    * Checks if the Car park is full or not using the Current Capacity and
    * the maximmum capacity of the station.
    * 
    * @return This returns whether the car park is full or not
    */
    public boolean carParkFull() {return currentCapacity == maxCapacity;}

    /**
    * This method is here for when the capacity of a station is increased or decreased.
    * It adjusts the carPark accordingly.
    * 
    * @param inCapacity This takes in an integer that specifies the new capacity of the car park of a given station.
    * @throws InvalidCapacityException is the capacity given is less than the current capacity.
    */
    public void setStationCapacity(int inCapacity) throws InvalidCapacityException{
        if (currentCapacity > inCapacity){
            throw new InvalidCapacityException("There are more units than parking spaces to be allocated");
        } else {
            //This is copying over all the units to the bigger car park
            maxCapacity = inCapacity;
            Unit[] unitTempCarPark = new Unit[inCapacity];
            for (int i = 0; i < carPark.length; i++){
                unitTempCarPark[i] = carPark[i];
            }
            carPark = unitTempCarPark;
        }
    }
    
    /**
    * This adds a unit to the car park of the station and also checks if is full first.
    * If the car park is full then it throws an excpetion.
    * 
    * @param unitToBeAdded Takes in the unit to be added to a given station.
    * @throws CapacityExceededException if the unit does not fit in this station.
    */
    public void addUnit(Unit unitToBeAdded) throws CapacityExceededException{   
        if (carParkFull() == true){
            throw new CapacityExceededException("No more units can be added to this station");
        } else {
            currentCapacity++;
            for (int i = 0; i < carPark.length; i++){
                //Adds the new unit in the empty space in the carPark array.
                if (carPark[i] == null){
                    carPark[i] = unitToBeAdded;
                }
            }
        }
    }
    
    /**
    * This removes a unit from the car park of a station and if the unit was not present then
    * it will throw an exception.
    * 
    * @param unitIDtoBeRemoved Takes in an integer that is the unit id of the unit to be removed from the station.
    * @throws IDNotRecognisedException if the unit ID does not exist in this station.
    */
    public void removeUnit(int unitIDtoBeRemoved) throws IDNotRecognisedException{
        boolean untiRemoved = false;
        for (int i = 0; i < carPark.length; i++){
            if (carPark[i].getUnitID() == unitIDtoBeRemoved){
                carPark[i] = null;
                untiRemoved = true;
                break;
            }
        }
        if (untiRemoved == false){
            throw new IDNotRecognisedException("The unit was not present in the station car park");
        } else {
            currentCapacity--;
        }
    }
}
