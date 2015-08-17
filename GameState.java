import java.util.LinkedList;

public class GameState
{
	private Grid<GridSpace> gameBoard;
	private int roryRow;
	private int roryCol;
	
	private int[] getDirections()
	{
		LinkedList<Integer> dirList = new LinkedList<>();
		for (int d = 0; d < Directions.LIST; d++)
		{
			int dir = Directions.LIST[d];
			if (canGo(dir)) dirList.add(Integer.valueOf(dir));
		}
		
		return dirList.stream().mapToInt(Integer::intValue).toArray();
	}
	
	private boolean canGo(int dir)
	{
		if (Directions.isVertical(dir) && !this.checkDir(Directions.verticalComponent(dir)))
			return false;
		if (Directions.isHorizontal(dir) && !this.checkDir(Directions.horizontalComponent(dir)))
			return false;
		if (Directions.isDiagonal(dir) && !this.checkDir(dir))
			return false;
	}
	
	private boolean checkDir(int dir)
	{
		int x = roryRow + Directions.verticalChange(dir);
		int y = roryCol + Directions.horizontalChange(dir);
		return this.gameBoard.inRange(x, y) && !this.gameBoard.get(x, y).isSolid()
	}
}
