package utility;

public final class Directions
{
	public static final int NONE       = 0b1010;
	public static final int UP         = 0b0110;
	public static final int DOWN       = 0b1110;
	public static final int LEFT       = 0b1001;
	public static final int RIGHT      = 0b1011;
	public static final int UP_LEFT    = 0b0101;
	public static final int UP_RIGHT   = 0b0111;
	public static final int DOWN_LEFT  = 0b1101;
	public static final int DOWN_RIGHT = 0b1111;
	
	public static final int[] LIST = {UP, DOWN, LEFT, RIGHT, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT};
	public static final String[] NAMES = new String[16];
	static
	{
		NAMES[NONE]       = "NONE";
		NAMES[UP]         = "UP";
		NAMES[DOWN]       = "DOWN";
		NAMES[LEFT]       = "LEFT";
		NAMES[RIGHT]      = "RIGHT";
		NAMES[UP_LEFT]    = "UP_LEFT";
		NAMES[UP_RIGHT]   = "UP_RIGHT";
		NAMES[DOWN_LEFT]  = "DOWN_LEFT";
		NAMES[DOWN_RIGHT] = "DOWN_RIGHT";
	}
	
	private static final int VERT  = 0b1100;
	private static final int HORIZ = 0b0011;
	private static final int DIAG  = 0b0101;
	
	public static boolean isVertical  (int dir) { return (dir & VERT)  != 0; }
	public static boolean isHorizontal(int dir) { return (dir & HORIZ) != 0; }
	public static boolean isDiagonal  (int dir) { return (dir & DIAG)  != 0; }
	
	public static int verticalChange  (int dir) { return ((dir & VERT) >> 2) - 2; }
	public static int horizontalChange(int dir) { return (dir & HORIZ) - 2; }
	
	public static int verticalComponent  (int dir) { return (dir & VERT) | 0b0010; }
	public static int horizontalComponent(int dir) { return (dir & HORIZ) | 0b1000; }
	
	public static int opposite(int dir) { return 0b10100 - dir; }
	public static boolean isDir(int dir) { return dir <= 0b1111 && NAMES[dir] != null && dir != NONE; }
}
