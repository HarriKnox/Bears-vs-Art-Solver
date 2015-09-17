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
		
		/*String[] board = {
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
		
		liaison.setDirection(7, 2, Directions.UP);/* */
		
		
		/*GridLiaison liaison = new GridLiaison(5, 4, 0, 1);
		
		liaison.setCell(0, 0, GridSpace.BUTTON_DOOR);
		for (int i = 0; i < 3; i++) liaison.setCell(1, i, GridSpace.BUTTON_DOOR);
		for (int i = 2; i < 4; i++) liaison.setCell(3, i, GridSpace.BUTTON_DOOR);
		liaison.setCell(4, 3, GridSpace.BUTTON_DOOR);
		
		liaison.setCell(4, 0, GridSpace.BUTTON);
		
		liaison.setArt(1, 0, true);
		liaison.setArt(0, 2, true);
		liaison.setArt(3, 3, true);
		
		System.out.println(liaison.copyGrid());/* */
		
		
		/*GridLiaison liaison = new GridLiaison(5, 5, 2, 2);
		
		liaison.setCell(1, 2, GridSpace.BUTTON_DOOR);
		liaison.setCell(2, 1, GridSpace.BUTTON_DOOR);
		liaison.setCell(2, 3, GridSpace.BUTTON_DOOR);
		liaison.setCell(3, 2, GridSpace.BUTTON_DOOR);
		
		liaison.setUp(1, 2, false);
		liaison.setUp(2, 1, false);
		liaison.setUp(2, 3, false);
		liaison.setUp(3, 2, false);
		
		liaison.setCell(0, 1, GridSpace.BUTTON);
		liaison.setArt(0, 1, true);
		liaison.setUp(0, 1, false);
		
		liaison.setCell(1, 4, GridSpace.BUTTON);
		liaison.setArt(1, 4, true);
		
		liaison.setCell(4, 3, GridSpace.BUTTON);
		liaison.setArt(4, 3, true);
		liaison.setUp(4, 3, false);
		
		liaison.setCell(3, 0, GridSpace.BUTTON);
		liaison.setArt(3, 0, true);/* */
		
		
		/*GridLiaison liaison = new GridLiaison(7, 5, 6, 0);
		
		liaison.setCell(2, 1, GridSpace.WALL);
		liaison.setCell(4, 3, GridSpace.WALL);
		for (int i = 1; i < 4; i++) liaison.setCell(3, i, GridSpace.WALL);
		liaison.setCell(4, 4, GridSpace.BUTTON_DOOR);
		liaison.setCell(6, 4, GridSpace.BUTTON);
		
		liaison.setUp(4, 4, false);
		
		liaison.setArt(2, 2, true);
		liaison.setArt(4, 2, true);
		liaison.setArt(3, 4, true);/* */
		
		
		/*GridLiaison liaison = new GridLiaison(5, 5, 0, 0);
		
		liaison.setCell(0, 2, GridSpace.BUTTON);
		liaison.setCell(2, 0, GridSpace.WALL);
		liaison.setCell(2, 1, GridSpace.BUTTON_DOOR);
		liaison.setCell(4, 2, GridSpace.BUTTON);
		
		liaison.setUp(2, 1, false);
		liaison.setUp(4, 2, false);
		liaison.setArt(0, 4, true);
		liaison.setArt(2, 1, true);
		liaison.setArt(4, 4, true);/* */
		
		
		/*GridLiaison liaison = new GridLiaison(8, 5, 0, 2);
		
		liaison.setCell(2, 2, GridSpace.WALL);
		liaison.setCell(5, 2, GridSpace.WALL);
		
		liaison.setArt(1, 2, true);
		liaison.setArt(2, 1, true);
		liaison.setArt(3, 2, true);
		liaison.setArt(2, 3, true);
		liaison.setArt(4, 2, true);
		liaison.setArt(5, 1, true);
		liaison.setArt(5, 3, true);
		liaison.setArt(6, 2, true);/* */
		
		
		GridLiaison liaison = new GridLiaison(9, 8, 6, 4);
		
		liaison.setCell(0, 0, GridSpace.WALL);
		for (int i = 5; i < 8; i++) liaison.setCell(0, i, GridSpace.WALL);
		for (int r = 7; r < 9; r++) for (int c = 6; c < 8; c++) liaison.setCell(r, c, GridSpace.WALL);
		
		liaison.setCell(1, 0, GridSpace.BUTTON);
		liaison.setCell(2, 4, GridSpace.BUTTON).setUp(false);
		
		liaison.setCell(5, 4, GridSpace.BUTTON_DOOR);
		liaison.setCell(5, 7, GridSpace.BUTTON_DOOR).setUp(false);
		
		liaison.setCell(1, 7, GridSpace.BUTTON).setColor(Colors.RED);
		liaison.setCell(3, 2, GridSpace.BUTTON).setColor(Colors.RED).setUp(false);
		
		liaison.setCell(2, 3, GridSpace.BUTTON_DOOR).setColor(Colors.RED);
		liaison.setCell(7, 3, GridSpace.BUTTON_DOOR).setColor(Colors.RED).setUp(false);
		
		
		liaison.setCell(8, 5, GridSpace.BUTTON).setColor(Colors.GREEN);
		liaison.setCell(5, 0, GridSpace.BUTTON).setColor(Colors.GREEN).setUp(false);
		
		liaison.setCell(7, 1, GridSpace.BUTTON_DOOR).setColor(Colors.GREEN);
		liaison.setCell(3, 1, GridSpace.BUTTON_DOOR).setColor(Colors.GREEN).setUp(false);
		
		
		liaison.setArt(0, 2, true);
		liaison.setArt(1, 5, true);
		liaison.setArt(1, 6, true);
		liaison.setArt(3, 0, true);
		liaison.setArt(4, 0, true);
		liaison.setArt(3, 7, true);
		liaison.setArt(4, 7, true);
		liaison.setArt(6, 0, true);
		liaison.setArt(6, 6, true);
		liaison.setArt(6, 7, true);
		liaison.setArt(8, 0, true);
		
		liaison.setLaserSourceDirection(0, 1, Directions.DOWN).setLaserSourceOn(true);
		liaison.setLaserSourceDirection(0, 4, Directions.DOWN).setLaserSourceOn(true);
		liaison.setLaserSourceDirection(2, 7, Directions.LEFT).setLaserSourceOn(true);
		liaison.setLaserSourceDirection(5, 7, Directions.LEFT).setLaserSourceOn(true);
		liaison.setLaserSourceDirection(7, 0, Directions.RIGHT).setLaserSourceOn(true);
		liaison.setLaserSourceDirection(8, 3, Directions.UP).setLaserSourceOn(true);/* */
		
		
		Solver solver = new Solver(liaison, 36);
		
		long start = System.currentTimeMillis();
		int[] solution = solver.solve(0);
		long stop = System.currentTimeMillis();
		
		System.out.println(new StringBuilder("Solved in ").append((double)(stop - start) / 1000.0D).append(" seconds"));
		System.out.println(solver.sizes());
		System.out.println(Solver.solution(solution));/* */
	}
}
