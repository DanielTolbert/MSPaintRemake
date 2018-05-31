

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class Drawable extends Application {
	
	public static HashMap<String, Image> optionsMap = new HashMap<String, Image>();
	public static HashMap<String, Tool> toolMap = new HashMap<String, Tool>();
	private static HashMap<String, Color> colorMap = new HashMap<String, Color>();
	private static ArrayList<String> imagesList = new ArrayList<String>();
	
	
	public Image originalImage = new Image(Drawable.class.getResourceAsStream("Picture1.jpg"));
	public ImageView img;
	public static PixelReader pix;
	public Group group;
	
	public Color[][] TRUE_COLORS;
	public HBox root;
	public GridPane grid;
	public Canvas drawArea;
	public GraphicsContext gc;
	public BorderPane wrapperPane;
	public BorderPane borderPane;
	public static Slider toolThickness;
	public static String toolVal, colorVal, effectVal;

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		VBox root = new VBox();
		wrapperPane = new BorderPane();
		borderPane = new BorderPane();
		Scene s = new Scene(root, 1280, 670);
		group = new Group();
	
		
				
		drawArea = new Canvas(originalImage.getWidth(), originalImage.getHeight());
		gc = drawArea.getGraphicsContext2D();
		
		TRUE_COLORS = getColors(originalImage);
		System.out.printf("Color width: %s Color height: %s\nCanvas width: %s Canvas height: %s", TRUE_COLORS.length, TRUE_COLORS[0].length, drawArea.getWidth(), drawArea.getHeight());
		System.out.println();
		
		build(primaryStage, s, originalImage);
		
		
	}
	
	public void build(Stage stage, Scene scene, Image image) {
		
//		System.out.println(TRUE_COLORS);
		
		optionsMap.put("Revert", revert(image));
		optionsMap.put("Mirror", mirrorImage(image));
		optionsMap.put("Invert", invert(image));
		optionsMap.put("Detect Edges", detectEdges(image, 0.17));
		
		toolMap.put("Pen", (new Pen()));
		toolMap.put("Color Picker", new ColorPicker());
		toolMap.put("Eraser", new Eraser());
//		toolMap.put("Line Tool", new LineTool());
		
		
		colorMap.put("red",  (Color.web("red")));
		colorMap.put("blue",  (Color.web("blue")));
		colorMap.put("yellow",  (Color.web("yellow")));
		colorMap.put("orange",  (Color.web("orange")));
		colorMap.put("white",  (Color.web("white")));
		colorMap.put("pink",  (Color.web("pink")));
		colorMap.put("brown",  (Color.web("brown")));
		colorMap.put("purple",  (Color.web("purple")));
		colorMap.put("green",  (Color.web("green")));
		colorMap.put("Color Picked", Color.WHITE);
		
		imagesList.add("FE.jpg");
		imagesList.add("Picture1.jpg");

		
		
		stage.setTitle("Editing Software");
		borderPane.setCenter(wrapperPane);
		wrapperPane.setCenter(drawArea);
		
		drawArea.widthProperty().addListener(event -> setCanvas(drawArea, image, scene));
        drawArea.heightProperty().addListener(event -> setCanvas(drawArea, image, scene));
		
		setCanvas(drawArea, image, scene);
		
	    grid = new GridPane();
		grid.setHgap(4);
		grid.setVgap(10);
		grid.setPadding(new Insets(5, 5, 5, 5));
		grid.add(new Label("Effects: "), 0, 0);
		grid.add(new Label("Tools: "), 0, 2);
		grid.add(new Label("Colors: "), 0, 4);
		grid.add(new Label("Thiccness: "), 0, 3);
		
		img = new ImageView();
		img.setImage(image);
		pix = img.getImage().getPixelReader();
		
		
		
		ComboBox toolsBox = makeComboBox((toolMap.keySet()).toArray(new String[toolMap.keySet().size()]));
		grid.add(toolsBox, 1, 2);

		
		ComboBox combob = makeComboBox((optionsMap.keySet()).toArray(new String[optionsMap.keySet().size()]));
		Button goButton = new Button("Go!");
		
		ComboBox colorsBox = makeComboBox((colorMap.keySet()).toArray((new String[colorMap.keySet().size()])));
		grid.add(colorsBox, 1, 4);
		colorsBox.setValue("red");
		
//		initCircles(group, scene);
		
		toolThickness = new Slider();
		toolThickness.setMin(1);
		toolThickness.setMax(20);
		toolThickness.setShowTickLabels(true);
		toolThickness.setShowTickMarks(false);
		toolThickness.setMajorTickUnit(5);
		toolThickness.setMajorTickUnit(2);
		toolThickness.setBlockIncrement(1);
		grid.add(toolThickness, 1, 3);
		
//		ComboBox imagesBox = makeComboBox(imagesList.toArray((new String[imagesList.size()])));
//		grid.add(imagesBox, 1, 5);
		
		
			
			drawArea.setOnMouseDragged(event -> toolMap.get(toolVal).draw(drawArea, event, ((colorVal.equals("Color Picked") || toolVal.equals("Color Picker"))? (Color) colorMap.get("Color Picked"): colorVal != null? Color.web((String)colorVal) : Color.WHITE), TRUE_COLORS, colorsBox, grid));
		
		
		//		tools.setOnMouseClicked(event -> toolMap.get(tools.getValue()).select(colorsBox));
		
//			if(imagesBox.getValue() != null)imagesBox.setOnMouseClicked(event -> {
//				try {
//					setNewImage((String)imagesBox.getValue());
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			});
		
		goButton.setOnAction(new EventHandler<ActionEvent>() { 
			
			@Override
			public void handle(ActionEvent event) {
				
				if(combob.getValue() != null)makeCurrentImage(optionsMap.get(combob.getValue()), scene);
				System.out.println(combob.getValue());
				System.out.println(toolsBox.getValue());
				
			}
		
		});
		
		
		
		
		
		grid.add(combob, 1, 0);
		grid.add(goButton, 1, 1);
		
	    root = new HBox();
