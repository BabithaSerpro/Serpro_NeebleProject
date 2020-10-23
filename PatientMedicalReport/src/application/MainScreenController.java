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
    private AnchorPane mainPane;

    @FXML
    private BorderPane borderPane;

    @FXML
    private JFXTabPane tabPane;

    @FXML
    private AnchorPane HomePage;

    @FXML
    private AnchorPane TemplatePage;

	
	private static BorderPane border;
	private static AnchorPane hometab,templateTab;
	private static JFXTabPane menuBar;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		border = borderPane;
		hometab = HomePage;
		menuBar = tabPane;
		loadTitleBar();
		loadHomePage();
		sidePanel();
		addTestTemplate();
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
		
//		try {
//			Parent root = FXMLLoader.load(getClass().getResource("/screens/HomePage.fxml"));
//			root.setTranslateY(30);
//			root.setTranslateX(30);
//			TemplatePage.getChildren().add(root);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
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
	
    public void addTestTemplate() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/addNewTest/addNewTest.fxml"));
			root.getStylesheets().add(getClass().getResource("/cssFiles/addTest.css").toExternalForm());
			TemplatePage.getChildren().add(root);
			root.setTranslateX(80);
			root.setTranslateY(60);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Cant load window");
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
	
	public static AnchorPane getTemplateTab() {
		return templateTab;
	}

	public static void setTemplateTab(AnchorPane templateTab) {
		MainScreenController.templateTab = templateTab;
	}
}
