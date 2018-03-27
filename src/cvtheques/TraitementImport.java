package cvtheques;

import java.io.Serializable;
import java.util.Date;

import com.ebay.xcelite.annotations.Column;
import com.ebay.xcelite.annotations.Row;


@Row(colsOrder = {"NomPrenom", "date", "tel", "formation","poste","experience","salaire","dispo","observation"})
public class TraitementImport implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column (name="NomPrenom",ignoreType = true)
	private String nomprenom;
	
	@Column (name="date",ignoreType = true)
	private Date date;
	
	@Column (name="tel",ignoreType = true)
	private String tel;
	
	@Column (name="formation",ignoreType = true)
	private String formation;
	
	@Column (name="poste",ignoreType = true)
	private String poste;
	
	@Column (name="experience",ignoreType = true)
	private String experience;
	
	@Column (name="salaire",ignoreType = true)
	private String salaire;
	
	@Column (name="dispo",ignoreType = true)
	private String dispo;
	
	@Column (name="observation",ignoreType = true)
	private String observation;

	public String getNomprenom() {
		return nomprenom;
	}

	public void setNomprenom(String nomprenom) {
		this.nomprenom = nomprenom;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFormation() {
		return formation;
	}

	public void setFormation(String formation) {
		this.formation = formation;
	}

	public String getPoste() {
		return poste;
	}

	public void setPoste(String poste) {
		this.poste = poste;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
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
