package ExcelFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;






public class PoiReadExcelFile {
	static ArrayList<String> firstColonne = new ArrayList<>();
	
	static ArrayList<String> firstLine = new ArrayList<>();
	
	static ArrayList<String> firstLineAfter = new ArrayList<>();
	
	static ArrayList<String> firstColonneAfter = new ArrayList<>();
	
	
	static HashMap<Integer,ArrayList<String>> hash = new HashMap<Integer,ArrayList<String>>();
	
	static HashMap<Integer,ArrayList<String>> hash2 = new HashMap<Integer,ArrayList<String>>();
	
	static HashMap<Integer,ArrayList<String>> hash2After = new HashMap<Integer,ArrayList<String>>();
	
	public static void main(String[] args) throws InvalidFormatException, IOException {
		PoiReadExcelFile tt = new PoiReadExcelFile();
		firstColonne = tt.extractExcelContentByColumnIndex(0);
		for(String chaine :firstColonne){
			if(chaine.equals("Total de l'équipe")) break;
			firstColonneAfter.add(chaine);
		}
		
		for(String chaine :firstLine){
			if(chaine.equals("TOTAL")) break;
			if(!chaine.equalsIgnoreCase("")){
				firstLineAfter.add(chaine);
			}
		}
		
		for(int i=1; i<16; i++){
			tt.extractExcelContentByColumnIndex(i);
		}
		
		hash.remove(0);
		
		for(Integer entier : hash.keySet()){
			if(hash.get(entier).size() >= 13){
				hash2.put(entier, hash.get(entier));
			}
		}
		
		int index = 0;
		
		for(Integer entier : hash2.keySet()){
			if(hash2.get(entier).get(0).equalsIgnoreCase("abs.école alternance") || hash2.get(entier).get(0).equalsIgnoreCase("Journée") || hash2.get(entier).get(0).equalsIgnoreCase("Repos") || hash2.get(entier).get(0).contains("-")){
				hash2After.put(index++, hash2.get(entier));
			}
		}
		
		for(int k=0;k<firstLineAfter.size();k++){
			String planning = firstLineAfter.get(k) + " ";
			
			for(int j=0;j<firstColonneAfter.size();j++){
				planning += firstColonneAfter.get(j) + " ";
			
					if(!hash2After.get(k).isEmpty()){
						planning +="";
						planning += hash2After.get(k).get(j);	
					}  		
			}
			System.out.println(planning);
			
		}
		
 
	}
	

	public ArrayList<String> extractExcelContentByColumnIndex(int columnIndex){
		String excelFilePath = "C:\\Users\\odiop\\Desktop\\carrefourJar\\20170401141014-MS040917-12-planning_realise.xlsx";
        ArrayList<String> columndata = null;
        try {
            File f = new File(excelFilePath);
            FileInputStream ios = new FileInputStream(f);
            XSSFWorkbook workbook = new XSSFWorkbook(ios);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            recupAllLineTheFileExcel(rowIterator);
            columndata = new ArrayList<>();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    if(row.getRowNum() > 0){ //To filter column headings
                        if(cell.getColumnIndex() == columnIndex){// To match column index
                            switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_NUMERIC:
                                columndata.add(cell.getNumericCellValue()+"");
                                break;
                            case Cell.CELL_TYPE_STRING:
                                columndata.add(cell.getStringCellValue());
                                break;
                            }
                        }
                    }
                }
            }
            ios.close();
            //System.out.println(columndata);
        } catch (Exception e) {
            e.printStackTrace();
        }
        hash.put(columnIndex, columndata);
        return columndata;
    }
	
	
	private void recupAllLineTheFileExcel(Iterator<Row> rows){
		
		 if(rows.hasNext()) {
            Row row =  rows.next();
            Iterator cells = row.cellIterator();

            while (cells.hasNext()) {
                Cell cell = (Cell) cells.next();
                firstLine.add(cell.toString());
            }
        }
		
	}
	
	
}