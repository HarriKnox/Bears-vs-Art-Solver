import gridspace.*;
import solver.*;
import utility.*;

import processing.core.PApplet;

public class Main extends PApplet
{
	private Grid<GridSpace> startingGrid;
	private Grid<GridSpace> allChanges;
	private int maxMoves = 20;
	
	public void setup()
	{
		this.size(200, 200);
		if (frame != null) frame.setResizable(true);
	}
	
	public void draw()
	{
		
	}
	
	public static void main(String[] args)
	{
		PApplet.main("Main");
		/*String[] board = {
			"WWWWWWWW",
			"Wa R  aW",
			"Wa    aW",
			"Wa    aW",
			"Wa    aW",
			"Wa    aW",
			"Wa    aW",
			"Wa    aW",
			"Wa    aW",
			"WWWWWWWW"
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
		Solver solver = new Solver(gameBoard, roryRow, roryCol, 20);
		long start = System.currentTimeMillis();
		int[] solution = solver.solve();
		long stop = System.currentTimeMillis();
		
		System.out.println(new StringBuilder("Solved in ").append((double)(stop - start) / 1000.0D).append(" seconds"));
		System.out.println(Solver.solution(solution));*/
	}
}
