package gridspace;

import solver.GameState;

public final class Spike extends GridSpace
{
	Spike() {}
	
	boolean up = false;
	
	int metadataHash() { return b2i(this.up); }
	
	public String toString() { return super.toString(this.up ? 'S' : 's'); }
	
	public void checkHazard(GameState state) { if (this.up) state.kill(); else super.checkHazard(state); }
	public void endOfMove(GameState state, int row, int col) { this.up = !this.up; }
	
	GridSpace makeCopy()
	{
		Spike s = new Spike();
		s.up = this.up;
		return s;
	}
}