//	    root.getChildren().add(img);
	    borderPane.getChildren().add(drawArea);
	    
//		root.getChildren().add(grid);
		MenuBar menuBar = new MenuBar();
		Menu effects = new Menu("Effects");
		Menu tools = new Menu("Tools");
		Menu colors = new Menu("Colors");
		
		
		addEffectMenuItems(optionsMap.keySet().toArray(), effects);
		addToolMenuItems(toolMap.keySet().toArray(), tools);
		addColorMenuItems(colorMap.keySet().toArray(), colors);
		
//		MenuItem menuitem = new MenuIte
		menuBar.getMenus().addAll(effects, tools, colors);
		
		
		borderPane.setTop(menuBar);
		
		
		
		borderPane.getChildren().add(root);
		Scene s = new Scene(borderPane, 1280, 670);
		s.getStylesheets().add(Drawable.class.getResource("fancy.css").getFile());
		
		s.setRoot(borderPane);
//		initCircles(group, s);
		stage.setScene(s);
		stage.show();
		
		
	}
	
	public void setCanvas(Canvas canvas, Image image, Scene s) {
		
		canvas.widthProperty().bind(wrapperPane.widthProperty());
		canvas.heightProperty().bind(wrapperPane.heightProperty());
		gc.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight());
		
	}
	
	public static ComboBox makeComboBox(List things) {
		ObservableList<String> methods = FXCollections.observableArrayList(things);
	    ComboBox combo = new ComboBox(methods);
		
		return combo;

	}

	public static ComboBox makeComboBox(Object...things) {
		
		ObservableList<Object> methods = FXCollections.observableArrayList(things);
	    ComboBox combo = new ComboBox(methods);
		
		return combo;
		
	}
	
	public Color[][] getColors(Image img) {
		
		ImageView imgv = new ImageView();
		imgv.setImage(img);
		
		
		PixelReader px = imgv.getImage().getPixelReader();
		
		int width = (int)(img.getWidth());
		int height = (int)(img.getHeight());
		
		Color[][] colors = new Color[width][height];
		
		
		for(int x = 0; x<width; x++) {
			for(int y = 0; y<height; y++) {
				colors[x][y] = px.getColor(x, y);
//				System.out.println(colors[x][y].getBlue());
			}
		}
		
		return colors;
		
		
	}
	
	public Image scrambleColors(Color[][] colors, Image currentImage) {
		
		ImageView imgv = new ImageView();
		imgv.setImage(currentImage);
		
		
		
		int width = (int)currentImage.getWidth();
		int height = (int)currentImage.getHeight();
		
		WritableImage writable = new WritableImage(width, height);
		PixelWriter pwx = writable.getPixelWriter();
		
		
		
		for(int x = 0; x<width; x++) {
			for(int y = 0; y<height; y++) {
				
				pwx.setColor(x, y, new Color(Math.random(), Math.random(), Math.random(), Math.random()));
				
			}
		}
		
		imgv.setImage(writable);
		
		return imgv.getImage();
		
	}
	
	public Image revert(Image image) {
		
//		return image;
		return toImage(TRUE_COLORS);		
		
	}
	
	public Image mirrorImage(Image currentImage) {
		
		int width = (int)currentImage.getWidth();
		int height = (int)currentImage.getHeight();
		
		ImageView imgv = new ImageView();
		imgv.setImage(currentImage);
		
		WritableImage write = new WritableImage(width, height);
		PixelWriter px = write.getPixelWriter();
		
		PixelReader pr = imgv.getImage().getPixelReader();
		
		for(int x = 0; x<width; x++) {
			for(int y = 0; y<height; y++) {
				System.out.printf("Width: %s Height: %s", (width - x), (height - 1));
				
				if(x<width/2) {
					px.setColor(x, y, pr.getColor(width - x - 1, y));
				}
				else {
					px.setColor(x, y, pr.getColor(x, y));
				}
				
			}
		}
		
		imgv.setImage(write);
		return imgv.getImage();
		
		
	}
	
	public Rectangle toRect(Paint color) {
		Rectangle r = new Rectangle();
		r.setFill(color);
		return r;
		
	}
	
	

	
	
