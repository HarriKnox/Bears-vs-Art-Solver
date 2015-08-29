package gridspace;

import solver.GameState;

public final class Spike extends GridSpace
{
	private boolean up = false;
	public boolean setUp(boolean up) { return this.up = up; }
	
	protected int ID() { return 2; }
	protected int metadataHash() { return b2i(this.up); }
	
	public String toString() { return super.toString(this.up ? 'S' : 's'); }
	
	public void checkHazard(GameState state) { if (this.up) state.kill(); else super.checkHazard(state); }
	public void endOfMove(GameState state) { this.up = !this.up; }
	
	public GridSpace copy()
	{
		Spike s = new Spike();
		s.up = this.up;
		return super.copy(s);
	}
}
