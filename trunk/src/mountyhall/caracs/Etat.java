package mountyhall.caracs;

import java.lang.reflect.Field;

/** les choses qui sont appliquabls à une entité */
public class Etat {
	
	/** si l'entité est intangible */
	public boolean intangible = false;
	
	/** si l'entité est sous l'effet d'un sort de glue */
	public boolean glue = false;
	
	/** si l'entité est en levitation */
	public boolean levitation = false;
	
	/** si l'entité est camouflée */
	public boolean camoufle = false;
	
	/** si l'entité est invisible */
	public boolean invisible = false;
	
	/** si l'entité est afféctée par un pouvoir de fumeux */
	public boolean tetalenvers = false;
	
	/** si l'entité ne peut plus jouer de PA */
	public boolean hypnotise = false;
	
	/** applique l'état à un autre état.<br />
	 * Il s'agit d'un "ou" binaire sur les différents états
	 */
	public void apply(Etat other) {
		for(Field f : this.getClass().getFields()) {
			try {
				f.set(other, (Boolean)f.get(other) || (Boolean)f.get(this));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/** si l'etat peut modifier une entite.<br />
	 * autrement di, si un de ses booleens n'est pas à faux
	 */
	public boolean modifier() {
		for(Field f : this.getClass().getFields()) {
			try {
				if(Boolean.class.isAssignableFrom(f.getType()) && (Boolean)f.get(this))  {
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public String toString() {
		String retour = "";

		for(Field f : this.getClass().getFields()) {
			try {
				if(boolean.class.isAssignableFrom(f.getType()) && (Boolean)f.get(this))  {
					if(retour.length()>0) {
						retour+=", ";
					}
					retour+=f.getName();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return retour;
	}

}
