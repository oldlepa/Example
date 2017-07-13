package ExcelFile;

import java.util.List;

public class PlanningDTO {

	private List<Planing_jour> listePlaningJour;
	
	private String unite;

	public PlanningDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<Planing_jour> getListePlaningJour() {
		return listePlaningJour;
	}

	public void setListePlaningJour(List<Planing_jour> listePlaningJour) {
		this.listePlaningJour = listePlaningJour;
	}

	public String getUnite() {
		return unite;
	}

	public void setUnite(String unite) {
		this.unite = unite;
	}
	
	
}
