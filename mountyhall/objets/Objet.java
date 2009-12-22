package mountyhall.objets;

/** les points communs à tous les objets
 * @author guigolum
 */
public abstract class Objet {
	
	public static enum Type {
		Equipement, potion, parchemin, gg, divers
	};
	
	protected Type type;
	
	
	protected int poids=0;
	/** Le poids de l'objet quand il est dans l'équipement, en secondes */
	public int poids() {return poids;}
	
	/** L'objet peut il être équipé*/
	public boolean equipable() {return type==Type.Equipement;}
	
	/** L'objet peut il être utilisé*/
	public boolean utilisable() {return type==Type.potion || type==Type.parchemin; }
	
	/** L'objet peut il être lancé */
	public boolean lancable() {return type==Type.potion; }

}
