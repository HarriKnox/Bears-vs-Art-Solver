package gridspace;

public final class Space extends GridSpace
{
	Space() {}
	
	int metadataHash() { return 0; }
	GridSpace makeCopy() { return new Space(); }
	
	public String toString() { return super.toString('-'); }
}
