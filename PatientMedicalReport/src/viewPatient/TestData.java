package viewPatient;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.jfoenix.controls.JFXButton;

import application.DashboardController;
import application.MainScreenController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import screens.HomePageController;
import testTemplates.CreateTestTemplate;
import testTemplates.ModifyTDController;
import testTemplates.EditTestTemplateController;

public class TestData {
	public final SimpleIntegerProperty tId = new SimpleIntegerProperty();
	public final SimpleStringProperty testName = new SimpleStringProperty();
	public final SimpleStringProperty reportDate = new SimpleStringProperty();
	public final SimpleStringProperty status = new SimpleStringProperty();

	public int gettId() {
		return tId.get();
	}

	public String getTestName() {
		return testName.get();
	}

	public String getReportDate() {
		return reportDate.get();
	}

	public String getStatus() {
		return status.get();
	}

	public static void addViewButton(int pid) {
		TableColumn<TestData, Void> colBtn = new TableColumn("View");

		Callback<TableColumn<TestData, Void>, TableCell<TestData, Void>> cellFactory = new Callback<TableColumn<TestData, Void>, TableCell<TestData, Void>>() {
			@Override
			public TableCell<TestData, Void> call(final TableColumn<TestData, Void> param) {
				final TableCell<TestData, Void> cell = new TableCell<TestData, Void>() {

					private final JFXButton btn = new JFXButton();

					{
						Image img = new Image("/imgs/eye_1.png");
						ImageView view = new ImageView(img);
						btn.setGraphic(view);

						btn.setOnAction((ActionEvent event) -> {
							TestData data = getTableView().getItems().get(getIndex());
							VBox vbox=new VBox(10);
							try {
								CreateTestTemplate.screenContent(data.getTestName(), pid, data.gettId(),vbox);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						});

//                    	btn.setOnAction((ActionEvent event) -> {
//                        	TestData data = getTableView().getItems().get(getIndex());
//                        	try {
//                        		reportScreen = new Stage();
//            					Parent root = FXMLLoader.load(getClass().getResource("/addTest/viewReport.fxml"));
//            					Scene scene = new Scene(root);
//            					reportScreen.setTitle("Patient Report");
//            					reportScreen.setScene(scene);
//            					reportScreen.show();
//            					setReportScreen(reportScreen);
//								viewReportController.viewReport(data.gettId());
//							} catch (IOException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//                            
//                        });
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}
				};
				return cell;
			}
		};

		colBtn.setCellFactory(cellFactory);

		ViewPDController.getTblTestTable().getColumns().add(colBtn);

	}

	public static void addEditButton(int pid) {
		TableColumn<TestData, Void> colBtn = new TableColumn("Edit");

		Callback<TableColumn<TestData, Void>, TableCell<TestData, Void>> cellFactory = new Callback<TableColumn<TestData, Void>, TableCell<TestData, Void>>() {
			@Override
			public TableCell<TestData, Void> call(final TableColumn<TestData, Void> param) {
				final TableCell<TestData, Void> cell = new TableCell<TestData, Void>() {

					private final JFXButton btn = new JFXButton();
					TableView<TestData> tblTest = ViewPDController.getTblTestTable();
					{
						Image img = new Image("/imgs/edit_1.png");
						ImageView view = new ImageView(img);
						btn.setGraphic(view);

						btn.setOnAction((ActionEvent event) -> {
							TestData data = getTableView().getItems().get(getIndex());
							try {

								AnchorPane pane = MainScreenController.getHomePage();
								for (int i = 0; i < pane.getChildren().size(); i++) {
									String paneID = pane.getChildren().get(i).getId();
									switch (paneID) {
									case "pane_Dashboard":
										MainScreenController.getHomePage().getChildren().get(i).setVisible(false);
										break;
									case "pane_viewDetails":
										MainScreenController.getHomePage().getChildren().get(i).setVisible(false);
										break;
									}
								}
								Parent root = FXMLLoader.load(DashboardController.class.getResource("/testTemplates/editTestTemplate.fxml"));
								MainScreenController.getHomePage().getChildren().add(root);
								root.setTranslateX(370);
								root.setTranslateY(30);
								EditTestTemplateController.editScreenContent(data.getTestName(), data.gettId());
								// EditTestController.editTestDetails(data.gettId());
							} catch (Exception e) {
								e.printStackTrace();
								System.out.println("Cant load window");
							}
						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}
				};
				return cell;
			}
		};

		colBtn.setCellFactory(cellFactory);
		ViewPDController.getTblTestTable().getColumns().add(colBtn);
	}

	public static void addDeleteButton(int pid, Connection con) {
		TableColumn<TestData, Void> colBtn = new TableColumn("Delete");

		Callback<TableColumn<TestData, Void>, TableCell<TestData, Void>> cellFactory = new Callback<TableColumn<TestData, Void>, TableCell<TestData, Void>>() {
			@Override
			public TableCell<TestData, Void> call(final TableColumn<TestData, Void> param) {
				final TableCell<TestData, Void> cell = new TableCell<TestData, Void>() {

					private final JFXButton btn = new JFXButton();

					{
						Image img = new Image("/imgs/delete.png");
						ImageView view = new ImageView(img);
						btn.setGraphic(view);

						btn.setOnAction((ActionEvent event) -> {
							Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
							alert.setTitle("Dr Subodh App");
							alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
							alert.getDialogPane().setHeaderText("Are you Sure!! You want to Delete!");
							alert.showAndWait().ifPresent(bt -> {
								if (bt == ButtonType.YES) {
									TestData data = getTableView().getItems().get(getIndex());
									String SQL_delete = "UPDATE patient_reportmasterdata SET active ='N' WHERE regNumber='" + pid + "'"
											+ "and id='"+ data.gettId() + "'";
									try {
										con.createStatement().executeUpdate(SQL_delete);
										ViewPDController.refreshTestDetails(pid);
										HomePageController.totalTestCount();
										HomePageController.totalTestCompleted();
										HomePageController.totalTestCount();
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								} else if (bt == ButtonType.NO) {
									event.consume();
								}
							});

						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}
				};
				return cell;
			}
		};

		colBtn.setCellFactory(cellFactory);

		ViewPDController.getTblTestTable().getColumns().add(colBtn);

	}
}
