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
		return (Directions.isVertical(dir) && (this.checkDir(Directions.verticalComponent(dir)) && (!Directions.isHorizontal(dir) || (this.checkDir(Directions.horizontalComponent(dir)) && this.checkDir(dir))))) || (Directions.isHorizontal(dir) && this.checkDir(Directions.horizontalComponent(dir)));
		
		/*if (Directions.isVertical(dir))
		{
			boolean vert = this.checkDir(Directions.verticalComponent(dir));
			if (Directions.isHorizontal(dir)) return vert && this.checkDir(Directions.horizontalComponent(dir)) && this.checkDir(dir);
			return vert;
		}
		else if (Directions.isHorizontal(dir))
		{
			return this.checkDir(Directions.horizontalComponent(dir));
		}
		return false;*/
	}
	
	private boolean checkDir(int dir)
	{
		int x = roryRow + Directions.verticalChange(dir);
		int y = roryCol + Directions.horizontalChange(dir);
		return this.gameBoard.inRange(x, y) && !this.gameBoard.get(x, y).isSolid()
	}
}
