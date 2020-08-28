package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(Main.class.getResource("/screens/MainScreen.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/cssFiles/application.css").toExternalForm());
			primaryStage.setTitle("Patient Medical Report");
			primaryStage.initStyle(StageStyle.UNDECORATED);
//			primaryStage.getTitle().setStyle("-fx-background-color: #018baf;");
			primaryStage.setY(20);
			primaryStage.setX(100);
			primaryStage.getIcons().add(new Image("/imgs/sdIcon.png")); 
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
