package Filetexte;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TestWriteFile {

//    public static void main(String[] args) {
////        BufferedWriter writer = null;
////        BufferedReader br = null;
////        String verify,verifys = null,putData;
////        try {
////            //create a temporary file
////            String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
////            File logFile = new File("logFile");
////            
////            if (!logFile.exists()) {
////            	logFile.createNewFile();
////			}
////            
////            writer = new BufferedWriter(new FileWriter(""+logFile));
////            writer.write("Test");
////
////            // This will output the full path where the file will be written to...
////            System.out.println(logFile.getCanonicalPath());
////            
////            FileReader fr = new FileReader(logFile);
////            br = new BufferedReader(fr);
////            while((verifys = br.readLine()) != null) {
////                System.out.println(verifys);
////            } 
////           
////         
////            
////
////            
////        } catch (Exception e) {
////            e.printStackTrace();
////        } finally {
////            try {
////                // Close the writer regardless of what happens...
////                writer.close();
////                br.close();
////            } catch (Exception e) {
////            }
////        }
//    	
//    	
//
//		try {
//			FileOutputStream f = new FileOutputStream(new File("logFile"));
//			ObjectOutputStream o = new ObjectOutputStream(f);
//
//			// Write objects to file
//			o.writeObject("Test 1");
//			o.writeObject("Test 2");
//
//			o.close();
//			f.close();
//
//			FileInputStream fi = new FileInputStream(new File("logFile"));
//			ObjectInputStream oi = new ObjectInputStream(fi);
//
//			System.out.println(oi.readObject());
//			System.out.println(oi.readObject());
//
//			oi.close();
//			fi.close();
//
//		} catch (FileNotFoundException e) {
//			System.out.println("File not found");
//		} catch (IOException e) {
//			System.out.println("Error initializing stream");
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//    }
//    	
    	
	public static void main(String [] args) {

        
		String fileName = null;
		
        // This will reference one line at a time
        String line = null;
        String timeLog = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(Calendar.getInstance().getTime());

        String lines = "",liness = "";

        try {
        	
        	File logFile = new File("logFile");
    	    if (!logFile.exists()) {
    	  	  logFile.createNewFile();
    		}

    	   fileName = logFile.getAbsolutePath();
        	
        	
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                lines += line + "\n";
            }  
            
            liness +="je suis une chaine \n";
            
            String lineDate = "Modification des logs du [" + timeLog +"] : \n";
            
            // Assume default encoding.
            FileWriter fileWriter = new FileWriter(fileName);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Note that write() does not automatically
//            bufferedWriter.newLine();
            // append a newline character.
            
            bufferedWriter.write(lines);
            
            bufferedWriter.write(lineDate);
            
            bufferedWriter.write("\t -"+liness);
           

            // Always close files.
            bufferedWriter.close();
            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
    }
    	
    	
    	
    	
    
    
    
    private void test(String chaine){
    	BufferedWriter writer = null;
        try {
            //create a temporary file
            String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            File logFile = new File(timeLog);

            // This will output the full path where the file will be written to...
            System.out.println(logFile.getCanonicalPath());

            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write("Hello world! \n");
            writer.write("Hello world! \n");
            writer.write("Hello world! \n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer regardless of what happens...
                writer.close();
            } catch (Exception e) {
            }
        }

    }
}