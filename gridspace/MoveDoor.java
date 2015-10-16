package gridspace;

import solver.GameState;

final class MoveDoor extends GridSpace
{
	MoveDoor() {}
	
	boolean up = false;
	boolean toggle = false;
	int movesRemaining;
	int movesStarted;
	
	
	int metadataHash() { return (this.movesStarted << 11) + (this.movesRemaining << 2) + (b2i(toggle) << 1) + b2i(up); }
	
	GridSpace makeCopy()
	{
		MoveDoor m = new MoveDoor();
		m.up = this.up;
		m.toggle = this.toggle;
		m.movesRemaining = this.movesRemaining;
		m.movesStarted = this.movesStarted;
		return m;
	}
	
	
	public boolean isSolid() { return this.up; }
	
	
	public void endOfMove(GameState state)
	{
		if (this.movesRemaining > 0)
		{
			this.movesRemaining--;
			if (this.movesRemaining <= 0)
			{
				this.up = !this.up;
				if (this.toggle)
				{
					this.movesRemaining = this.movesStarted;
				}
			}
		}
		super.endOfMove(state);
	}
	
	
	public String toString() { return (this.toggle ? "\033[1;35m" : "\033[1;33m") + super.toString(this.up ? 'D' : 'd') + "\033[0m"; }
}
