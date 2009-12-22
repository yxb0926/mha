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
	
	public static final BaseType
		points = new BaseType() {
			public String format(int baseVal) {
				if(baseVal==0) {
					return "";
				}
				return ""+baseVal;
			}
			public double meanVal(int baseVal) {
				return baseVal;
			}
		},
		d6 = new BaseType() {
			public String format(int baseVal) {
				return ""+baseVal+"D6";
			}
			public double meanVal(int baseVal) {
				return 3.5*baseVal;
			}
		},
		d3 = new BaseType() {
			public String format(int baseVal) {
				return ""+baseVal+"D3";
			}
			public double meanVal(int baseVal) {
				return 2*baseVal;
			}
		},
		none = new BaseType() {
			public String format(int baseVal) {
				return "";
			}
			public double meanVal(int baseVal) {
				return 0.0;
			}
		},
		/** le type de la dla, qui est un peu spécial */
		dla = new BaseType() {
			@Override
			public String format(int baseVal) {
				String retour="";
				int hour=baseVal/120;
				
				return retour;
			}
			@Override
			public double meanVal(int baseVal) {
				// TODO Auto-generated method stub
				return 0;
			}
		}
		;

}
