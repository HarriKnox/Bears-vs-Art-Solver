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
		
		
		/// Level 33 "All About Buttons"
		/*
		GridLiaison liaison = new GridLiaison(5, 4, 0, 1);
		liaison.setCell(0, 0, GridSpace.BUTTON_DOOR);
		for (int i = 0; i < 3; i++) liaison.setCell(1, i, GridSpace.BUTTON_DOOR);
		for (int i = 2; i < 4; i++) liaison.setCell(3, i, GridSpace.BUTTON_DOOR);
		liaison.setCell(4, 3, GridSpace.BUTTON_DOOR);
		liaison.setCell(4, 0, GridSpace.BUTTON);
		liaison.setArt(1, 0, true);
		liaison.setArt(0, 2, true);
		liaison.setArt(3, 3, true);/* */
		
		
		/// Level 34 "Up And Down"
		/*
		GridLiaison liaison = new GridLiaison(5, 5, 2, 2);
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
		
		
		/// Level 35 "Stop Gap Solution"
		/*
		GridLiaison liaison = new GridLiaison(7, 5, 6, 0);
		liaison.setCell(2, 1, GridSpace.WALL);
		liaison.setCell(4, 3, GridSpace.WALL);
		for (int i = 1; i < 4; i++) liaison.setCell(3, i, GridSpace.WALL);
		liaison.setCell(4, 4, GridSpace.BUTTON_DOOR);
		liaison.setCell(6, 4, GridSpace.BUTTON);
		liaison.setUp(4, 4, false);
		liaison.setArt(2, 2, true);
		liaison.setArt(4, 2, true);
		liaison.setArt(3, 4, true);/* */
		
		
		/// Level 37 "Toggle"
		/*
		GridLiaison liaison = new GridLiaison(5, 5, 0, 0);
		liaison.setCell(0, 2, GridSpace.BUTTON);
		liaison.setCell(2, 0, GridSpace.WALL);
		liaison.setCell(2, 1, GridSpace.BUTTON_DOOR);
		liaison.setCell(4, 2, GridSpace.BUTTON);
		liaison.setUp(2, 1, false);
		liaison.setUp(4, 2, false);
		liaison.setArt(0, 4, true);
		liaison.setArt(2, 1, true);
		liaison.setArt(4, 4, true);/* */
		
		
		/// Level 39 "Two Pillars"
		/*
		GridLiaison liaison = new GridLiaison(8, 5, 0, 2);
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
		
		
		/// Level 117 "Masterplan"
		/*
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
		liaison.setLaserSourceDirection(0, 1, Direction.DOWN).setLaserSourceOn(true);
		liaison.setLaserSourceDirection(0, 4, Direction.DOWN).setLaserSourceOn(true);
		liaison.setLaserSourceDirection(2, 7, Direction.LEFT).setLaserSourceOn(true);
		liaison.setLaserSourceDirection(5, 7, Direction.LEFT).setLaserSourceOn(true);
		liaison.setLaserSourceDirection(7, 0, Direction.RIGHT).setLaserSourceOn(true);
		liaison.setLaserSourceDirection(8, 3, Direction.UP).setLaserSourceOn(true);/* */
		
		
		/// Level 65 "In The Way"
		/*
		GridLiaison liaison = new GridLiaison(3, 4, 0, 0);
		liaison.setCell(1, 3, GridSpace.WALL);
		liaison.setCell(2, 3, GridSpace.WALL);
		liaison.setArt(2, 2, true);
		liaison.setCell(1, 0, GridSpace.MOVE_DOOR).setMovesStarted(4);/* */
		
		
		/// Level 66 "Right Place, Right Time"
		/*
		GridLiaison liaison = new GridLiaison(6, 4, 0, 0);
		for (int i = 0; i < 3; i++) liaison.setCell(i, 2, GridSpace.WALL);
		for (int i = 2; i < 5; i++) liaison.setCell(i, 1, GridSpace.WALL);
		liaison.setCell(2, 0, GridSpace.MOVE_DOOR).setUp(false).setMovesStarted(4);
		liaison.setCell(4, 0, GridSpace.MOVE_DOOR).setMovesStarted(4);
		liaison.setCell(5, 2, GridSpace.MOVE_DOOR).setUp(false).setMovesStarted(7);
		liaison.setCell(5, 3, GridSpace.MOVE_DOOR).setUp(false).setMovesStarted(7);
		liaison.setCell(4, 3, GridSpace.MOVE_DOOR).setUp(false).setMovesStarted(8);
		liaison.setCell(4, 2, GridSpace.MOVE_DOOR).setUp(false).setMovesStarted(9);
		liaison.setCell(3, 2, GridSpace.MOVE_DOOR).setUp(false).setMovesStarted(10);
		liaison.setCell(3, 3, GridSpace.MOVE_DOOR).setUp(false).setMovesStarted(11);
		liaison.setCell(1, 3, GridSpace.MOVE_DOOR).setMovesStarted(11);
		liaison.setArt(5, 0, true);
		liaison.setArt(0, 3, true);/* */
		
		
		/// Level 70 "Through Space And Time"
		/*
		GridLiaison liaison = new GridLiaison(6, 3, 0, 0);
		liaison.setCell(2, 1, GridSpace.TELEPORTER);
		liaison.setCell(4, 1, GridSpace.TELEPORTER);
		for (int i = 0; i < 3; i++) liaison.setCell(3, i, GridSpace.WALL);
		liaison.setArt(0, 1, true);/* */
		
		
		/// Level 91 "Tiny Tim"
		/*
		GridLiaison liaison = new GridLiaison(3, 2, 2, 0);
		liaison.setCell(0, 0, GridSpace.BUTTON_DOOR).setArt(true).setUp(false);
		liaison.setCell(0, 1, GridSpace.BUTTON).setUp(false).setColor(Colors.RED);
		liaison.setCell(1, 0, GridSpace.BUTTON).setArt(true);
		liaison.setCell(1, 1, GridSpace.BUTTON_DOOR).setArt(true).setColor(Colors.RED);
		liaison.setCell(2, 0, GridSpace.BUTTON).setUp(false);
		liaison.setCell(2, 1, GridSpace.BUTTON).setColor(Colors.RED);/* */
		
		
		/// Level 93 "High Stakes 2"
		/*
		GridLiaison liaison = new GridLiaison(7, 5, 0, 2);
		liaison.setCell(0, 0, GridSpace.BOOSTER).setDirection(Direction.DOWN);
		liaison.setCell(0, 4, GridSpace.BOOSTER).setDirection(Direction.DOWN);
		for (int i = 1; i < 4; i++) liaison.setCell(1, i, GridSpace.BOOSTER).setDirection(Direction.DOWN);
		liaison.setCell(3, 1, GridSpace.BOOSTER).setRotates(true).setDirection(Direction.DOWN);
		liaison.setCell(3, 2, GridSpace.BOOSTER).setRotates(true).setDirection(Direction.LEFT).setClockwise(false);
		liaison.setCell(3, 3, GridSpace.BOOSTER).setRotates(true).setDirection(Direction.DOWN);
		liaison.setCell(4, 1, GridSpace.BOOSTER).setRotates(true).setDirection(Direction.DOWN).setClockwise(false);
		liaison.setCell(4, 2, GridSpace.BOOSTER).setRotates(true).setDirection(Direction.LEFT).setClockwise(false);
		liaison.setCell(4, 3, GridSpace.BOOSTER).setRotates(true).setDirection(Direction.LEFT).setClockwise(false);
		liaison.setCell(5, 1, GridSpace.BOOSTER).setRotates(true).setDirection(Direction.UP);
		liaison.setCell(5, 2, GridSpace.BOOSTER).setRotates(true).setDirection(Direction.LEFT).setClockwise(false);
		liaison.setCell(5, 3, GridSpace.BOOSTER).setRotates(true).setDirection(Direction.RIGHT);
		liaison.setCell(5, 0, GridSpace.BOOSTER);
		liaison.setCell(5, 4, GridSpace.BOOSTER).setDirection(Direction.LEFT);
		liaison.setArt(6, 2, true);/* */
		
		
		/// Level 95 "Swerve"
		/*
		GridLiaison liaison = new GridLiaison(8, 6, 3, 2);
		liaison.setCell(0, 0, GridSpace.WALL);
		liaison.setCell(1, 0, GridSpace.WALL);
		liaison.setCell(6, 0, GridSpace.WALL);
		liaison.setCell(7, 0, GridSpace.WALL);
		for (int i = 3; i <= 4; i++) for (int j = 4; j <= 5; j++) liaison.setCell(i, j, GridSpace.WALL);
		liaison.setCell(2, 0, GridSpace.BOOSTER);
		liaison.setCell(5, 0, GridSpace.BOOSTER);
		liaison.setCell(2, 4, GridSpace.BOOSTER).setDirection(Direction.UP);
		liaison.setCell(5, 4, GridSpace.BOOSTER).setDirection(Direction.DOWN);
		liaison.setCell(2, 2, GridSpace.BUTTON).setUp(false);
		liaison.setCell(5, 2, GridSpace.BUTTON);
		liaison.setCell(2, 3, GridSpace.BUTTON_DOOR).setUp(false);
		liaison.setCell(5, 3, GridSpace.BUTTON_DOOR);
		liaison.setCell(3, 1, GridSpace.TELEPORTER);
		liaison.setCell(4, 1, GridSpace.TELEPORTER);
		liaison.setLaserSourceDirection(0, 3, Direction.DOWN).setLaserSourceOn(true);
		liaison.setLaserSourceDirection(2, 5, Direction.LEFT).setLaserSourceOn(true);
		liaison.setLaserSourceDirection(5, 5, Direction.LEFT).setLaserSourceOn(true);
		liaison.setArt(0, 2, true);
		liaison.setArt(1, 5, true);
		liaison.setArt(6, 1, true);
		liaison.setArt(7, 2, true);
		liaison.setArt(7, 4, true);/* */
		
		
		/*liaison.updateLasers();
		System.out.println(liaison.copyGrid());
		
		Solver solver = new Solver(liaison, 16);
		
		long start = System.currentTimeMillis();
		int[] solution = solver.solve(0);
		long stop = System.currentTimeMillis();
		
		System.out.println(new StringBuilder("Solved in ").append((double)(stop - start) / 1000.0D).append(" seconds"));
		System.out.println(solver.sizes());
		System.out.println(Solver.solution(solution));/* */
		
		System.out.println(Direction.UP_LEFT.opposite());
	}
}
