package cityrescue;

import java.util.InputMismatchException;

public class CityMap
{
    private int[] gridSize;
    private Object[][] cityGrid;
    private boolean[][] blocked;

    public void setGridSize(int[] gridSizeIn)
    {
        gridSize = gridSizeIn;
    }

    public int[] getGridSize()
    {
        return gridSize;
    }

    public void setCityGrid(Object[][] cityGridIn)
    {
        cityGrid = cityGridIn;
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

    public void setBlocked(boolean[][] blockedIn)
    {
        blocked = blockedIn;
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
