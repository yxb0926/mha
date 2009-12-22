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
	 * De même, un skrim a une attaque de base de 4.
	 */
	public int base() {
		return base;
	}
	public void base(int val) {
		base = val;
	}
	
	protected int percent=0;
	/** @return le gain supplémentaire en % de cette carac.<br />
	 * Pour Deg, vue,  il s'agit de 0.<br />
	 * Pour la rm/mm, il s'agit de la somme des gains des objets*/
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
		return basetype().meanVal(base())+magique();
	}
	

	public String toString() {
		if(base()==0 && physique()==0 && magique()==0) {
			return "";
		}
		String retour=shortName()+" : "+basetype().format(base());
		if(physique()!=0 || magique()!=0) {
			retour+=" +"+physique();
		}
		if(magique() != 0) {
			retour +="/"+magique();
		}
		return retour;
	}
}
