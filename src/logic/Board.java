package logic;

import java.util.List;

public class Board {
	
	private int size;
	private Field[][] fields;

	
	public Board(int size) {
		this.size = size;
		fields = new Field[size][size];
		for (int i=0; i<size; i++)
			for (int j=0; j<size; j++)
				field[i][j] = new Field(i+1, j+1);
	}
}
