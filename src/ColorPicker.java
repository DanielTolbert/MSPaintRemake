import javafx.collections.FXCollections;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class ColorPicker implements Tool{

	@Override
	public void select(ComboBox combo) {

		combo.hide();
		
	}


	@Override
	public GraphicsContext draw(Canvas canvas, MouseEvent event, Paint fill, Color[][] colors, ComboBox combo, GridPane grid) {
		 
		System.out.println(event.getX() + ", " + event.getY());
		
		
		if (Drawable.isWithinImage(event.getX(), event.getY(), colors.length, colors[0].length )) {
			
			
			Color c = (colors[(int)event.getX()][(int)event.getY()]);
				Drawable.getColorMap().replace("Color Picked", c);
				combo.setItems(FXCollections.observableArrayList((Drawable.getColorMap().keySet().toArray(new String[Drawable.getColorMap().keySet().size()]))));
				combo.setValue("Color Picked");
		}
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		
		
		
		
		return gc;
		
		
		
		
	}
		

}
