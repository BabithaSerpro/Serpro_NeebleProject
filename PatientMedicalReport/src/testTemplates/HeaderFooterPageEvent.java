package testTemplates;

import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderFooterPageEvent extends PdfPageEventHelper {
	private static PdfTemplate t;
    private static Image total;
    public void onStartPage(PdfWriter writer, Document document) {
    	try {
    		String home = System.getProperty("user.home");
    		String path_headerfile = home + "/Desktop/PatientReports/header.png";
			Image image = Image.getInstance(path_headerfile);
			image.setAlignment(Element.ALIGN_CENTER);
			image.scaleToFit(600, 140);
			image.setAbsolutePosition(0, (float) (PageSize.A4.getHeight() - 110));
			document.add(image);
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		} catch (IOException e) {
			throw new ExceptionConverter(e);
		}
    }

    public void onEndPage(PdfWriter writer, Document document) {
    	try {
    		String home = System.getProperty("user.home");
    		String path_footerfile = home + "/Desktop/PatientReports/footer.png";
			Image image = Image.getInstance(path_footerfile);
			image.setAlignment(Element.ALIGN_CENTER);
			image.scaleToFit(190, 90);
			image.setAbsolutePosition(25, (float) (PageSize.A4.getHeight()-820));
			document.add(image);
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		} catch (IOException e) {
			throw new ExceptionConverter(e);
		}
    }
}