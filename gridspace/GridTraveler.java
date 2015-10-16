package gridspace;

import java.util.LinkedList;

import utility.Direction;
import utility.Grid;

public class GridTraveler
{
	public static Direction[] getPossibleDirections(Grid<GridSpace> gameBoard, int row, int col)
	{
		LinkedList<Direction> dirList = new LinkedList<>();
		for (Direction dir : Direction.values())
			if (dir != Direction.NONE && canGo(gameBoard, row, col, dir))
				dirList.add(dir);
		
		return dirList.toArray(new Direction[dirList.size()]);
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
