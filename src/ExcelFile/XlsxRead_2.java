package ExcelFile;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XlsxRead_2 {

	  //static  List<String> ligneListe = null; 
	 static HashMap<String, List<String>> mapListeTable = new HashMap<String,List<String>>();
	  
	  public static void main(String[] args) {
	       XlsxRead_2 xread2 = new XlsxRead_2();
	       
	       System.out.println(mapListeTable);
	   }

	   public XlsxRead_2() {
		   // C:\\Users\\odiop\\Desktop\\BPWeb\\20170401092402-MS040114-12-planning_realise.xlsx
           //C:\\Users\\odiop\\Desktop\\BPWeb\\20170401120258-MS040538-12-planning_realise.xlsx
		   //"C:\\Users\\odiop\\Desktop\\BPWeb\\20170401112947-MS041062-12-planning_realise.xlsx"
		  // mapListeTable = getvalue_1(path);
	   }

	   public static HashMap<String, List<String>> getvalue_1(String path, int size) {
	       XSSFRow row;
	       XSSFCell cell;
	       String[][] value = null;
	       double[][] nums = null;
	       List<String> ligneListe = null; 
	       HashMap<String, List<String>> mapListeTable = new HashMap<String,List<String>>();
	       try {
	    	   FileInputStream inputStream = new FileInputStream(path);
	           XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

	           // get sheet number
	           int sheetCn = workbook.getNumberOfSheets();

	           for (int cn = 0; cn < sheetCn; cn++) {

	               // get 0th sheet data
	               XSSFSheet sheet = workbook.getSheetAt(cn);

	               // get number of rows from sheet
	               int rows = sheet.getPhysicalNumberOfRows();

	               // get number of cell from row
	               int cells = sheet.getRow(cn).getPhysicalNumberOfCells();

	               value = new String[rows][cells];

	               for (int r = 0; r < rows; r++) {
	                   row = sheet.getRow(r); // bring row
	                   if (row != null) {
	                       for (int c = 0; c < cells; c++) {
	                           cell = row.getCell(c);
	                           nums = new double[rows][cells];

	                           if (cell != null) {

	                               switch (cell.getCellType()) {

	                               case XSSFCell.CELL_TYPE_FORMULA:
	                                   value[r][c] = cell.getCellFormula();
	                                   break;

	                               case XSSFCell.CELL_TYPE_NUMERIC:
	                                   value[r][c] = ""
	                                        + cell.getNumericCellValue();
	                                   break;

	                               case XSSFCell.CELL_TYPE_STRING:
	                            	   if(cell.getStringCellValue() != null){
	                            		   value[r][c] = ""
	   	                                        + cell.getStringCellValue(); 
	                            	   }
	                                   
	                                   break;

//	                               case XSSFCell.CELL_TYPE_BLANK:
//	                                  value[r][c] = "[BLANK]";
//	                                  break;

	                               case XSSFCell.CELL_TYPE_ERROR:
	                                  value[r][c] = ""+cell.getErrorCellValue();
	                                break;
	                            default:
	                            }
	       

	                        } 
	                    } // for(c)
	                }
	                   
	                
	            } // for(r)
	        }
	          
	        for(int v=0;v<size;v++){// c'est la taille de la premiere colonne + 1
	        	ligneListe = new ArrayList<String>();
	        	for(int z=0;z<14;z++){// le nombre de colonne en commencant par premier colonne jusqu'a total.
	        		if(value[v][z] != null){
	        			ligneListe.add(value[v][z].replace(" ", "_").replace("\n", " "));
	        		}

	        	}
	        	if(v == 0){
	        		mapListeTable.put("Jour", ligneListe);
	        	}else{
	        		if(!ligneListe.isEmpty()){
		        		mapListeTable.put(ligneListe.get(0), ligneListe);
	        		}
	        	}
	        }
	        
	        
	           
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return mapListeTable;
	  }
	}