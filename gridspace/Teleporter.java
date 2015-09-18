package gridspace;

import solver.GameState;
import utility.*;

final class Teleporter extends GridSpace
{
	Teleporter() {}
	
	int color = Colors.BLUE;
	
	int metadataHash() { return this.color; }
	
	public void passThrough(GameState state)
	{
		Grid<GridSpace> gameBoard = state.getGameBoard();
		
		for (int x = 0, rows = gameBoard.rows(); x < rows; x++)
		{
			for (int y = 0, cols = gameBoard.cols(); y < cols; y++)
			{
				GridSpace t = gameBoard.get(x, y);
				// If the space in question is a teleporter and its color is the same as this color and Rory is not in it, then it's probably the teleporter we're teleporting him to
				if (t.ID() == this.ID() && ((Teleporter)t).color == this.color && !(x == state.getRoryRow() && y == state.getRoryCol()))
				{
					state.teleportRory(x, y);
					return;
				}
			}
		}
	}
	
	GridSpace makeCopy()
	{
		Teleporter t = new Teleporter();
		t.color = this.color;
		return t;
	}
	
	public String toString() { return (this.color == Colors.BLUE ? "\033[1;34m" : (this.color == Colors.GREEN ? "\033[1;32m" : "\033[1;31m")) + "T\033[0m"; }
}
