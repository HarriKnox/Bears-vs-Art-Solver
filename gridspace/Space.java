package gridspace;

public final class Space extends GridSpace
{
	Space() {}
	
	GridSpace makeCopy() { return new Space(); }
	
	public String toString() { return super.toString('-'); }
}
