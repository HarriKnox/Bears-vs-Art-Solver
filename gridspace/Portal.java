package gridspace;

import solver.GameState;

import utility.Color;
import utility.Grid;

final class Portal extends GridSpace
{
	Portal() {}
	
	Color color = Color.BLUE;
	
	
	int metadataHash() { return this.color.hash; }
	
	GridSpace makeCopy()
	{
		Portal t = new Portal();
		t.color = this.color;
		return t;
	}
	
	
	public void passThrough(GameState state)
	{
		int row = state.getRoryRow();
		int col = state.getRoryCol();
		
		Grid<GridSpace> gameBoard = state.getGameBoard();
		
		for (int x = 0, rows = gameBoard.rows(); x < rows; x++)
		{
			for (int y = 0, cols = gameBoard.cols(); y < cols; y++)
			{
				if (x != row || y != col)
				{
					GridSpace t = gameBoard.get(x, y);
					// If the space in question is a portal and its color is the same as this color and Rory is not in it, then it's probably the portal we're teleporting him to
					if (t.ID() == this.ID() && ((Portal)t).color == this.color)
					{
						state.teleportRory(x, y);
						return;
					}
				}
			}
		}
	}
	
	
	public String toString() { return (this.color == Color.BLUE ? "\033[1;34m" : (this.color == Color.GREEN ? "\033[1;32m" : "\033[1;31m")) + "T\033[0m"; }
}
