import gridspace.*;
import solver.*;
import utility.*;

public class Main
{
	public static void main(String[] args)
	{
		String[] board = {
				"WWWWWW",
				"WWR WW",
				"WW  WW",
				"W   aW",
				"Wa  aW",
				"Wa   W",
				"WW  WW",
				"WWWWWW"
		};
		Grid<GridSpace> gameBoard = new Grid<>(board.length, board[0].length(), (Integer x, Integer y) -> board[x].charAt(y) == 'W' ? GridSpace.getWall() : GridSpace.getSpace());
		
		int roryRow = 2;
		int roryCol = 2;
		
		for (int x = 0; x < board.length; x++)
		{
			for (int y = 0; y < board[0].length(); y++)
			{
				if (board[x].charAt(y) == 'R')
				{
					roryRow = x;
					roryCol = y;
				}
				if (board[x].charAt(y) == 'a')
				{
					gameBoard.get(x, y).setArt();
				}
			}
		}
		Solver solver = new Solver();
		solver.addState(new GameState(gameBoard, roryRow, roryCol));
		GameState solved = null;
		int counter = 0;
		while (!solver.openQueue.isEmpty())
		{
			solved = solver.openQueue.peekFirst();
			if (solved.success())
			{
				int[] dirs = solved.directions;
				System.out.println(solution(dirs));
			}
			solver.processFirstState();
		}
		System.out.println(solver.sizes());
		//System.out.println(solver.sizes());}
		//solver.closedSet.forEach((GameState gs) -> System.out.print(gs.success() ? "true\n" : ""));
		//solver.openQueue.forEach((GameState gs) -> System.out.println(gs.dirsToString(gs.getPossibleDirections())));
	}
}
