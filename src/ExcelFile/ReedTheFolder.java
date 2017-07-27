package ExcelFile;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ebay.xcelite.Xcelite;
import com.ebay.xcelite.reader.SheetReader;
import com.ebay.xcelite.sheet.XceliteSheet;
import com.ebay.xcelite.writer.SheetWriter;


public class ReedTheFolder {

	
	FileInputStream inputStream = null;
	
	static String excelFilePath ="";
	
	static  String nomMagasin ="MS040114";
	
    static ArrayList<String> firstColonne = null;
	
	static ArrayList<String> firstLine = null;
	
    static List<String> listTOTAL = null;
    
    static List<String> listeTOTAL = null;
	
	static ArrayList<String> firstLineAfter = null;
	
	static ArrayList<String> firstColonneAfter = null;
	
    static HashMap<Integer,ArrayList<String>> hash = null;
	
	static HashMap<Integer,ArrayList<String>> hash2 = null;
	
	static HashMap<Integer,ArrayList<String>> hash2After = null;
	
    static HashMap<String,ArrayList<Planing_jour>> mapPlanning = null;
    
    static HashMap<String,PlanningDTO> mapPlanningDTO = null;
    
    static HashMap<String,HashMap<String,ArrayList<Planing_jour>>> mapInsertionBase = new HashMap<String,HashMap<String,ArrayList<Planing_jour>>>();

    static HashMap<String,HashMap<String,PlanningDTO>> mapInsertionBaseDTO = new HashMap<String,HashMap<String,PlanningDTO>>();

	 static HashMap<String, List<String>> mapListeTable = null;

    static ArrayList<Planing_jour> planingListe = null;
	
	static ArrayList<String> listeInsertion = new ArrayList<>();
	
	static HashMap<String,List<Date>> typeAbscenceMapPlanning = null;
	
	static List<String> listeExeption = new ArrayList<String>();
	
