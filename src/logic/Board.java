package logic;

import java.util.ArrayList;
import java.util.List;

public class Board {
	
	private int size;
	public List<Field> getFields() {
		return fields;
	}

	private List<Field> fields;

	public Field getField(int x, int y) {
		return fields.get(size*(y-1)+x-1);
	}
	
	public int getSize() {
		return size;
	}
	
	public Board(int size) throws InvalidBoardSizeException {
		if (size <= 0)
			throw new InvalidBoardSizeException();
		this.size = size;
		fields = new ArrayList<Field>();
		for (int i=0; i<size*size; i++)
			fields.add(new Field((i%size)+1, (i/size)+1));
	}
}
