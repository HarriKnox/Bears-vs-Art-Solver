package gridspace;

import utility.Color;

final class ButtonDoor extends GridSpace
{
	ButtonDoor() {}
	
	boolean up = false;
	Color color = Color.BLUE;
	
	int metadataHash()
	{
		return (this.color.hash << 1) + b2i(up);
	}
	
	public boolean isSolid() { return this.up; }
	
	GridSpace makeCopy()
	{
		ButtonDoor bd = new ButtonDoor();
		bd.up = this.up;
		bd.color = this.color;
		return bd;
	}
	
	public String toString() { return (this.color == Color.BLUE ? "\033[1;34m" : (this.color == Color.GREEN ? "\033[1;32m" : "\033[1;31m")) + super.toString(this.up ? 'D' : 'd') + "\033[0m"; }
}
