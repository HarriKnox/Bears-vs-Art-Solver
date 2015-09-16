package gridspace;

import utility.Colors;

final class ButtonDoor extends GridSpace
{
	ButtonDoor() {}
	
	boolean up = true;
	int color = Colors.RED;
	
	public boolean isSolid() { return this.up; }
	
	GridSpace makeCopy()
	{
		ButtonDoor bd = new ButtonDoor();
		bd.up = this.up;
		bd.color = this.color;
		return bd;
	}
}
