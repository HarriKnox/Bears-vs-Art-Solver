package gridspace;

final class Wall extends GridSpace
{
	Wall() {}
	
	
	int metadataHash() { return 0; }
	
	GridSpace makeCopy() { return this; }
	
	
	public boolean isSolid() { return true; }
	
	
	public String toString() { return super.toString('W'); }
}
