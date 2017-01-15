package logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel
 *
 *a group of adjacent stones in one color 
 */
public class Group implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Field> fields;
	private List<Field> out;
	private state mystate;
	private Board board;

	/**
	 * @param field
	 * 
	 * creates new group with a just placed stone and absorbs all adjacent groups
	 */
	Group(Field field) {
		fields = new ArrayList<Field>();
		fields.add(field);
		out = new ArrayList<Field>();

		try {
			out.add(field.getDown());
		} catch (EndOfBoardException e1) {
		}
		try {
			out.add(field.getUp());
		} catch (EndOfBoardException e1) {
		}
		try {
			out.add(field.getRight());
		} catch (EndOfBoardException e1) {
		}
		try {
			out.add(field.getLeft());
		} catch (EndOfBoardException e1) {
		}

		board = field.getBoard();
		mystate = field.getState();

		board.getGroups().add(this);
		try {
			if (field.getLeft().getState() == mystate && field.getLeft().getGroup() != null)
				merge(field.getLeft());
		} catch (EndOfBoardException e) {
		}
		try {
			if (field.getRight().getState() == mystate && field.getRight().getGroup() != null)
				merge(field.getRight());
		} catch (EndOfBoardException e) {
		}
		try {
			if (field.getUp().getState() == mystate && field.getUp().getGroup() != null)
				merge(field.getUp());
		} catch (EndOfBoardException e) {
		}
		try {
			if (field.getDown().getState() == mystate && field.getDown().getGroup() != null)
				merge(field.getDown());
		} catch (EndOfBoardException e) {
		}
	}

	/**
	 * @return list of fields
	 */
	public List<Field> getFields() {
		return fields;
	}

	/**
	 * @return list of fields adjacent to this
	 */
	List<Field> getOut() {
		return out;
	}

	/**
	 * @return mystate
	 */
	state getState() {
		return mystate;
	}

	/**
	 * counts empty fields in 'out'
	 * 
	 * @return breaths
	 */
	int countBreaths() {
		int i = 0;
		for (Field aField : out)
			if (aField.isEmpty())
				i++;
		return i;
	}

	/**
	 * @param field
	 * 
	 * absorbing the group the field belongs to by this
	 */
	private void merge(Field field) {
		if (this.equals(field.getGroup()))
			return;

		Group oldGroup = field.getGroup();
		for (Field aField : field.getGroup().getFields()) {
			fields.add(aField);
			aField.setGroup(this);
		}

		oldGroup.getOut().removeAll(out);
		out.addAll(oldGroup.getOut());
		out.removeAll(fields);

		board.getGroups().remove(oldGroup);
	}
}
