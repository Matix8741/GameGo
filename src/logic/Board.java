package logic;

import java.util.ArrayList;
import java.util.List;

public class Board {
	
	private int size;
	public List<Field> getFields() {
		return fields;
	}

	private List<Field> fields;

	public int getSize() {
		return size;
	}
	
	public Board(int size) throws InvalidBoardSizeException {
		if (size <= 0)
			throw new InvalidBoardSizeException();
		this.size = size;
		fields = new ArrayList<Field>();
		for (int i=0; i<size; i++)
			for (int j=0; j<size; j++)
				fields.add(new Field(i+1,j+1));
	}
}
