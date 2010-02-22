package mountyhall.caracs;

import java.lang.reflect.Field;

/** les gains de caracs qu'une entit� joueuse peut avoir.<br />
 * Contient des acc�s aux valeurs en public<br />
 * Il s'agit d'une simple structure de donn�es<br />
 * @author guigolum
 * 
 */
public class ModifEntite implements Cloneable{
	
	/** points d'attaque physique */
	public int att=0;
	
	/** attaque magique */
	public int attM=0;
	
	/** armure physique */
	public int arm=0;
	
	/** armure magique */
	public int armM=0;
	
	/** d�gats physiques*/
	public int deg=0;
	
	/** d�gats magiques */
	public int degM=0;
	
	/** bm de dla en minutes */
	public int dla=0;
	
	/** esquive*/
	public int esq=0;
	
	/** �tat glu� de l'entit� */
	public boolean glue=false;
	
	/** intangibilit� de l'entit� */
	public boolean intangible=false;
	
	/** gain/perte de pvs � chaque activation */
	public int pvs=0;
	
	/** gain de % de maitrise magique */
	public int mmp=0;
	
 	/** maximum de points de vie */
	public int mPV=0;
	
	/** regeneration */
	public int reg=0;
	
	/** gain de % de resistance magique */
	public int rmp=0;
	
	/** constructeur par d�faut interdit <br />
	 * Au lieu d'utiliser le contructeur par d�faut<br />
	 *  - utiliser la factory
	 *  - utiliser nullValue pour avoir des valeurs nulles*/
	protected ModifEntite() {};
	
	/** valeur par d�faut � renvoyer pour un objet qui ne modifie pas un troll */
	protected static final ModifEntite nullValue = new ModifEntite();
	
	/** renvoie un modifEntite qui ne modifie pas une entit� */
	public static ModifEntite nullValue() {
		try {
			return (ModifEntite) nullValue.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return nullValue;
		}
	}
	
	/** liste des attributs qui sont physiques OU magique sur une entit�
	 * utilis� pour l'affichage*/
	protected static final String listMagique[] = {
		"att", "deg", "arm"
	};
	
	protected static boolean isMagic(Field f) {
		for(String s:listMagique) {
			if(f.getName().startsWith(s)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		String retour="";
		for(Field f : this.getClass().getFields()) {
			if(f.getType()==Boolean.class) {
				try {
					if(! (Boolean)f.get(this)) {
						continue;
					}
				} catch (Exception e) {
					continue;
				}
				if(retour.length()>0) {
					retour+=", ";
				}
				retour+=f.getName();
			} else if(f.getType()== Integer.class) {
				
			}
		}
		return retour;
	}
}
