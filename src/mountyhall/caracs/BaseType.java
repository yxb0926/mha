package mountyhall.caracs;

/** représente la partie personnelle d'une carac<br />
 * Pour un troll, cela représente le nombre de D, par exemple, d'esquive.
 * 
 * @author guigolum
 *
 */
public abstract class BaseType {
	
	/** Affiche la partie "base" d'une carac.<br />
	 * exemple : affichera 3D3 pour un durakuir lvl 1
	 */
	public abstract String format(int baseVal);
	
	/** génère la moyenne des lancers de base sur une carac de cette base<br />
	 * @param baseVal la valeur de base de la carac<br />
	 */
	public abstract double meanVal(int baseVal);
	
		/** caractéristiques sans D, comme l'armure, la vue, la mm. */
	public static final BaseType points = new BaseType() {
			public String format(int baseVal) {
				if(baseVal==0) {
					return "";
				}
				return ""+baseVal;
			}
			public double meanVal(int baseVal) {
				return baseVal;
			}
		};
	
		/** caractértistiques attaques et esquive qui utilisent des D6*/
	public static final BaseType d6 = new BaseType() {
			public String format(int baseVal) {
				return ""+baseVal+"D6";
			}
			public double meanVal(int baseVal) {
				return 3.5*baseVal;
			}
		};
	
		/** caractéristique dégat et regen, à D3 */
	public static final BaseType d3 = new BaseType() {
			public String format(int baseVal) {
				return ""+baseVal+"D3";
			}
			public double meanVal(int baseVal) {
				return 2*baseVal;
			}
		};
	public static final BaseType none = new BaseType() {
			public String format(int baseVal) {
				return "";
			}
			public double meanVal(int baseVal) {
				return 0.0;
			}
		};
		
	/** la dla est un peu spéciale puisqu'il n'y a pas de jet de dla<br />
	 * Et qu'il faut traduire l'heure*/
	public static final BaseType dla = new BaseType() {
			@Override
			public String format(int baseVal) {
				String retour="";
				int hours=baseVal/120;
				int minutes = (baseVal-hours*120)/2;
				if(hours>0) {
					retour+= hours+"H";
				}
				if(minutes>0 ) {
					if(retour.length()>0) {retour+= " ";}
					retour+=minutes+"min";
				}
				return retour;
			}
			@Override
			public double meanVal(int baseVal) {
				return 0;
			}
		}
		;

}
