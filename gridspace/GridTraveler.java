package gridspace;

import utility.*;
import java.util.LinkedList;

public class GridTraveler
{
	public static int[] getPossibleDirections(Grid<GridSpace> gameBoard, int row, int col)
	{
		LinkedList<Directions> dirList = new LinkedList<>();
		for (int d = 0, len = Directions.LIST.length; d < len; d++)
		{
			Directions dir = Directions.LIST[d];
			if (canGo(gameBoard, row, col, dir)) dirList.add(Integer.valueOf(dir));
		}
		
		return dirList.stream().toArray();
	}
	
	public static boolean canGo(Grid<GridSpace> gameBoard, int row, int col, Directions dir)
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
	
	public static boolean checkDir(Grid<GridSpace> gameBoard, int row, int col, Directions dir)
	{
		row += Directions.verticalChange(dir);
		col += Directions.horizontalChange(dir);
		return gameBoard.inRange(row, col) && !gameBoard.get(row, col).isSolid();
	}
}
