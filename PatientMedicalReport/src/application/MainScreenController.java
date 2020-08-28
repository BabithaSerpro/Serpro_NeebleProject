package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTabPane;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class MainScreenController implements Initializable{

	@FXML
	public AnchorPane mainPane;
	
	@FXML
	private JFXTabPane tabpane;
	
	@FXML
	private AnchorPane HomePage;

	@FXML
	private AnchorPane HelpPage;

	@FXML
	private BorderPane borderPane;
	
	private static BorderPane border;
	private static AnchorPane hometab;
	private static JFXTabPane menuBar;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		border = borderPane;
		hometab = HomePage;
		menuBar = tabpane;
		loadTitleBar();
		loadHomePage();
		sidePanel();
	}

	public void sidePanel() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/screens/HomePage.fxml"));
			root.setTranslateY(30);
			root.setTranslateX(30);
			HomePage.getChildren().add(root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/screens/HomePage.fxml"));
			root.setTranslateY(30);
			root.setTranslateX(30);
			HelpPage.getChildren().add(root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void loadTitleBar() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/screens/TitleBar.fxml"));
			borderPane.setTop(root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadHomePage() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/Dashboard.fxml"));
			HomePage.getChildren().add(root);
			root.setTranslateX(370);
			root.setTranslateY(30);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static BorderPane getMainLayout() {
		return border;
	}

	public static AnchorPane getHomePage() {
		return hometab;
	}

	public static JFXTabPane getMenuBar() {
		return menuBar;
	}

	public void setMainLayout(BorderPane mainLayout) {
		borderPane = mainLayout;
	}
	
	public AnchorPane getHelpPage() {
		return HelpPage;
	}

	public void setHelpPage(AnchorPane helpPage) {
		HelpPage = helpPage;
	}
}
