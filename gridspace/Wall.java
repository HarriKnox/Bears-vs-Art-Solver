package gridspace;

final class Wall extends GridSpace
{
	public boolean setArt(boolean has) { return false; }
	public boolean setLaser(boolean las) { return false; }
	
	protected int ID() { return 1; }
	protected int metadataHash() { return 0; }
	
	public boolean isSolid() { return true; }
	public GridSpace copy() { return this; }
	
	public String toString() { return super.toString('W'); }
}
