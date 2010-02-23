package mha.engine.core;

public enum Competences {
		BS("Botte secrète", 0, 1, 2),
		RA("Régénération accrue", 0, 1, 2),
		AM("Accélération du métabolisme", 0, 1, 2),
		camou("Camouflage", 0, 1, 2),
		AP("Attaque précise", 5, 10, 4),
		charge("Charger", 5, 1, 4),
		PaF("Piège a feu", 5, 1, 4),
		CA("Contre-attaque", 2, 1, 2),
		CdB("Coup de butoir", 5, 10, 4),
		DE("Déplacement éclair", 0, 1, 1),
		frenzy("Frénésie", 10, 1, 6),
		LdP("Lancer de potions", 3, 1, 2),
		parer("Parer", 2, 2, 2),
		pistage("Pistage", 1, 1, 1),
		retraite("Retraite", 5, 2, 2);

	protected final String name;
	protected final int minTrollLvl;
	protected final int maxCompLvl;
	protected final int minPaCost;

	Competences(String name, int minTrollLvl, int maxCompLvl, int minPaCost) {
		this.name = name;
		this.minTrollLvl = minTrollLvl;
		this.maxCompLvl = maxCompLvl;
		this.minPaCost = minPaCost;
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

	protected static final Competences reservedComp[] = { BS, RA, AM, camou };

	public boolean isReserved() {
		for (Competences c : reservedComp) {
			if (c.equals(this)) { return true; }
		}
		return false;
	}

}
