package gridspace;

import solver.GameState;
import utility.*;

final class SlideDoor extends GridSpace
{
	SlideDoor() {}
	
	RailDirection rail = RailDirection.LEFT_RIGHT;
	Direction heading = Direction.RIGHT;
	boolean up = true;
	
	
	int metadataHash() { return (this.rail.hash << 5) + (this.heading.hash << 1) + b2i(this.up); }
	
	public boolean isSolid() { return this.up; }
	
	
	public void endOfMove(GameState state, int row, int col)
	{
		if (this.up)
		{
			this.up = false;
			
			Grid<GridSpace> gameboard = state.getGameBoard();
			
	
	GridSpace makeCopy()
	{
		SlideDoor sd = new SlideDoor();
		sd.rail = this.rail;
		sd.heading = this.heading;
		sd.up = this.up;
		return sd;
	}
}
