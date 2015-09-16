package gridspace;

import solver.GameState;
import utility.*;

final class Button extends GridSpace
{
	Button() {}
	
	boolean up = true;
	int color = Colors.RED;
	
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
	
	GridSpace makeCopy()
	{
		Button b = new Button();
		b.up = this.up;
		b.color = this.color;
		return b;
	}
}
