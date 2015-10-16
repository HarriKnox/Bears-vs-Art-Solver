package gridspace;

import solver.GameState;

import utility.Direction;

final class Booster extends GridSpace
{
	Booster() {}
	
	Direction direction = Direction.RIGHT;
	boolean rotates = false;
	boolean clockwise = true;
	
	
	int metadataHash()
	{
		return (this.direction.hash << 2) +
		       (this.rotates ? (b2i(this.clockwise) << 1) + 1 : 0);
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
	
	
	public void passThrough(GameState state)
	{
		state.changeDirection(this.direction);
		this.direction = this.getDirection();
	}
	
	
	public String toString() { return (this.rotates ? (this.clockwise ? "\033[1;33m" : "\033[1;34m") : "") + super.toString(this.direction == Direction.UP ? '^' : (this.direction == Direction.DOWN ? 'v' : (this.direction == Direction.LEFT ? '<' : '>'))) + "\033[0m"; }
	
	private Direction getDirection()
	{
		if (this.rotates)
		{
			if (this.direction == (this.clockwise ? Direction.RIGHT : Direction.LEFT))  return Direction.DOWN;
			if (this.direction == (this.clockwise ? Direction.LEFT  : Direction.RIGHT)) return Direction.UP;
			if (this.direction == (this.clockwise ? Direction.UP    : Direction.DOWN))  return Direction.RIGHT;
			if (this.direction == (this.clockwise ? Direction.DOWN  : Direction.UP))    return Direction.LEFT;
		}
		return this.direction;
	}
}
