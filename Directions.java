public final class Directions
{
	public static final int NONE       = 0b0000;
	public static final int UP         = 0b0100;
	public static final int DOWN       = 0b1100;
	public static final int LEFT       = 0b0001;
	public static final int RIGHT      = 0b0011;
	public static final int UP_LEFT    = UP + LEFT;
	public static final int UP_RIGHT   = UP + RIGHT;
	public static final int DOWN_LEFT  = DOWN + LEFT;
	public static final int DOWN_RIGHT = DOWN + RIGHT;
	
	private static final int VERT  = 0b1100;
	private static final int HORIZ = 0b0011;
	private static final int DIAG  = 0b0101;
	
	public static boolean isVertical(int dir) { return (dir & VERT) != 0; }
	public static boolean isHorizontal(int dir) { return (dir & HORIZ) != 0; }
	public static boolean isDiagonal(int dir) { return (dir & DIAG) == DIAG; }
	
	public static int verticalChange(int dir) { return ((dir & VERT) >> 2) - 2; }
	public static int horizontalChange(int dir) { return (dir & HORIZ) - 2; }
}
