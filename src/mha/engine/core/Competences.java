package mha.engine.core;

public enum Competences {
		BS("Botte secr�te", 0, 1, 2),
		RA("R�g�n�ration accrue", 0, 1, 2),
		AM("Acc�l�ration du m�tabolisme", 0, 1, 2),
		camou("Camouflage", 0, 1, 2),
		AP("Attaque pr�cise", 5, 10, 4),
		charge("Charger", 5, 1, 4),
		PaF("Pi�ge a feu", 5, 1, 4),
		CA("Contre-attaque", 2, 1, 2),
		CdB("Coup de butoir", 5, 10, 4),
		DE("D�placement �clair", 0, 1, 1),
		frenzy("Fr�n�sie", 10, 1, 6),
		LdP("Lancer de potions", 3, 1, 2),
		parer("Parer", 2, 2, 2),
		pistage("Pistage", 1, 1, 1),
		retraite("Retraite", 5, 2, 2);

	protected final String name;
	protected final int minTrollLvl;
	protected final int maxCompLvl;
	protected final int minPaCost;
	protected boolean isReserved; 

	Competences(String name, int minTrollLvl, int maxCompLvl, int minPaCost) {
		this.name = name;
		this.minTrollLvl = minTrollLvl;
		this.maxCompLvl = maxCompLvl;
		this.minPaCost = minPaCost;
		isReserved = false;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public int minTrollLvl() {
		return this.minTrollLvl;
	}

	public int maxCompLvl() {
		return this.maxCompLvl;
	}

	public int minPaRequired() {
		return this.minPaCost;
	}
	
	public boolean isReserved() {
		return isReserved;
	}

	public static final Competences reserved[] = { BS, RA, AM, camou };
	static {
		for(Competences c : reserved) {
			c.isReserved = true;
		}
	}

}
