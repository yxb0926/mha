package mountyhall.caracs;

public class Attaque extends Carac {

	@Override
	protected BaseType basetype() {
		return BaseType.d6;
	}

	@Override
	public String shortName() {
		return "att";
	}

}
