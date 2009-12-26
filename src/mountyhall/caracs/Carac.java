package mountyhall.caracs;

/**
 * Une caractéristique d'une entité, ou d'un objet
 * @author guigolum
 */
public abstract class Carac {
	
	/** retourne le nom de la caractéristique */
	public String name() {
		return this.getClass().getSimpleName().toLowerCase();
	}
	
	/** retourne le nomcours de la caractéristique*/
	public abstract String shortName();
	
	

	protected int physique=0;
	/** @return le composant physique de cette carac */
	public int physique() {
		return physique;
	}
	public void physique(int val) {
		physique = val;
	}
	
	protected int magique=0;
	/** @return le composant magique de cette carac */
	public int magique() {
		return magique;
	}
	public void magique(int val) {
		magique = val;
	}
	
	/** comment la carac génère des valeurs par rapport à la base*/
	protected abstract BaseType basetype();
	
	protected int base=0;
	/** @return le composant de base de cette carac<br />
	 * Le composant de base correspond à l'invesissement pour un troll<br />
	 * Par exemple, un tom lvl 1 a sa carac vue qui a une base de 4.<br />
	 * De même, un skrim a une attaque de base de 4( = 4D6 d'attaque).
	 */
	public int base() {
		return base;
	}
	public void base(int val) {
		base = val;
	}
	
	protected int modifiedBase=0;
	/** @return la base modifiée de cette carac <br />
	 *  cela correspond à la base après les bm.<br />
	 *  Un trollinet qui aura subi une frappe sera à 2 (D6) en esq par exemple.
	 */
	public int modifiedBase() {
		return modifiedBase;
	}
	public void modifiedBase(int val) {
		modifiedBase = val;
	}
	
	protected int percent=0;
	/** @return le gain supplémentaire en % de cette carac.<br />
	 * Pour Deg, vue,  il s'agit de 0.<br />
	 * Pour la rm/mm, il s'agit de la somme des gains des objets<br />
	 * Ce gain est ajouté à la base modifiée.
	 */
	public int percent() {
		return percent;
	}
	public void percent(int val) {
		percent = val;
	}
	
	
	
	/** @return la valeur moyenne de cette carac sur un jet physique<br />
	 * Les valeurs magiques sont prises en compte*/
	public double meanPhys() {
		return meanMag()+physique();
	}
	
	/** @return la valeur moyenne de cette carac sur un jet magique<br />
	 * Vaut 0 pour la plupart des caracs sauf Deg, Att, Arm, Esq*/
	public double meanMag() {
		return basetype().meanVal(modifiedBase())*(1+(double)percent()/100)+magique();
	}
	
	
	/** Remet à 0 les bm */
	public void resetToBase() {
		modifiedBase = base;
		physique= 0;
		magique = 0;
		percent = 0;
	}
	
	/** applique les valeurs de la carac à une carac cible.<br />
	 * Correspond à l'ajout des valeurs.<br />
	 * Permet d'appliquer facilement des bm.
	 */
	public void apply(Carac other) {
		other.modifiedBase(modifiedBase()+other.modifiedBase());
		other.physique(physique()+other.physique());
		other.magique(magique()+other.magique());
		other.percent(percent()+other.percent());
	}
	
	
	public String printSigned(int n) {
		if(n>=0) {
			return "+"+n;
		} else {
			return ""+n;
		}
	}
	
	public String show(boolean expensed) {
		if(modifiedBase()==0 && physique()==0 && magique()==0 && !expensed) {
			return "";
		}
		String retour=shortName()+(expensed?" : ":":")+basetype().format(modifiedBase());
		if(expensed) retour+=" ";
		if(physique()!=0 || magique()!=0 || expensed) {
			retour+=printSigned(physique());
		}
		if(magique() != 0 || expensed) {
			retour +="/"+printSigned(magique());
		}
		return retour;
	}
	public String toString() {
		return show(true);
	}
	
	public String shortView() {
		return show(false);
	}
}
