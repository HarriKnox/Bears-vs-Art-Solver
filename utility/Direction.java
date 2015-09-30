package utility;

public enum Direction
{
	NONE      (0b1010),
	UP        (0b0110),
	DOWN      (0b1110),
	LEFT      (0b1001),
	RIGHT     (0b1011),
	UP_LEFT   (0b0101),
	UP_RIGHT  (0b0111),
	DOWN_LEFT (0b1101),
	DOWN_RIGHT(0b1111);
	
	public final int hash;
	private Direction(int hash) { this.hash = hash; }
	
	public static final Direction[] LIST = {UP, DOWN, LEFT, RIGHT, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT};
	private static final Direction[] MAP = new Direction[16];
	static
	{
		MAP[NONE.hash]       = NONE;
		MAP[UP.hash]         = UP;
		MAP[DOWN.hash]       = DOWN;
		MAP[LEFT.hash]       = LEFT;
		MAP[RIGHT.hash]      = RIGHT;
		MAP[UP_LEFT.hash]    = UP_LEFT;
		MAP[UP_RIGHT.hash]   = UP_RIGHT;
		MAP[DOWN_LEFT.hash]  = DOWN_LEFT;
		MAP[DOWN_RIGHT.hash] = DOWN_RIGHT;
	}
	
	private static final int VERT  = 0b1100;
	private static final int HORIZ = 0b0011;
	
	public int verticalChange  () { return ((this.hash & VERT) >> 2) - 2; }
	public int horizontalChange() { return (this.hash & HORIZ) - 2; }
	
	public boolean isVertical  () { return this.verticalChange() != 0; }
	public boolean isHorizontal() { return this.horizontalChange() != 0; }
	public boolean isDiagonal  () { return this.isVertical() && this.isHorizontal(); }
	public boolean isCardinal  () { return this.isVertical() != this.isHorizontal(); }
	
	public Direction verticalComponent  () { return MAP[(this.hash & VERT) | 0b0010]; }
	public Direction horizontalComponent() { return MAP[(this.hash & HORIZ) | 0b1000]; }
	
	public Direction opposite() { return MAP[0b10100 - this.hash]; }
}
