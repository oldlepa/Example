package ExcelFile;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileSystemView;

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
	
	static String insert_planingJour;
	
	static ArrayList<String> insertionPaning;
	
	static Date dateDebut;
	
	static Integer numJour;
	
	static Integer nombreHeure;
	
	static int typeRetrouve;
	
	static String datDebutSemaint;
	
	static String insert_plageHoraire;
	
	static ArrayList<String> insertionPlage;
	static ArrayList<String> insertion_evJour;
	
	static String insert_planingPlage,exceptions,insert_ev_hebdo,insert_evJour,insert_planning_point_vente;

	
	static boolean verificationJour = false;
	
	static ArrayList<String> insertion_planingPlage;
	
	static String information;

	

	
	
	public static void main(String[] args) throws Exception {

		 information="";
		
		
		information +="**************************************DEBUT DU TRAITEMENT************************************* \n";
		
		 long startTime = System.nanoTime();
		 
		 SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy:MM:ddHH:mm:ss");
	     String dateText = simpleDate.format(startTime);
	     
	     SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy-MM-dd");
		 
		information +="Le programme a d�but� � :" +startTime +"\n";
		 
		information +="Le programme a d�but� � :" +dateText +"\n";
		 
		 TreeMap<String, File> mapFile = new TreeMap<String, File>();
	    
	    Planing_jour planing1 = null;
	    PlanningDTO planingDTO = null;
	    String repertoire1 ="C:\\Users\\odiop\\Desktop\\BPWeb",repertoire2="C:\\Users\\odiop\\Desktop\\Contrats_supprimes.xlsx";
	    
	    JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		int returnValue = jfc.showOpenDialog(null);
		File selectedFile = null;
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			selectedFile = jfc.getSelectedFile();
			information +=(selectedFile.getAbsolutePath());
		}else{
			System.err.println("La g�n�ration est annul�e");
			System.exit(0);
		}

	    
	    //Collection<ContratSupprimer> contratCollection = readFileExcelContratSupprimer(repertoire2);
	    Collection<ContratSupprimer> contratCollection = readFileExcelContratSupprimer(selectedFile.toString());
	    
	    
	    File repertoir = new File(repertoire1);
	    try {
				addFile(repertoir,mapFile);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	    
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
			    	
			    	HashMap<String, List<String>> mapListeTableTest = new HashMap<String,List<String>>();
			    	
			    	//recherche du fichier dans la map des fichier
			    	excelFilePath = sortedMap.get(cleSorted).getPath();
			    	//recupere la premiere colonne (les noms)
				    firstColonne = red.extractExcelContentByColumnIndex(0);
			     
				    //on fait un filtre pour rejeter tous se qui vient apres total de l'equipe
			    	for(String chaine :firstColonne){
						if(chaine.equals("Total de l'�quipe")) break;
						if(chaine.contains("\n")) chaine = chaine.replace(" ", "_");
						firstColonneAfter.add(chaine);
					}
			    	
			    	mapListeTable = XlsxRead_2.getvalue_1(sortedMap.get(cleSorted).getAbsolutePath(),firstColonneAfter.size()+1);

			    	mapListeTableTest = XlsxRead_2.getvalue_1(sortedMap.get(cleSorted).getAbsolutePath(),firstColonne.size()+1);
			    	
			    	
			    	for(String jour : mapListeTable.get("Jour")){
			    		firstLineAfter.add(jour);
			    	}
			    	
			    	for(String nom :firstColonneAfter){
			    		listTOTAL = new ArrayList<>();
			    		listeTOTAL = new ArrayList<>();
			    		listTOTAL = mapListeTable.get(nom.replace(" ", "_").replace("\n", " "));
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
							String []transDateTab = firstLineAfter.get(k).split(" ")[2].split("/");
							String transDate = transDateTab[2]+"-"+transDateTab[1]+"-"+transDateTab[0];
							planing1.setDate(transDate);
							planing1.setAutre(horaire);
							transDateTab = firstLineAfter.get(0).split(" ")[2].split("/");
							transDate = transDateTab[2]+"-"+transDateTab[1]+"-"+transDateTab[0];
							planing1.setFirstDayWeek(transDate);
			    			if(horaire.contains("-")){
			    				String tabHeure[] = horaire.split(" ");
			    				String []tab = new String[tabHeure.length];
			    				
			    				for(int heure=0;heure<tabHeure.length;heure++){
									if(tabHeure[heure].contains("-") && tabHeure[heure].contains("h")){
										tab[heure] = tabHeure[heure];
									}
								}
			    				
			    				List<String> list = new ArrayList<String>(Arrays.asList(tab));
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
			    	
			    	//ajouter dans une map avec comme cl� le nom du magasin et la semaine
					mapInsertionBaseDTO.put(cleSorted, mapPlanningDTO);
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
	   
	   String chemin="";
	   
	   try {
			chemin = createFile("test");
		} catch (IOException e) {
			System.err.println("Le fichier ne peut pas �tre cr��");
			e.printStackTrace();
		}
	   
	   System.out.println("Une interface de r�capitulatif sera affich�.");
	   
	   if (!listeInsertion.isEmpty()) writeSQLFile(listeInsertion,chemin);
	   if (!listeExeption.isEmpty()) writeSQLFile(listeExeption,"ExceptionFile.txt");
	   if (!listeRejetContrat.isEmpty()) writeExcelContrat(listeRejetContrat);
	   
	   long endTime = System.nanoTime();
	   	   
	  information +="Le programme a d�but� � :" +simpleDate.format(endTime) +"\n";
	   
	   long duration = (endTime - startTime);
	   
	  information +="La dur� � la fin de l'execution est :" +simpleDate.format(duration)+"\n";
	   
	  information +="**************************************FIN DU TRAITEMENT*************************************\n";
	   
	  createPanel(information);
	}
	
	
	public static void createPanel(String information){
		JTextArea textArea = new JTextArea(20,25);
	    textArea.setColumns(50);
	    textArea.setLineWrap(true);
	    textArea.setWrapStyleWord(true);
	    textArea.append(information);
	    textArea.setSize(textArea.getPreferredSize().width, 1);
	    JOptionPane.showMessageDialog(null, new JScrollPane( textArea), "Information!",
	        JOptionPane.WARNING_MESSAGE);
	}
	
	

	
	
	
	
/**
 * Traite chaque ligne du fichier excel pour la g�n�ration des requetes
 *
 * @param ligne chaque ligne est transforme en objet contratSupprimer
 * @throws Exception
 * 
 */
	static void recherchePlaningPersonne(ContratSupprimer ligne) throws Exception{
		
		System.out.println("Recherche planning personne.");
		
		HashMap<String,ArrayList<Planing_jour>> planing_jour;
		List<Date> date = null;
		HashMap<String,PlanningDTO> planningDTO;
		List<Planing_jour> planingListe;
		String magasin = ligne.getPoint_vente();
		nombreHeure=0;
		numJour = 0;
		String dateJour = null;
		String nom = ligne.getPrenom()+" "+ligne.getNom();
		Map<String, Integer> MapJourDate = null;
		

		for(int numSemaine=12; numSemaine<19; numSemaine++){
			typeAbscenceMapPlanning = new HashMap<String,List<Date>>();
			date = new ArrayList<>();
			planningDTO = mapInsertionBaseDTO.get(magasin +"-"+ numSemaine);
			
			exceptions = "\n=========================================================================================== \n";
			exceptions += "EXCEPTION RELATIFVE A LA SEMAINE DU " +numSemaine +" POUR LE MAGASIN " +magasin +"\n";
			exceptions += "=========================================================================================== \n";
			
			

			if(planningDTO == null){
				String exception = "le planning jour du point de vente "+ magasin + " pour la semaine du " +numSemaine +" n'existe pas";
				listeExeption.add(exceptions);
				listeExeption.add(exception);
				if(!listeRejetContrat.contains(ligne)) listeRejetContrat.add(ligne);
			}else{
			
					if(!planningDTO.containsKey(nom)){
						String exception = "L'utilisateur "+ nom +" n'a pas de planning dans le magasin " + magasin +" pour la p�riode " +numSemaine;
						listeExeption.add(exceptions);
						
						listeExeption.add(exception);
						
						if(!listeRejetContrat.contains(ligne)) listeRejetContrat.add(ligne);
						
					}else{
						
						planingListe = planningDTO.get(nom).getListePlaningJour();
						if(planingListe == null || planingListe.isEmpty()){
							String exception = "L'utilisateur "+ nom +" est dans le magasin" + magasin +" pour la p�riode " +numSemaine +" mais il n'est pas plannifi�";
							listeExeption.add(exception);
							listeExeption.add(exceptions);
							if(!listeRejetContrat.contains(ligne)) listeRejetContrat.add(ligne);							
						}else{			

							datDebutSemaint = planingListe.get(0).getFirstDayWeek();
							
							String insert_planingSalarie ="\n/*Creation du planning salarie de la semaine " +numSemaine +" pour l'employ� " + nom;
							insert_planingSalarie += " */ \n";
							insert_planingSalarie += "INSERT INTO planning_salarie(id_salarie,id_planning_point_vente,termine) VALUES ( (select id_salarie from contrat where numero_contrat like '" +ligne.getId_contrat() +"'),";
							insert_planingSalarie +="(select id_planning_point_vente from planning_point_vente where id_point_vente in";
							insert_planingSalarie +=" (select id_point_vente from point_vente where numero_point_vente like '"+ligne.getPoint_vente() +"') and date_debut = '"+ datDebutSemaint+"'),";
							insert_planingSalarie +="true);";
							
							listeInsertion.add(insert_planingSalarie);
							
							insert_ev_hebdo(nom,numSemaine,ligne.getId_contrat(), ligne.getPoint_vente(),datDebutSemaint);

							insert_planingJour = "\n/* Requete pour tous les jours de la semaine " +numSemaine +" pour l'employ� " + nom +"  */ \n";
														
							insert_plageHoraire ="\n/* toutes les plages horaires du salari� "+ nom +" pour de la semaine " +numSemaine +" */ \n";
							
							insert_evJour ="\n/* pour tous les jours de la semaine du salari� "+ nom +" */ \n";
																		
							insertionPaning = new ArrayList<>();
							insertionPaning.add(insert_planingJour);
							
							insertionPlage = new ArrayList<>();
							insertionPlage.add(insert_plageHoraire);
							
							insertion_evJour = new ArrayList<>();
							insertion_evJour.add(insert_evJour);
							
							insertion_planingPlage = new ArrayList<>();
							
							MapJourDate = new HashMap<String, Integer>();
							
							for(Planing_jour planing :planingListe){
								
								String []heure = null;
								String []heureSansRetour = null;
								
								nombreHeure = planing.getNombre_heure();
								numJour = planing.getNum_jour();
								dateJour = planing.getDate();
								MapJourDate.put(dateJour, numJour);
								if(nombreHeure == null) nombreHeure = null;
																			
								if(planing.getAutre().contains("-")){
									
									
									insert_planingJour(ligne.getId_contrat(),ligne.getPoint_vente(),planningDTO.get(nom).getUnite(),"-1","N",(planing.getNombre_heure()/60)/60,planing.getDate(),numJour,planing.getDate());
							
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
						
												insert_plageHoraire(ligne.getId_contrat(),ligne.getPoint_vente(),datDebutSemaint,planing.getDate(),"-1",heur);
	
											}else{
												String typeAbscence = heur;
												typeAbscence = typeAbscence.replace("\n", " ").replace(".", "_").replace("/", "_").replaceAll(" ", "_");
												typeRetrouve = TypeAbscence.get(typeAbscence);
												
												if(typeRetrouve == -1){
													String exception = "Le type d'abscence " +typeAbscence +" ne peut pas etre traiter pour l'employe " + nom + "de la semaine du "+numSemaine +" et de la date du " + planing.getDate();
													listeExeption.add(exception);
													listeExeption.add(exceptions);
												}else{

													if(incrementation == 0 && tabHeure.length == 2){
														insert_plageHoraire(ligne.getId_contrat(),ligne.getPoint_vente(),datDebutSemaint,planing.getDate(),"'"+typeRetrouve+"'","09h00-13h00");
													}else{
														insert_plageHoraire(ligne.getId_contrat(),ligne.getPoint_vente(),datDebutSemaint,planing.getDate(),"'"+typeRetrouve+"'","14h00-18h00");
													}
												}												
												
											}
											
											incrementation++;
										}
									}else{
										heureSansRetour = planing.getAutre().split("-");
										String heureSans = heureSansRetour[0]+"-"+heureSansRetour[1];
										insert_plageHoraire(ligne.getId_contrat(),ligne.getPoint_vente(),datDebutSemaint,planing.getDate(),"-1",heureSans);									
									}
								}else{
									if(planing.getAutre().equals("Repos")){
										//on met tous les champs de la table planning_plage a null	
										insert_plageHoraire(ligne.getId_contrat(),ligne.getPoint_vente(),datDebutSemaint,planing.getDate(),"-2","9:00-17:00");
										insert_planingJour(ligne.getId_contrat(),ligne.getPoint_vente(),planningDTO.get(nom).getUnite(),"-2","N",planing.getNombre_heure(),planing.getDate(),numJour,planing.getDate());
									
									}
									
									if(planing.getAutre().equals("Journ�e")){
										insert_plageHoraire(ligne.getId_contrat(),ligne.getPoint_vente(),datDebutSemaint,planing.getDate(),"-2","9:00-17:00");
										insert_planingJour(ligne.getId_contrat(),ligne.getPoint_vente(),planningDTO.get(nom).getUnite(),"-1","N",(planing.getNombre_heure()/60)/60,planing.getDate(),numJour,planing.getDate());
									}
									//"Journ�e" "Repos"
									if(!"Journ�e".equals(planing.getAutre()) && !"Repos".equals(planing.getAutre())){
										String typeAbscence = planing.getAutre();
										 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
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
								
								insert_ev_jour(nom,ligne.getPoint_vente(),ligne.getId_contrat(),datDebutSemaint,planing.getDate());
								
							}
							
							for(String typeAbscence :typeAbscenceMapPlanning.keySet()){
								List<Date> listeDate = typeAbscenceMapPlanning.get(typeAbscence);
								dateDebut = listeDate.get(0);
								Date dateFin = null;
								
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
											String datDF  = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(dateDebut);
											insert_planingPlage(numSemaine,nom, datDF,datDF,typeAbscence,planningDTO.get(nom).getUnite(),ligne.getId_contrat(),ligne.getPoint_vente());
											if(verificationJour){
												insert_planingJour(ligne.getId_contrat(),ligne.getPoint_vente(),planningDTO.get(nom).getUnite(),"'"+TypeAbscence.get(typeAbscence)+"'","A",nombreHeure,new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(dateDebut),MapJourDate.get(datDF),datDF);
												verificationJour = false;
											}
										}else{
											
											insert_planingPlage(numSemaine,nom, new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(dateDebut),new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(dateFin),typeAbscence,planningDTO.get(nom).getUnite(),ligne.getId_contrat(),ligne.getPoint_vente());
											if(verificationJour){
												for(Date datePourJour :listeDate){
													insert_planingJour(ligne.getId_contrat(),ligne.getPoint_vente(),planningDTO.get(nom).getUnite(),"'"+TypeAbscence.get(typeAbscence)+"'","A",nombreHeure,new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(listeDate.get(0)),MapJourDate.get(new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(datePourJour)),new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(datePourJour));
												}
												verificationJour = false;
											}
									 }
										if(i < listeDate.size()-1){
											dateDebut = listeDate.get(i+1);
										}
									}
								}
								
								if(j == listeDate.size()){
									
									insert_planingPlage(numSemaine,nom, new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(dateDebut),new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(dateFin),typeAbscence,planningDTO.get(nom).getUnite(),ligne.getId_contrat(),ligne.getPoint_vente());
									if(verificationJour){
										insert_planingJour(ligne.getId_contrat(),ligne.getPoint_vente(),planningDTO.get(nom).getUnite(),"'"+TypeAbscence.get(typeAbscence)+"'","A",nombreHeure,new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(dateDebut),MapJourDate.get(new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(dateDebut)),new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(dateDebut));
										verificationJour = false;
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
							
							if(!insertionPlage.isEmpty()) {
								for(String requete : insertionPlage){
									listeInsertion.add(requete);
								}
							}
							
							if(!insertion_evJour.isEmpty()) {
								for(String requete : insertion_evJour){
									listeInsertion.add(requete);
								}
							}
							
							update_planning_point_de_vente(datDebutSemaint,ligne.getPoint_vente());
				
							insertionPaning = null;
							insertionPlage = null;
							insertion_evJour = null;				
						}

			}
			
			}
			 
		}
		
		
	}
	
	/***
	 * g�n�re la requete pour planning jour dans la base BPWeb
	 * @param id_contrat
	 * @param point_vent
	 * @param unite (H->pour heure et J-> Pour Jour)
	 * @param typeAbsence (S'il est absence il prend le code, sil est en Repos -2 et -1 sil a travaill� journ�e est consid�re comme travailler)
	 * @param type_contenu (N->normal sil es en Repos ou journ�� et A s'il est absent)
	 * @param heures_effectuees
	 * @param date la date du jour
	 * @version 20092017 ajout de l'order by sur la selection de planning salarie
	 * 
	 */
	static void insert_planingJour(String id_contrat,String point_vent,String unite,String typeAbsence,String type_contenu,Integer heures_effectuees,String dateDebutPlage,int numJour,String dateJour){
		System.out.println("G�n�ration des requ�tes de planning JOUR.");
		
		insert_planingJour ="";
		insert_planingJour +="insert into planning_jour (id_planning_salarie,date,id_planning_type_jour,contractuel,id_planning_plage,";
		insert_planingJour +="unite,presence_matin,presence_apm,type_contenu,heures_effectuees) values (";
		//recuperartion de id_planning_salarie
		insert_planingJour +="(select id_planning_salarie from planning_salarie ps, planning_point_vente ppv,point_vente pv ";
		insert_planingJour +="where ps.id_planning_point_vente = ppv.id_planning_point_vente";
		insert_planingJour +=" and ppv.id_point_vente = pv.id_point_vente ";
		insert_planingJour +="and ps.id_salarie in (select id_salarie from contrat where numero_contrat like '"+ id_contrat;
		insert_planingJour +="') and pv.numero_point_vente like '"+point_vent +"' and ppv.date_debut='"+ datDebutSemaint; 
		insert_planingJour+="' order by id_planning_salarie desc limit 1),";
		//recuperation de la date du jour
		insert_planingJour +="'"+dateJour+"',";
		//recuperation de id_planning_type_jour
		insert_planingJour +="(select ptj.id_planning_type_jour from planning_type_jour ptj,planning_type pt, contrat c ";
		insert_planingJour +="where ptj.id_planning_type=pt.id_planning_type ";
		insert_planingJour +="and c.id_contrat=pt.id_contrat ";
		insert_planingJour +="and ptj.numero_jour="+numJour +" ";
		insert_planingJour +="and c.numero_contrat like '" +id_contrat +"' ";
		insert_planingJour +="and pt.debut <='"+datDebutSemaint+"'";
		insert_planingJour +=" and pt.fin is null),";
		//recuperation de contractuel
		insert_planingJour +="(select ptj.contractuel from planning_type_jour ptj,planning_type pt, contrat c ";
		insert_planingJour +="where ptj.id_planning_type=pt.id_planning_type ";
		insert_planingJour +="and c.id_contrat=pt.id_contrat ";
		insert_planingJour +="and ptj.numero_jour="+numJour +" ";
		insert_planingJour +="and c.numero_contrat like '" +id_contrat +"' ";
		insert_planingJour +="and pt.debut <='"+datDebutSemaint+"'";
		insert_planingJour +="),";
		//recuperation de id_planning_plage avec la date du jour
		insert_planingJour +="(select id_planning_plage from planning_plage pg, point_vente pv ";
		insert_planingJour +="where pg.id_point_vente = pv.id_point_vente ";
		insert_planingJour +="and pg.debut ='"+dateDebutPlage+"'";;
		insert_planingJour +=" and pv.numero_point_vente like '"+point_vent;
		insert_planingJour +="' and pg.id_salarie in (select id_salarie from contrat where numero_contrat like '"+id_contrat +"')),";;
		//recuperation de l'unite H ou J
		insert_planingJour +="'"+unite +"',";
		//presence matin, soir et Normal N ou Abscence A
		insert_planingJour +=typeAbsence+"," +typeAbsence+",'"+type_contenu+"',";
		//recuperation de nombre d'heure travailler en minuite 
		insert_planingJour +=heures_effectuees!=null ? heures_effectuees : 0;
		insert_planingJour +=");";
		insertionPaning.add(insert_planingJour);
	}
	/***
	 * g�n�re la requete pour plage horeaire
	 * @param id_contrat 
	 * @param point_vent
	 * @param dateDebut le debut de la semaine
	 * @param date la date du jour
	 * @param typeAbsence (S'il est absence il prend le code, sil est en Repos -2 et -1 sil a travaill� journ�e est consid�re comme travailler)
	 * @param heur le creneau horaire dans la journ�e
	 */
	
	static void insert_plageHoraire(String id_contrat,String point_vent,String dateDebut,String date,String typeAbsence,String heur){
		System.out.println("G�n�ration de requ�te pour plage horaire.");
		insert_plageHoraire="";
		insert_plageHoraire+="insert into plage_horaire(id_planning_jour,debut_plage, fin_plage, id_motif) values ";
		insert_plageHoraire+="((select id_planning_jour from planning_jour pj, planning_salarie ps,contrat c,point_vente pv,planning_point_vente ppv";
		insert_plageHoraire+=" where pj.id_planning_salarie=ps.id_planning_salarie";
		insert_plageHoraire+=" and ppv.id_planning_point_vente = ps.id_planning_point_vente";
		insert_plageHoraire+=" and ps.id_salarie = c.id_salarie";
		insert_plageHoraire+=" and pv.id_point_vente = ppv.id_point_vente";
		insert_plageHoraire+=" and c.numero_contrat like '"+id_contrat;
		insert_plageHoraire+="' and pv.numero_point_vente like '"+point_vent+"'";
		insert_plageHoraire+=" and ppv.date_debut ='"+ dateDebut +"'";
		insert_plageHoraire+=" and pj.date = "+"'"+date+"'),";
		if(heur.contains("-")){
			insert_plageHoraire +="'"+heur.split("-")[0].replaceAll("h", ":")+"'," +"'"+heur.split("-")[1].replaceAll("h", ":")+"'" +",";
		}else{
			insert_plageHoraire +="''," +"''" +",";
		}
		insert_plageHoraire +="'"+typeAbsence+"');";
		
		insertionPlage.add(insert_plageHoraire);
	}
	
	/**
	 * g�n�re la requete pour planning plage
	 * @param numSemaine le numero de la semaine
	 * @param nom le nom de l'employe
	 * @param dateDebut date de d�but de l'abscence
	 * @param dateFin date de fin de l'abscence
	 * @param typeAbscence (S'il est absence il prend le code, sil est en Repos -2 et -1 sil a travaill� journ�e est consid�re comme travailler)
	 * @param unite (H->pour heure et J-> Pour Jour)
	 * @param id_contrat
	 * @param point_vent
	 */
	
	static void insert_planingPlage(int numSemaine,String nom, String dateDebut,String dateFin,String typeAbscence,String unite,String id_contrat,String point_vent){
		System.out.println("G�n�ration de requ�te pour planning plage");
		insert_planingPlage = "\n/* Planing plage pour chaque jour de la semaine " +numSemaine +" pour l'employe "+ nom +" dont la date de debut est "+dateDebut+" et la date de fin est "+dateFin+" */\n";
		insert_planingPlage +="insert into planning_plage (debut,fin,id_motif,unite,duree_semaine_0,id_salarie,id_point_vente) values (";
		insert_planingPlage +="'"+dateDebut+"',";
		insert_planingPlage +="'"+dateFin+"',";
		typeRetrouve = TypeAbscence.get(typeAbscence);
		if(typeRetrouve == -1){
			String exception = "Le type d'abscence " +typeAbscence +" ne peut pas etre traiter pour l'employe " + nom + "de la semaine du "+numSemaine +" et de la date du " + dateDebut;
			listeExeption.add(exception);
			listeExeption.add(exceptions);
		}else{
			insert_planingPlage +=typeRetrouve+",";
			insert_planingPlage +="'"+unite +"',";
			insert_planingPlage +="0,";
			insert_planingPlage +="(select id_salarie from contrat where numero_contrat like '"+id_contrat +"'),";
			insert_planingPlage +="(select id_point_vente from point_vente where numero_point_vente like '" +point_vent;
			insert_planingPlage +="'));";
			insertion_planingPlage.add(insert_planingPlage);
			verificationJour = true;
		}
	}
	
	/**
	 * Permet de g�n�rer les requetes pour hev_hebdo
	 * @author odiop
	 * @param nom
	 * @param numSemaine
	 * @param numContrat
	 * @param pointVente
	 * @param date
	 */
	static void insert_ev_hebdo(String nom, int numSemaine,String numContrat, String pointVente, String date){
		System.out.println("G�n�ration de requ�te pour evenement hebdo.");
		insert_ev_hebdo = "\n/* Cr�ation de l'�venement Hebdo de l'employe "+ nom +"pour la semaine du " + numSemaine +" */ \n";
		insert_ev_hebdo += "insert into ev_hebdo(id_planning_salarie,id_element,code_element,valeur) values (";
		insert_ev_hebdo += "(select id_planning_salarie from planning_salarie ps, contrat c,planning_point_vente ppv,point_vente pv where ps.id_salarie = c.id_salarie";
		insert_ev_hebdo +=" and ps.id_planning_point_vente = ppv.id_planning_point_vente ";
		insert_ev_hebdo +=" and ppv.id_point_vente = pv.id_point_vente ";
		insert_ev_hebdo +=" and c.numero_contrat like '"+ numContrat;
		insert_ev_hebdo +="' and pv.id_point_vente like '"+pointVente;
		insert_ev_hebdo +="' and date_debut ='"+date;
		insert_ev_hebdo +="')";
		insert_ev_hebdo +=", '000', 'CODE','000');";
		listeInsertion.add(insert_ev_hebdo);
	}
	
	/**
	 * Permet de g�n�rer les requetes pour ev_jour.
	 * @author odiop
	 * @param nom
	 * @param pointvente
	 * @param numContrat
	 * @param date_debut
	 * @param date
	 */
	static void insert_ev_jour(String nom,String pointvente,String numContrat,String date_debut,String date){
		System.out.println("G�n�ration de requete pour evenement jour.");
		insert_evJour ="insert into ev_jour(id_planning_jour,id_element,code_element,valeur) values (";
		insert_evJour +="(select id_planning_jour from planning_jour pj, planning_salarie ps,planning_point_vente ppv,point_vente pv ";
		insert_evJour +="where pj.id_planning_salarie = ps.id_planning_salarie ";
		insert_evJour +="and ps.id_planning_point_vente = ppv.id_planning_point_vente ";
		insert_evJour +="and ppv.id_point_vente = pv.id_point_vente ";
		insert_evJour +="and pv.numero_point_vente like '"+pointvente;
		insert_evJour +="' and ps.id_salarie='"+numContrat;
		insert_evJour +="' and date_debut ='"+date_debut;
		insert_evJour +="' and date ='"+date;
		insert_evJour +="'),'000','CODE','000');";
		
		insertion_evJour.add(insert_evJour);
		
	}
	
	/**
	 * Permet de d�valider les semaines
	 * @author odiop
	 * @param date_debut_semaine
	 * @param point_vente
	 */
	static void update_planning_point_de_vente(String date_debut_semaine,String point_vente){
		insert_planning_point_vente ="\n/*cr�ation de la requ�te de modification de la table planning_point_vente pour la semaine "+date_debut_semaine+" */ \n";;
		insert_planning_point_vente +="UPDATE planning_point_vente SET etat=0";
		insert_planning_point_vente +=" where date_debut='"+date_debut_semaine+"' and id_point_vente in (select id_point_vente from point_vente where numero_point_vente like '"+point_vente+"');";
		listeInsertion.add(insert_planning_point_vente);
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
            	information +="Fin de la creation des requetes dans le fichier "+fileName +"\n";
            }else{
            	information +="Le fichier des exceptions "+fileName+"\n";
            }
        }
        catch(FileNotFoundException ex) {
           information +=
                "Unable to open file '" + 
                fileName + "'\n";                
        }
        catch(IOException ex) {
           information +=
                "Error reading file '" 
                + fileName + "'\n";                  
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
		//DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		File[] children = file.listFiles();
		String numMangasin;
		if (children != null) {
			for (File child : children) {
				if(child.getName().endsWith("xlsx")){
					String[] tab = child.getName().split("-");
					String date = tab[0];
					//if(date.contains("~$")) date = date.substring(2, date.length());
					numMangasin = tab[1]+"-"+tab[2];
					if(mapFile.containsKey(numMangasin)){
						File fileTree = mapFile.get(numMangasin);
						String[] tab1 = fileTree.getName().split("-");
						String date1 = tab1[0];
						if(dateFormat.parse(date.replaceAll("[-+.^:,~$]","")).after(dateFormat.parse(date1.replaceAll("[-+.^:,~$]","")))){
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
		System.out.println("Lecture du fichier d'import.");
		
		Xcelite xcelite = new Xcelite(new File(excelFile));
		XceliteSheet sheet = xcelite.getSheet("contrats");
		SheetReader<ContratSupprimer> reader = sheet.getBeanReader(ContratSupprimer.class);
		Collection<ContratSupprimer> contratSupprimer = reader.read();

		return contratSupprimer;
		
	}
	
	public static void writeExcelContrat(List<ContratSupprimer> contratSupprimer){
		System.out.println("Ecriture dans le fichier de rejet.");
		
		String fileName ="contratSupprimerRejeter.xlsx";
		Xcelite xcelite = new Xcelite() ;    
		XceliteSheet sheet = xcelite.createSheet("contrats");
		SheetWriter<ContratSupprimer> writer = sheet.getBeanWriter(ContratSupprimer.class);
		writer.write(contratSupprimer); 
		File file = new File(fileName);
		
		xcelite.write(file);
		
		information +="fin de la creation du fichier de rejet des contrat supprimer "+file.getAbsolutePath() +"\n";

	}
	
	
	
   public static String createFile(String fileName) throws IOException{
	   
	   System.out.println("Cr�ation du fichier sql");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		
		String chemin = Paths.get("").toAbsolutePath().toString() + File.separator +"requete";
		
		String filePath = chemin + File.separator + fileName + dateFormat.format(date) +".sql";

		Path path = Paths.get(convertToAbsolutePath(filePath));
		
    	
    	Files.createDirectories(path.getParent());
    	
    	try {
            Files.createFile(path);
        } catch (FileAlreadyExistsException e) {
            System.err.println("already exists: " + e.getMessage());
        }
    	
    	File file = new File(filePath);
    	
    	file.getParentFile().mkdirs();
    	
    	file.createNewFile();
    	
    	return file.getAbsolutePath();
	}	
	
	
	public static String convertToAbsolutePath(String fileName) {

	    boolean absolute = false;

	    if (fileName.startsWith("/") || fileName.startsWith("\\") || fileName.startsWith("//") || fileName.startsWith("\\\\")) {
	      absolute = true;
	    } else if (fileName.length() > 1) {
	      if (fileName.charAt(1) == ':') absolute = true;
	    }

	    File file = new File(fileName);

	    String absolutePath = null;
	    try {
	      absolutePath = file.getCanonicalPath();
	    } catch (IOException e) {
	    }

	    return absolutePath;
	  }
	
}
