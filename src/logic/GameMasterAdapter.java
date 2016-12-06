package logic;

import FX.FXField;
import javafx.scene.paint.Color;

public class GameMasterAdapter {
	public Color which_color() {
		return Color.WHITE;
	}

	public boolean isCan(FXField field) {
		return true;
	}
}