//	
//	public Image detectEdges() {
//		
//	}
	
	public Image toImage(Color[][] colors) {
		
		ImageView imgv = new ImageView();
		
		WritableImage wri = new WritableImage(colors.length, colors[0].length);
		PixelWriter px = wri.getPixelWriter();
		
		for(int x = 0; x<wri.getWidth(); x++) {
			for(int y = 0; y<wri.getHeight(); y++) {
				px.setColor(x, y, new Color(colors[x][y].getRed(), colors[x][y].getGreen(), colors[x][y].getBlue(), colors[x][y].getOpacity() ));
			}
		}
		
		imgv.setImage(wri);
		return imgv.getImage();
		
		
	}
	
	public void makeCurrentImage(Image image, Scene scene) {
		
//		root.getChildren().add(image);
		img.setImage(image);
		setCanvas(drawArea, image, scene);
		
	}
	
	
	
	public Color[][] getCurrentImage() {
		
		return (getColors(((ImageView)root.getChildren().get(root.getChildren().indexOf(img))).getImage()));
		
	}
	
	public static boolean isWithinImage(double w, double h, double ww, double hh) {
		return (w<ww && w>=0d && h<hh && h>=0);
}
	
	public Image invert(Image image) {
		
		int width = (int)image.getWidth();
		int height = (int)image.getHeight();
		
		ImageView imgv = new ImageView();
		imgv.setImage(image);
		
		WritableImage writ = new WritableImage(width, height);
		PixelReader pr = imgv.getImage().getPixelReader();
		PixelWriter px = writ.getPixelWriter();
		
		for(int x = 0; x<width; x++) {
			for(int y = 0; y<height; y++) {
				px.setColor(x, y, new Color(1.0 - (pr.getColor(x, y).getRed()), 1.0 - (pr.getColor(x, y).getGreen()), 1.0 - (pr.getColor(x, y).getBlue()), 1.0));			
			}
		}
		
		imgv.setImage(writ);
		return imgv.getImage();
		
	}
	
	public Image detectEdges(Image image, double threshold) {
		
		int width = (int)image.getWidth();
		int height = (int)image.getHeight();
		
		ImageView imgv = new ImageView();
		imgv.setImage(image);
		PixelReader pr = imgv.getImage().getPixelReader();
		
		WritableImage write = new WritableImage(width, height);
		PixelWriter pw = write.getPixelWriter();
		
		for(int x = 1; x<width; x++) {
			for(int y = 0; y<height; y++) {
				if((ThreeDDistance(pr.getColor(x - 1, y).getRed(), pr.getColor(x - 1, y).getGreen(), pr.getColor(x - 1, y).getBlue(), pr.getColor(x, y).getRed(), pr.getColor(x, y).getGreen(), pr.getColor(x, y).getBlue()) > threshold)) {
					pw.setColor(x, y, Color.WHITE);
				} else {
					pw.setColor(x, y, Color.BLACK);
				}
			}
		}
		imgv.setImage(write);
		return imgv.getImage();
	}
	
	public double ThreeDDistance(double x, double y, double z, double a, double b, double c) {
		
		double legOne = Math.abs(((x-a) * (x-a))) + Math.abs((y-b) * (y-b));
		double legTwo = Math.abs((z - c) * (z - c));
		
		return Math.sqrt(legOne + legTwo);
	}
	
	
	public static PixelReader getThePixelReader() {
		return pix;
	}
	
	public static Slider getTheSlider() {
		return toolThickness;
	}
	
	public void stylize(Control...stuff) {
		
		
	}
	
	public void initCircles(Group circles, Scene s) {
		
//		for(int i = 0; i<30; i++) {
//			Circle circle = new Circle(150, Color.web("white", 0.05));
//			circle.setStrokeType(StrokeType.OUTSIDE);
//			circle.setStroke(Color.web("white", 0.16));
//			circle.setStrokeWidth(4);
//			circles.getChildren().add(circle);
//		}
//		
//		root.getChildren().add(circles);
		
		Rectangle colors = new Rectangle(s.getWidth(), s.getHeight(),
			     new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE, new 
			         Stop[]{
			            new Stop(0, Color.web("#f8bd55")),
			            new Stop(0.14, Color.web("#c0fe56")),
			            new Stop(0.28, Color.web("#5dfbc1")),
			            new Stop(0.43, Color.web("#64c2f8")),
			            new Stop(0.57, Color.web("#be4af7")),
			            new Stop(0.71, Color.web("#ed5fc2")),
			            new Stop(0.85, Color.web("#ef504c")),
			            new Stop(1, Color.web("#f2660f")),}));
			colors.widthProperty().bind(s.widthProperty());
			colors.heightProperty().bind(s.heightProperty());
			root.getChildren().add(colors);

		
	}
	
	public void addToolMenuItems(Object[] items, Menu menu ) {
		
		for(Object a: items)
		{
			MenuItem menuItem = new MenuItem((String)a);
			menuItem.setOnAction(e -> toolVal = (String)a);
			menu.getItems().addAll(menuItem);
		}
		
	}
	
	public void addEffectMenuItems(Object[] items, Menu menu) {
		
		for(Object a: items)
		{
			MenuItem menuItem = new MenuItem((String)a);
			menuItem.setOnAction(e -> effectVal = (String)a);
			menu.getItems().addAll(menuItem);
		}
		
	}
	public void addColorMenuItems(Object[] items, Menu menu) {
		
		for(Object a: items)
		{
			MenuItem menuItem = new MenuItem((String)a);
			menuItem.setOnAction(e -> colorVal = (String)a);
			menu.getItems().addAll(menuItem);
		}
		
	}
		
	
	public static void main(String[] args) {
		launch(args);
		
	}
	
	public static HashMap getColorMap() {
		
		return colorMap;
		
	}
	
	

}