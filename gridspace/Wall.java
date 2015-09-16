package gridspace;

public final class Wall extends GridSpace
{
	Wall() {}
	
	int metadataHash() { return 0; }
	public boolean isSolid() { return true; }
	GridSpace makeCopy() { return this; }
	
	public String toString() { return super.toString('W'); }
}
