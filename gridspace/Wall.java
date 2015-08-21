package gridspace;

final class Wall extends GridSpace
{
	public boolean setArt() { return false; }
	
	protected int ID() { return 1; }
	protected int metadataHash() { return 0; }
	
	public boolean isSolid() { return true; }
	public GridSpace copy() { return this; }
	
	public String toString() { return super.toString('W'); }
}
