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
}