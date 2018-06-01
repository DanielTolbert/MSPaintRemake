import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Eraser implements Tool {
	
	private GraphicsContext gc;
	private int tx, ty;

	@Override
	public void select(ComboBox combo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GraphicsContext draw(Canvas canvas, MouseEvent event, Paint fill, Color[][] colors, ComboBox combo, GridPane grid) {
		gc = canvas.getGraphicsContext2D();
		tx = (int)event.getX();
		ty = (int)event.getY();
		
		for(int x = tx; x<tx+Drawable.getTheSlider().getValue(); x++) {
			
			for(int y = ty; y<ty+Drawable.getTheSlider().getValue(); y++) {
			try {	
				fill = Drawable.getThePixelReader().getColor(x,y);
				gc.setFill(fill);
				gc.fillRect(x, y, 1, 1);
			}catch(Exception e) {}
				
			}
		}
		
		return gc;
		
	}

}
