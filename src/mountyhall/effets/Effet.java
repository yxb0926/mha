package mountyhall.effets;

import java.lang.reflect.Field;

import mountyhall.Entite;
import mountyhall.caracs.Carac;
import mountyhall.caracs.Etat;

/** Un effet est un ensemble de modifications qui s'appliquent à une entité.<br />
 * Il est constituré des bonus-malus, et d'un état, à appliquer à cette entité.<br />
 * @author guigolum
 *
 */
public abstract class Effet {
	/** le temps d'application restant du pouvoir */
	int remainingdla=0;
	
	/** si le pouvoir doit être conservé en mémoire */
	public boolean remains() {
		return remainingdla>=0;
	}
	
	/** etat qu'applique cet effet à l'entité.<br />
	 * Par défaut, rien ne change.
	 */
	protected Etat etat = new Etat();
	
	public Etat etat() {
		return etat;
	}
	
	/** applique l'effet à une entité <br />
	 * @param e L'entité à modifier.
	 */
	public void apply(Entite e) {
		
		etat.apply(e.etat());
		
		for(Field f : this.getClass().getFields()) {
			if(Carac.class.isAssignableFrom(f.getClass())) {
				try {
					Carac c = (Carac) f.get(this);
					c.apply(e.carac(c.shortName()));
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public String toString() {
		String retour = this.getClass().getSimpleName()+ "("+remainingdla+"): ";

		for(Field f : this.getClass().getDeclaredFields()) {
			if(Carac.class.isAssignableFrom(f.getType())) {
				try {
					Carac c = (Carac) f.get(this);
					retour+=c.shortView();
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		String setat = etat.toString();
		if(setat.length()>0) {
			retour+="; "+setat;
		}
		return retour;
	}
}
