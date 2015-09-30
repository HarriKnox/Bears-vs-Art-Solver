package gridspace;

import utility.*;
import java.util.List;
import java.util.LinkedList;

public class GridTraveler
{
	public static List<Direction> getPossibleDirections(Grid<GridSpace> gameBoard, int row, int col)
	{
		LinkedList<Direction> dirList = new LinkedList<>();
		for (int d = 0, len = Direction.LIST.length; d < len; d++)
		{
			Direction dir = Direction.LIST[d];
			if (canGo(gameBoard, row, col, dir)) dirList.add(dir);
		}
		
		return dirList;
	}
	
	public static boolean canGo(Grid<GridSpace> gameBoard, int row, int col, Direction dir)
	{
		if (dir.isVertical())
		{
			boolean vert = checkDir(gameBoard, row, col, dir.verticalComponent());
			if (dir.isHorizontal()) return vert && checkDir(gameBoard, row, col, dir.horizontalComponent()) && checkDir(gameBoard, row, col, dir);
			return vert;
		}
		else if (dir.isHorizontal())
		{
			return checkDir(gameBoard, row, col, dir.horizontalComponent());
		}
		return false;
	}
	
	public static boolean checkDir(Grid<GridSpace> gameBoard, int row, int col, Direction dir)
	{
		row += dir.verticalChange();
		col += dir.horizontalChange();
		return gameBoard.inRange(row, col) && !gameBoard.get(row, col).isSolid();
	}
}
