package application;

import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public interface ScannerGUI_IF {
	HBox createPanels();

	VBox createScanner();

	HBox createInput();

	HBox createSum();

	void setSum(double sum);

	void insertToLog(String nextLog);

	void clearLog();
}
