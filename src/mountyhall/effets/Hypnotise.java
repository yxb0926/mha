package mountyhall.effets;

import mountyhall.caracs.Carac;
import mountyhall.caracs.Esquive;

public class Hypnotise extends Effet {
	Carac esq;
	
	/** 
	 * @param desq le nombre de D d'esquive du lanceur
	 * @param full si l'hypno est passé en full
	 */
	public Hypnotise(int desq, boolean full) {
		esq = new Esquive();
		int Denleves = (int)(full?1.5*desq:desq/3);
		esq.modifiedBase(-Denleves);
		etat.hypnotise=full;
		remainingdla = 0;
	}
}
