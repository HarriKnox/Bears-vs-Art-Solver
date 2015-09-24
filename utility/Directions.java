package utility;

public final class Directions
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
	
	private int hash;
	private Directions(int hash)
	{
		this.hash = hash;
	}
	
	public static final Directions[] LIST = {UP, DOWN, LEFT, RIGHT, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT};
	public static final String[] NAMES = new String[16];
	static
	{
		NAMES[NONE.hash]       = "NONE";
		NAMES[UP.hash]         = "UP";
		NAMES[DOWN.hash]       = "DOWN";
		NAMES[LEFT.hash]       = "LEFT";
		NAMES[RIGHT.hash]      = "RIGHT";
		NAMES[UP_LEFT.hash]    = "UP_LEFT";
		NAMES[UP_RIGHT.hash]   = "UP_RIGHT";
		NAMES[DOWN_LEFT.hash]  = "DOWN_LEFT";
		NAMES[DOWN_RIGHT.hash] = "DOWN_RIGHT";
	}
	
	private static final int VERT  = 0b1100;
	private static final int HORIZ = 0b0011;
	
	public static int verticalChange  (Directions dir) { return (dir.hash ? ((dir.hash & VERT) >> 2) - 2 : 0); }
	public static int horizontalChange(Directions dir) { return (dir.hash ? (dir.hash & HORIZ) - 2 : 0); }
	
	public static boolean isVertical  (Directions dir) { return verticalChange(dir.hash) != 0; }
	public static boolean isHorizontal(Directions dir) { return horizontalChange(dir.hash) != 0; }
	public static boolean isDiagonal  (Directions dir) { return isVertical(dir.hash) && isHorizontal(dir.hash); }
	public static boolean isCardinal  (Directions dir) { return isVertical(dir.hash) != isHorizontal(dir.hash); }
	
	public static int verticalComponent  (Directions dir) { return (dir.hash & VERT) | 0b0010; }
	public static int horizontalComponent(Directions dir) { return (dir.hash & HORIZ) | 0b1000; }
	
	public static int opposite (Directions dir) { return 0b10100 - dir.hash; }
	public static boolean isDir(Directions dir) { return dir.hash <= 0b1111 && NAMES[dir.hash] != null; }
}
