import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Smudge implements Tool {
	
	private GraphicsContext gc;

	@Override
	public void select(ComboBox combo) {
		// TODO Auto-generated method stub

	}

	@Override
	public GraphicsContext draw(Canvas canvas, MouseEvent event, Paint fill, Color[][] colors, ComboBox combo, GridPane grid) {
		gc = canvas.getGraphicsContext2D();
		try {
			
			gc.setFill(Drawable.getThePixelReader().getColor((int)event.getX(), (int)event.getY()));
			gc.fillOval(event.getX(), event.getY(), Drawable.getTheSlider().getValue(), Drawable.getTheSlider().getValue());
			}catch(Exception e) {
			
		}
			return gc;

	}

}
