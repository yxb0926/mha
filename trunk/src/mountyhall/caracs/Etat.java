package mountyhall.caracs;

import java.lang.reflect.Field;

/** les choses qui sont appliquabls � une entit� */
public class Etat {
	
	/** si l'entit� est intangible */
	public boolean intangible = false;
	
	/** si l'entit� est sous l'effet d'un sort de glue */
	public boolean glue = false;
	
	/** si l'entit� est en levitation */
	public boolean levitation = false;
	
	/** si l'entit� est camoufl�e */
	public boolean camoufle = false;
	
	/** si l'entit� est invisible */
	public boolean invisible = false;
	
	/** si l'entit� est aff�ct�e par un pouvoir de fumeux */
	public boolean tetalenvers = false;
	
	/** si l'entit� ne peut plus jouer de PA */
	public boolean hypnotise = false;
	
	/** applique l'�tat � un autre �tat.<br />
	 * Il s'agit d'un "ou" binaire sur les diff�rents �tats
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
	 * autrement di, si un de ses booleens n'est pas � faux
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
