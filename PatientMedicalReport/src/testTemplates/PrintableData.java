package testTemplates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;

public class PrintableData {
	public static AnchorPane contentPane = Test_Screens.getContentPane();
	public static VBox vbox = Test_Screens.getVbox();

	public static void printData() {
		for (int i = 0; i < HeaderController.getPaneHeader().getChildren().size(); i++) {
			if (HeaderController.getPaneHeader().getChildren().get(i).getTypeSelector().equals("TextField")) {
				String txtID = ((TextField) HeaderController.getPaneHeader().getChildren().get(i)).getId();
				String text = null;
				Double x = null;
				Double y = null;
				switch (txtID) {
				case "refDoc":
					text = ((TextField) HeaderController.getPaneHeader().getChildren().get(i)).getText();
					Label lbl_refDoc = new Label(text);
					x = ((TextField) HeaderController.getPaneHeader().getChildren().get(i)).getLayoutX();
					y = ((TextField) HeaderController.getPaneHeader().getChildren().get(i)).getLayoutY();
					lbl_refDoc.setLayoutX(x);
					lbl_refDoc.setLayoutY(y);
					((TextField) HeaderController.getPaneHeader().getChildren().get(i)).setVisible(false);
					HeaderController.getPaneHeader().getChildren().add(lbl_refDoc);
					break;

				case "testDate":
					text = ((TextField) HeaderController.getPaneHeader().getChildren().get(i)).getText();
					Label lbl_testDate = new Label(text);
					x = ((TextField) HeaderController.getPaneHeader().getChildren().get(i)).getLayoutX();
					y = ((TextField) HeaderController.getPaneHeader().getChildren().get(i)).getLayoutY();
					lbl_testDate.setLayoutX(x);
					lbl_testDate.setLayoutY(y);
					((TextField) HeaderController.getPaneHeader().getChildren().get(i)).setVisible(false);
					HeaderController.getPaneHeader().getChildren().add(lbl_testDate);
					break;
				}
			}
		}
		for (int i = 0; i < vbox.getChildren().size(); i++) {
			if (vbox.getChildren().get(i).getTypeSelector().equals("HTMLEditor")) {
				String htmlID = ((HTMLEditor) vbox.getChildren().get(i)).getId();
				String htmlEditor = null;
				String text = null;
				Double x = null;
				Double y = null;
				switch (htmlID) {
				case "hePastHistory":
					htmlEditor = ((HTMLEditor) vbox.getChildren().get(i)).getHtmlText();
					text = stripHTMLTags(htmlEditor);
					Label lblPasthistory = new Label(text);
					lblPasthistory.setPrefWidth(750);
					lblPasthistory.setWrapText(true);
					x = ((HTMLEditor) vbox.getChildren().get(i)).getLayoutX();
					y = ((HTMLEditor) vbox.getChildren().get(i)).getLayoutY();
					lblPasthistory.setLayoutX(x);
					lblPasthistory.setLayoutY(y);
					((HTMLEditor) vbox.getChildren().get(i)).setVisible(false);
					contentPane.getChildren().add(lblPasthistory);
					break;
				case "heFetalParameter":
					htmlEditor = ((HTMLEditor) vbox.getChildren().get(i)).getHtmlText();
					text = stripHTMLTags(htmlEditor);
					Label lblFetalParameter = new Label(text);
					lblFetalParameter.setPrefWidth(750);
					lblFetalParameter.setWrapText(true);
					x = ((HTMLEditor) vbox.getChildren().get(i)).getLayoutX();
					y = ((HTMLEditor) vbox.getChildren().get(i)).getLayoutY();
					lblFetalParameter.setLayoutX(x);
					lblFetalParameter.setLayoutY(y);
					((HTMLEditor) vbox.getChildren().get(i)).setVisible(false);
					contentPane.getChildren().add(lblFetalParameter);
					break;
				case "heFetalDS":
					htmlEditor = ((HTMLEditor) vbox.getChildren().get(i)).getHtmlText();
					text = stripHTMLTags(htmlEditor);
					Label lblFetalDS = new Label(text);
					lblFetalDS.setPrefWidth(750);
					lblFetalDS.setWrapText(true);
					x = ((HTMLEditor) vbox.getChildren().get(i)).getLayoutX();
					y = ((HTMLEditor) vbox.getChildren().get(i)).getLayoutY();
					lblFetalDS.setLayoutX(x);
					lblFetalDS.setLayoutY(y);
					((HTMLEditor) vbox.getChildren().get(i)).setVisible(false);
					contentPane.getChildren().add(lblFetalDS);
					break;
				case "heImpression":
					htmlEditor = ((HTMLEditor) vbox.getChildren().get(i)).getHtmlText();
					text = stripHTMLTags(htmlEditor);
					Label lblImpression = new Label(text);
					lblImpression.setPrefWidth(750);
					lblImpression.setWrapText(true);
					x = ((HTMLEditor) vbox.getChildren().get(i)).getLayoutX();
					y = ((HTMLEditor) vbox.getChildren().get(i)).getLayoutY();
					lblImpression.setLayoutX(x);
					lblImpression.setLayoutY(y);
					((HTMLEditor) vbox.getChildren().get(i)).setVisible(false);
					contentPane.getChildren().add(lblImpression);
					break;
				case "heNote":
					htmlEditor = ((HTMLEditor) vbox.getChildren().get(i)).getHtmlText();
					text = stripHTMLTags(htmlEditor);
					Label lblNote = new Label(text);
					lblNote.setPrefWidth(750);
					lblNote.setWrapText(true);
					x = ((HTMLEditor) vbox.getChildren().get(i)).getLayoutX();
					y = ((HTMLEditor) vbox.getChildren().get(i)).getLayoutY();
					lblNote.setLayoutX(x);
					lblNote.setLayoutY(y);
					((HTMLEditor) vbox.getChildren().get(i)).setVisible(false);
					contentPane.getChildren().add(lblNote);
					break;
				}
			}
		}
	}

	public static String stripHTMLTags(String htmlText) {
		Pattern pattern = Pattern.compile("<[^>]*>");
		Matcher matcher = pattern.matcher(htmlText);
		final StringBuffer sb = new StringBuffer(htmlText.length());
		while (matcher.find()) {
			matcher.appendReplacement(sb, " ");
		}
		matcher.appendTail(sb);
		return sb.toString().trim();

	}
}
