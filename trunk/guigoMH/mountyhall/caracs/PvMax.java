package mountyhall.caracs;

/** caract�ristique des pvs max d'une entit�.<br />
 * Pour l'instant les bonus magiques et physiques ont la m�me utilit�:<br />
 * Un poison fera autant de d�gats qu'une popo de soin surplusieurs tours soignera.
 */
public class PvMax extends Carac{

	@Override
	protected BaseType basetype() {
		return BaseType.points;
	}

	@Override
	public String shortName() {
		return "pvsmax";
	}

}
