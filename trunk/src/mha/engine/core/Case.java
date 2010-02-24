package mha.engine.core;

public class Case {

	protected final int posX;
	protected final int posY;
	protected final int posN;

	public int getX() {
		return posX;
	}

	public int getY() {
		return posY;
	}

	public int getN() {
		return posN;
	}

	public Case( int posX, int posY, int posN ) {
		this.posX = posX;
		this.posY = posY;
		this.posN = posN;
	}

	public boolean inRange( Case other, int distX, int distY, int distN ) {
		return ( Math.abs( getX() - other.getX() ) <= distX
				&& Math.abs( getY() - other.getY() ) <= distY && Math.abs( getN()
				- other.getN() ) <= distN );
	}

	public boolean inViewRange( Case other, int vue ) {
		return inRange( other, vue, vue, ( vue + 1 ) / 2 );
	}
	
	public boolean inProjoRange(Case other, final int vue) {
		return inRange(other, Sorts.porteeProjo( vue ), Sorts.porteeProjo( vue ), 0);
	}
	
	public boolean inVlCRange(Case other, final int vueBase) {
		return inRange(other, Sorts.porteeVlC(vueBase), Sorts.porteeVlC(vueBase), 0);
	}

	@Override
	public boolean equals( Object o ) {
		if( o instanceof Case ) {
			Case other = (Case) o;
			return ( other.getN() == getN() && other.getX() == getX() && other.getY() == getY() );
		}
		return false;
	}

	@Override
	public int hashCode() {
		return 10000 * getX() + 100 * getY() + getN();
	}

	@Override
	public String toString() {
		return getX() + " " + getY() + " " + getN();
	}

}
