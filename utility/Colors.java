package utility;

public enum Color
{
	RED  (1),
	GREEN(2),
	BLUE (3);
	
	public final int hash;
	
	private Color(int hash) { this.hash = hash; }
}
