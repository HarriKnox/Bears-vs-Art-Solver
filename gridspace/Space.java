package gridspace;

public final class Space extends GridSpace
{
	GridSpace makeCopy() { return new Space(); }
	
	public String toString() { return super.toString('-'); }
}