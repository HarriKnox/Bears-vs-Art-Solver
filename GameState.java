import java.util.LinkedList;

public class GameState
{
	public Grid<GridSpace> gameBoard;
	public int roryRow;
	public int roryCol;
	
	
	
	private int[] getDirections()
	{
		LinkedList<Integer> dirList = new LinkedList<>();
		for (int d = 0, len = Directions.LIST.length; d < len; d++)
		{
			int dir = Directions.LIST[d];
			if (canGo(dir)) dirList.add(Integer.valueOf(dir));
		}
		
		return dirList.stream().mapToInt(Integer::intValue).toArray();
	}
	
	private boolean canGo(int dir)
	{
		if (Directions.isVertical(dir))
		{
			boolean vert = this.checkDir(Directions.verticalComponent(dir));
			if (Directions.isHorizontal(dir)) return vert && this.checkDir(Directions.horizontalComponent(dir)) && this.checkDir(dir);
			return vert;
		}
		else if (Directions.isHorizontal(dir))
		{
			return this.checkDir(Directions.horizontalComponent(dir));
		}
		return false;
	}
	
	private boolean checkDir(int dir)
	{
		int x = this.roryRow + Directions.verticalChange(dir);
		int y = this.roryCol + Directions.horizontalChange(dir);
		return this.gameBoard.inRange(x, y) && !this.gameBoard.get(x, y).isSolid();
	}
	
	public static void main(String[] args)
	{
		GameState gs = new GameState();
		gs.gameBoard = new Grid<GridSpace>(5, 5, (Integer x, Integer y) -> new GridSpace.Wall(x, y));
		gs.roryRow = 2;
		gs.roryCol = 2;
		for (int x = 1; x < 4; x++)
			for (int y = 1; y < 4; y++)
				gs.gameBoard.set(x, y, new GridSpace.Space(x, y));
		gs.gameBoard.set(1, 1, new GridSpace.Wall(2, 1));
		System.out.println(java.util.Arrays.toString(java.util.Arrays.stream(gs.getDirections()).mapToObj((int i) -> Directions.NAMES[i]).toArray()));
	}
}
