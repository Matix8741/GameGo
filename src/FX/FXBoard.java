package FX;

import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import logic.GameMasterAdapter;

public class FXBoard extends Canvas {

	private int x=19;
	private int y=19;
	private List<FXField> fields;
	private GameMasterAdapter gameMaster;
	public FXBoard() {
		super();
	}
	public FXBoard( double arg1, double arg2) {
		super(arg1,arg2);
		gameMaster = new GameMasterAdapter();
		fields = new ArrayList<FXField>();
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(Color.BLUE);
		gc.setLineWidth(2);
		int j=0;
		for(int i=0; i<x;i++,j+=19){
			gc.strokeLine(30+j, 30, 30+j, 370);
			gc.strokeLine(30, 30+j, 370, 30+j);
		}
		j=0;
		for(int i =0;i<x*19;i+=19) {
			for(int k=0; k<y*19;k+=19){
				fields.add(new FXField(gc,30+i,30+k));
			}
		}
		setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event) {
                for(FXField field : fields) {
                	if((Math.abs(event.getX()-field.getX())<7)&& (Math.abs(event.getY()-field.getY())<7) ){
                		System.out.print("JEstemmm"+fields.size());
                		if(gameMaster.isCan(field)){
                			gc.setFill(gameMaster.which_color());
                			gc.fillOval(field.getX()-8, field.getY()-8, 16, 16);
                		}
                	}
                }
            }
        });
	}

}
