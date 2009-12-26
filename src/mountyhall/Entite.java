package mountyhall;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;

import mountyhall.caracs.Armure;
import mountyhall.caracs.Attaque;
import mountyhall.caracs.Carac;
import mountyhall.caracs.Degat;
import mountyhall.caracs.Esquive;
import mountyhall.caracs.Etat;
import mountyhall.caracs.MaitriseMagique;
import mountyhall.caracs.PvMax;
import mountyhall.caracs.PvRestant;
import mountyhall.caracs.Regeneration;
import mountyhall.caracs.ResistanceMagique;

/** les caractéristiques communes aux entités dans mh. 
 * 
 * @author guigolum
 *
 */
public class Entite {
	
	final Armure 		arm = new Armure();
	final Attaque 		att	= new Attaque();
	final Degat 		deg = new Degat();
	final Esquive 		esq = new Esquive();
	final MaitriseMagique mm = new MaitriseMagique();
	final PvMax 		pvsmax = new PvMax();
	final PvRestant		pvs = new PvRestant();
	final Regeneration 	reg = new Regeneration();
	final ResistanceMagique rm = new ResistanceMagique();
	protected Etat etat= new Etat();
	
	
	/** Map des caractéristiques accéssibles de l'éxtérieur 
	 * Utile pour les accès des */
	protected HashMap<String, Carac> caracs = new HashMap<String, Carac>();
	
	/** enregistre une caractéristique accéssible de l'extérieur*/
	protected void add(Carac c) {
		caracs.put(c.shortName(), c);
	}
	
	public Carac carac(String name) {
		Carac retour = caracs.get(name);
		if(retour==null) {
			System.err.println("erreur : caractéristique "+name+" non présente sur l'entité "+this);
		}
		return retour;
	}
	
	public Etat etat() { return etat; }
	
	Date activation = new Date();
	
	public Entite() {
		for(Field f : this.getClass().getFields()) {
			if(Carac.class.isAssignableFrom(f.getType())) {
				try {
					add((Carac) f.get(this));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
