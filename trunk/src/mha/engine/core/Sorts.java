package mha.engine.core;

public enum Sorts {
		hypno("Hypnotisme"),
		RP("Rafale Psychique"),
		vampi("Vampirisme"),
		projo("Projectile magique"),
		AA("Analyse anatomique"),
		AE("Armure éthérée"),
		AdA("Augmentation de l'attaque"),
		AdE("Augmentation de l'esquive"),
		AdD("Augmentation des dégats"),
		BAM("Bulle Anti-magie"),
		BuM("Bulle magique"),
		explo("Explosion"),
		FP("Faiblesse passagère"),
		FA("Flash aveuglant"),
		glue("Glue"),
		GdS("Griffe du sorcier"),
		invi("Invisibilité"),
		projection("Projection"),
		sacro("Sacrifice"),
		TP("Téléportation"),
		VA("Vision accrue"),
		VL("Vision lointaine"),
		VlC("Voir le caché"),
		VT("Vue troublée");

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
