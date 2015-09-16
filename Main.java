import gridspace.*;
import solver.*;
import utility.*;
import java.util.*;

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
			"ww ww",
			"ww ww",
			"R b a",
			"ww ww",
			"ww ww",
			"  b a",
			"ww ww",
			"wwbww"
		};
		GridLiaison liaison = new GridLiaison(board.length, board[0].length());
		
		for (int x = 0; x < board.length; x++)
		{
			for (int y = 0; y < board[0].length(); y++)
			{
				switch (board[x].charAt(y))
				{
					case 'R': liaison.setRory(x, y); break;
					case ' ': liaison.setCell(x, y, GridSpace.SPACE); break;
					case 'b': liaison.setCell(x, y, GridSpace.BOOSTER); break;
					case 'a': liaison.setCell(x, y, GridSpace.SPACE); liaison.setArt(x, y, true); break;
				}
			}
		}
		
		liaison.setDirection(2, 2, Directions.UP);
		liaison.setRotates(2, 2, true);
		liaison.setClockwise(2, 2, true);
		
		liaison.setDirection(5, 2, Directions.LEFT);
		liaison.setRotates(5, 2, true);
		liaison.setClockwise(5, 2, false);
		
		liaison.setDirection(7, 2, Directions.UP);
		
		Solver solver = new Solver(liaison, 7);
		
		//System.out.println(solver.first().gameBoard);
		System.out.println(Solver.solution(solver.solve(0)));
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
