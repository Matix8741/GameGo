package logic;

import java.util.ArrayList;
import java.util.List;

import FX.FXField;
import javafx.scene.paint.Color;

public class GameMaster {
	
	List<Field> fields;
	
	public GameMaster(int x) {
		fields = new ArrayList<Field>();
		for(int i=0; i<x*x;i++) {
			fields.add(new Field(i/x,i%x));
			System.out.println(fields.get(i).getX()+">>>" +fields.get(i).getY());
		}
	}
	public Color which_color() {
		return Color.WHITE;
	}

	public boolean isCan(FXField field) {
		return true;
	}
}
