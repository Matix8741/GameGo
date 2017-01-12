package logic;

import java.util.ArrayList;
import java.util.List;

public class Territory {
	private List<Field> fields;
	private List<Field> out;
	private state owner;
	private Board board;
	
	public state getOwner() {
		return owner;
	}
	
	public List<Field> getFields() {
		return fields;
	}
	
	public List<Field> getOut() {
		return out;
	}
	
	public Territory(Field field) {
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
		board.getTerritories().add(this);
		
		try {
			if (field.getLeft().isEmpty() && field.getLeft().getTerritory()!=null)
				merge(field.getLeft());
		} catch (EndOfBoardException e) {}
		try {
			if (field.getRight().isEmpty() && field.getRight().getTerritory()!=null)
				merge(field.getRight());
		} catch (EndOfBoardException e) {}
		try {
			if (field.getUp().isEmpty() && field.getUp().getTerritory()!=null)
				merge(field.getUp());
		} catch (EndOfBoardException e) {}
		try {
			if (field.getDown().isEmpty() && field.getDown().getTerritory()!=null)
				merge(field.getDown());
		} catch (EndOfBoardException e) {}
		
		if (!this.getOut().isEmpty())
			if (!this.getOut().get(0).isEmpty()) {
				this.setOwner(this.getOut().get(0).getState());
				for (Field aField : this.getOut())
					if (aField.getState()!=this.owner) {
						this.setOwner(null);
						break;
					}
			}
	}
	
	private void setOwner(state state) {
		this.owner = state;
	}

	private void merge(Field field) {
		if (this.equals(field.getTerritory()))
			return;
		
		Territory oldTerritory = field.getTerritory();
		for (Field aField : field.getTerritory().getFields()){
			fields.add(aField);
			aField.setTerritory(this);
		}
		
		oldTerritory.getOut().removeAll(out);
		out.addAll(oldTerritory.getOut());
		out.removeAll(fields);
		
		board.getTerritories().remove(oldTerritory);
	}
}
