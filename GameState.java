import java.util.LinkedList;

public class GameState
{
	public Grid<GridSpace> gameBoard;
	public int roryRow;
	public int roryCol;
	
	private int[] getPossibleDirections()
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
	
	private void moveRory(int dir)
	{
		while (this.canGo(dir))
		{
			this.roryRow += Directions.verticalChange(dir);
			this.roryCol += Directions.horizontalChange(dir);
		}
	}
	
	private static String dirsToString(int[] dirs)
	{
		return java.util.Arrays.toString(java.util.Arrays.stream(dirs).mapToObj((int i) -> Directions.NAMES[i]).toArray());
	}
	
	private String getBoard()
	{
		StringBuilder sb = new StringBuilder();
		for (int x = 0, rows = this.gameBoard.rows(); x < rows; x++)
		{
			for (int y = 0, cols = this.gameBoard.cols(); y < cols; y++)
			{
				sb.append((x == this.roryRow && y == this.roryCol) ? 'R' : this.gameBoard.get(x, y).toString());
			}
			sb.append('\n');
		}
		return sb.toString();
	}
	
	public static void main(String[] args)
	{
		GameState gs = new GameState();
		gs.roryRow = 2;
		gs.roryCol = 8;
		gs.gameBoard = new Grid<GridSpace>(5, 10, (Integer x, Integer y) -> new GridSpace.Space(x, y));
		for (int x = 0; x < 5; x++)
		{
			gs.gameBoard.set(x, 0, new GridSpace.Wall(x, 0));
			gs.gameBoard.set(x, 9, new GridSpace.Wall(x, 9));
		}
		for (int y = 1; y < 9; y++)
		{
			gs.gameBoard.set(0, y, new GridSpace.Wall(0, y));
			gs.gameBoard.set(4, y, new GridSpace.Wall(4, y));
		}
		gs.moveRory(Directions.UP_LEFT);
		gs.moveRory(Directions.DOWN_LEFT);
		gs.moveRory(Directions.UP);
		System.out.println(gs.getBoard());
		System.out.println(dirsToString(gs.getPossibleDirections()));
	}
}
