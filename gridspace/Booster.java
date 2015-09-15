package gridspace;

import solver.GameState;
import utility.*

final class Booster extends GridSpace
{
	int direction = Directions.RIGHT;
	boolean rotates = false;
	boolean clockwise = true;
	
	int metadataHash()
	{
		return (this.direction << 2) +
		       (this.rotates ? (b2i(this.clockwise) << 1) + 1 : 0);
		// I shortcutted right here since this.rotates is already true, so it would always be a 1 in that spot.
	}
	
	public String toString()
	{
		return super.toString('b');
	}

	public void passThrough(GameState state)
	{
		state.changeDirection(this.direction);
	}
	
	private int rotate()
	{
		if (this.rotates)
		{
			if (this.direction == (this.clockwise ? Directions.RIGHT : Directions.LEFT)) return Directions.DOWN;
			if (this.direction == (this.clockwise ? Directions.LEFT : Directions.RIGHT)) return Directions.UP;
			if (this.direction == (this.clockwise ? Directions.UP : Directions.DOWN)) return Directions.RIGHT;
			if (this.direction == (this.clockwise ? Directions.DOWN : Directions.UP)) return Directions.LEFT;
		}
		return this.direction;
	}
}
