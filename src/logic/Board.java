package logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Board implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 199410800481618070L;
	private int size;
	public List<Field> getFields() {
		return fields;
	}

	private List<Field> fields;
	private List<Group> groups;
	
	public List<Group> getGroups() {
		return groups;
	}

	public Field getField(int x, int y) {
		return fields.get(size*(y-1)+x-1);
	}
	
	public Field getField(Field field) {
		return getField(field.getX(), field.getY());
	}
	
	public int getSize() {
		return size;
	}
	
	public Board(int size) throws InvalidBoardSizeException {
		if (size <= 0)
			throw new InvalidBoardSizeException();
		this.size = size;
		groups = new ArrayList<Group>();
		fields = new ArrayList<Field>();
		for (int i=0; i<size*size; i++)
			fields.add(new Field((i%size)+1, (i/size)+1, this));
	}
}
