package cvtheques;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.ebay.xcelite.Xcelite;
import com.ebay.xcelite.reader.SheetReader;
import com.ebay.xcelite.sheet.XceliteSheet;
import com.ebay.xcelite.writer.SheetWriter;


public class Main {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		String path ="C:\\Users\\odiop\\Desktop\\cvtheques\\candidats 2018 EJ ET ZB.xlsx";
		
		String path2 ="C:\\Users\\odiop\\Desktop\\cvtheques\\candidatsTraiterV2.xlsx";
		
		String date = "Thu Feb 02 00:00:00 WET 2012";
		SimpleDateFormat formatnow = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZ yyyy", Locale.ENGLISH); 
		SimpleDateFormat formatneeded=new SimpleDateFormat("YYYY-MM-dd");
		Date date1 = formatnow.parse(date);
		String date2 = formatneeded.format(date1);
		Date date3= formatneeded.parse(date2);
		System.out.println(date3);
		
		Collection<TraitementImport> traitementImport = readExcel(path);
		
		System.out.println(traitementImport.toString());
		
		writeExcel(path2,traitementImport);

	}
	
	
	private static Collection<TraitementImport> readExcel(String path){
		
		Xcelite xcelite = new Xcelite(new File(path)) ; 
		XceliteSheet sheet = xcelite.getSheet("candidate") ;
		SheetReader<TraitementImport> reader = sheet.getBeanReader(TraitementImport.class);
		Collection<TraitementImport> contratSupprimer = reader.read();
		
		return contratSupprimer;
		
	}
	
	
	private static void writeExcel(String path, Collection<TraitementImport> traitementImport) throws ParseException{
		
		Xcelite xcelite = new Xcelite() ;    
		XceliteSheet sheet = xcelite.createSheet("candidate");
		SheetWriter<TraitementExport> writer = sheet.getBeanWriter(TraitementExport.class);
		List<TraitementExport> traitementExportliste=new ArrayList< TraitementExport>();
		for(TraitementImport line : traitementImport){	
			
			TraitementExport traitementExport = new TraitementExport();
			String[] tab = line.getNomprenom().toLowerCase().split(" ");
			String email = null;
			
			if(tab.length == 3){
				email = tab[0]+"."+tab[1]+"."+tab[2]+"@temp.com";
				traitementExport.setMail(email);
				traitementExport.setNom(tab[0]+" "+tab[1]);
				traitementExport.setPrenom(tab[2]);
			}else if(tab.length == 2){
				email = tab[0]+"."+tab[1]+"@temp.com";
				traitementExport.setMail(email);
				traitementExport.setNom(tab[0]);
				traitementExport.setPrenom(tab[1]);
			}

			traitementExport.setCivilite(null);
			
			if(line.getTel() == null || line.getTel().isEmpty()){
				traitementExport.setTel(null);
			}else{
				traitementExport.setTel(line.getTel());
			}
			
			SimpleDateFormat formatnow = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZ yyyy", Locale.ENGLISH); 
			SimpleDateFormat formatneeded=new SimpleDateFormat("YYYY-MM-dd");
			Date date1 = formatnow.parse(line.getDate().toString());
			String date2 = formatneeded.format(date1);
			
			
			traitementExport.setDate1(date2);
			traitementExport.setDate2(null);
			traitementExport.setCodeFormation(line.getFormation());
			
			if(line.getSalaire() == null || line.getSalaire().isEmpty()){
				traitementExport.setSalaire(null);
			}else{
				traitementExport.setSalaire(line.getSalaire());
			}
			
			if(line.getDispo() == null || line.getDispo().isEmpty()){
				traitementExport.setDispo(null);
			}else{
				traitementExport.setDispo(line.getDispo());
			}
			
			if(line.getExperience() == null || line.getExperience().isEmpty()){
				traitementExport.setCodeExp(null);
			}else{
				traitementExport.setCodeExp(line.getExperience());
			}
			
			if(line.getPoste() == null || line.getPoste().isEmpty()){
				traitementExport.setCodePoste(null);
			}else{
				traitementExport.setCodePoste(line.getPoste());
			}
			
			if(line.getObservation() == null || line.getObservation().isEmpty()){
				traitementExport.setObservation(null);
			}else{
				traitementExport.setObservation(line.getObservation());
			}
			
			traitementExportliste.add(traitementExport);
		}
		
		writer.write(traitementExportliste); 
		xcelite.write(new File(path));

	}

}
