package logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Group implements Serializable {
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
			if (field.getLeft().getState()==mystate)
				merge(field.getLeft().getGroup());
		} catch (EndOfBoardException e) {}
		try {
			if (field.getRight().getState()==mystate)
				merge(field.getRight().getGroup());
		} catch (EndOfBoardException e) {}
		try {
			if (field.getUp().getState()==mystate)
				merge(field.getUp().getGroup());
		} catch (EndOfBoardException e) {}
		try {
			if (field.getDown().getState()==mystate)
				merge(field.getDown().getGroup());
		} catch (EndOfBoardException e) {}
	}

	private void merge(Group addedGroup) {
		if (this.equals(addedGroup))
			return;
		for (Field aField : addedGroup.getFields()){
			fields.add(aField);
			aField.setGroup(this);
		}
		addedGroup.getOut().removeAll(out);
		out.addAll(addedGroup.getOut());
		board.getGroups().remove(addedGroup);
	}

}
