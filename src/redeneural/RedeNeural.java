package redeneural;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RedeNeural extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage palco) throws IOException {
		URL url = getClass().getResource("Principal.fxml");
		
		Parent parent = (Parent) FXMLLoader.load(url);
		
		palco.setScene(new Scene(parent));
		palco.setTitle("Redes Neurais");
		palco.show();
	}
}
