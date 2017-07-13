package PdfToExcel;

import java.io.FileOutputStream;

import java.io.*;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;


//import com.itextpdf.text.pdf.PdfReader;
//import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
//import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
//import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;



public class PdfToExcel {

	 private static String INPUTFILE = "C:/Users/odiop/Desktop/20170401092402-MS040114-12-planning_realise.pdf" ;

	 private static String OUTPUTFILE = "file:///C:/Users/odiop/Desktop/20170401092402-MS040114-12-planning_realise.pdf";
	
	public static void main(String[] args) throws DocumentException,
    IOException {
		// TODO Auto-generated method stub
//		Document document = new Document();
//
//        PdfWriter writer = PdfWriter.getInstance(document,
//                new FileOutputStream(OUTPUTFILE));
//
//        document.open();
//
//        PdfReader reader = new PdfReader(INPUTFILE);
//
//        int n = reader.getNumberOfPages();
//
//        PdfImportedPage page;
//
//
//        for (int i = 1; i <= n; i++) {
//
//                page = writer.getImportedPage(reader, i);
//
//                Image instance = Image.getInstance(page);
//
//                document.add(instance);
//
//        }
//
//        document.close();
//		
		
//		 PdfReader pdfReader = new PdfReader(INPUTFILE);
//         PdfReaderContentParser PdfParser = new PdfReaderContentParser(
//                 pdfReader);
//         PrintWriter out = new PrintWriter(new FileOutputStream(
//                 "recharge.txt"));
//         TextExtractionStrategy textStrategy;
//         for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
//             textStrategy = PdfParser.processContent(i,
//                     new SimpleTextExtractionStrategy());
//             out.println(textStrategy.getResultantText());
//         }
//         out.flush();
//         out.close();
//         pdfReader.close();
		
//		
//		PDDocument document = PDDocument.load(INPUTFILE);
//		PDFTextStripper s = new PDFTextStripper();
//		String content = s.getText(document);
//		System.out.println(content);
		
		
		 String string = null;
	        try {
	            PDFParser pdfParser = new PDFParser(new FileInputStream(INPUTFILE));
	            pdfParser.parse();
	            PDDocument pdDocument = new PDDocument(pdfParser.getDocument());
	            PDFTextStripper pdfTextStripper = new PDFTextStripper();
	            string = pdfTextStripper.getText(pdDocument);
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        };
	        System.out.println(string);
		
	
		
		
	}

}
