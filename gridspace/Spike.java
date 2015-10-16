package gridspace;

import solver.GameState;

final class Spike extends GridSpace
{
	Spike() {}
	
	boolean up = false;
	
	int metadataHash() { return b2i(this.up); }
	
	GridSpace makeCopy()
	{
		Spike s = new Spike();
		s.up = this.up;
		return s;
	}
	
	
	public void checkHazard(GameState state) { if (this.up) state.kill(); else super.checkHazard(state); }
	
	public void endOfMove(GameState state) { this.up = !this.up; super.endOfMove(state); }
	
	
	public String toString() { return super.toString(this.up ? 'S' : 's'); }
}
