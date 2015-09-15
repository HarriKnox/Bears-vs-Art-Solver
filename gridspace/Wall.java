package gridspace;

public final class Wall extends GridSpace
{
	Wall() {}
	
	public boolean isSolid() { return true; }
	GridSpace makeCopy() { return this; }
	
	public String toString() { return super.toString('W'); }
}
