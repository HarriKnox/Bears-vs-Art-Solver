import gridspace.*;
import solver.*;
import utility.*;

public class Main
{
	public static void main(String[] args)
	{
		GameState gs;
		{
			String[] board = {
				"WWWWWW",
				"WWR WW",
				"WW  WW",
				"W    W",
				"W    W",
				"W    W",
				"WW  WW",
				"WWWWWW"
			};
			Grid<GridSpace> gameBoard = new Grid<>(board.length, board[0].length(), (Integer x, Integer y) -> board[x].charAt(y) == 'W' ? GridSpace.getWall() : GridSpace.getSpace());
			gameBoard.get(2, 3).setArt();
			
			int roryRow = 2;
			int roryCol = 2;
			spawn:
			for (int x = 0; x < board.length; x++)
			{
				for (int y = 0; y < board[0].length(); y++)
				{
					if (board[x].charAt(y) == 'R')
					{
						roryRow = x;
						roryCol = y;
						break spawn;
					}
				}
			}
			gs = new GameState(gameBoard, roryRow, roryCol);
		}
		System.out.println(gs.getBoard());
		GameState[] states = gs.createResultingGameStates();
		for (int i = 0; i < states.length; i++) System.out.println(states[i].success());
	}
}
