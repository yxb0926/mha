package mountyhall.effets;

import static org.junit.Assert.*;
import mountyhall.caracs.Carac;
import mountyhall.caracs.Etat;

import org.junit.Before;
import org.junit.Test;

public class HypnotiseTest {
	
	Hypnotise full5, full11, res5, res11;
	
	@Before
	public void setUp() {
		full5 = new Hypnotise(5, true);
		full11 = new Hypnotise(11, true);
		res5 = new Hypnotise(5, false);
		res11 = new Hypnotise(11, false);
	}
	
	public static  boolean onlyBaseModif(Hypnotise h) {
		Carac c = h.esq;
		return c.base()==0
			&& c.physique() == 0
			&& c.magique() == 0
			&& c.percent() == 0;
	}
	
	public static boolean hypnofull(Hypnotise h) {
		Etat e = h.etat();
		return !e.camoufle && !e.glue && e.hypnotise && !e.intangible && !e.invisible && !e.levitation && !e.tetalenvers;
	}
	
	public static boolean hypnores(Hypnotise h) {
		return ! h.etat().modifier();
	}
	
	@Test
	public void testfull5() {
		assertTrue(onlyBaseModif(full5));
		assertTrue(hypnofull(full5));
		assertEquals(full5.esq.modifiedBase(),-7);
	}
	
	@Test
	public void testfull11() {
		assertTrue(onlyBaseModif(full11));
		assertTrue(hypnofull(full11));
		assertEquals(full11.esq.modifiedBase(),-16);
	}
	
	@Test
	public void testres5() {
		assertTrue(onlyBaseModif(res5));
		assertTrue(hypnores(res5));
		assertEquals(res5.esq.modifiedBase(),-1);
	}
	
	@Test
	public void testres11() {
		assertTrue(onlyBaseModif(res11));
		assertTrue(hypnores(res11));
		assertEquals(res11.esq.modifiedBase(),-3);
	}
}
