import gridspace.GridSpace;
import gridspace.GridLiaison;

import solver.Solver;

import utility.Color;
import utility.Direction;
import utility.RailDirection;

public class Main
{
	public static void main(String[] args)
	{
		Direction RIGHT = Direction.RIGHT,
		          LEFT  = Direction.LEFT,
		          UP    = Direction.UP,
		          DOWN  = Direction.DOWN;
		
		RailDirection UP_UP       = RailDirection.UP_UP,
		              UP_DOWN     = RailDirection.UP_DOWN,
		              UP_LEFT     = RailDirection.UP_LEFT,
		              UP_RIGHT    = RailDirection.UP_RIGHT,
		              DOWN_DOWN   = RailDirection.DOWN_DOWN,
		              DOWN_LEFT   = RailDirection.DOWN_LEFT,
		              DOWN_RIGHT  = RailDirection.DOWN_RIGHT,
		              LEFT_LEFT   = RailDirection.LEFT_LEFT,
		              LEFT_RIGHT  = RailDirection.LEFT_RIGHT,
		              RIGHT_RIGHT = RailDirection.RIGHT_RIGHT;
		
		Color RED   = Color.RED,
		      GREEN = Color.GREEN,
		      BLUE  = Color.BLUE;
		
		
		GridLiaison liaison;
		
		
		/// Level 33 "All About Buttons"
		/*
		liaison = new GridLiaison(5, 4, 0, 1);
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
		liaison = new GridLiaison(5, 5, 2, 2);
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
		liaison = new GridLiaison(7, 5, 6, 0);
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
		liaison = new GridLiaison(5, 5, 0, 0);
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
		liaison = new GridLiaison(8, 5, 0, 2);
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
		liaison = new GridLiaison(9, 8, 6, 4);
		liaison.setCell(0, 0, GridSpace.WALL);
		for (int i = 5; i < 8; i++) liaison.setCell(0, i, GridSpace.WALL);
		for (int r = 7; r < 9; r++) for (int c = 6; c < 8; c++) liaison.setCell(r, c, GridSpace.WALL);
		liaison.setCell(1, 0, GridSpace.BUTTON);
		liaison.setCell(2, 4, GridSpace.BUTTON).setUp(false);
		liaison.setCell(5, 4, GridSpace.BUTTON_DOOR);
		liaison.setCell(5, 7, GridSpace.BUTTON_DOOR).setUp(false);
		liaison.setCell(1, 7, GridSpace.BUTTON).setColor(RED);
		liaison.setCell(3, 2, GridSpace.BUTTON).setColor(RED).setUp(false);
		liaison.setCell(2, 3, GridSpace.BUTTON_DOOR).setColor(RED);
		liaison.setCell(7, 3, GridSpace.BUTTON_DOOR).setColor(RED).setUp(false);
		liaison.setCell(8, 5, GridSpace.BUTTON).setColor(GREEN);
		liaison.setCell(5, 0, GridSpace.BUTTON).setColor(GREEN).setUp(false);
		liaison.setCell(7, 1, GridSpace.BUTTON_DOOR).setColor(GREEN);
		liaison.setCell(3, 1, GridSpace.BUTTON_DOOR).setColor(GREEN).setUp(false);
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
		liaison.setLaserSourceDirection(0, 1, DOWN).setLaserSourceOn(true);
		liaison.setLaserSourceDirection(0, 4, DOWN).setLaserSourceOn(true);
		liaison.setLaserSourceDirection(2, 7, LEFT).setLaserSourceOn(true);
		liaison.setLaserSourceDirection(5, 7, LEFT).setLaserSourceOn(true);
		liaison.setLaserSourceDirection(7, 0, RIGHT).setLaserSourceOn(true);
		liaison.setLaserSourceDirection(8, 3, UP).setLaserSourceOn(true);/* */
		
		
		/// Level 65 "In The Way"
		/*
		liaison = new GridLiaison(3, 4, 0, 0);
		liaison.setCell(1, 3, GridSpace.WALL);
		liaison.setCell(2, 3, GridSpace.WALL);
		liaison.setArt(2, 2, true);
		liaison.setCell(1, 0, GridSpace.MOVE_DOOR).setMovesStarted(4);/* */
		
		
		/// Level 66 "Right Place, Right Time"
		//*
		liaison = new GridLiaison(6, 4, 0, 0);
		for (int i = 0; i < 3; i++) liaison.setCell(i, 2, GridSpace.WALL);
		for (int i = 2; i < 5; i++) liaison.setCell(i, 1, GridSpace.WALL);
		liaison.setCell(2, 0, GridSpace.MOVE_DOOR).setMovesStarted(4);
		liaison.setCell(4, 0, GridSpace.MOVE_DOOR).setUp(true).setMovesStarted(4);
		liaison.setCell(5, 2, GridSpace.MOVE_DOOR).setMovesStarted(7);
		liaison.setCell(5, 3, GridSpace.MOVE_DOOR).setMovesStarted(7);
		liaison.setCell(4, 3, GridSpace.MOVE_DOOR).setMovesStarted(8);
		liaison.setCell(4, 2, GridSpace.MOVE_DOOR).setMovesStarted(9);
		liaison.setCell(3, 2, GridSpace.MOVE_DOOR).setMovesStarted(10);
		liaison.setCell(3, 3, GridSpace.MOVE_DOOR).setMovesStarted(11);
		liaison.setCell(1, 3, GridSpace.MOVE_DOOR).setUp(true).setMovesStarted(11);
		liaison.setArt(5, 0, true);
		liaison.setArt(0, 3, true);/* */
		
		
		/// Level 70 "Through Space And Time"
		/*
		liaison = new GridLiaison(6, 3, 0, 0);
		liaison.setCell(2, 1, GridSpace.TELEPORTER);
		liaison.setCell(4, 1, GridSpace.TELEPORTER);
		for (int i = 0; i < 3; i++) liaison.setCell(3, i, GridSpace.WALL);
		liaison.setArt(0, 1, true);/* */
		
		
		/// Level 91 "Tiny Tim"
		/*
		liaison = new GridLiaison(3, 2, 2, 0);
		liaison.setCell(0, 0, GridSpace.BUTTON_DOOR).setArt(true).setUp(false);
		liaison.setCell(0, 1, GridSpace.BUTTON).setUp(false).setColor(RED);
		liaison.setCell(1, 0, GridSpace.BUTTON).setArt(true);
		liaison.setCell(1, 1, GridSpace.BUTTON_DOOR).setArt(true).setColor(RED);
		liaison.setCell(2, 0, GridSpace.BUTTON).setUp(false);
		liaison.setCell(2, 1, GridSpace.BUTTON).setColor(RED);/* */
		
		
		/// Level 93 "High Stakes 2"
		/*
		liaison = new GridLiaison(7, 5, 0, 2);
		liaison.setCell(0, 0, GridSpace.BOOSTER).setDirection(DOWN);
		liaison.setCell(0, 4, GridSpace.BOOSTER).setDirection(DOWN);
		for (int i = 1; i < 4; i++) liaison.setCell(1, i, GridSpace.BOOSTER).setDirection(DOWN);
		liaison.setCell(3, 1, GridSpace.BOOSTER).setRotates(true).setDirection(DOWN);
		liaison.setCell(3, 2, GridSpace.BOOSTER).setRotates(true).setDirection(LEFT).setClockwise(false);
		liaison.setCell(3, 3, GridSpace.BOOSTER).setRotates(true).setDirection(DOWN);
		liaison.setCell(4, 1, GridSpace.BOOSTER).setRotates(true).setDirection(DOWN).setClockwise(false);
		liaison.setCell(4, 2, GridSpace.BOOSTER).setRotates(true).setDirection(LEFT).setClockwise(false);
		liaison.setCell(4, 3, GridSpace.BOOSTER).setRotates(true).setDirection(LEFT).setClockwise(false);
		liaison.setCell(5, 1, GridSpace.BOOSTER).setRotates(true).setDirection(UP);
		liaison.setCell(5, 2, GridSpace.BOOSTER).setRotates(true).setDirection(LEFT).setClockwise(false);
		liaison.setCell(5, 3, GridSpace.BOOSTER).setRotates(true).setDirection(RIGHT);
		liaison.setCell(5, 0, GridSpace.BOOSTER);
		liaison.setCell(5, 4, GridSpace.BOOSTER).setDirection(LEFT);
		liaison.setArt(6, 2, true);/* */
		
		
		/// Level 95 "Swerve"
		/*
		liaison = new GridLiaison(8, 6, 3, 2);
		liaison.setCell(0, 0, GridSpace.WALL);
		liaison.setCell(1, 0, GridSpace.WALL);
		liaison.setCell(6, 0, GridSpace.WALL);
		liaison.setCell(7, 0, GridSpace.WALL);
		for (int i = 3; i <= 4; i++) for (int j = 4; j <= 5; j++) liaison.setCell(i, j, GridSpace.WALL);
		liaison.setCell(2, 0, GridSpace.BOOSTER);
		liaison.setCell(5, 0, GridSpace.BOOSTER);
		liaison.setCell(2, 4, GridSpace.BOOSTER).setDirection(UP);
		liaison.setCell(5, 4, GridSpace.BOOSTER).setDirection(DOWN);
		liaison.setCell(2, 2, GridSpace.BUTTON).setUp(false);
		liaison.setCell(5, 2, GridSpace.BUTTON);
		liaison.setCell(2, 3, GridSpace.BUTTON_DOOR).setUp(false);
		liaison.setCell(5, 3, GridSpace.BUTTON_DOOR);
		liaison.setCell(3, 1, GridSpace.TELEPORTER);
		liaison.setCell(4, 1, GridSpace.TELEPORTER);
		liaison.setLaserSourceDirection(0, 3, DOWN).setLaserSourceOn(true);
		liaison.setLaserSourceDirection(2, 5, LEFT).setLaserSourceOn(true);
		liaison.setLaserSourceDirection(5, 5, LEFT).setLaserSourceOn(true);
		liaison.setArt(0, 2, true);
		liaison.setArt(1, 5, true);
		liaison.setArt(6, 1, true);
		liaison.setArt(7, 2, true);
		liaison.setArt(7, 4, true);/* */
		
		
		/// Level 102 "On Rails"
		/*
		liaison = new GridLiaison(3, 3, 0, 0);
		liaison.setCell(0, 0, GridSpace.SLIDE_DOOR).setRailDOWN_DOWN);
		liaison.setCell(1, 0, GridSpace.SLIDE_DOOR).setRailUP_DOWN);
		liaison.setCell(2, 0, GridSpace.SLIDE_DOOR).setRailUP_UP).setDirection(UP).setUp(true);
		liaison.setCell(0, 1, GridSpace.SLIDE_DOOR).setRailDOWN_DOWN).setDirection(DOWN).setUp(true).setArt(true);
		liaison.setCell(1, 1, GridSpace.SLIDE_DOOR).setRailUP_DOWN);
		liaison.setCell(2, 1, GridSpace.SLIDE_DOOR).setRailUP_UP).setArt(true);/* */
		
		
		/// Level 103 "Whirlpool"
		/*
		liaison = new GridLiaison(9, 5, 0, 2);
		for (int i = 0; i < 3; i++)
		{
			liaison.setCell(i, 0, GridSpace.WALL);
			liaison.setCell(i, 4, GridSpace.WALL);
		}
		liaison.setCell(8, 0, GridSpace.WALL);
		liaison.setCell(8, 4, GridSpace.WALL);
		liaison.setCell(2, 2, GridSpace.WALL);
		liaison.setCell(3, 2, GridSpace.BUTTON_DOOR).setArt(true);
		liaison.setCell(5, 3, GridSpace.BUTTON_DOOR);
		liaison.setCell(5, 0, GridSpace.BUTTON).setUp(true);
		liaison.setCell(5, 4, GridSpace.BUTTON).setUp(true);
		liaison.setCell(7, 2, GridSpace.BUTTON);
		liaison.setCell(4, 1, GridSpace.SLIDE_DOOR).setRailDOWN_RIGHT).setDirection(RIGHT).setUp(true);
		liaison.setCell(4, 2, GridSpace.SLIDE_DOOR).setRailLEFT_RIGHT);
		liaison.setCell(4, 3, GridSpace.SLIDE_DOOR).setRailDOWN_LEFT).setDirection(DOWN).setUp(true);
		liaison.setCell(5, 3, GridSpace.SLIDE_DOOR).setRailUP_DOWN);
		liaison.setCell(6, 3, GridSpace.SLIDE_DOOR).setRailUP_LEFT).setDirection(LEFT).setUp(true);
		liaison.setCell(6, 2, GridSpace.SLIDE_DOOR).setRailLEFT_RIGHT);
		liaison.setCell(6, 1, GridSpace.SLIDE_DOOR).setRailUP_RIGHT).setDirection(UP).setUp(true);
		liaison.setCell(5, 1, GridSpace.SLIDE_DOOR).setRailUP_DOWN);
		liaison.setCell(8, 1, GridSpace.SLIDE_DOOR).setRailRIGHT_RIGHT);
		liaison.setCell(8, 2, GridSpace.SLIDE_DOOR).setRailLEFT_RIGHT).setDirection(LEFT).setUp(true);
		liaison.setCell(8, 3, GridSpace.SLIDE_DOOR).setRailLEFT_LEFT);/* */
		
		
		/// Level 105 "Bear Trap"
		/*
		liaison = new GridLiaison(9, 7, 7, 0);
		liaison.setCell(0, 0, GridSpace.WALL);
		liaison.setCell(4, 0, GridSpace.WALL);
		liaison.setCell(5, 0, GridSpace.WALL);
		liaison.setCell(4, 1, GridSpace.WALL);
		liaison.setCell(0, 4, GridSpace.WALL);
		liaison.setCell(0, 5, GridSpace.WALL);
		liaison.setCell(0, 6, GridSpace.WALL);
		liaison.setCell(1, 6, GridSpace.WALL);
		liaison.setCell(3, 3, GridSpace.WALL);
		liaison.setCell(4, 3, GridSpace.WALL);
		liaison.setCell(4, 5, GridSpace.WALL);
		liaison.setCell(8, 0, GridSpace.WALL);
		liaison.setCell(8, 1, GridSpace.WALL);
		liaison.setCell(8, 2, GridSpace.WALL);
		liaison.setCell(7, 2, GridSpace.WALL);
		liaison.setCell(8, 6, GridSpace.WALL);
		liaison.setCell(2, 0, GridSpace.BUTTON).setArt(true);
		liaison.setCell(3, 5, GridSpace.BUTTON).setUp(true);
		liaison.setCell(5, 5, GridSpace.BUTTON).setArt(true);
		liaison.setCell(6, 3, GridSpace.BUTTON).setUp(true);
		liaison.setCell(1, 2, GridSpace.BUTTON_DOOR);
		liaison.setCell(4, 2, GridSpace.BUTTON_DOOR).setUp(true);
		liaison.setCell(4, 4, GridSpace.BUTTON_DOOR);
		liaison.setCell(4, 6, GridSpace.BUTTON_DOOR).setUp(true);
		liaison.setCell(7, 3, GridSpace.BUTTON_DOOR).setUp(true);
		liaison.setCell(7, 4, GridSpace.BUTTON_DOOR);
		liaison.setCell(7, 5, GridSpace.BUTTON_DOOR).setUp(true);
		liaison.setArt(2, 6, true);
		liaison.setArt(8, 3, true);
		liaison.setLaserSourceDirection(6, 2, UP).setLaserSourceOn(true);
		liaison.setLaserSourceDirection(1, 4, DOWN).setLaserSourceOn(true);/* */
		
		
		/// Level 108 "Break Out"
		/*
		liaison = new GridLiaison(6, 5, 5, 3);
		liaison.setCell(0, 1, GridSpace.SLIDE_DOOR).setRailDOWN_DOWN);
		liaison.setCell(5, 1, GridSpace.SLIDE_DOOR).setRailUP_UP).setDirection(UP).setUp(true);
		for (int i = 1; i < 5; i++) liaison.setCell(i, 1, GridSpace.SLIDE_DOOR).setRailUP_DOWN);
		for (int i = 0; i < 6; i++) liaison.setArt(i, 4, true);
		liaison.setLaserSourceDirection(5, 0, UP).setLaserSourceOn(true).setLaserSourceBlue(true);/* */
		
		
		/// Level 115 "Three Times"
		/*
		liaison = new GridLiaison(9, 6, 0, 0);
		for (int i = 4; i <= 8; i++) liaison.setCell(i, 0, GridSpace.WALL);
		for (int i = 1; i <= 3; i++) liaison.setCell(4, i, GridSpace.WALL);
		for (int i = 0; i <= 4; i++) for (int j = 4; j <= 5; j++) liaison.setCell(i, j, GridSpace.WALL);
		liaison.setCell(0, 1, GridSpace.TELEPORTER).setColor(GREEN);
		liaison.setCell(0, 2, GridSpace.TELEPORTER).setColor(RED);
		liaison.setCell(1, 2, GridSpace.TELEPORTER);
		liaison.setCell(8, 2, GridSpace.TELEPORTER).setColor(RED);
		liaison.setCell(8, 3, GridSpace.TELEPORTER).setColor(GREEN);
		liaison.setCell(7, 2, GridSpace.TELEPORTER);
		liaison.setLaserSourceDirection(5, 4, DOWN).setLaserSourceOn(true);
		liaison.setArt(6, 5, true);
		liaison.setArt(7, 5, true);/* */
		
		
		/// Level 122 "Art Factory"
		/*
		liaison = new GridLiaison(10, 5, 0, 2);
		liaison.setCell(2, 1, GridSpace.WALL);
		for (int i = 2; i <= 4; i++) liaison.setCell(i, 3, GridSpace.WALL);
		for (int i = 4; i <= 6; i++) liaison.setCell(i, 1, GridSpace.WALL);
		for (int i = 6; i <= 7; i++) liaison.setCell(i, 2, GridSpace.WALL);
		for (int i = 2; i <= 7; i++) for (int j = 0; j < 5; j += 4) liaison.setCell(i, j, GridSpace.SLIDE_DOOR).setRailUP_DOWN);
		for (int i = 1; i <= 3; i++) for (int j = 1; j < 9; j += 7) liaison.setCell(j, i, GridSpace.SLIDE_DOOR).setRailLEFT_RIGHT);
		liaison.setCell(1, 0, GridSpace.SLIDE_DOOR).setRailDOWN_RIGHT);
		liaison.setCell(1, 4, GridSpace.SLIDE_DOOR).setRailDOWN_LEFT);
		liaison.setCell(8, 0, GridSpace.SLIDE_DOOR).setRailUP_RIGHT);
		liaison.setCell(8, 4, GridSpace.SLIDE_DOOR).setRailUP_LEFT);
		liaison.setDirection(1, 0, DOWN).setUp(true);
		liaison.setDirection(1, 3, LEFT).setUp(true);
		liaison.setDirection(4, 0, DOWN).setUp(true);
		liaison.setDirection(5, 4, UP).setUp(true);
		liaison.setDirection(8, 1, RIGHT).setUp(true);
		liaison.setDirection(8, 4, UP).setUp(true);
		liaison.setLaserSourceDirection(2, 4, LEFT);
		liaison.setLaserSourceDirection(3, 0, RIGHT);
		liaison.setLaserSourceDirection(5, 4, LEFT).setLaserSourceOn(true);
		liaison.setLaserSourceDirection(6, 0, RIGHT).setLaserSourceOn(true);
		liaison.setLaserSourceDirection(9, 4, LEFT).setLaserSourceBlue(true).setLaserSourceOn(true);
		liaison.setArt(0, 1, true);
		liaison.setArt(0, 3, true);
		liaison.setArt(2, 2, true);
		liaison.setArt(3, 4, true);
		liaison.setArt(5, 0, true);
		liaison.setArt(5, 2, true);
		liaison.setArt(5, 3, true);
		liaison.setArt(8, 0, true);/* */
		
		//*
		liaison.updateLasers();
		System.out.println(liaison.copyGrid());/* */
		
		//*
		Solver solver = new Solver(liaison, 12);
		
		long start = System.currentTimeMillis();
		Direction[] solution = solver.solve(0);
		long stop = System.currentTimeMillis();
		
		System.out.println(new StringBuilder("Solved in ").append((double)(stop - start) / 1000.0D).append(" seconds"));
		System.out.println(solver.sizes());
		System.out.println(Solver.solution(solution));/* */
	}
}
