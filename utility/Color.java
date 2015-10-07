package utility;

public enum Color
{
	RED,
	GREEN,
	BLUE;
	
	public final int hash;
	
	private Color() { this.hash = this.ordinal(); }
}
