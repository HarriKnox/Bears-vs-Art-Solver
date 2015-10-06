package utility;

public enum RailDirection
{
	UP_UP      (Direction.UP,    Direction.UP),
	UP_DOWN    (Direction.UP,    Direction.DOWN),
	UP_LEFT    (Direction.UP,    Direction.LEFT),
	UP_RIGHT   (Direction.UP,    Direction.RIGHT),
	DOWN_DOWN  (Direction.DOWN,  Direction.DOWN),
	DOWN_LEFT  (Direction.DOWN,  Direction.LEFT),
	DOWN_RIGHT (Direction.DOWN,  Direction.RIGHT),
	LEFT_LEFT  (Direction.LEFT,  Direction.LEFT),
	LEFT_RIGHT (Direction.LEFT,  Direction.RIGHT),
	RIGHT_RIGHT(Direction.RIGHT, Direction.RIGHT);
	
	
	public final int hash;
	private RailDirection(Direction first, Direction last) { this.hash = getHash(first, last); }
	
	
	private static final int UP = 0b10;
	private static final int DOWN = 0b11;
	private static final int LEFT = 0b00;
	private static final int RIGHT = 0b01;
	
	
	public static RailDirection get(Direction first, Direction last) { return MAP[add(convert(first), convert(last))]; }
	
	
	private static int convert(Direction dir) { return dir.isVertical() ? (dir.verticalComponent() == Direction.UP ? UP : DOWN) : (dir.horizontalComponent() == Direction.LEFT ? LEFT : RIGHT); }
	private static Direction unconvert(int d) { return (d == UP ? Direction.UP : (d == DOWN ? Direction.DOWN : (d == LEFT ? Direction.LEFT : Direction.RIGHT))); }
	private static int add(int first, int last) { return (first << 2) + last; }
	private static int getHash(Direction first, Direction last) { return add(convert(first), convert(last)); }
	
	
	public boolean contains(Direction dir)
	{
		int converted = convert(dir);
		return ((this.hash >> 2) == converted) || ((this.hash & 0b11) == converted);
	}
	
	public Direction getFirst() { return unconvert(this.hash >> 2); }
	public Direction getLast() { return unconvert(this.hash & 0b11); }
	
	
	private static final RailDirection[] MAP = new RailDirection[16];
	static
	{
		MAP[add(UP, UP)]       = UP_UP;
		MAP[add(UP, DOWN)]     = UP_DOWN;
		MAP[add(UP, LEFT)]     = UP_LEFT;
		MAP[add(UP, RIGHT)]    = UP_RIGHT;
		MAP[add(DOWN, UP)]     = UP_DOWN;
		MAP[add(DOWN, DOWN)]   = DOWN_DOWN;
		MAP[add(DOWN, LEFT)]   = DOWN_LEFT;
		MAP[add(DOWN, RIGHT)]  = DOWN_RIGHT;
		MAP[add(LEFT, UP)]     = UP_LEFT;
		MAP[add(LEFT, DOWN)]   = DOWN_LEFT;
		MAP[add(LEFT, LEFT)]   = LEFT_LEFT;
		MAP[add(LEFT, RIGHT)]  = LEFT_RIGHT;
		MAP[add(RIGHT, UP)]    = UP_RIGHT;
		MAP[add(RIGHT, DOWN)]  = DOWN_RIGHT;
		MAP[add(RIGHT, LEFT)]  = LEFT_RIGHT;
		MAP[add(RIGHT, RIGHT)] = RIGHT_RIGHT;
	}
}
