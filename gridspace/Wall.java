package gridspace;

import utility.Directions;

final class Wall extends GridSpace
{
	public boolean setArt(boolean has) { return false; }
	public boolean setLaser(boolean las) { return false; }
	public int setLaserSource(int dir) { return Directions.NONE; }
	public boolean setLaserSourceOn(boolean on) { return false; }
	
	protected int ID() { return 1; }
	protected int metadataHash() { return 0; }
	
	public boolean isSolid() { return true; }
	public GridSpace copy() { return this; }
	
	public String toString() { return super.toString('W'); }
}
