package utility;

public enum Color
{
	RED  (0),
	GREEN(1),
	BLUE (2);
	
	public final int hash;
	
	private Color(int hash) { this.hash = hash; }
}
