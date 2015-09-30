package utility;

public enum RailDirection
{
	UP_UP      (UP, UP),
	UP_DOWN    (UP, DOWN),
	UP_LEFT    (UP, LEFT),
	UP_RIGHT   (UP, RIGHT),
	DOWN_DOWN  (DOWN, DOWN),
	DOWN_LEFT  (DOWN, LEFT),
	DOWN_RIGHT (DOWN, RIGHT),
	LEFT_LEFT  (LEFT, LEFT),
	LEFT_RIGHT (LEFT, RIGHT),
	RIGHT_RIGHT(RIGHT, RIGHT);
	
	private static final int LEFT = 0b00;
	private static final int RIGHT = 0b01;
	private static final int UP = 0b10;
	private static final int DOWN = 0b11;
	
	private static final RailDirection[] MAP = new RailDirection[16];
	static
	{
		MAP[UP_UP]       = UP_UP;
		MAP[UP_DOWN]     = UP_DOWN;
		MAP[UP_LEFT]     = UP_LEFT;
		MAP[UP_RIGHT]    = UP_RIGHT;
		MAP[DOWN_DOWN]   = DOWN_DOWN;
		MAP[DOWN_LEFT]   = DOWN_LEFT;
		MAP[DOWN_RIGHT]  = DOWN_RIGHT;
		MAP[LEFT_LEFT]   = LEFT_LEFT;
		MAP[LEFT_RIGHT]  = LEFT_RIGHT;
		MAP[RIGHT_RIGHT] = RIGHT_RIGHT;
	}
	
	public final int hash;
	
	private RailDirection(int first, int last) { this.hash = (first << 2) + last; }
}
