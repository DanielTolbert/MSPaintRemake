import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Pen implements Tool{
	private GraphicsContext gc;

	@Override
	public GraphicsContext draw(Canvas canvas, MouseEvent event, Paint fill, Color[][] colors, ComboBox combo, GridPane grid) {
		
		gc = canvas.getGraphicsContext2D();
		try {
			gc.setFill(fill);	
			gc.fillOval(event.getX(), event.getY(), Drawable.getTheSlider().getValue(), Drawable.getTheSlider().getValue());
		}catch(Exception e) {}
		return gc;

	}


	@Override
	public void select(ComboBox combo) {
		
		combo.show();
		
	}



}
