package gridspace;

import solver.GameState;

import utility.Color;

final class Button extends GridSpace
{
	Button() {}
	
	boolean up = false;
	Color color = Color.BLUE;
	
	
	int metadataHash() { return (this.color.hash << 1) + b2i(up); }
	
	GridSpace makeCopy()
	{
		Button b = new Button();
		b.up = this.up;
		b.color = this.color;
		return b;
	}
	
	
	public void passThrough(GameState state)
	{
		if (this.up)
		{
			for (GridSpace gs : state.getGameBoard())
			{
				if (gs instanceof Button)
				{
					Button b = (Button)gs;
					if (b.color == this.color) b.up = !b.up;
				}
				if (gs instanceof ButtonDoor)
				{
					ButtonDoor b = (ButtonDoor)gs;
					if (b.color == this.color) b.up = !b.up;
				}
			}
		}
	}
	
	
	public String toString() { return (this.color == Color.BLUE ? "\033[1;34m" : (this.color == Color.GREEN ? "\033[1;32m" : "\033[1;31m")) + super.toString(this.up ? 'B' : 'b') + "\033[0m"; }
}