	static List<ContratSupprimer> listeRejetContrat = new ArrayList<ContratSupprimer>();
	
	
	public static void main(String[] args) throws Exception {
	 
	    //Map<String,File> mapFile = new HashMap<>();
		 TreeMap<String, File> mapFile = new TreeMap<String, File>();
	    
	    Planing_jour planing1 = null;
	    PlanningDTO planingDTO = null; //Y:\Echange\odiop\caroll_vrac_excel //C:\\Users\\odiop\\Desktop\\BPWe
	    String repertoire1 ="C:\\Users\\odiop\\Desktop\\BPWeb",repertoire2="C:\\Users\\odiop\\Desktop\\Contrats_supprimes.xlsx";
	    
	  //  writeExcelContrat();
	    
	    Collection<ContratSupprimer> contratCollection = readFileExcelContratSupprimer(repertoire2);
	    
	    
	    File repertoir = new File(repertoire1);
	    try {
				addFile(repertoir,mapFile);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    System.out.println(mapFile);
	    
	    ReedTheFolder red = new ReedTheFolder();
	    for(ContratSupprimer numMangasin : contratCollection){	
	    	SortedMap<String, File> sortedMap = mapFile.subMap(numMangasin.getPoint_vente(), numMangasin.getPoint_vente() + Character.MAX_VALUE);
	    	if(sortedMap.isEmpty()){
	    		continue;
	    	}
	    	
 		     for(String cleSorted : sortedMap.keySet()){
		    		firstColonne = new ArrayList<>();
			    	firstLine = new ArrayList<>();
			    	
			    	firstLineAfter = new ArrayList<>();
			    	firstColonneAfter = new ArrayList<>();
			    	hash = new HashMap<Integer,ArrayList<String>>();
			    	hash2 = new HashMap<Integer,ArrayList<String>>();
			    	hash2After = new HashMap<Integer,ArrayList<String>>();
			    	mapPlanning = new HashMap<String,ArrayList<Planing_jour>>();
			    	mapPlanningDTO = new HashMap<>();
			    	
			    	mapListeTable = new HashMap<String,List<String>>();
			    	
			    	//recherche du fichier dans la map des fichier
			    	excelFilePath = sortedMap.get(cleSorted).getPath();
			    	//recupere la premiere colonne (les noms)
				    firstColonne = red.extractExcelContentByColumnIndex(0);
			     
				    //on fait un filtre pour rejeter tous se qui vient apres total de l'equipe
			    	for(String chaine :firstColonne){
						if(chaine.equals("Total de l'équipe")) break;
						if(chaine.contains("\n")) chaine = chaine.replace(" ", "_");
						firstColonneAfter.add(chaine);
					}
			    	
			    	mapListeTable = XlsxRead_2.getvalue_1(sortedMap.get(cleSorted).getAbsolutePath(),firstColonneAfter.size()+1);

			    	for(String jour : mapListeTable.get("Jour")){
//			    		if(jour.contains("\n")){
//			    			String[] jourFormer = jour.split("\n");
//			    			firstLineAfter.add(jourFormer[0] +"-" +jourFormer[2]);
//			    		}
			    		
			    		firstLineAfter.add(jour);
			    	}
			    	
			    	for(String nom :firstColonneAfter){
			    		listTOTAL = new ArrayList<>();
			    		listeTOTAL = new ArrayList<>();
			    		listTOTAL = mapListeTable.get(nom.replace(" ", "_").replace("\n", " "));
			    		//if(listTOTAL == null) listTOTAL = mapListeTable.get(nom.replace(" ", "\n"));
			    		listTOTAL.remove(0);
			    		planingListe = new ArrayList<>();
			    		planingDTO = new PlanningDTO();
			    		
			    		String unite = listTOTAL.get(listTOTAL.size()-1);
						if(unite.contains("h") || unite.contains("H")){
							planingDTO.setUnite("H");
						}
						
						if(unite.contains("j") || unite.contains("J")){
							planingDTO.setUnite("J");
						}
						
						listTOTAL.remove(listTOTAL.size()-1);
						
						for(String listeT : listTOTAL){
							String []tabListeT = listeT.split(" ");
							String valeur="";
							ArrayList<String> array = new ArrayList<>();
							for(String ptabListeT : tabListeT){
								if(ptabListeT.contains("-") && ptabListeT.contains("_")) {
									array.add(ptabListeT.replace("-", "_"));
								}else{
									array.add(ptabListeT);
								}
							}
							
							int r=0;
							
							for(String valArray : array){
								valeur +=valArray;
								 r++;
								 if(r < array.size()){
									 valeur +=" "; 
								 }
							}
							
							listeTOTAL.add(valeur);
							
						}
						
						
						int k =0;
			    		for(String horaire : listeTOTAL){
			    			
			    			planing1 = new Planing_jour();
							planing1.setNum_jour(ListeJour.get(firstLineAfter.get(k).split(" ")[0]));
							planing1.setDate(firstLineAfter.get(k).split(" ")[2]);
							planing1.setAutre(horaire);
			    			if(horaire.contains("-")){
			    				String tabHeure[] = horaire.split(" ");
			    				String []tab = new String[tabHeure.length];
			    				
			    				for(int heure=0;heure<tabHeure.length;heure++){
									if(tabHeure[heure].contains("-") && tabHeure[heure].contains("h")){
										tab[heure] = tabHeure[heure];
									}
								}
			    				
			    				List<String> list = new ArrayList<String>(Arrays.asList(tab));
			    				System.out.println(list);
			    				list.removeAll(Arrays.asList("", null));
			    				
			    				
			    				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
								List<Date> dates = new ArrayList<Date>();
								for(int z=0;z<list.size();z++){
									if(!list.get(z).contains("_") && list.get(z).contains("-")){
										String tabHeure2[] = list.get(z).split("-");
										if(tabHeure2.length > 1){
											for(String str : tabHeure2) {
											    dates.add(simpleDateFormat.parse(str.replace("h", ":")));
											}
											planing1.setNombre_heure((int) TimeUnit.MINUTES.toMinutes((dates.get(1).getTime()-dates.get(0).getTime())/60*60));
										}
									}
								}
			    			}

			    			k++;
			    			//ajouter dans une liste l'objet planing
							planingListe.add(planing1);	
			    		}
			    		planingDTO.setListePlaningJour(planingListe);
						//ajout dans une map la liste 
						mapPlanningDTO.put(nom.replace("\n", " "), planingDTO);			    		
			    		
			    	}
			    	
			    	//ajouter dans une map avec comme clé le nom du magasin et la semaine
					mapInsertionBaseDTO.put(cleSorted, mapPlanningDTO);

		       // }
				
				
		     }
	    	
	    }	
	    
	    String test = "/**** \n";
	      test +="============================================== \n";
		  test +="= \t\t  REQUETES GENERES\t\t\t\t\t = \n";
	      test +="============================================== \n";
	      test +="***/ \n";
	    listeInsertion.add(test);
	    
	   for(ContratSupprimer contratSupprimer : contratCollection){
		   recherchePlaningPersonne(contratSupprimer);   
	   } 
	   
	   if (!listeInsertion.isEmpty()) writeSQLFile(listeInsertion,"requeteGenere.sql");
	   if (!listeExeption.isEmpty()) writeSQLFile(listeExeption,"ExceptionFile.txt");
	   if (!listeRejetContrat.isEmpty()) writeExcelContrat(listeRejetContrat);
	   
	   System.out.println("**************************************FIN DU TRAITEMENT*************************************");
	   
	    
	}
	
	//prend un objet de contratSupprimer
	static void recherchePlaningPersonne(ContratSupprimer ligne) throws Exception{
		
		
		HashMap<String,ArrayList<Planing_jour>> planing_jour;
		List<Date> date = null;
		HashMap<String,PlanningDTO> planningDTO;
		List<Planing_jour> planingListe;
		String magasin = ligne.getPoint_vente();
		Integer nombreHeure=0;
		Integer numJour = 0;
		String dateJour = null;
		String nom = ligne.getPrenom()+" "+ligne.getNom();
		

		for(int numSemaine=12; numSemaine<19; numSemaine++){
			typeAbscenceMapPlanning = new HashMap<String,List<Date>>();
			date = new ArrayList<>();
			planningDTO = mapInsertionBaseDTO.get(magasin +"-"+ numSemaine);
			
			String exceptions = "\n=========================================================================================== \n";
			exceptions += "EXCEPTION RELATIFVE A LA SEMAINE DU " +numSemaine +" POUR LE MAGASIN " +magasin +"\n";
			exceptions += "=========================================================================================== \n";
			
			

			if(planningDTO == null){
				String exception = "le planning jour du point de vente "+ magasin + " pour la semaine du " +numSemaine +" n'existe pas";
				listeExeption.add(exceptions);
				listeExeption.add(exception);
				if(!listeRejetContrat.contains(ligne)) listeRejetContrat.add(ligne);
			}else{
			
					if(!planningDTO.containsKey(nom)){
						String exception = "L'utilisateur "+ nom +" n'a pas de planning dans le magasin " + magasin +" pour la période " +numSemaine;
						listeExeption.add(exceptions);
						
						listeExeption.add(exception);
						
						if(!listeRejetContrat.contains(ligne)) listeRejetContrat.add(ligne);
						
					}else{
						
						planingListe = planningDTO.get(nom).getListePlaningJour();
						if(planingListe == null || planingListe.isEmpty()){
							String exception = "L'utilisateur "+ nom +" est dans le magasin" + magasin +" pour la période " +numSemaine +" mais il n'est pas plannifié";
							listeExeption.add(exception);
							listeExeption.add(exceptions);
							if(!listeRejetContrat.contains(ligne)) listeRejetContrat.add(ligne);							
						}else{			
						
							String insert_planingSalarie ="<!--Creation du planning salarie de la semaine " +numSemaine +" pour l'employé " + nom;
							insert_planingSalarie += " --> \n";
							insert_planingSalarie += "INSERT INTO planning_salarie(id_salarie,id_planning_point_vente,termine) VALUES ( select id_salarie from contrat where numero_contrat like '" +ligne.getId_contrat() +"',";
							insert_planingSalarie +="select id_planning_point_vente from planning_point_vente where id_point_vente in";
							insert_planingSalarie +=" (select id_point_vente from point_vente where numero_point_vente like "+ligne.getPoint_vente() +") and date_debut = '"+ firstLineAfter.get(0).split(" ")[2] +"',";
							insert_planingSalarie +="true);\n";
							
							listeInsertion.add(insert_planingSalarie);
							
							String insert_ev_hebdo = "<!-- Création de l'évenement Hebdo de l'employe "+ nom +"pour la semaine du " + numSemaine +" --> \n";
							insert_ev_hebdo +="insert into ev_hebdo(id_planning_salarie,id_element,code_element,valeur) values (";
							insert_ev_hebdo +="select id_planning_salarie from planning_salarie where id_salarie in (select id_salarie from contrat where numero_contrat like '"+ligne.getId_contrat();
							insert_ev_hebdo +="') and id_planning_point_vente in (select id_planning_point_vente from planning_point_vente where id_point_vente in";
							insert_ev_hebdo +=" (select id_point_vente from point_vente where numero_point_vente like " + ligne.getPoint_vente() +" ) and date_debut = '"+ firstLineAfter.get(0).split(" ")[2] +"'), '000', 'CODE','000'";
							insert_ev_hebdo +=");\n";
							listeInsertion.add(insert_ev_hebdo);
							
							
							String insert_planingJour = "\n<!-- Requete pour tous les jours de la semaine " +numSemaine +" pour l'employé " + nom +"  --> \n";
														
							String insert_plageHoraire ="\n<!-- toutes les plages horaires du salarié "+ nom +" pour de la semaine " +numSemaine +" --> \n";
						
							String insert_evJour ="\n<!-- pour tous les jours de la semaine du salarié "+ nom +" --> \n";
												
							ArrayList<String> insertionPaning = new ArrayList<>();
							insertionPaning.add(insert_planingJour);
							
							ArrayList<String> insertionPlage = new ArrayList<>();
							ArrayList<String> insertion_evJour = new ArrayList<>();
							ArrayList<String> insertion_planingPlage = new ArrayList<>();
							
							for(Planing_jour planing :planingListe){
								
								String []heure = null;
								String []heureSansRetour = null;
								
								nombreHeure = planing.getNombre_heure();
								numJour = planing.getNum_jour();
								dateJour = planing.getDate();
								if(nombreHeure == null) nombreHeure = null;
																			
								if(planing.getAutre().contains("-")){
									
									insert_planingJour ="";
									insert_planingJour +="insert into planning_jour (id_planning_salarie,date,id_planning_type_jour,contractuel,id_planning_plage,";
									insert_planingJour +="unite,presence_matin,presence_apm,type_contenu,heures_effectuees) values (";
									//recuperartion de id_planning_salarie
									insert_planingJour +="(select id_planning_salarie from planning_salarie ps, planning_point_vente ppv,point_vente pv ";
									insert_planingJour +="where ps.id_planning_point_vente = ppv.id_planning_point_vente";
									insert_planingJour +=" and ppv.id_point_vente = pv.id_point_vente ";
									insert_planingJour +="and ps.id_salarie in (select id_salarie from contrat where numero_contrat like '"+ ligne.getId_contrat();
									insert_planingJour +="') and pv.numero_point_vente like '"+ligne.getPoint_vente() +"' and ppv.date_debut='"+ firstLineAfter.get(0).split(" ")[2] +"'),";
									//recuperation de la date du jour
									insert_planingJour +="'"+planing.getDate()+"',";
									//recuperation de id_planning_type_jour
									insert_planingJour +="(select ptj.id_planning_type_jour from planning_type_jour ptj,planning_type pt, contrat c ";
									insert_planingJour +="where ptj.id_planning_type=pt.id_planning_type ";
									insert_planingJour +="and c.id_contrat=pt.id_contrat ";
									insert_planingJour +="and ptj.numero_jour="+planing.getNum_jour() +" ";
									insert_planingJour +="and c.numero_contrat like '" +ligne.getId_contrat() +"' ";
									insert_planingJour +="and pt.debut <='"+firstLineAfter.get(0).split(" ")[2]+"'";
									insert_planingJour +=" and pt.fin is null),";
									//recuperation de contractuel
									insert_planingJour +="(select ptj.contractuel from planning_type_jour ptj,planning_type pt, contrat c ";
									insert_planingJour +="where ptj.id_planning_type=pt.id_planning_type ";
									insert_planingJour +="and c.id_contrat=pt.id_contrat ";
									insert_planingJour +="and ptj.numero_jour="+planing.getNum_jour() +" ";
									insert_planingJour +="and c.numero_contrat like '" +ligne.getId_contrat() +"' ";
									insert_planingJour +="and pt.debut <='"+firstLineAfter.get(0).split(" ")[2]+"'";
									insert_planingJour +=" and pt.fin is null),";
									//recuperation de id_planning_plage
									insert_planingJour +="(select id_planning_plage from planning_plage pg, point_vente pv ";
									insert_planingJour +="where pg.id_point_vente = pv.id_point_vente ";
									insert_planingJour +="and pg.debut ='"+firstLineAfter.get(0).split(" ")[2]+"'";;
									insert_planingJour +=" and pv.numero_point_vente like '"+ligne.getPoint_vente();
									insert_planingJour +="' and pg.id_salarie in (select id_salarie from contrat where numero_contrat like '"+ligne.getId_contrat() +"')),";;
									//recuperation de l'unite H ou J
									insert_planingJour +="'"+planningDTO.get(nom).getUnite() +"',";
									//presence matin, soir et Normal N ou Abscence A
									insert_planingJour +="-1,-1,'N',";
									//recuperation de nombre d'heure travailler en minuite 
									insert_planingJour +=(planing.getNombre_heure()/60)/60;
									insert_planingJour +=");";

									insertionPaning.add(insert_planingJour);
									
									if(planing.getAutre().contains(" ")){
										heure = planing.getAutre().split(" ");
										String []tabHeure = new String[heure.length];
										for(int k=0;k<heure.length;k++){
											if(!heure[k].contains("-") ){
												if(k < heure.length) {
													if(!heure[k+1].contains("-")){
													 tabHeure[k] = heure[k]+"_"+heure[k+1];
													}else{
														tabHeure[k] = heure[k];
													}
												}
												
											}else{
												tabHeure[k] = heure[k];
											}
											
										}
										
										int incrementation = 0;
										
										for(String heur :tabHeure){
											
											if(heur.contains("-") && !heur.contains("_")){
						
												insert_plageHoraire+="insert into plage_horaire(id_planning_jour,debut_plage, fin_plage, id_motif) values ";
												insert_plageHoraire+="((select id_planning_jour from planning_jour pj, planning_salarie ps,contrat c,point_vente pv,planning_point_vente ppv";
												insert_plageHoraire+=" where pj.id_planning_salarie=ps.id_planning_salarie";
												insert_plageHoraire+=" and ppv.id_planning_point_vente = ps.id_planning_point_vente";
												insert_plageHoraire+=" and ps.id_salarie = c.id_salarie";
												insert_plageHoraire+=" and pv.id_point_vente = ppv.id_point_vente";
												insert_plageHoraire+=" and c.numero_contrat like '"+ligne.getId_contrat();
												insert_plageHoraire+="' and pv.numero_point_vente like '"+ligne.getPoint_vente()+"'";
												insert_plageHoraire+=" and ppv.date_debut ='"+ firstLineAfter.get(0).split(" ")[2] +"'";
												insert_plageHoraire+=" and pj.date = "+"'"+planing.getDate()+"'),";
												insert_plageHoraire +="'"+heur.split("-")[0].replaceAll("h", ":")+"'," +"'"+heur.split("-")[1].replaceAll("h", ":")+"'" +",";
												insert_plageHoraire +="'-1');\n";
	
											}else{
												String typeAbscence = heur;
												typeAbscence = typeAbscence.replace("\n", " ").replace(".", "_").replace("/", "_").replaceAll(" ", "_");
												int typeRetrouve = TypeAbscence.get(typeAbscence);
												
												if(typeRetrouve == -1){
													String exception = "Le type d'abscence " +typeAbscence +" ne peut pas etre traiter pour l'employe " + nom + "de la semaine du "+numSemaine +" et de la date du " + planing.getDate();
													listeExeption.add(exception);
													listeExeption.add(exceptions);
												}else{

													if(incrementation == 0 && tabHeure.length == 2){
														insert_plageHoraire+="insert into plage_horaire(id_planning_jour,debut_plage, fin_plage, id_motif) values ";
														insert_plageHoraire+="((select id_planning_jour from planning_jour pj, planning_salarie ps,contrat c,point_vente pv,planning_point_vente ppv";
														insert_plageHoraire+=" where pj.id_planning_salarie=ps.id_planning_salarie";
														insert_plageHoraire+=" and ppv.id_planning_point_vente = ps.id_planning_point_vente";
														insert_plageHoraire+=" and ps.id_salarie = c.id_salarie";
														insert_plageHoraire+=" and pv.id_point_vente = ppv.id_point_vente";
														insert_plageHoraire+=" and c.numero_contrat like '"+ligne.getId_contrat();
														insert_plageHoraire+="' and pv.numero_point_vente like '"+ligne.getPoint_vente()+"'";
														insert_plageHoraire+=" and ppv.date_debut ='"+ firstLineAfter.get(0).split(" ")[2] +"'";
														insert_plageHoraire+=" and pj.date = "+"'"+planing.getDate()+"'),";
														insert_plageHoraire +="'09:00'," +"'13:00'" +",";
														insert_plageHoraire +=typeRetrouve +");\n";	
													}else{
														insert_plageHoraire+="insert into plage_horaire(id_planning_jour,debut_plage, fin_plage, id_motif) values ";
														insert_plageHoraire+="((select id_planning_jour from planning_jour pj, planning_salarie ps,contrat c,point_vente pv,planning_point_vente ppv";
														insert_plageHoraire+=" where pj.id_planning_salarie=ps.id_planning_salarie";
														insert_plageHoraire+=" and ppv.id_planning_point_vente = ps.id_planning_point_vente";
														insert_plageHoraire+=" and ps.id_salarie = c.id_salarie";
														insert_plageHoraire+=" and pv.id_point_vente = ppv.id_point_vente";
														insert_plageHoraire+=" and c.numero_contrat like '"+ligne.getId_contrat();
														insert_plageHoraire+="' and pv.numero_point_vente like '"+ligne.getPoint_vente()+"'";
														insert_plageHoraire+=" and ppv.date_debut ='"+ firstLineAfter.get(0).split(" ")[2] +"'";
														insert_plageHoraire+=" and pj.date = "+"'"+planing.getDate()+"'),";
														insert_plageHoraire +="'14:00'," +"'18:00'" +",";
														insert_plageHoraire +=typeRetrouve +";\n";	
														
													}
												}												
												
											}
											
											incrementation++;
										}
									}else{
										heureSansRetour = planing.getAutre().split("-");
										insert_plageHoraire+="insert into plage_horaire(id_planning_jour,debut_plage, fin_plage, id_motif) values ";
										insert_plageHoraire+="((select id_planning_jour from planning_jour pj, planning_salarie ps,contrat c,point_vente pv,planning_point_vente ppv";
										insert_plageHoraire+=" where pj.id_planning_salarie=ps.id_planning_salarie";
										insert_plageHoraire+=" and ppv.id_planning_point_vente = ps.id_planning_point_vente";
										insert_plageHoraire+=" and ps.id_salarie = c.id_salarie";
										insert_plageHoraire+=" and pv.id_point_vente = ppv.id_point_vente";
										insert_plageHoraire+=" and c.numero_contrat like '"+ligne.getId_contrat();
										insert_plageHoraire+="' and pv.numero_point_vente like '"+ligne.getPoint_vente()+"'";
										insert_plageHoraire+=" and ppv.date_debut ='"+ firstLineAfter.get(0).split(" ")[2] +"'";
										insert_plageHoraire+=" and pj.date = "+"'"+planing.getDate()+"'),";
										insert_plageHoraire +="'"+heureSansRetour[0].replaceAll("h", ":")+"'," +"'"+heureSansRetour[1].replaceAll("h", ":")+"'" +",";
										insert_plageHoraire +="'-1');\n";										
									}
								}else{
									if(planing.getAutre().equals("Repos")){
										//on met tous les champs de la table planning_plage a null	
										insert_plageHoraire+="insert into plage_horaire(id_planning_jour,debut_plage, fin_plage, id_motif) values ";
										insert_plageHoraire+="((select id_planning_jour from planning_jour pj, planning_salarie ps,contrat c,point_vente pv,planning_point_vente ppv";
										insert_plageHoraire+=" where pj.id_planning_salarie=ps.id_planning_salarie";
										insert_plageHoraire+=" and ppv.id_planning_point_vente = ps.id_planning_point_vente";
										insert_plageHoraire+=" and ps.id_salarie = c.id_salarie";
										insert_plageHoraire+=" and pv.id_point_vente = ppv.id_point_vente";
										insert_plageHoraire+=" and c.numero_contrat like '"+ligne.getId_contrat();
										insert_plageHoraire+="' and pv.numero_point_vente like '"+ligne.getPoint_vente()+"'";
										insert_plageHoraire+=" and ppv.date_debut ='"+ firstLineAfter.get(0).split(" ")[2] +"'";
										insert_plageHoraire+=" and pj.date = "+"'"+planing.getDate()+"'),";
										insert_plageHoraire +="''," +"''" +",";
										insert_plageHoraire +="'-2');\n";
										
										insert_planingJour ="";
										insert_planingJour +="insert into planning_jour (id_planning_salarie,date,id_planning_type_jour,contractuel,id_planning_plage,";
										insert_planingJour +="unite,presence_matin,presence_apm,type_contenu,heures_effectuees) values (";
										//recuperartion de id_planning_salarie
										insert_planingJour +="(select id_planning_salarie from planning_salarie ps, planning_point_vente ppv,point_vente pv ";
										insert_planingJour +="where ps.id_planning_point_vente = ppv.id_planning_point_vente";
										insert_planingJour +=" and ppv.id_point_vente = pv.id_point_vente ";
										insert_planingJour +="and ps.id_salarie in (select id_salarie from contrat where numero_contrat like '"+ ligne.getId_contrat();
										insert_planingJour +="') and pv.numero_point_vente like '"+ligne.getPoint_vente() +"' and ppv.date_debut='"+ firstLineAfter.get(0).split(" ")[2] +"'),";
										//recuperation de la date du jour
										insert_planingJour +="'"+planing.getDate()+"',";
										//recuperation de id_planning_type_jour
										insert_planingJour +="(select ptj.id_planning_type_jour from planning_type_jour ptj,planning_type pt, contrat c ";
										insert_planingJour +="where ptj.id_planning_type=pt.id_planning_type ";
										insert_planingJour +="and c.id_contrat=pt.id_contrat ";
										insert_planingJour +="and ptj.numero_jour="+planing.getNum_jour() +" ";
										insert_planingJour +="and c.numero_contrat like '" +ligne.getId_contrat() +"' ";
										insert_planingJour +="and pt.debut <='"+firstLineAfter.get(0).split(" ")[2]+"'";
										insert_planingJour +=" and pt.fin is null),";
										//recuperation de contractuel
										insert_planingJour +="(select ptj.contractuel from planning_type_jour ptj,planning_type pt, contrat c ";
										insert_planingJour +="where ptj.id_planning_type=pt.id_planning_type ";
										insert_planingJour +="and c.id_contrat=pt.id_contrat ";
										insert_planingJour +="and ptj.numero_jour="+planing.getNum_jour() +" ";
										insert_planingJour +="and c.numero_contrat like '" +ligne.getId_contrat() +"' ";
										insert_planingJour +="and pt.debut <='"+firstLineAfter.get(0).split(" ")[2]+"'";
										insert_planingJour +=" and pt.fin is null),";
										//recuperation de id_planning_plage
										insert_planingJour +="(select id_planning_plage from planning_plage pg, point_vente pv ";
										insert_planingJour +="where pg.id_point_vente = pv.id_point_vente ";
										insert_planingJour +="and pg.debut ='"+firstLineAfter.get(0).split(" ")[2]+"'";;
										insert_planingJour +=" and pv.numero_point_vente like '"+ligne.getPoint_vente();
										insert_planingJour +="' and pg.id_salarie in (select id_salarie from contrat where numero_contrat like '"+ligne.getId_contrat() +"')),";;
										//recuperation de l'unite H ou J
										insert_planingJour +="'"+planningDTO.get(nom).getUnite() +"',";
										//presence matin, soir et Normal N ou Abscence A
										insert_planingJour +="-2,-2,'N',";
										//recuperation de nombre d'heure travailler en minuite 
										insert_planingJour +=planing.getNombre_heure();
										insert_planingJour +=");";

										insertionPaning.add(insert_planingJour);
										
									}
									
									if(planing.getAutre().equals("Journée")){
										insert_plageHoraire+="insert into plage_horaire(id_planning_jour,debut_plage, fin_plage, id_motif) values ";
										insert_plageHoraire+="((select id_planning_jour from planning_jour pj, planning_salarie ps,contrat c,point_vente pv,planning_point_vente ppv";
										insert_plageHoraire+=" where pj.id_planning_salarie=ps.id_planning_salarie";
										insert_plageHoraire+=" and ppv.id_planning_point_vente = ps.id_planning_point_vente";
										insert_plageHoraire+=" and ps.id_salarie = c.id_salarie";
										insert_plageHoraire+=" and pv.id_point_vente = ppv.id_point_vente";
										insert_plageHoraire+=" and c.numero_contrat like '"+ligne.getId_contrat();
										insert_plageHoraire+="' and pv.numero_point_vente like '"+ligne.getPoint_vente()+"'";
										insert_plageHoraire+=" and ppv.date_debut ='"+ firstLineAfter.get(0).split(" ")[2] +"'";
										insert_plageHoraire+=" and pj.date = "+"'"+planing.getDate()+"'),";
										insert_plageHoraire +="'09:00:00'," +"'17:00:00'" +",";
										insert_plageHoraire +="'-1');\n";
										
										insert_planingJour ="";
										insert_planingJour +="insert into planning_jour (id_planning_salarie,date,id_planning_type_jour,contractuel,id_planning_plage,";
										insert_planingJour +="unite,presence_matin,presence_apm,type_contenu,heures_effectuees) values (";
										//recuperartion de id_planning_salarie
										insert_planingJour +="(select id_planning_salarie from planning_salarie ps, planning_point_vente ppv,point_vente pv ";
										insert_planingJour +="where ps.id_planning_point_vente = ppv.id_planning_point_vente";
										insert_planingJour +=" and ppv.id_point_vente = pv.id_point_vente ";
										insert_planingJour +="and ps.id_salarie in (select id_salarie from contrat where numero_contrat like '"+ ligne.getId_contrat();
										insert_planingJour +="') and pv.numero_point_vente like '"+ligne.getPoint_vente() +"' and ppv.date_debut='"+ firstLineAfter.get(0).split(" ")[2] +"'),";
										//recuperation de la date du jour
										insert_planingJour +="'"+planing.getDate()+"',";
										//recuperation de id_planning_type_jour
										insert_planingJour +="(select ptj.id_planning_type_jour from planning_type_jour ptj,planning_type pt, contrat c ";
										insert_planingJour +="where ptj.id_planning_type=pt.id_planning_type ";
										insert_planingJour +="and c.id_contrat=pt.id_contrat ";
										insert_planingJour +="and ptj.numero_jour="+planing.getNum_jour() +" ";
										insert_planingJour +="and c.numero_contrat like '" +ligne.getId_contrat() +"' ";
										insert_planingJour +="and pt.debut <='"+firstLineAfter.get(0).split(" ")[2]+"'";
										insert_planingJour +=" and pt.fin is null),";
										//recuperation de contractuel
										insert_planingJour +="(select ptj.contractuel from planning_type_jour ptj,planning_type pt, contrat c ";
										insert_planingJour +="where ptj.id_planning_type=pt.id_planning_type ";
										insert_planingJour +="and c.id_contrat=pt.id_contrat ";
										insert_planingJour +="and ptj.numero_jour="+planing.getNum_jour() +" ";
										insert_planingJour +="and c.numero_contrat like '" +ligne.getId_contrat() +"' ";
										insert_planingJour +="and pt.debut <='"+firstLineAfter.get(0).split(" ")[2]+"'";
										insert_planingJour +=" and pt.fin is null),";
										//recuperation de id_planning_plage
										insert_planingJour +="(select id_planning_plage from planning_plage pg, point_vente pv ";
										insert_planingJour +="where pg.id_point_vente = pv.id_point_vente ";
										insert_planingJour +="and pg.debut ='"+firstLineAfter.get(0).split(" ")[2]+"'";;
										insert_planingJour +=" and pv.numero_point_vente like '"+ligne.getPoint_vente();
										insert_planingJour +="' and pg.id_salarie in (select id_salarie from contrat where numero_contrat like '"+ligne.getId_contrat() +"')),";;
										//recuperation de l'unite H ou J
										insert_planingJour +="'"+planningDTO.get(nom).getUnite() +"',";
										//presence matin, soir et Normal N ou Abscence A
										insert_planingJour +="-1,-1,'N',";
										//recuperation de nombre d'heure travailler en minuite 
										insert_planingJour +=planing.getNombre_heure();
										insert_planingJour +=");";
										insertionPaning.add(insert_planingJour);
										
									}
									//"Journée" "Repos"
									if(!"Journée".equals(planing.getAutre()) && !"Repos".equals(planing.getAutre())){
										String typeAbscence = planing.getAutre();
										 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
											typeAbscence = typeAbscence.replace("\n", " ").replace(".", "_").replace("/", "_").replaceAll(" ", "_");
											 try {
										            Date dateType = formatter.parse(planing.getDate());
										            
										            if(typeAbscenceMapPlanning.containsKey(typeAbscence)){
										            	date = typeAbscenceMapPlanning.get(typeAbscence);
										            	date.add(dateType);
										            }else{
										            	List<Date> newDate = new ArrayList<Date>();
										            	newDate.add(dateType);
										            	typeAbscenceMapPlanning.put(typeAbscence, newDate);
										            }
										            

										            
										        } catch (ParseException e) {
										            e.printStackTrace();
										        }
		
										                                                                        
									}
		
								}
		
								
								insertionPlage.add(insert_plageHoraire);
								
								
								insert_evJour +="insert into ev_jour(id_planning_jour,id_element,code_element,valeur) values (";
								insert_evJour +="select id_planning_jour from planning_jour where id_planning_salarie in (select id_planning_salarie from planning_salarie where id_salarie='"+ligne.getId_contrat();
								insert_evJour +="' and id_planning_point_vente in (select id_planning_point_vente from planning_point_vente where id_point_vente in (";
								insert_evJour +="select id_point_vente from point_vente where numero_point_vente like '"+ligne.getPoint_vente() +"') and date_debut ='"+firstLineAfter.get(0).split(" ")[2] +"')) and date = " +"'"+planing.getDate()+"',";
								insert_evJour +="'000','CODE','000');\n";
								
								insertion_evJour.add(insert_evJour);
								
							}
							
							for(String typeAbscence :typeAbscenceMapPlanning.keySet()){
								List<Date> listeDate = typeAbscenceMapPlanning.get(typeAbscence);
								Date dateDebut = listeDate.get(0);
								Date dateFin = null;
								String insert_planingPlage;
								int typeRetrouve;
								int j = 0;
								for(int i=0;i<listeDate.size();i++){
									Calendar c = Calendar.getInstance(); 
									c.setTime(listeDate.get(i)); 
									c.add(Calendar.DATE, 1);
									Date datr = null;
									if(i < listeDate.size()-1){
										datr = listeDate.get(i+1);
									}else{
										datr = listeDate.get(i);
									}
									
									if(c.getTime().compareTo(datr) == 0){
										 dateFin = listeDate.get(i+1);
										 j++;
									}else{
										if(dateFin == null){
											insert_planingPlage = "<!-- Planing plage pour chaque jour de la semaine " +numSemaine +" pour l'employe "+ nom +" dont la date de debut est "+new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(dateDebut)+" et la date de fin est "+new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(dateDebut)+" -->\n";
											insert_planingPlage +="insert into planning_plage (debut,fin,id_motif,unite,duree_semaine_0,id_salarie,id_point_vente) values (";
											insert_planingPlage +="'"+new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(dateDebut)+"',";
											insert_planingPlage +="'"+new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(dateDebut)+"',";
											typeRetrouve = TypeAbscence.get(typeAbscence);
											if(typeRetrouve == -1){
												String exception = "Le type d'abscence " +typeAbscence +" ne peut pas etre traiter pour l'employe " + nom + "de la semaine du "+numSemaine +" et de la date du " + new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(dateDebut);
												listeExeption.add(exception);
												listeExeption.add(exceptions);
											}else{
												insert_planingPlage +=typeRetrouve+",";
												insert_planingPlage +="'"+planningDTO.get(nom).getUnite() +"',";
												insert_planingPlage +="select id_salarie from contrat where numero_contrat like '"+ligne.getId_contrat() +"',";
												insert_planingPlage +="select id_point_vente from point_vente where numero_point_vente like '" +ligne.getPoint_vente();
												insert_planingPlage +="');";
												insertion_planingPlage.add(insert_planingPlage);

												insert_planingJour ="";
												insert_planingJour +="insert into planning_jour (id_planning_salarie,date,id_planning_type_jour,contractuel,id_planning_plage,";
												insert_planingJour +="unite,presence_matin,presence_apm,type_contenu,heures_effectuees) values (";
												//recuperartion de id_planning_salarie
												insert_planingJour +="(select id_planning_salarie from planning_salarie ps, planning_point_vente ppv,point_vente pv ";
												insert_planingJour +="where ps.id_planning_point_vente = ppv.id_planning_point_vente";
												insert_planingJour +=" and ppv.id_point_vente = pv.id_point_vente ";
												insert_planingJour +="and ps.id_salarie in (select id_salarie from contrat where numero_contrat like '"+ ligne.getId_contrat();
												insert_planingJour +="') and pv.numero_point_vente like '"+ligne.getPoint_vente() +"' and ppv.date_debut='"+ firstLineAfter.get(0).split(" ")[2] +"'),";
												//recuperation de la date du jour
												insert_planingJour +="'"+new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(dateDebut)+"',";
												//recuperation de id_planning_type_jour
												insert_planingJour +="(select ptj.id_planning_type_jour from planning_type_jour ptj,planning_type pt, contrat c ";
												insert_planingJour +="where ptj.id_planning_type=pt.id_planning_type ";
												insert_planingJour +="and c.id_contrat=pt.id_contrat ";
												insert_planingJour +="and ptj.numero_jour="+numJour +" ";
												insert_planingJour +="and c.numero_contrat like '" +ligne.getId_contrat() +"' ";
												insert_planingJour +="and pt.debut <='"+firstLineAfter.get(0).split(" ")[2]+"'";
												insert_planingJour +=" and pt.fin is null),";
												//recuperation de contractuel
												insert_planingJour +="(select ptj.contractuel from planning_type_jour ptj,planning_type pt, contrat c ";
												insert_planingJour +="where ptj.id_planning_type=pt.id_planning_type ";
												insert_planingJour +="and c.id_contrat=pt.id_contrat ";
												insert_planingJour +="and ptj.numero_jour="+numJour +" ";
												insert_planingJour +="and c.numero_contrat like '" +ligne.getId_contrat() +"' ";
												insert_planingJour +="and pt.debut <='"+firstLineAfter.get(0).split(" ")[2]+"'";
												insert_planingJour +=" and pt.fin is null),";
												//recuperation de id_planning_plage
												insert_planingJour +="(select id_planning_plage from planning_plage pg, point_vente pv ";
												insert_planingJour +="where pg.id_point_vente = pv.id_point_vente ";
												insert_planingJour +="and pg.debut ='"+firstLineAfter.get(0).split(" ")[2]+"'";;
												insert_planingJour +=" and pv.numero_point_vente like '"+ligne.getPoint_vente();
												insert_planingJour +="' and pg.id_salarie in (select id_salarie from contrat where numero_contrat like '"+ligne.getId_contrat() +"')),";;
												//recuperation de l'unite H ou J
												insert_planingJour +="'"+planningDTO.get(nom).getUnite() +"',";
												//presence matin, soir et Normal N ou Abscence A
												insert_planingJour +=typeRetrouve+"," +typeRetrouve+",'A',";
												//recuperation de nombre d'heure travailler en minuite 
												insert_planingJour +=nombreHeure;
												insert_planingJour +=");";
												
												insertionPaning.add(insert_planingJour);
												System.out.println("La date de debut est "+ new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(dateDebut) +" et la date de fin est "+new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(dateDebut));
											}
										}else{
											insert_planingPlage = "<!-- Planing plage pour chaque jour de la semaine " +numSemaine +" pour l'employe "+ nom +" dont la date de debut est "+new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(dateDebut)+" et la date de fin est "+new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(dateFin)+" -->\n";
											insert_planingPlage +="insert into planning_plage (debut,fin,id_motif,unite,duree_semaine_0,id_salarie,id_point_vente) values (";
											insert_planingPlage +="'"+new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(dateDebut)+"',";
											insert_planingPlage +="'"+new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(dateFin)+"',";
											typeRetrouve = TypeAbscence.get(typeAbscence);
											if(typeRetrouve == -1){
												String exception = "Le type d'abscence " +typeAbscence +" ne peut pas etre traiter pour l'employe " + nom + "de la semaine du "+numSemaine +" et de la date du " + new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(dateDebut);
												listeExeption.add(exceptions);
												listeExeption.add(exception);
											}else{
											insert_planingPlage +=typeRetrouve+",";
											insert_planingPlage +="'"+planningDTO.get(nom).getUnite() +"',";
											insert_planingPlage +="select id_salarie from contrat where numero_contrat like '"+ligne.getId_contrat() +"',";
											insert_planingPlage +="select id_point_vente from point_vente where numero_point_vente like '" +ligne.getPoint_vente();
											insert_planingPlage +="');";
											insertion_planingPlage.add(insert_planingPlage);
											
											insert_planingJour ="";
											insert_planingJour +="insert into planning_jour (id_planning_salarie,date,id_planning_type_jour,contractuel,id_planning_plage,";
											insert_planingJour +="unite,presence_matin,presence_apm,type_contenu,heures_effectuees) values (";
											//recuperartion de id_planning_salarie
											insert_planingJour +="(select id_planning_salarie from planning_salarie ps, planning_point_vente ppv,point_vente pv ";
											insert_planingJour +="where ps.id_planning_point_vente = ppv.id_planning_point_vente";
											insert_planingJour +=" and ppv.id_point_vente = pv.id_point_vente ";
											insert_planingJour +="and ps.id_salarie in (select id_salarie from contrat where numero_contrat like '"+ ligne.getId_contrat();
											insert_planingJour +="') and pv.numero_point_vente like '"+ligne.getPoint_vente() +"' and ppv.date_debut='"+ firstLineAfter.get(0).split(" ")[2] +"'),";
											//recuperation de la date du jour
											insert_planingJour +="'"+new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(dateDebut)+"',";
											//recuperation de id_planning_type_jour
											insert_planingJour +="(select ptj.id_planning_type_jour from planning_type_jour ptj,planning_type pt, contrat c ";
											insert_planingJour +="where ptj.id_planning_type=pt.id_planning_type ";
											insert_planingJour +="and c.id_contrat=pt.id_contrat ";
											insert_planingJour +="and ptj.numero_jour="+numJour +" ";
											insert_planingJour +="and c.numero_contrat like '" +ligne.getId_contrat() +"' ";
											insert_planingJour +="and pt.debut <='"+firstLineAfter.get(0).split(" ")[2]+"'";
											insert_planingJour +=" and pt.fin is null),";
											//recuperation de contractuel
											insert_planingJour +="(select ptj.contractuel from planning_type_jour ptj,planning_type pt, contrat c ";
											insert_planingJour +="where ptj.id_planning_type=pt.id_planning_type ";
											insert_planingJour +="and c.id_contrat=pt.id_contrat ";
											insert_planingJour +="and ptj.numero_jour="+numJour +" ";
											insert_planingJour +="and c.numero_contrat like '" +ligne.getId_contrat() +"' ";
											insert_planingJour +="and pt.debut <='"+firstLineAfter.get(0).split(" ")[2]+"'";
											insert_planingJour +=" and pt.fin is null),";
											//recuperation de id_planning_plage
											insert_planingJour +="(select id_planning_plage from planning_plage pg, point_vente pv ";
											insert_planingJour +="where pg.id_point_vente = pv.id_point_vente ";
											insert_planingJour +="and pg.debut ='"+firstLineAfter.get(0).split(" ")[2]+"'";;
											insert_planingJour +=" and pv.numero_point_vente like '"+ligne.getPoint_vente();
											insert_planingJour +="' and pg.id_salarie in (select id_salarie from contrat where numero_contrat like '"+ligne.getId_contrat() +"')),";;
											//recuperation de l'unite H ou J
											insert_planingJour +="'"+planningDTO.get(nom).getUnite() +"',";
											//presence matin, soir et Normal N ou Abscence A
											insert_planingJour +=typeRetrouve+"," +typeRetrouve+",'A',";
											//recuperation de nombre d'heure travailler en minuite 
											insert_planingJour +=nombreHeure;
											insert_planingJour +=");";
											insertionPaning.add(insert_planingJour);
											
											System.out.println("La date de debut est "+new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(dateDebut) +" et la date de fin est "+ new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(dateFin));
										}
										
									 }
										if(i < listeDate.size()-1){
											dateDebut = listeDate.get(i+1);
										}
									}
								}
								
								if(j == listeDate.size()){
									insert_planingPlage = "<!-- Planing plage pour chaque jour de la semaine " +numSemaine +" pour l'employe "+ nom +" dont la date de debut est "+new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(dateDebut)+" et la date de fin est "+new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(dateFin)+"  -->\n";
									insert_planingPlage +="insert into planning_plage (debut,fin,id_motif,unite,duree_semaine_0,id_salarie,id_point_vente) values (";
									insert_planingPlage +="'"+new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(dateDebut)+"',";
									insert_planingPlage +="'"+new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(dateFin)+"',";
									typeRetrouve = TypeAbscence.get(typeAbscence);
									if(typeRetrouve == -1){
										String exception = "Le type d'abscence " +typeAbscence +" ne peut pas etre traiter pour l'employe " + nom + "de la semaine du "+numSemaine +" et de la date du " + new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(dateDebut);
										listeExeption.add(exceptions);
										listeExeption.add(exception);
									}else{
									insert_planingPlage +="'"+planningDTO.get(nom).getUnite() +"',";
									insert_planingPlage +="select id_salarie from contrat where numero_contrat like '"+ligne.getId_contrat() +"',";
									insert_planingPlage +="select id_point_vente from point_vente where numero_point_vente like '" +ligne.getPoint_vente();
									insert_planingPlage +="');";
									insertion_planingPlage.add(insert_planingPlage);
									
									//a modifier mettre une boucle
									insert_planingJour ="";
									insert_planingJour +="insert into planning_jour (id_planning_salarie,date,id_planning_type_jour,contractuel,id_planning_plage,";
									insert_planingJour +="unite,presence_matin,presence_apm,type_contenu,heures_effectuees) values (";
									//recuperartion de id_planning_salarie
									insert_planingJour +="(select id_planning_salarie from planning_salarie ps, planning_point_vente ppv,point_vente pv ";
									insert_planingJour +="where ps.id_planning_point_vente = ppv.id_planning_point_vente";
									insert_planingJour +=" and ppv.id_point_vente = pv.id_point_vente ";
									insert_planingJour +="and ps.id_salarie in (select id_salarie from contrat where numero_contrat like '"+ ligne.getId_contrat();
									insert_planingJour +="') and pv.numero_point_vente like '"+ligne.getPoint_vente() +"' and ppv.date_debut='"+ firstLineAfter.get(0).split(" ")[2] +"'),";
									//recuperation de la date du jour
									insert_planingJour +="'"+new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(dateDebut)+"',";
									//recuperation de id_planning_type_jour
									insert_planingJour +="(select ptj.id_planning_type_jour from planning_type_jour ptj,planning_type pt, contrat c ";
									insert_planingJour +="where ptj.id_planning_type=pt.id_planning_type ";
									insert_planingJour +="and c.id_contrat=pt.id_contrat ";
									insert_planingJour +="and ptj.numero_jour="+numJour +" ";
									insert_planingJour +="and c.numero_contrat like '" +ligne.getId_contrat() +"' ";
									insert_planingJour +="and pt.debut <='"+firstLineAfter.get(0).split(" ")[2]+"'";
									insert_planingJour +=" and pt.fin is null),";
									//recuperation de contractuel
									insert_planingJour +="(select ptj.contractuel from planning_type_jour ptj,planning_type pt, contrat c ";
									insert_planingJour +="where ptj.id_planning_type=pt.id_planning_type ";
									insert_planingJour +="and c.id_contrat=pt.id_contrat ";
									insert_planingJour +="and ptj.numero_jour="+numJour +" ";
									insert_planingJour +="and c.numero_contrat like '" +ligne.getId_contrat() +"' ";
									insert_planingJour +="and pt.debut <='"+firstLineAfter.get(0).split(" ")[2]+"'";
									insert_planingJour +=" and pt.fin is null),";
									//recuperation de id_planning_plage
									insert_planingJour +="(select id_planning_plage from planning_plage pg, point_vente pv ";
									insert_planingJour +="where pg.id_point_vente = pv.id_point_vente ";
									insert_planingJour +="and pg.debut ='"+firstLineAfter.get(0).split(" ")[2]+"'";;
									insert_planingJour +=" and pv.numero_point_vente like '"+ligne.getPoint_vente();
									insert_planingJour +="' and pg.id_salarie in (select id_salarie from contrat where numero_contrat like '"+ligne.getId_contrat() +"')),";;
									//recuperation de l'unite H ou J
									insert_planingJour +="'"+planningDTO.get(nom).getUnite() +"',";
									//presence matin, soir et Normal N ou Abscence A
									insert_planingJour +=typeRetrouve+"," +typeRetrouve+",'A',";
									//recuperation de nombre d'heure travailler en minuite 
									insert_planingJour +=nombreHeure;
									insert_planingJour +=");";
									insertionPaning.add(insert_planingJour);
									
									System.out.println("La date de debut est "+new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(dateDebut) +" et la date de fin est "+new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(dateFin));
								}
							}
						}
							

							if(!insertion_planingPlage.isEmpty()) {
								for(String requete : insertion_planingPlage){
									listeInsertion.add(requete);
								}
							}
							
							if(!insertionPaning.isEmpty()) {
								for(String requete : insertionPaning){
									listeInsertion.add(requete);
								}
							}
							
							//listeInsertion.add(insertionPaning.get(insertionPaning.size()-1));
							listeInsertion.add(insertionPlage.get(insertionPlage.size()-1));
							listeInsertion.add(insertion_evJour.get(insertion_evJour.size()-1));
				
							insertionPaning = null;
							insertionPlage = null;
							insertion_evJour = null;				
						}
						
				
				
				
			}
			
			}
			 
		}
		
		
	}
	
	
	static void writeSQLFile(List<String> listeInsertion, String nameFile){
		String fileName = null;
		
        try {
        	
        	File logFile = new File(nameFile);//logFile.sql
    	    if (!logFile.exists()) {
    	  	  logFile.createNewFile();
    		}

    	   fileName = logFile.getAbsolutePath();
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);
            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            // Assume default encoding.
            FileWriter fileWriter = new FileWriter(fileName);
            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for(String line : listeInsertion){
            	 bufferedWriter.write(line +"\n");
            }
            // Always close files.
            bufferedWriter.close();
            // Always close files.
            bufferedReader.close();   
            if(nameFile.endsWith("sql")){
            	System.out.println("Fin de la creation des requetes dans le fichier "+fileName);
            }else{
            	System.out.println("Le fichier des exceptions "+fileName);
            }
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
	
	

	
	
	static void addTree(File file, Collection<File> all) {
	    File[] children = file.listFiles();
	    if (children != null) {
	        for (File child : children) {
	        	if(child.getName().endsWith("xlsx")){
	        		all.add(child);
	        	}else{
		        	addTree(child, all);
	        	}
	            
	        }
	    }
	}
	
	static void addFile(File file, TreeMap<String,File> mapFile) throws ParseException{
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		File[] children = file.listFiles();
		String numMangasin;
		if (children != null) {
			for (File child : children) {
				if(child.getName().endsWith("xlsx")){
					String[] tab = child.getName().split("-");
					String date = tab[0];
					numMangasin = tab[1]+"-"+tab[2];
					if(mapFile.containsKey(numMangasin)){
						File fileTree = mapFile.get(numMangasin);
						String[] tab1 = fileTree.getName().split("-");
						String date1 = tab1[0];
						if(dateFormat.parse(date).after(dateFormat.parse(date1))){
							mapFile.put(numMangasin, child);
						}
						
					}else{
						mapFile.put(numMangasin, child);
					}
	        		
	        	}else{
	        		addFile(child, mapFile);
	        	}
			}
	    }
	}
	
	
	public ArrayList<String> extractExcelContentByColumnIndex(int columnIndex){
		ArrayList<String> columndata = null;
        try {
            File f = new File(excelFilePath);
            FileInputStream ios = new FileInputStream(f);
            XSSFWorkbook workbook = new XSSFWorkbook(ios);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
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
	
	public static Collection<ContratSupprimer> readFileExcelContratSupprimer(String excelFile){
		
		Xcelite xcelite = new Xcelite(new File(excelFile));
		XceliteSheet sheet = xcelite.getSheet("contrats");
		SheetReader<ContratSupprimer> reader = sheet.getBeanReader(ContratSupprimer.class);
		Collection<ContratSupprimer> contratSupprimer = reader.read();

		return contratSupprimer;
		
	}
	
	public static void writeExcelContrat(List<ContratSupprimer> contratSupprimer){
		String fileName ="contratSupprimerRejeter.xlsx";
		Xcelite xcelite = new Xcelite() ;    
		XceliteSheet sheet = xcelite.createSheet("contrats");
		SheetWriter<ContratSupprimer> writer = sheet.getBeanWriter(ContratSupprimer.class);
//		List<ContratSupprimer> contratSupprimer=new ArrayList< ContratSupprimer>();
//		ContratSupprimer contratSup = new ContratSupprimer();
//		contratSup.setEnseigne("33");
//		contratSup.setId_contrat(2222);
//		contratSup.setMatricule(12548);
//		contratSup.setNom("DIOP");
//		contratSup.setPrenom("Ousmane");
//		contratSup.setPoint_vente("aduneo2017");
//		contratSupprimer.add(contratSup);
		writer.write(contratSupprimer); 
		File file = new File(fileName);
		
		xcelite.write(file);
		
		System.out.println("fin de la creation du fichier de rejet des contrat supprimer "+file.getAbsolutePath());

	}
	
}
