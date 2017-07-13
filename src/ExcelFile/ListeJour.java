package ExcelFile;

import java.util.*;

public enum ListeJour {

	Lundi(0),Mardi(1),Mercredi(2),
	Jeudi(3),Vendredi(4),Samedi(5),
	Dimanche(6);
	
	private final int value;
	
    static  Map<String, Integer> lookup = new HashMap<String, Integer>();
	
	static {
        for (ListeJour jour : ListeJour.values()) {
            lookup.put(jour.toString(), jour.value);
        }
    }
	
	private ListeJour(int value){
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	
	public static int get(String value) {
		Integer t = lookup.get(value);
        return t;
    }
	
}
