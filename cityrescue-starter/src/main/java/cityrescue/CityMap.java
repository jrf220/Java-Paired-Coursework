package cityrescue;
import cityrescue.exceptions.InvalidLocationException;
/**
* The CityMap class is a class used to keep track of tiles on the city that are blocked and also
* managing the placement and removal of the blocks.
* 
* @author Appsharan Chandrarajan
* @version 1.0
* @since 2026
*/
public class CityMap
{   
    //The grid uses a 0-based coordinate system. A location (x, y) is in bounds iff 0 ≤ x < width and 0 ≤ y < height.
    private int[] gridSize;
    private boolean[][] blocked;
    static private int blockedCount = 0;

    public CityMap(int[] gridSizeIn)
    {
        gridSize = gridSizeIn;
        blocked = new boolean[gridSizeIn[1]][gridSizeIn[0]];
    }

    public int[] getGridSize()
    {
        return gridSize;
    }

    public static int getBlockedCount()
    {
        return blockedCount;
    }
    
    /**
    * This method checks whether the coordinates entered are within the grid and returns true if
    * they are and false if they are not.
    */
    public boolean checkInGrid(int[] coords)
    {
        if (coords[0] >= gridSize[0] || coords[0] < 0)
        {
            return false;
        }
        else if (coords[1] >= gridSize[1] || coords[1] < 0)
        {
            return false;
        }
        return true;
    }
    
    /**
    * This adds a blocked tile to the grid and checks whether the coordinates are valid
    * and the blockage is not placed on another.
    */
    public void addBlockedTile(int[] coords) throws InvalidLocationException
    {
        if (checkInGrid(coords) == false)
        {
            throw new InvalidLocationException("Coordinates not within the grid");
        }
        else
        {
            if (blocked[coords[1]][coords[0]] == false)
            {
                blocked[coords[1]][coords[0]] = true;
                blockedCount++;
            }
           else
            {
                throw new InvalidLocationException("Roadcblock already placed at " + coords[0] +", " + coords[1]);
            }
        }
    }

    /**
    * This removes a blocked tile on the  grid and checks whether the coordinates are valid
    * and when removing, you are not removing from something that is not there.
    */
    public void removeBlockedTile(int[] coords) throws InvalidLocationException
    {
        if (checkInGrid(coords) == false)
        {
            throw new InvalidLocationException("Coordinates not within the grid");
        }
        else
        {
            if (blocked[coords[1]][coords[0]] == true)
            {
                blocked[coords[1]][coords[0]] = false;
                blockedCount--;
            }
            else
            {
                throw new InvalidLocationException("Roadcblock is not already placed at " + coords[0] +", " + coords[1]);
            }
        }
    }

    /**
    * Checks whether the coordinates entered are within the grid, and if they are it will
    * return whether there is a blockage placed there or not.
    */
    public boolean isBlocked(int[] coords) throws InvalidLocationException
    {
        if (checkInGrid(coords) == false)
        {
            throw new InvalidLocationException("Coordinates not within the grid.");
        }
        else
        {
            return blocked[coords[1]][coords[0]];
        }
    }

    /**
    * If the coordinates entered are within the grid, it checks the directions around the
    * location to see if they can move N, E, S, W without moving of the grid or into a blockage.
    */
    public boolean[] checkAround(int[] coords) throws InvalidLocationException
    {
        if (checkInGrid(coords) == false)
        {
            throw new InvalidLocationException("Coordinates not within the grid");
        }

        else
        {
            boolean[] unblockedDirectionList = new boolean[4]; // In the order: N, E, S, W

            int tempx = coords[0] + 1;
            if (tempx < gridSize[0])
            {
                unblockedDirectionList[1] = isBlocked(new int[]{tempx, coords[1]});
            }
            else
            {
                unblockedDirectionList[1] = true;
            }
        
            tempx = coords[0] - 1;
            if (tempx >= 0)
            {
                unblockedDirectionList[3] = isBlocked(new int[]{tempx, coords[1]});
            }
            else
            {
                unblockedDirectionList[3] = true;
            }

            int tempy = coords[1] + 1;
            if (tempy < gridSize[1])
            {
                unblockedDirectionList[0] = isBlocked(new int[]{coords[0], tempy});
            }
            else
            {
                unblockedDirectionList[0] = true;
            }

            tempy = coords[0] - 1;
            if (tempy >= 0)
            {
                unblockedDirectionList[2] = isBlocked(new int[]{coords[0], tempy});
            }
            else
            {
                unblockedDirectionList[2] = true;
            }

            return unblockedDirectionList;
        }
    }
}
