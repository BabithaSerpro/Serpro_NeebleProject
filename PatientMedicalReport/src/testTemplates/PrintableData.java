package testTemplates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;

public class PrintableData {
	

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
