package logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Group implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Field> fields;
	private List<Field> out;
	private Board board;
	private state mystate;

	public List<Field> getFields() {
		return fields;
	}
	
	public List<Field> getOut() {
		return out;
	}
	
	public int countBreaths() {
		int i=0;
		for (Field aField : out)
			if (aField.isEmpty())
				i++;
		return i;
	}
	
	public state getState() {
		return mystate;
	}
	
	public Group(Field field) {
		//System.out.println("Hello 3");
		fields = new ArrayList<Field>();
		fields.add(field);
		out = new ArrayList<Field>();
		
		try {
			out.add(field.getDown());
		} catch (EndOfBoardException e1) {}
		try {
			out.add(field.getUp());
		} catch (EndOfBoardException e1) {}
		try {
			out.add(field.getRight());
		} catch (EndOfBoardException e1) {}
		try {
			out.add(field.getLeft());
		} catch (EndOfBoardException e1) {}
		
		board = field.getBoard();
		mystate = field.getState();
		
		board.getGroups().add(this);
		
		try {
			if (field.getLeft().getState()==mystate && field.getLeft().getGroup()!=null)
				merge(field.getLeft());
		} catch (EndOfBoardException e) {}
		try {
			if (field.getRight().getState()==mystate && field.getRight().getGroup()!=null)
				merge(field.getRight());
		} catch (EndOfBoardException e) {}
		try {
			if (field.getUp().getState()==mystate && field.getUp().getGroup()!=null)
				merge(field.getUp());
		} catch (EndOfBoardException e) {}
		try {
			if (field.getDown().getState()==mystate && field.getDown().getGroup()!=null)
				merge(field.getDown());
		} catch (EndOfBoardException e) {}
	}

	private void merge(Field field) {
		if (this.equals(field.getGroup()))
			return;
		
		for (Field aField : field.getGroup().getFields()){
			fields.add(aField);
			aField.setGroup(this);
		}
		field.getGroup().getOut().removeAll(out);
		out.addAll(field.getGroup().getOut());
		board.getGroups().remove(field);
	}

}
