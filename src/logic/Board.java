package mytest;

public class Board {
	
	private int size;
	private Field[][] fields;

	public int getSize() {
		return size;
	}
	
	public Board(int size) throws InvalidBoardSizeException {
		if (size <= 0)
			throw new InvalidBoardSizeException();
		this.size = size;
		fields = new Field[size][size];
		for (int i=0; i<size; i++)
			for (int j=0; j<size; j++)
				fields[i][j] = new Field(i+1, j+1);
	}
}
