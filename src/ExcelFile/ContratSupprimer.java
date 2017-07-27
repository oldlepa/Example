package ExcelFile;

import java.io.Serializable;

import com.ebay.xcelite.annotations.Column;
import com.ebay.xcelite.annotations.Row;

@Row(colsOrder = {"Matricule", "numContrat", "Enseigne", "PointVente","Nom","Prenom","id_salarie"})
public class ContratSupprimer implements Serializable{
	//matricule de l'utilisateur
	@Column (name="Matricule",ignoreType = true)
	private Integer matricule;
	
	@Column (name="numContrat",ignoreType = true)
	private Integer numContrat;
	
	@Column (name="Enseigne",ignoreType = true)
	private String enseigne;
	
	@Column (name="PointVente",ignoreType = true)
	private String point_vente;
	
	@Column (name="Nom",ignoreType = true)
	private String nom;
	
	@Column (name="Prenom",ignoreType = true)
	private String prenom;
	
	@Column (name="id_salarie",ignoreType = true)
	private String id_contrat;

	public ContratSupprimer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getMatricule() {
		return matricule;
	}

	public void setMatricule(Integer matricule) {
		this.matricule = matricule;
	}

	public Integer getNumContrat() {
		return numContrat;
	}

	public void setNumContrat(Integer numContrat) {
		this.numContrat = numContrat;
	}

	public String getEnseigne() {
		return enseigne;
	}

	public void setEnseigne(String enseigne) {
		this.enseigne = enseigne;
	}

	public String getPoint_vente() {
		return point_vente;
	}

	public void setPoint_vente(String point_vente) {
		this.point_vente = point_vente;
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

	public String getId_contrat() {
		return id_contrat;
	}

	public void setId_contrat(String id_contrat) {
		this.id_contrat = id_contrat;
	}

	@Override
	public String toString() {
		return "ContratSupprimer [matricule=" + matricule + ", numContrat=" + numContrat + ", enseigne=" + enseigne
				+ ", point_vente=" + point_vente + ", nom=" + nom + ", prenom=" + prenom + ", id_contrat=" + id_contrat
				+ "]";
	}
	
	
	
	
}
