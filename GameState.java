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
		gs.roryRow = 1;
		gs.roryCol = 1;
		{
			String[] board = {
				"WWWWWW",
				"WWR WW",
				"WW  WW",
				"W    W",
				"W    W",
				"W    W",
				"WW  WW",
				"WWWWWW"
			};
			gs.gameBoard = new Grid<GridSpace>(board.length, board[0].length(), (Integer x, Integer y) -> board[x].charAt(y) == 'W' ? new GridSpace.Wall() : new GridSpace.Space());
			
			spawn:
			for (int x = 0; x < board.length; x++)
			{
				for (int y = 0; y < board[0].length(); y++)
				{
					if (board[x].charAt(y) == 'R')
					{
						gs.roryRow = x;
						gs.roryCol = y;
						break spawn;
					}
				}
			}
		}
		gs.moveRory(Directions.LEFT);
		System.out.println(gs.getBoard());
		System.out.println(dirsToString(gs.getPossibleDirections()));
	}
}
