package ExcelFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class SimpleExcelReaderExample {

	/*public static void main(String[] args) throws IOException{
		 String excelFilePath = "C:\\Users\\odiop\\Desktop\\carrefourJar\\importExcel.xlsx";
	      /*  FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
	         
	        Workbook workbook = new XSSFWorkbook(inputStream);
	        Sheet firstSheet = workbook.getSheetAt(0);
	        Iterator<Row> iterator = firstSheet.iterator();
	         
	        while (iterator.hasNext()) {
	            Row nextRow = iterator.next();
	            Iterator<Cell> cellIterator = nextRow.cellIterator();

	            while (cellIterator.hasNext()) {
	                Cell cell = cellIterator.next();
	                 
	                switch (cell.getCellType()) {
	                    case Cell.CELL_TYPE_STRING:
	                        System.out.print(cell.getStringCellValue());
	                        break;
	                    case Cell.CELL_TYPE_BOOLEAN:
	                        System.out.print(cell.getBooleanCellValue());
	                        break;
	                    case Cell.CELL_TYPE_NUMERIC:
	                        System.out.print(cell.getNumericCellValue());
	                        break;
	                }
	                System.out.print(" - ");
	            }
	            System.out.println();
	        }
	         
	        workbook.close();
	        inputStream.close();*/
		/* List sheetData = new ArrayList();
		 FileInputStream fis = null;
	        try {
	        	
	        	
		        
		        
	            //
	            // Create a FileInputStream that will be use to read the excel file.
	            //
	            fis = new FileInputStream(new File(excelFilePath));

	            //
	            // Create an excel workbook from the file system.
	            //
	            Workbook workbook = new XSSFWorkbook(fis);
	            //
	            // Get the first sheet on the workbook.
	            //
	            Sheet firstSheet = workbook.getSheetAt(0);

	            //
	            // When we have a sheet object in hand we can iterator on each
	            // sheet's rows and on each row's cells. We store the data read
	            // on an ArrayList so that we can printed the content of the excel
	            // to the console.
	            //
	            Iterator<Row> rows = firstSheet.rowIterator();
	            if(rows.hasNext()){
	            	Row row =  rows.next();
	                Iterator cells = row.cellIterator();

	                List data = new ArrayList();
	                while (cells.hasNext()) {
	                    Cell cell = (Cell) cells.next();
	                    data.add(cell);
	                }
	            }

	            while (rows.hasNext()) {
	                Row row =  rows.next();
	                Iterator cells = row.cellIterator();

	                List data = new ArrayList();
	                while (cells.hasNext()) {
	                    Cell cell = (Cell) cells.next();
	                    data.add(cell);
	                }

	                sheetData.add(data);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (fis != null) {
	                fis.close();
	            }
	        }

	        showExelData(sheetData);
		 
		 
		 
		 
		 
		 
	    }*/
	
	public static final String EXAMPLE_TEST = "7702.0";

	public static void main(String[] args) throws IOException{
		
	/*	Pattern pattern = Pattern.compile("[0-9]*");
		
		Matcher matcher = pattern.matcher(EXAMPLE_TEST);
		
		System.out.println("la reponse est :"+matcher.matches());
		
		String string = "7702";
		
		String test [] = string.split(string, string.lastIndexOf('-') + 1);
		
		int i = string.lastIndexOf(".");
		
		if(i > 0){
			String last = string.substring(0, string.lastIndexOf("."));
			System.out.println("la chaine est : " +last);
		}else{			
			System.out.println("la chaine est : " +string);
		}*/
		
		String tab2[];
		
		Map<String, String> corespondanceApplication = new HashMap<>();
		
		String application = "appli1=jesuisAppli1---appli2=jesuisAppli2---appli3=jesuisAppli3";
		
		String test [] = application.split("---");
		
		for(int i=0;i<test.length;i++){
			tab2 = test[i].split("=");
			
			corespondanceApplication.put(tab2[0], tab2[1]);
		}
		
		//affichage des application
		for(String apllication : corespondanceApplication.keySet()){
			System.out.println("l'application " + apllication + "à pour URL " + corespondanceApplication.get(apllication));
		}
		
		
		
	}
	
	
	
	private static void showExelData(List sheetData) {
        for (int i = 0; i < sheetData.size(); i++) {
            List list = (List) sheetData.get(i);
            for (int j = 0; j < list.size(); j++) {
                Cell cell = (Cell) list.get(j);
                System.out.print(cell.getRichStringCellValue().getString());
                if (j < list.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("");
        }
    }
	
	
	

	}


