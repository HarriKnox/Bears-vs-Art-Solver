package gridspace;

final class Space extends GridSpace
{
	protected int ID() { return 2; }
	protected int metadataHash() { return 0; }
	
	public boolean isSolid() { return false; }
	public GridSpace copy() { return super.copy(new Space()); }
	
	public String toString() { return super.toString('-'); }
}
