package cityrescue;

import java.util.InputMismatchException;

public class CityMap
{   
    //The grid uses a 0-based coordinate system. A location (x, y) is in bounds iff 0 ≤ x < width and 0 ≤ y < height.
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
        if (blocked[coords[1]][coords[0]] == false)
        {
            blocked[coords[1]][coords[0]] = true;
        }
        else
        {
            throw new InputMismatchException("Roadcblock already placed at " + coords[0] +", " + coords[1]);
        }
    }

    public void removeBlockedTile(int[] coords)
    {
        if (blocked[coords[1]][coords[0]] == true)
        {
            blocked[coords[1]][coords[0]] = false;
        }
        else
        {
            throw new InputMismatchException("Roadcblock is not already placed at " + coords[0] +", " + coords[1]);
        }
    }

    public void addItem(int[] coords, Object toBeAdded)
    {
        cityGrid[coords[1]][coords[0]] = toBeAdded;
    }

    public void removeIten(int[] coords)
    {
        cityGrid[coords[1]][coords[0]] = new Object();
    }

    public boolean[][] getBlocked()
    {
        return blocked;
    }

    public boolean isBlocked(int[] coords)
    {
        return blocked[coords[1]][coords[0]];
    }

    public boolean legalMove()
    {
        return false;
    }
    
    public boolean[] checkAround(int[] coords)
    {
        boolean[] unblockedDirectionList = new boolean[4]; // In the order: N, E, S, W

        int tempx = coords[0] + 1;
        if (tempx < gridSize[0])
        {
            unblockedDirectionList[0] = isBlocked(new int[]{tempx, coords[1]});
        }
        else
        {
            unblockedDirectionList[0] = true;
        }
        
        tempx = coords[0] - 1;
        if (tempx >= 0)
        {
            unblockedDirectionList[1] = isBlocked(new int[]{tempx, coords[1]});
        }
        else
        {
            unblockedDirectionList[1] = true;
        }

        int tempy = coords[1] + 1;
        if (tempy < gridSize[1])
        {
            unblockedDirectionList[2] = isBlocked(new int[]{coords[0], tempy});
        }
        else
        {
            unblockedDirectionList[2] = true;
        }

        tempy = coords[0] - 1;
        if (tempy >= 0)
        {
            unblockedDirectionList[3] = isBlocked(new int[]{coords[0], tempy});
        }
        else
        {
            unblockedDirectionList[3] = true;
        }

        return unblockedDirectionList;
    }
}

