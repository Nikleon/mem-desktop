package mem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MEM extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = FXMLLoader.load(getClass().getClassLoader().getResource("GUI.fxml"));

		primaryStage.setScene(new Scene(root));
		primaryStage.setTitle("Multi-tasking Enhancement Modules");
		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch();
	}

}
