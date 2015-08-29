import gridspace.*;
import gridspace.*;
import solver.*;
import utility.*;

import processing.core.PApplet;

public class Main //extends PApplet
{
	/*private Grid<GridSpace> startingGrid;
	private int maxMoves = 20;
	
	public void setup()
	{
		this.size(200, 200);
		if (frame != null) frame.setResizable(true);
		this.rectMode(CORNER);
		this.noLoop();
	}
	
	public void draw()
	{
		this.background(255);
		this.rect(0, 0, 32, 32);
		this.rect(0, 32, 32, 32);
		this.rect(32, 0, 32, 32);
		this.rect(32, 32, 32, 32);
	}*/
	
	public static void main(String[] args)
	{
		//PApplet.main("Main");
		String[] board = {
			"WWWWWWWWWWWW",
			"W    W  v aW",
			"W>         W",
			"W          W",
			"W          W",
			"W    R  W  W",
			"W          W",
			"W>  W      W",
			"W         <W",
			"W          W",
			"W     ^    W",
			"WWWWWWWWWWWW"
		};
		Grid<GridSpace> gameBoard = new Grid<>(board.length, board[0].length(), (Integer x, Integer y) -> board[x].charAt(y) == 'W' ? GridSpace.getWall() : GridSpace.getSpace());
		
		int roryRow = 2;
		int roryCol = 2;
		
		for (int x = 0; x < board.length; x++)
		{
			for (int y = 0; y < board[0].length(); y++)
			{
				GridSpace square = gameBoard.get(x, y);
				switch (board[x].charAt(y))
				{
					case 'R':
						roryRow = x;
						roryCol = y;
						break;
					case 'a':
						square.setArt(true);
						break;
					case 'v':
						square.setLaserSource(Directions.DOWN);
						break;
					case '<':
						square.setLaserSource(Directions.LEFT);
						break;
					case '>':
						square.setLaserSource(Directions.RIGHT);
						break;
					case '^':
						square.setLaserSource(Directions.UP);
						break;
				}
				if (square.isLaserSource()) square.setLaserSourceOn(true);
			}
		}
		gameBoard.get(7, 1).setLaserBlue(true);
		
		Solver solver = new Solver(gameBoard, roryRow, roryCol, 20);
		
		int[] solution = solver.solve(100000);
		System.out.println(Solver.solution(solution));
		System.out.println(solver.sizes());
		/*
		long start = System.currentTimeMillis();
		int[] solution = solver.solve();
		long stop = System.currentTimeMillis();
		
		System.out.println(new StringBuilder("Solved in ").append((double)(stop - start) / 1000.0D).append(" seconds"));
		System.out.println(solver.sizes());
		System.out.println(Solver.solution(solution));/* */
	}
}
