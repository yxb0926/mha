package mountyhall.caracs;

/** caractéristique des pvs max d'une entité.<br />
 * Pour l'instant les bonus magiques et physiques ont la même utilité:<br />
 * Un poison fera autant de dégats qu'une popo de soin surplusieurs tours soignera.
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
