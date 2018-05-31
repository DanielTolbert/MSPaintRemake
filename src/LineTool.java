import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

public class LineTool implements Tool {
	
	GraphicsContext gc;
	double startX, startY, endX, endY;

	@Override
	public void select(ComboBox combo) {
		// TODO Auto-generated method stub

	}

	@Override
	public GraphicsContext draw(Canvas canvas, MouseEvent event, Paint fill, Color[][] colors, ComboBox combo, GridPane grid) {

		gc = canvas.getGraphicsContext2D();

		Line line = new Line();
		line.setFill(fill);
		canvas.setOnDragEntered(e -> line.setStartX(event.getX()));
		canvas.setOnDragEntered(e -> line.setStartY(event.getY()));
		canvas.setOnDragExited(e -> line.setEndX(event.getX()));
		canvas.setOnDragExited(e -> line.setEndY(event.getY()));
		
		grid.add(line, 0, 0);
		return gc;
		
		
	}
	

}
