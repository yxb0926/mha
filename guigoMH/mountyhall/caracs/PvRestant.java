package mountyhall.caracs;

/** le nombre de pvs restants qu'il reste à l'entité */
public class PvRestant extends Carac {

	@Override
	protected BaseType basetype() {
		return BaseType.points;
	}

	@Override
	public String shortName() {
		return "pvs";
	}

}
