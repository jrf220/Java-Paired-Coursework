package cityrescue;

import java.util.InputMismatchException;

public class CityMap
{
    final private int[] gridSize;
    final private Object[][] cityGrid;
    private boolean[][] blocked;

    public CityMap(int[] gridSizeIn, Object[][] cityGridIn, boolean[][] blockedIn)
    {
        gridSize = gridSizeIn;
        cityGrid = cityGridIn;
        blocked = blockedIn;
    }

    public int[] getGridSize()
    {
        return gridSize;
    }

    public Object[][] getCityGrid()
    {
        return cityGrid;
    }

    public void addBlockedTile(int[] coords)
    {
        if (blocked[coords[0]][coords[1]] == false)
        {
            blocked[coords[0]][coords[1]] = true;
        }
        else
        {
            throw new InputMismatchException("Roadcblock already placed at " + coords[0] +", " + coords[1]);
        }
    }

    public void removeBlockedTile(int[] coords)
    {
        if (blocked[coords[0]][coords[1]] == true)
        {
            blocked[coords[0]][coords[1]] = false;
        }
        else
        {
            throw new InputMismatchException("Roadcblock is not already placed at " + coords[0] +", " + coords[1]);
        }
    }

    public void addStation(int[] coords, Station stationToBeAdded)
    {
        cityGrid[coords[0]][coords[1]] = stationToBeAdded;
    }

    public void removeStation(int[] coords)
    {
        cityGrid[coords[0]][coords[1]] = new Object();
    }

    public boolean[][] getBlocked()
    {
        return blocked;
    }

    public boolean isBlocked(int[] coords)
    {
        return blocked[coords[0]][coords[1]];
    }

    public boolean legalMove()
    {
        return false;
    }
    
    public boolean[] checkAround(int[] coords)
    {
        boolean[] unblockedDirectionList = new boolean[4]; // In the order: N, E, S, W

        int tempx = coords[1] + 1;
        if (tempx <= gridSize[1])
        {
            unblockedDirectionList[0] = isBlocked(new int[]{coords[0], tempx});
        }
        else
        {
            unblockedDirectionList[0] = true;
        }
        
        tempx = coords[1] - 1;
        if (tempx >= 0)
        {
            unblockedDirectionList[1] = isBlocked(new int[]{coords[0], tempx});
        }
        else
        {
            unblockedDirectionList[1] = true;
        }

        int tempy = coords[0] + 1;
        if (tempy <= gridSize[0])
        {
            unblockedDirectionList[2] = isBlocked(new int[]{tempy, coords[1]});
        }
        else
        {
            unblockedDirectionList[2] = true;
        }

        tempy = coords[0] - 1;
        if (tempy >= 0)
        {
            unblockedDirectionList[3] = isBlocked(new int[]{tempy, coords[1]});
        }
        else
        {
            unblockedDirectionList[3] = true;
        }

        return unblockedDirectionList;
    }
}
