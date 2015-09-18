package gridspace;

import solver.GameState;
import utility.*;

final class Booster extends GridSpace
{
	Booster() {}
	
	int direction = Directions.RIGHT;
	boolean rotates = false;
	boolean clockwise = true;
	
	int metadataHash()
	{
		return (this.direction << 2) +
		       (this.rotates ? (b2i(this.clockwise) << 1) + 1 : 0);
	}
	
	public String toString()
	{
		return (this.rotates ? (this.clockwise ? "\033[1;33m" : "\033[1;34m") : "") + super.toString(this.direction == Directions.UP ? '^' : (this.direction == Directions.DOWN ? 'v' : (this.direction == Directions.LEFT ? '<' : '>'))) + "\033[0m";
	}

	public void passThrough(GameState state)
	{
		state.changeDirection(this.direction);
		this.direction = this.getDirection();
	}
	
	GridSpace makeCopy()
	{
		Booster b = new Booster();
		b.direction = this.direction;
		if (this.rotates)
		{
			b.rotates = true;
			b.clockwise = this.clockwise;
		}
		return b;
	}
	
	private int getDirection()
	{
		if (this.rotates)
		{
			if (this.direction == (this.clockwise ? Directions.RIGHT : Directions.LEFT))  return Directions.DOWN;
			if (this.direction == (this.clockwise ? Directions.LEFT  : Directions.RIGHT)) return Directions.UP;
			if (this.direction == (this.clockwise ? Directions.UP    : Directions.DOWN))  return Directions.RIGHT;
			if (this.direction == (this.clockwise ? Directions.DOWN  : Directions.UP))    return Directions.LEFT;
		}
		return this.direction;
	}
}
