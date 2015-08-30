package gridspace;

public class GridTraveler
{
	
	
	public static boolean checkDir(Grid<GridSpace> gameBoard, int x, int y, int dir)
	{
		x += Directions.verticalChange(dir);
		y += Directions.horizontalChange(dir);
		return gameBoard.inRange(x, y) && !gameBoard.get(x, y).isSolid();
	}
}
