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
			
			int dr = this.heading.verticalChange();
			int dc = this.heading.horizontalChange();
			
			int r = row + dr;
			int c = col + dc;
			GridSpace target = gameboard.get(r, c);
			
			if (target instanceof SlideDoor)
			{
				SlideDoor sd = (SlideDoor)target;
				sd.up = true;
				
				Direction first = sd.rail.getFirst();
				sd.heading = (first == this.heading.opposite()) ? sd.rail.getSecond() : first;
				
				if (state.getRoryRow() == r && state.getRoryCol() == c) state.teleportRory(r + dr, c + dc);
			}
		}
	}
	
	GridSpace makeCopy()
	{
		SlideDoor sd = new SlideDoor();
		sd.rail = this.rail;
		sd.heading = this.heading;
		sd.up = this.up;
		return sd;
	}
}