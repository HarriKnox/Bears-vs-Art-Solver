package gridspace;

import utility.*;
import java.util.LinkedList;

public class GridTraveler
{
	public static int[] getPossibleDirections(Grid<GridSpace> gameBoard, int row, int col)
	{
		LinkedList<Direction> dirList = new LinkedList<>();
		for (int d = 0, len = Direction.LIST.length; d < len; d++)
		{
			Direction dir = Direction.LIST[d];
			if (canGo(gameBoard, row, col, dir)) dirList.add(Integer.valueOf(dir));
		}
		
		return dirList.stream().toArray();
	}
	
	public static boolean canGo(Grid<GridSpace> gameBoard, int row, int col, Direction dir)
	{
		if (Direction.isVertical(dir))
		{
			boolean vert = checkDir(gameBoard, row, col, Direction.verticalComponent(dir));
			if (Direction.isHorizontal(dir)) return vert && checkDir(gameBoard, row, col, Direction.horizontalComponent(dir)) && checkDir(gameBoard, row, col, dir);
			return vert;
		}
		else if (Direction.isHorizontal(dir))
		{
			return checkDir(gameBoard, row, col, Direction.horizontalComponent(dir));
		}
		return false;
	}
	
	public static boolean checkDir(Grid<GridSpace> gameBoard, int row, int col, Direction dir)
	{
		row += Direction.verticalChange(dir);
		col += Direction.horizontalChange(dir);
		return gameBoard.inRange(row, col) && !gameBoard.get(row, col).isSolid();
	}
}
