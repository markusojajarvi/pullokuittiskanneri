package application;
	
import java.text.DecimalFormat;

import controller.ScannerController;
import controller.ScannerController_IF;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class ScannerGUI extends Application implements ScannerGUI_IF {
	private ScannerController_IF controller;
	
	private Label sumTotal = new Label();
	private Label sumPrevious = new Label();
	private Label sumAdd = new Label("Lisää kokonaissummaan");
	private Label sumContainer = new Label("Kori");
	
	private static DecimalFormat df2 = new DecimalFormat("0.00");
	
	private Button sumLargeBottle = new Button("Pullo 0,40€");
	private Button sumMediumBottle = new Button("Pullo 0,20€");
	private Button sumSmallBottle = new Button("Pullo 0,10€");
	private Button sumCan = new Button("Tölkki 0,15€");
	private Button clearBtn = new Button();
	private Button undoBtn = new Button();
	private Button addInputSum = new Button("Lisää kokonaissummaan");
	
    private TextField input = new TextField();
    private TextField manualInput = new TextField();
    private TextArea log = new TextArea();
    
	@Override
	public void init() {
		controller = new ScannerController(this);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,700,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image("file:icon.png"));
			primaryStage.setTitle("Pullokuittiskanneri");
			primaryStage.show();
			root.setCenter(this.createPanels());
			root.setBottom(this.createSum());

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public HBox createPanels() {
		HBox hBox = new HBox();
		hBox.getChildren().addAll(this.createScanner(),this.createInput());
		return hBox;
	}
	@Override
	public VBox createScanner() {
		VBox vBox = new VBox();
		vBox.setPrefWidth(400);
		vBox.setId("scanner");
		
		input.setPromptText("Skannaa pullokuitit tähän (esim. 2000000000020)");
		input.setPrefWidth(350);
	    input.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent arg0) {
				String inputText = input.getText();
				if (inputText.length() >= 13) {
					controller.updateSum(inputText);
					input.clear();
				}
				
			}
	    	
	    });
	    log.setPrefSize(350, 400);
	    log.setEditable(false);
	    vBox.getChildren().addAll(input,log);
	    return vBox;   
	}
	
	@Override
	public HBox createInput() {
		HBox hBox = new HBox();
		hBox.setPrefWidth(300);
		hBox.setId("manual");
		
		VBox inputLeft = new VBox();
		inputLeft.setId("inputLeft");
		VBox inputRight = new VBox();
		inputRight.setId("inputRight");
		
		sumLargeBottle.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				controller.insertIntoSum(0.4, "bottle");
			}
		});
		sumMediumBottle.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				controller.insertIntoSum(0.2, "bottle");
			}
		});
		sumSmallBottle.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				controller.insertIntoSum(0.1, "bottle");
			}
		});
		sumCan.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				controller.insertIntoSum(0.15, "bottle");
			}
		});

		addInputSum.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					String inputText = manualInput.getText().replace(',', '.');
					controller.insertIntoSum(Double.parseDouble(inputText), "otherInput");
				} catch (NumberFormatException e) {
					insertToLog("VIRHEELLINEN SYÖTTÖ!\n");
				}
				manualInput.clear();
			}
		});
		manualInput.setPromptText("Esim: 2,00");
		
		inputLeft.getChildren().addAll(sumLargeBottle, sumMediumBottle, sumSmallBottle, sumCan);
		inputRight.getChildren().addAll(manualInput, addInputSum);
		
		hBox.getChildren().addAll(inputLeft, inputRight);
		return hBox;
	}
	
	@Override
	public HBox createSum() {
		HBox hBox = new HBox();
		hBox.setId("sumBox");
		
		VBox left = new VBox();
		left.setId("vBoxleft");
		left.setPrefWidth(350);
		VBox right = new VBox();
		right.setPrefWidth(350);
		right.setId("vBoxright");
		
		sumTotal.setText("0.00€");
		sumTotal.setId("sum");
		
		clearBtn.setText("Clear");
		clearBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				controller.clearSumTotal();
			}
		});
		undoBtn.setText("Undo");
		undoBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				controller.undoLast();
			}
		});
		left.getChildren().addAll(sumTotal);
		right.getChildren().addAll(undoBtn, clearBtn);
		
		
		hBox.getChildren().addAll(left, right);
		return hBox;
	}
	
	@Override
	public void setSum(double sum) {
		sumTotal.setText(df2.format(sum) + "€");
	}
	@Override
	public void insertToLog(String nextLog) {
		log.insertText(0, nextLog);
	}
	@Override
	public void clearLog() {
		log.clear();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
