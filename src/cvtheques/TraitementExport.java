package cvtheques;

import java.io.Serializable;

import com.ebay.xcelite.annotations.Column;
import com.ebay.xcelite.annotations.Row;

@Row(colsOrder = {"mail","nom","prenom","civilite","tel","date1","date2","codeFormation","salaire","dispo","codeExp","codePoste","codeSource","codeStatut","recruteur","observation"})
public class TraitementExport implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column (name="mail",ignoreType = true)
	private String mail;
	
	@Column (name="nom",ignoreType = true)
	private String nom;
	
	@Column (name="prenom",ignoreType = true)
	private String prenom;
	
	@Column (name="civilite",ignoreType = true)
	private String civilite;
	
	@Column (name="tel",ignoreType = true)
	private String tel;
	
	@Column (name="date1",ignoreType = true)
	private String date1;
	
	@Column (name="date2",ignoreType = true)
	private String date2;
	
	@Column (name="codeFormation",ignoreType = true)
	private String codeFormation;
	
	@Column (name="salaire",ignoreType = true)
	private String salaire;
	
	@Column (name="dispo",ignoreType = true)
	private String dispo;
	
	@Column (name="codeExp",ignoreType = true)
	private String codeExp;
	
	@Column (name="codePoste",ignoreType = true)
	private String codePoste;
	
	@Column (name="codeSource",ignoreType = true)
	private String codeSource;
	
	@Column (name="codeStatut",ignoreType = true)
	private String codeStatut;
	
	@Column (name="recruteur",ignoreType = true)
	private String recruteur;
	
	@Column (name="observation",ignoreType = true)
	private String observation;

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getCivilite() {
		return civilite;
	}

	public void setCivilite(String civilite) {
		this.civilite = civilite;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getDate1() {
		return date1;
	}

	public void setDate1(String date1) {
		this.date1 = date1;
	}

	public String getDate2() {
		return date2;
	}

	public void setDate2(String date2) {
		this.date2 = date2;
	}

	public String getCodeFormation() {
		return codeFormation;
	}

	public void setCodeFormation(String codeFormation) {
		this.codeFormation = codeFormation;
	}

	public String getSalaire() {
		return salaire;
	}

	public void setSalaire(String salaire) {
		this.salaire = salaire;
	}

	public String getDispo() {
		return dispo;
	}

	public void setDispo(String dispo) {
		this.dispo = dispo;
	}

	public String getCodeExp() {
		return codeExp;
	}

	public void setCodeExp(String codeExp) {
		this.codeExp = codeExp;
	}

	public String getCodePoste() {
		return codePoste;
	}

	public void setCodePoste(String codePoste) {
		this.codePoste = codePoste;
	}

	public String getCodeSource() {
		return codeSource;
	}

	public void setCodeSource(String codeSource) {
		this.codeSource = codeSource;
	}

	public String getCodeStatut() {
		return codeStatut;
	}

	public void setCodeStatut(String codeStatut) {
		this.codeStatut = codeStatut;
	}

	public String getRecruteur() {
		return recruteur;
	}

	public void setRecruteur(String recruteur) {
		this.recruteur = recruteur;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	

}
