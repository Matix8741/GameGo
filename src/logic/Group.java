package logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Group implements Serializable {
	private int breaths;
	private List<Field> fields;
	private Board board;
	private state mystate;
	private state opponentstate;

	public int getBreaths() {
		return breaths;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void takeBreath() {
		breaths--;
	}
	
	public Group(Field field) {
		fields = new ArrayList<Field>();
		fields.add(field);
		board = field.getBoard();
		mystate = field.getState();
		opponentstate = field.getOpponentState();
		
		board.getGroups().add(this);
		
		breaths = GameRules.countBreaths(board, field, mystate, opponentstate);
		//System.out.println(breaths);
		
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
		for (Field aField : addedGroup.getFields()){
			fields.add(aField);
			aField.setGroup(this);
		}
		//System.out.println(breaths+"/"+addedGroup.getBreaths());
		breaths += (addedGroup.getBreaths()-2);
		//System.out.println(breaths);
		if (addedGroup.getBreaths()==1)
			breaths++;
		board.getGroups().remove(addedGroup);
	}

}
