package gridspace;

public class GridTraveler
{
	
	
	public static boolean canGo(Grid<GridSpace> gameBoard, int row, int col, int dir)
	{
		if (Directions.isVertical(dir))
		{
			boolean vert = checkDir(gameBoard, row, col, Directions.verticalComponent(dir));
			if (Directions.isHorizontal(dir)) return vert && checkDir(gameBoard, row, col, Directions.horizontalComponent(dir)) && checkDir(gameBoard, row, col, dir);
			return vert;
		}
		else if (Directions.isHorizontal(dir))
		{
			return checkDir(gameBoard, row, col, Directions.horizontalComponent(dir));
		}
		return false;
	}
	
	public static boolean checkDir(Grid<GridSpace> gameBoard, int row, int col, int dir)
	{
		row += Directions.verticalChange(dir);
		col += Directions.horizontalChange(dir);
		return gameBoard.inRange(row, col) && !gameBoard.get(row, col).isSolid();
	}
}
