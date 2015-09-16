package gridspace;

import utility.Colors;

final class ButtonDoor extends GridSpace
{
	ButtonDoor() {}
	
	boolean up = true;
	int color = Colors.BLUE;
	
	int metadataHash()
	{
		return (this.color << 1) + b2i(up);
	}
	
	public boolean isSolid() { return this.up; }
	
	GridSpace makeCopy()
	{
		ButtonDoor bd = new ButtonDoor();
		bd.up = this.up;
		bd.color = this.color;
		return bd;
	}
	
	public String toString() { return super.toString(this.up ? 'D' : 'd'); }
}
