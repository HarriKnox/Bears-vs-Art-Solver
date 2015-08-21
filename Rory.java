public class Rory
{
	private int row;
	private int col;
	
	public Rory(int row, int col)
	{
		this.row = row;
		this.col = col;
	}
	
	public Rory copy()
	{
		Rory r = new Rory(this.row, this.col);
		return r;
	}
}
