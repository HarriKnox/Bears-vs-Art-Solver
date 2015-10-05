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
	
	
	public void endOfMove(GameState state)
	{
		if (this.up)
		{
			this.up = false;
			
			Grid<GridSpace> gameboard = state.getGameBoard();
			
			for (int x = 0, rows = gameboard.rows(); x < rows; x++)
			{
				for (int y = 0, cols = gameboard.cols(); y < cols; y++)
				{
					if (gameboard.get(x, y) == this)
					{
						
}
