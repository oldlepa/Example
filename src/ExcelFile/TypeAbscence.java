package ExcelFile;

import java.util.HashMap;
import java.util.Map;

public enum TypeAbscence {

	abs__autorisée_non_payée(0),
	abs__autorisée_payée(1),
	abs__congé_parental_présence(3),
	abs__création_entreprise(4),
	abs__congé_sabbatique(5),
	abs__congé_enfant_malade_non_payé(6),
	abs__congé_paternité(7),
	abs__congé_adoption(10),
	abs__accident_travail(11),
	abs__accident_trajet(12),
	abs__congé_décès(13),
	abs__congé_déménagement(14),
	abs__congé_ind__form__payé_CIF(17),
	abs__maladie(18),
	abs__congé_mariage(19),
	abs__maternité(20),
	abs__MI_TP_thérapeutique(21),
	abs__congé_naissance(22),
	abs__congé_pathologique(23),
	abs__congés_payés(34),
	abs__hrs_visite_médicale(36),
	abs__injustifiée(37),
	abs_sans_contrat(48),
	abs__Formation(50),
	abs__jour_férié(54),
	abs_réunion_commerciale(60),
	abs_école_alternance(63),
	abs_congé_PACS(64),
	Récup_temps_trajet(82),
	abs_recup_jour_férié(80),
	abs__journée_RTT(90),
	abs_congé_ancienneté(92),
	abs_autre_IRP(93),
	abs_réunion_IRP(94),
	Réunion_NEGOCIATION(101),
	Délég_CE(111),
	Délég_DP(112),
	Délég_CHSCT(113),
	Délég_SYNDICALE(114),
	Délég_CCE(115),
	Délég_GROUPE(116),
	en_attente_de_justificatif(-3),
	abs__récup_dimanche(-10),
	abs__événements_familiaux(200),
	délégation(201),
	abs__congé_enfant_malade(202),
	Sans_contrat(203);

	
	
	
	
	private final int value;
	
    static  Map<String, Integer> lookup = new HashMap<String, Integer>();
    
    static {
        for (TypeAbscence abscence : TypeAbscence.values()) {
            lookup.put(abscence.toString(), abscence.value);
        }
    }

	private TypeAbscence(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static int get(String value) {
		Integer t = lookup.get(value);
		if(t == null) return -1;
        return lookup.get(value);
    }
    
    
    
	
}
