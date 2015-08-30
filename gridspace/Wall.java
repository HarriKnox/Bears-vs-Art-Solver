package gridspace;

public final class Wall extends GridSpace
{
	int ID() { return 1; }
	int metadataHash() { return 0; }
	
	public boolean isSolid() { return true; }
	public GridSpace copy() { return this; }
	
	public String toString() { return super.toString('W'); }
}
