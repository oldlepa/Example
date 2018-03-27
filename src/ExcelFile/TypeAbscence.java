package ExcelFile;

import java.util.HashMap;
import java.util.Map;

public enum TypeAbscence {

	abs__autoris�e_non_pay�e(0),
	abs__autoris�e_pay�e(1),
	abs__cong�_parental_pr�sence(3),
	abs__cr�ation_entreprise(4),
	abs__cong�_sabbatique(5),
	abs__cong�_enfant_malade_non_pay�(6),
	abs__cong�_paternit�(7),
	abs__cong�_adoption(10),
	abs__accident_travail(11),
	abs__accident_trajet(12),
	abs__cong�_d�c�s(13),
	abs__cong�_d�m�nagement(14),
	abs__cong�_ind__form__pay�_CIF(17),
	abs__maladie(18),
	abs__cong�_mariage(19),
	abs__maternit�(20),
	abs__MI_TP_th�rapeutique(21),
	abs__cong�_naissance(22),
	abs__cong�_pathologique(23),
	abs__cong�s_pay�s(34),
	abs__hrs_visite_m�dicale(36),
	abs__injustifi�e(37),
	abs_sans_contrat(48),
	abs__Formation(50),
	abs__jour_f�ri�(54),
	abs_r�union_commerciale(60),
	abs_�cole_alternance(63),
	abs_cong�_PACS(64),
	R�cup_temps_trajet(82),
	abs_recup_jour_f�ri�(80),
	abs__journ�e_RTT(90),
	abs_cong�_anciennet�(92),
	abs_autre_IRP(93),
	abs_r�union_IRP(94),
	R�union_NEGOCIATION(101),
	D�l�g_CE(111),
	D�l�g_DP(112),
	D�l�g_CHSCT(113),
	D�l�g_SYNDICALE(114),
	D�l�g_CCE(115),
	D�l�g_GROUPE(116),
	en_attente_de_justificatif(-3),
	abs__r�cup_dimanche(-10),
	abs__�v�nements_familiaux(49),
	d�l�gation(103),
	abs__cong�_enfant_malade(84),
	Sans_contrat(48);

	
	
	
	
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
