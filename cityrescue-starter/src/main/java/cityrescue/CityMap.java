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

    public boolean[][] getBlocked()
    {
        return blocked;
    }

    public boolean legalMove()
    {
        return false;
    }
}
