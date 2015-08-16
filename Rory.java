public class Rory
{
	private int x;
	private int y;
	
	public static final int UP         = 0b0100;
	public static final int DOWN       = 0b1100;
	public static final int LEFT       = 0b0001;
	public static final int RIGHT      = 0b0011;
	public static final int UP_LEFT    = UP + LEFT;
	public static final int UP_RIGHT   = UP + RIGHT;
	public static final int DOWN_LEFT  = DOWN + LEFT;
	public static final int DOWN_RIGHT = DOWN + RIGHT;
	
	public static final int VERT  = 0b1100;
	public static final int HORIZ = 0b0011;
}
