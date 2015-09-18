package gridspace;

import solver.GameState;
import utility.Colors;

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
				// If the space in question is a teleporter and Rory is in it, then it's not the teleporter we're teleporting him to
				if (gameBoard.get(x, y).ID() == this.ID() && !(x == gameBoard.roryRow() && y == gameBoard.roryCol()))
				{
					state.teleportRory(x, y);
					break;
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
