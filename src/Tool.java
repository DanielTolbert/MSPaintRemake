import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public interface Tool {
	
	void select(ComboBox combo);
//	WritableImage draw(WritableImage wri, PixelWriter pwx);
//	GraphicsContext draw(Canvas canvas, MouseEvent event);
//	GraphicsContext draw(Canvas canvas, MouseEvent event, Paint fill);
//	GraphicsContext draw(Canvas canvas, MouseEvent event,  Paint fill, Color[][] colors);
//	GraphicsContext draw(Canvas canvas, MouseEvent event,  Paint fill, Color[][] colors, ComboBox combo);
	GraphicsContext draw(Canvas canvas, MouseEvent event, Paint fill, Color[][] colors, ComboBox combo, GridPane grid);

	

}
