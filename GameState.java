import java.util.LinkedList;

import gridspace.GridSpace;
import utility.Directions;

public class GameState
{
	private Grid<GridSpace> gameBoard;
	private int roryRow;
	private int roryCol;
	private int[] directions;
	private int direction;
	
	public boolean alive = true;
	
	public GameState(Grid<GridSpace> gb, int row, int col)
	{
		this.gameBoard = gb;
		this.roryRow = row;
		this.roryCol = col;
		this.directions = new int[0];
		this.direction = Directions.NONE;
	}
	
	private GameState(Grid<GridSpace> gb, int row, int col, int[] dirs, int dir)
	{
		this.gameBoard = gb;
		this.roryRow = row;
		this.roryCol = col;
		this.directions = dirs;
		this.direction = dir;
		this.moveRory();
	}
	
	private GameState[] createResultingGameStates()
	{
		int rows = this.gameBoard.rows();
		int cols = this.gameBoard.cols();
		
		int[] dirs = this.getPossibleDirections();
		int len = dirs.length;
		GameState[] states = new GameState[len];
		
		for (int d = 0; d < len; d++)
		{
			GameState gs = new GameState(new Grid<GridSpace>(rows, cols, (Integer x, Integer y) -> this.gameBoard.get(x, y).copy()), this.roryRow, this.roryCol, this.directions, dirs[d]);
			states[d] = gs;
		}
		return states;
	}
	
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
	
	private void moveRory()
	{
		if (this.canGo(this.direction))
		{
			int len = this.directions.length;
			int[] dirs = new int[len + 1];
			System.arraycopy(this.directions, 0, dirs, 0, len);
			dirs[len] = this.direction;
			this.directions = dirs;
			
			while (this.canGo(this.direction))
			{
				this.roryRow += Directions.verticalChange(this.direction);
				this.roryCol += Directions.horizontalChange(this.direction);
			}
		}
	}
	
	public void changeDirection(int dir)
	{
		this.direction = dir;
	}
	
	public int hashCode() { return (this.gameBoard.hashCode() << 12) + (this.roryRow << 8) + (this.roryCol << 4) + this.directions[this.directions.length - 1]; }
	public boolean equals(Object o) { return (o instanceof GameState) && (o.hashCode() == this.hashCode()); }
	
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
		GameState gs;
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
			Grid<GridSpace> gameBoard = new Grid<>(board.length, board[0].length(), (Integer x, Integer y) -> board[x].charAt(y) == 'W' ? GridSpace.getWall() : GridSpace.getSpace());
			
			int roryRow = 2;
			int roryCol = 2;
			spawn:
			for (int x = 0; x < board.length; x++)
			{
				for (int y = 0; y < board[0].length(); y++)
				{
					if (board[x].charAt(y) == 'R')
					{
						roryRow = x;
						roryCol = y;
						break spawn;
					}
				}
			}
			gs = new GameState(gameBoard, roryRow, roryCol, new int[]{});
		}
		GameState gs2;
		{
			int rows = gs.gameBoard.rows();
			int cols = gs.gameBoard.cols();
			gs2 = new GameState(new Grid<GridSpace>(rows, cols, (Integer x, Integer y) -> gs.gameBoard.get(x, y).copy()), gs.roryRow, gs.roryCol, gs.directions);
		}
		//gs.gameBoard.get(1, 3).setArt();
		System.out.println(gs.gameBoard.hashCode());
		System.out.println(gs2.gameBoard.hashCode());
		System.out.println(gs.gameBoard.equals(gs2.gameBoard));
		/*gs.moveRory(Directions.RIGHT);
		gs.moveRory(Directions.DOWN);
		gs.moveRory(Directions.UP_LEFT);
		gs.moveRory(Directions.RIGHT);
		gs.moveRory(Directions.UP);
		gs.moveRory(Directions.DOWN_LEFT);
		gs.moveRory(Directions.LEFT);
		System.out.println(gs.getBoard());
		System.out.println(dirsToString(gs.directions));*/
	}
}
