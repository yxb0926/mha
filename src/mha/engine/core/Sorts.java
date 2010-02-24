package mha.engine.core;

public enum Sorts {
		hypno( "Hypnotisme" ),
		RP( "Rafale Psychique" ),
		vampi( "Vampirisme" ),
		projo( "Projectile magique" ),
		AA( "Analyse anatomique" ),
		AE( "Armure éthérée" ),
		AdA( "Augmentation de l'attaque" ),
		AdE( "Augmentation de l'esquive" ),
		AdD( "Augmentation des dégats" ),
		BAM( "Bulle Anti-magie" ),
		BuM( "Bulle magique" ),
		explo( "Explosion" ),
		FP( "Faiblesse passagère" ),
		FA( "Flash aveuglant" ),
		glue( "Glue" ),
		GdS( "Griffe du sorcier" ),
		invi( "Invisibilité" ),
		projection( "Projection" ),
		sacro( "Sacrifice" ),
		TP( "Téléportation" ),
		VA( "Vision accrue" ),
		VL( "Vision lointaine" ),
		VlC( "Voir le caché" ),
		VT( "Vue troublée" );

	protected String name;
	protected boolean isReserved = false;

	Sorts( String name ) {
		this.name = name;
	}

	public String toString() {
		return name;
	}

	public boolean isReserved() {
		return isReserved;
	}

	public static final Sorts reserved[] = { hypno, projo, vampi, RP };
	static {
		for( Sorts sort : reserved ) {
			sort.isReserved = true;
		}
	}

	public static int porteeProjo( final int vueTotale ) {
		int portee = 0;
		int max = 0;
		int add = 4;
		while( vueTotale > max ) {
			max += add;
			add++ ;
			portee++ ;
		}
		return portee;
	}

	public static int porteeVlC( final int vueBase ) {
		if( vueBase < 6 ) { return 1; }
		if( vueBase < 12 ) { return 2; }
		if( vueBase < 22 ) { return 3; }
		if( vueBase < 35 ) { return 4; }
		return 5;
	}
}
