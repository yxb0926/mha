package mha.engine.core;

public enum Sorts {
		hypno("Hypnotisme"),
		RP("Rafale Psychique"),
		vampi("Vampirisme"),
		projo("Projectile magique"),
		AA("Analyse anatomique"),
		AE("Armure �th�r�e"),
		AdA("Augmentation de l'attaque"),
		AdE("Augmentation de l'esquive"),
		AdD("Augmentation des d�gats"),
		BAM("Bulle Anti-magie"),
		BuM("Bulle magique"),
		explo("Explosion"),
		FP("Faiblesse passag�re"),
		FA("Flash aveuglant"),
		glue("Glue"),
		GdS("Griffe du sorcier"),
		invi("Invisibilit�"),
		projection("Projection"),
		sacro("Sacrifice"),
		TP("T�l�portation"),
		VA("Vision accrue"),
		VL("Vision lointaine"),
		VlC("Voir le cach�"),
		VT("Vue troubl�e");

	protected String name;

	Sorts(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}

	protected static Sorts reserved[] = { hypno, projo, vampi, RP };

	public boolean isReserved() {
		for (Sorts s : reserved) {
			if (s.equals(this)) { return true; }
		}
		return false;
	}
}
