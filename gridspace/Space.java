package gridspace;

public final class Space extends GridSpace
{
	int ID() { return 2; }
	int metadataHash() { return 0; }
	
	public GridSpace copy() { return super.copy(new Space()); }
	
	public String toString() { return super.toString('-'); }
}
