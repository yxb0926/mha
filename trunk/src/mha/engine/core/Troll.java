/*********************************************************************************
 * This file is part of Mountyhall Arena * * Mountyhall Arena is free software;
 * you can redistribute it and/or modify * it under the terms of the GNU General
 * Public License as published by * the Free Software Foundation; either version
 * 2 of the License, or * (at your option) any later version. * * Mountyhall
 * Arena is distributed in the hope that it will be useful, * but WITHOUT ANY
 * WARRANTY; without even the implied warranty of * MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the * GNU General Public License for more
 * details. * * You should have received a copy of the GNU General Public
 * License * along with Mountyzilla; if not, write to the Free Software *
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA *
 *********************************************************************************/

package mha.engine.core;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import mha.engine.MHABot;
import mha.engine.core.Equipement.types;
import mha.engine.core.MHAGame.gameModes;

public class Troll {

	public static enum races {
			skrim( Sorts.hypno, Competences.BS ),
			durakuir( Sorts.RP, Competences.RA ),
			kastar( Sorts.vampi, Competences.AM ),
			tomawak( Sorts.projo, Competences.camou );

		protected Sorts sortReserve;
		protected Competences compReserve;

		races( Sorts sortReserve, Competences compReserve ) {
			this.sortReserve = sortReserve;
			this.compReserve = compReserve;
		}

		public Sorts sortReserve() {
			return sortReserve;
		}

		public Competences compReserve() {
			return compReserve;
		}
	}

	public static enum bodyPositions {
		tete, maingauche, maindroite, armure, cou, bottes;
	}

	protected Map<Competences, Hashtable<Integer, Integer>> pourcentagesComp = new Hashtable<Competences, Hashtable<Integer, Integer>>();
	protected Map<Competences, Integer> maxPrctComp = new HashMap<Competences, Integer>();

	protected Map<Sorts, Integer> pourcentagesSort = new HashMap<Sorts, Integer>();
	protected Map<Sorts, Integer> maxPrctSort = new HashMap<Sorts, Integer>();

	protected int deginflig = 0;
	protected int kill = 0;
	protected int nbPA_utile = 0;
	protected int nbPVRegen = 0;

	protected String name;
	protected int id;
	protected int idSocket;
	protected int team;

	protected races race;
	protected int niveau = 1;
	protected int tempsRestant = 0;
	protected int attD = 3;
	protected int esqD = 3;
	protected int degD = 3;
	protected int pvMax = 30;
	protected int pvRestant = 30;
	protected int regD = 1;
	protected int vue = 3;
	protected int dlaMinutes = 12 * 60;
	protected int rm = 25;
	protected int mm = 25;

	protected BM bmExternals = new BM();
	protected BM bmMouches = new BM();
	protected BM bmEquip = new BM();
	protected BM bmTurnFull = new BM();

	protected int date_jeu; // en minutes depuis le début
	protected int debut_tour; // en minutes depuis le début
	protected boolean isCreated = false; // Est ce que le joueur a validé son
	// troll
	protected boolean isActive = false; // DLA de ce tour activée
	protected boolean reservedCompTryed = false; // comp réservée déjà tentée
	protected boolean reservedSortTryed = false;// sort réservé déjà tenté
	protected boolean isCertif = false;
	protected String icon = "";

	protected int arm = 0;
	protected int armM = 0;

	protected int fatigue = 0;
	protected int pa = 0;
	protected int posX = 0;
	protected int posY = 0;
	protected int posN = 0;
	protected int concentration = 0;
	protected int poids = 0;

	protected int dureeTourTotale = 0;

	protected boolean isCamoufle = false;
	protected boolean isInvisible = false;
	protected int nbResu = 0;
	protected Vector<String> inbox = new Vector<String>();
	protected Vector<BM> externalBmList = new Vector<BM>();
	protected Vector<Mouche> listeMouches = new Vector<Mouche>();

	// Gestion de l'équipement

	protected Vector<Equipement> listeEquipement = new Vector<Equipement>();
	// null signifie qu'il n'y a rien
	protected Equipement tete = null;
	protected Equipement cou = null;
	protected Equipement mainDroite = null;
	protected Equipement mainGauche = null;
	protected Equipement torse = null;
	protected Equipement pieds = null;

	public MHABot bot = null;

	public Troll( String name, int id, int idsocket, int team, races race ) {
		this.name = name;
		this.id = id;
		this.team = team;
		this.idSocket = idsocket;
		this.pa = 0;
		this.debut_tour = MHAGame.instance().roll( 1, dlaMinutes ) - 1;
		this.date_jeu = debut_tour;
		this.race = race;
		addComp( race.compReserve(), 25, 1 );
		addSort( race.sortReserve(), 25 );
		switch( race ) {
		case skrim:
			attD = 4;
		break;
		case kastar:
			degD = 4;
		break;
		case durakuir:
			pvMax = pvRestant = 40;
		break;
		case tomawak:
			vue = 4;
		break;
		default:
			System.err.println( "error : race : " + race
					+ "not handled in troll creation" );
		}
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive( boolean b ) {
		isActive = b;
	}

	public void addExternalBM( BM bm ) {
		externalBmList.add( bm );
		bmExternals.addOtherBM( bm, 0 );
		bmTurnFull.addOtherBM( bm );

	}

	public String getBM() {
		if( externalBmList.size() == 0 ) return "";
		String s = externalBmList.elementAt( 0 ).toString();
		for( int i = 1 ; i < externalBmList.size() ; i++ )
			s += "\n" + externalBmList.elementAt( i ).toString();
		return s;
	}

	public String getMouches() {
		if( listeMouches.size() == 0 ) return "";
		String s = listeMouches.elementAt( 0 ).toString();
		for( int i = 1 ; i < listeMouches.size() ; i++ )
			s += "\n" + listeMouches.elementAt( i ).toString();
		return s;
	}

	public void addMouche( Mouche m ) {
		// System.out.println("Mouche de type "+m.getType()+" ajoutée");
		listeMouches.add( m );
		bmMouches.addOtherBM( m.getEffet() );
		bmTurnFull.addOtherBM( m.getEffet() );
	}

	public void setTeam( int i ) {
		team = i;
	}

	public void setResu( int i ) {
		nbResu = i;
	}

	public int getResu() {
		return nbResu;
	}

	public void setIcon( String i ) {
		icon = i;
	}

	public String getIcon() {
		return icon;
	}

	public void addKill() {
		kill++ ;
	}

	public int getNbKills() {
		return kill;
	}

	public void addDeg( int i ) {
		deginflig += i;
	}

	public int getDegInflig() {
		return deginflig;
	}

	public void addPAUtil( int i ) {
		nbPA_utile += i;
	}

	public int getPAUtil() {
		return nbPA_utile;
	}

	public void addPVReg( int i ) {
		nbPVRegen += i;
	}

	public int getPVRegen() {
		return nbPVRegen;
	}

	protected void removeBMs() {
		for( BM toPass : externalBmList ) {
			if( toPass.newTurn() ) {
				externalBmList.remove( toPass );
				bmExternals.remove( toPass );
				bmTurnFull.remove( toPass );
			}
		}
	}

	protected void removeAllExternalsBMs() {
		externalBmList.clear();
		updateBmExternal();
		updateBmTurn();
	}

	protected void updateBmTurn() {
		bmTurnFull = new BM();
		bmTurnFull.addOtherBM( bmEquip );
		bmTurnFull.addOtherBM( bmExternals );
		bmTurnFull.addOtherBM( bmMouches );
	}

	protected void updateBmExternal() {
		bmExternals = new BM();
		for( BM external : externalBmList ) {
			bmExternals.addOtherBM( external );
		}
	}

	protected void updateBmEquipement() {
		bmEquip = new BM();
		for( Equipement equip : listeEquipement ) {
			bmEquip.addOtherBM( equip );
		}
	}

	public void addEquipementEffect( Equipement e ) {
		if( !listeEquipement.contains( e ) ) {
			listeEquipement.add( e );
			bmEquip.addOtherBM( e );
			bmTurnFull.addOtherBM( e );
			poids += e.getPoids();
		}
	}

	public void removeEquipementEffect( Equipement e ) {
		if( listeEquipement.contains( e ) ) {
			listeEquipement.remove( e );
			bmEquip.remove( e );
			bmTurnFull.remove( e );
			poids -= e.getPoids();
		}
	}

	public void setProfil( int niv, int att, int esq, int deg, int reg, int pv,
			int v, int dla, int resm, int maim ) {
		this.niveau = niv;
		this.attD = att;
		this.esqD = esq;
		this.degD = deg;
		this.pvMax = pv;
		this.pvRestant = pv;
		this.regD = reg;
		this.vue = v;
		this.dlaMinutes = dla;
		this.rm = resm;
		this.mm = maim;
	}

	public String getInfosTroll() {
		if( !isCreated ) return "Error: Le troll n'a pas encore été validé !";
		String r = ( "Numéro      : " + id + "\n" + "Nom         : " + name + "\n"
				+ "Race        : " + race.name() + "\n" + "Niveau      : " + niveau
				+ "\n" + "Mouches     : " + listeMouches.size() + "\n"
				+ formatEquipePorte( tete ) + formatEquipePorte( cou )
				+ formatEquipePorte( mainDroite ) + formatEquipePorte( mainGauche )
				+ formatEquipePorte( torse ) + formatEquipePorte( pieds ) );
		return r.substring( 0, r.length() - 1 );
	}

	protected String formatEquipePorte( Equipement e ) {
		if( e == null ) return "";
		switch( e.getType() ) {
		case armure:
			return "Torse       : " + e.getId() + " " + e.getName() + "\n";
		case bouclier:
			return "Main gauche : " + e.getId() + " " + e.getName() + "\n";
		case casque:
			return "Tête        : " + e.getId() + " " + e.getName() + "\n";
		case arme1H:
			return "Main droite : " + e.getId() + " " + e.getName() + "\n";
		case talisman:
			return "Cou         : " + e.getId() + " " + e.getName() + "\n";
		case bottes:
			return "Pieds       : " + e.getId() + " " + e.getName() + "\n";
		case arme2h:
			return "Deux mains  : " + e.getId() + " " + e.getName() + "\n";
		}
		return "";
	}

	public String getProfil() {
		String returnstring = "Nom : " + name + "\n" + "Niveau " + niveau + "\n"
				+ "Race : " + race.name() + "\n\n" + "Attaque : " + attD + "D6 + "
				+ bmTurnFull.att;
		returnstring += "/" + bmTurnFull.attM;
		returnstring += "\n" + "Esquive : " + esqD + "D6 + " + bmTurnFull.esq + "/"
				+ bmTurnFull.esqM + "\n" + "Dégats : " + degD + "D3 + "
				+ bmTurnFull.deg;
		returnstring += "/" + bmTurnFull.degM;
		returnstring += "\n" + "Régénération : " + regD + "D3 + " + bmTurnFull.reg
				+ "/" + bmTurnFull.regM + "\n" + "Vue : " + vue + " cases + "
				+ bmTurnFull.vue + "/" + bmTurnFull.vueM + "\n" + "PV : " + pvRestant
				+ "/" + pvMax + "\n\n" + "Durée DLA : " + convertTime( dlaMinutes )
				+ "\n" + "Poids équipement : " + convertTime( poids ) + "\n"
				+ "Bonus/malus DLA : " + convertTime( bmTurnFull.dlaMin ) + "\n\n"
				+ "Armure : " + arm + " + " + armM + "\n" + "MM : " + mm + " + "
				+ ( ( mm * bmTurnFull.mm ) / 100 ) + "\n" + "RM : " + rm + " + "
				+ ( ( rm * bmTurnFull.rm ) / 100 ) + "\n";

		returnstring += "Compétences : \n";

		Iterator<Competences> it = pourcentagesComp.keySet().iterator();
		while( it.hasNext() ) {
			Competences idComp = it.next();
			Iterator<Integer> it2 = pourcentagesComp.get( idComp ).keySet()
					.iterator();
			while( it2.hasNext() ) {
				int level = it2.next();
				int pour = pourcentagesComp.get( idComp ).get( level );
				if( pour > 0 ) returnstring += idComp.toString() + " : " + pour
						+ "% (Niveau " + level + ")\n";
			}
		}
		returnstring += "Sorts : \n";
		for( Sorts sorts : Sorts.values() ) {
			if( pourcentagesSort.get( sorts ) > 0 ) {
				returnstring += sorts + " : " + pourcentagesSort.get( sorts ) + "%\n";
			}
		}
		return returnstring.substring( 0, returnstring.length() - 1 );
	}

	public String getFullProfil() {
		String s = debut_tour + ";" + dlaMinutes + ";" + bmTurnFull.dlaMin + ";"
				+ ( 250 * ( pvMax - pvRestant ) / pvMax ) + ";" + poids + ";" + posX
				+ ";" + posY + ";" + posN + ";" + vue + ";" + formateCarac( vue ) + "/"
				+ formateCarac( bmTurnFull.vueM ) + ";" + niveau + ";" + pvRestant
				+ ";" + pvMax + ";" + regD + ";" + formateCarac( bmTurnFull.reg ) + "/"
				+ formateCarac( bmTurnFull.regM ) + ";" + fatigue + ";" + attD + ";";
		s += formateCarac( bmTurnFull.att ) + "/" + formateCarac( bmTurnFull.attM )
				+ ";";
		s += esqD + ";" + formateCarac( bmTurnFull.esq ) + "/"
				+ formateCarac( bmTurnFull.esqM ) + ";" + degD + ";";
		s += formateCarac( bmTurnFull.deg ) + "/" + formateCarac( bmTurnFull.degM )
				+ ";";
		s += arm + ";" + armM + ";" + bmTurnFull.esqD + ";" + bmTurnFull.parades
				+ ";" + bmTurnFull.CA + ";" + rm + ";" + bmTurnFull.rm + ";" + mm + ";"
				+ bmTurnFull.mm + ";" + concentration + ";";
		if( isCamoufle ) s += "1;";
		else s += "0;";
		if( isInvisible ) s += "1";
		else s += "0";
		return s;
	}

	public boolean addComp( Competences comp, int prctSuccess, int level ) {
		if( comp.isReserved() && comp != race.compReserve() ) return false;
		if( prctSuccess > 95 ) {
			prctSuccess = 95;
		}
		int maxPrct = 90;
		if( prctSuccess > maxPrct ) {
			maxPrct = prctSuccess;
		}
		maxPrctComp.put( comp, maxPrct );
		if( level < 1 ) {
			level = 1;
		}
		if( level > comp.maxCompLvl ) {
			level = comp.maxCompLvl;
		}
		if( !pourcentagesComp.containsKey( comp ) ) {
			pourcentagesComp.put( comp, new Hashtable<Integer, Integer>() );
		}
		pourcentagesComp.get( comp ).put( level, prctSuccess );
		return true;
	}

	public boolean addSort( Sorts sorts, int pour ) {
		if( sorts.isReserved() && sorts != race.sortReserve() ) { return false; }
		if( pour > 85 ) {
			pour = 85;
		}
		maxPrctSort.put( sorts, ( pour > 80 ? pour : 80 ) );
		pourcentagesSort.put( sorts, pour );
		return true;
	}

	protected int somme( int i ) {
		return ( ( i + 1 ) * i ) / 2;
	}

	public boolean valideTroll() {
		if( !verifNiveau() ) return false;
		isCreated = true;
		return true;
	}

	public boolean isCreated() {
		return isCreated;
	}

	public static String formateCarac( int carac ) {
		if( carac > 0 ) return "+" + carac;
		return "" + carac;
	}

	public boolean verifNiveau() {
		int px = 0;
		if( race == races.skrim ) px += somme( attD - 4 ) * 12;
		else px += somme( attD - 3 ) * 16;
		if( race == races.durakuir ) px += somme( ( pvMax - 40 ) / 10 ) * 12;
		else px += somme( ( pvMax - 30 ) / 10 ) * 16;
		if( race == races.kastar ) px += somme( degD - 4 ) * 12;
		else px += somme( degD - 3 ) * 16;
		if( race == races.tomawak ) px += somme( vue - 4 ) * 12;
		else px += somme( vue - 3 ) * 16;
		px += somme( esqD - 3 ) * 16;
		px += somme( regD - 1 ) * 30;
		switch( dlaMinutes ) {
		case 720:
		break;
		case 690:
			px += 1 * 18;
		break;
		case 663:
			px += 3 * 18;
		break;
		case 639:
			px += 6 * 18;
		break;
		case 618:
			px += 10 * 18;
		break;
		case 600:
			px += 15 * 18;
		break;
		case 585:
			px += 21 * 18;
		break;
		case 573:
			px += 28 * 18;
		break;
		case 564:
			px += 36 * 18;
		break;
		case 558:
			px += 45 * 18;
		break;
		case 555:
			px += 55 * 18;
		break;
		default:
			if( dlaMinutes > 555 ) return false;
			px += ( somme( ( 555 - dlaMinutes ) * 2 / 5 ) + 55 + 10 * ( ( 555 - dlaMinutes ) * 2 / 5 ) ) * 18;
		}
		Iterator<Competences> it = pourcentagesComp.keySet().iterator();
		while( it.hasNext() ) {
			Competences ownedComp = it.next();
			int level = 0;
			Iterator<Integer> it2 = pourcentagesComp.get( ownedComp ).keySet()
					.iterator();
			while( it2.hasNext() ) {
				int lev = it2.next();
				if( lev > level ) level = lev;
			}
			for( int i = 1 ; i < level ; i++ )
				if( pourcentagesComp.get( ownedComp ).get( i ) == null
						|| pourcentagesComp.get( ownedComp ).get( i ) < 75 ) return false;
			px += ownedComp.minTrollLvl() * ( ( level * ( level + 1 ) ) / 2 );
		}
		// if((niveau)*(niveau+3)*5<=px)
		// System.out.println("Problème : Un niveau "+niveau+" a au max "+((niveau)*(niveau+3)*5)+" et vous en avez dépensé "+px);
		return ( niveau ) * ( niveau + 3 ) * 5 > px;
	}

	public void equipe( Equipement e ) {
		if( !listeEquipement.contains( e ) ) return;
		switch( e.getType() ) {
		case armure:
			if( torse != null ) {
				removeEquipementEffect( torse );
			}
			torse = e;
			addEquipementEffect( e );
			return;
		case bouclier:
			if( mainGauche != null ) {
				removeEquipementEffect( mainGauche );
			}
			if( mainDroite != null && mainDroite.getType() == types.arme2h ) {
				removeEquipementEffect( mainDroite );
				mainDroite = null;
			}
			mainGauche = e;
			addEquipementEffect( e );
			return;
		case casque:
			if( tete != null ) removeEquipementEffect( tete );
			tete = e;
			addEquipementEffect( e );
			return;
		case arme1H:
			if( mainDroite != null ) removeEquipementEffect( mainDroite );
			mainDroite = e;
			addEquipementEffect( e );
			return;
		case talisman:
			if( cou != null ) removeEquipementEffect( cou );
			cou = e;
			addEquipementEffect( e );
			return;
		case bottes:
			if( pieds != null ) removeEquipementEffect( pieds );
			pieds = e;
			addEquipementEffect( e );
			return;
		case arme2h:
			if( mainDroite != null ) removeEquipementEffect( mainDroite );
			if( mainGauche != null ) {
				removeEquipementEffect( mainGauche );
				mainGauche = null;
			}
			mainDroite = e;
			addEquipementEffect( e );
			return;
		}
		return;
	}

	public Equipement getEquipementById( int id ) {
		for( Equipement equip : listeEquipement ) {
			if( equip.getId() == id ) return equip;
		}
		return null;
	}

	public String getEquip() {
		String s = "";
		for( Equipement e : listeEquipement ) {
			int equip = 0;
			if( e == torse || e == mainGauche || e == mainDroite || e == tete
					|| e == cou || e == pieds ) equip = 1;
			s += e.getId() + ";" + e.getType() + ";" + equip + ";" + e.getDescr()
					+ ";" + e.getName() + "\n";
		}
		if( s.equals( "" ) ) return "Aucun équipement";
		return s.substring( 0, s.length() - 1 );
	}

	public boolean isEquipe( Equipement e ) {
		switch( e.getType() ) {
		case armure:
			return torse == e;
		case bouclier:
			return mainGauche == e;
		case casque:
			return tete == e;
		case arme1H:
		case arme2h:
			return mainDroite == e;
		case talisman:
			return cou == e;
		case bottes:
			return pieds == e;
		}
		return false;
	}

	public int getBMAttaque() {
		return bmTurnFull.att;
	}

	public int getBMMAttaque() {
		return bmTurnFull.attM;
	}

	public int getBMEsquive() {
		return bmTurnFull.esq;
	}

	public int getBMMEsquive() {
		return bmTurnFull.esqM;
	}

	public int getBMDegat() {
		return bmTurnFull.deg;
	}

	public int getBMMDegat() {
		return bmTurnFull.degM;
	}

	public int getBMVue() {
		return bmTurnFull.vue;
	}

	public int getBMMVue() {
		return bmTurnFull.vueM;
	}

	public int getRM() {
		return rm * ( ( 100 + bmTurnFull.rm ) / 100 );
	}

	public int getMM() {
		return mm * ( ( 100 + bmTurnFull.mm ) / 100 );
	}

	public boolean isGlued() {
		return bmTurnFull.glued;
	}

	public int getReussiteSort( Sorts sorts ) {
		return pourcentagesSort.get( sorts );
	}

	public int getHighestCompLevel( Competences comp ) {
		Hashtable<Integer, Integer> h = pourcentagesComp.get( comp );
		if( h == null ) return 0;
		int level = 0;
		for( Integer lvl : h.keySet() ) {
			if( lvl > level ) {
				level = lvl;
			}
		}
		return level;
	}

	public int getReussiteComp( Competences comp, int level ) {
		Hashtable<Integer, Integer> h = pourcentagesComp.get( comp );
		if( h == null ) return 0;
		Integer i = h.get( level );
		if( i == null ) return 0;
		return i;
	}

	public void augmentComp( Competences comp, int level, int pour ) {
		Hashtable<Integer, Integer> h = pourcentagesComp.get( comp );
		if( h == null ) { return; }
		Integer val = h.get( level );
		if( val == null ) { return; }
		val = val + pour;
		if( val > maxPrctComp.get( comp ) ) {
			val = maxPrctComp.get( comp );
		}
		h.put( level, val );
	}

	public void augmentSort( Sorts sort, int i ) {
		if( !pourcentagesSort.containsKey( sort ) ) { return; }
		int val = pourcentagesSort.get( sort );
		val += i;
		if( val > maxPrctSort.get( sort ) ) {
			val = maxPrctSort.get( sort );
		}
		pourcentagesSort.put( sort, val );
	}

	public void setPos( int x, int y, int n ) {
		posX = x;
		posY = y;
		posN = n;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public int getPosN() {
		return posN;
	}

	public void move( int x, int y, int n ) {
		posX += x;
		posY += y;
		posN += n;
	}

	public String getPos() {
		return posX + " " + posY + " " + posN;
	}

	public int getTeam() {
		return team;
	}

	public void blesse( int i ) {
		pvRestant -= i;
		// Je suis mort :) peut etre ?
	}

	public int getArmurePhy() {
		return arm;
	}

	public int getArmureMag() {
		return armM;
	}

	public int getAttaque() {
		return Math.max( 0, attD + bmTurnFull.attD );
	}

	public boolean isDead() {
		return ( pvRestant <= 0 );
	}

	public int getEsquive() {
		return Math.max( 0, esqD + bmTurnFull.esqD );
	}

	public int getEsquiveTotale() {
		return esqD;
	}

	public int getDegat() {
		return degD;
	}

	public int getRegeneration() {
		return regD;
	}

	public void getTouch() {
		bmTurnFull.esqD-- ;
	}

	public void getParade() {
		bmTurnFull.attD-- ;
		bmTurnFull.esqD-- ;
	}

	public String getName() {
		return name;
	}

	public int getNiveau() {
		return niveau;
	}

	public int getId() {
		return id;
	}

	public int getSocketId() {
		return idSocket;
	}

	public races getRace() {
		return race;
	}

	public boolean sameNameOrId( Troll t ) {
		return ( ( t.getId() == id ) || ( t.getName().equals( name ) ) );
	}

	public int getDateJeu() {
		return date_jeu;
	}

	public int getNouveauTour() {
		return debut_tour;
	}

	public void setNouveauTour( int i ) {
		debut_tour = i;
	}

	public int getFatigue() {
		return fatigue;
	}

	public void setFatigue( int i ) {
		fatigue = i;
	}

	public void decale( int i ) {
		date_jeu += i;
		isActive = false;
	}

	public int getPA() {
		return pa;
	}

	public int getPV() {
		return pvRestant;
	}

	public int getPVTotaux() {
		return pvMax;
	}

	public void setPV( int i ) {
		pvRestant = i;
	}

	public void setPA( int i ) {
		pa = i;
	}

	public void tryReservedSort( boolean b ) {
		reservedSortTryed = b;
	}

	public boolean reservedSortTryed() {
		return reservedSortTryed;
	}

	public void setCertif( boolean b ) {
		isCertif = b;
	}

	public boolean isCertif() {
		return isCertif;
	}

	public void tryReservedComp( boolean b ) {
		reservedCompTryed = b;
	}

	public boolean reservedCompTryed() {
		return reservedCompTryed;
	}

	public int getConcentration() {
		return concentration;
	}

	public void setConcentration( int i ) {
		concentration = i;
	}

	public int getVue() {
		return vue;
	}

	public void endTurn() {
		date_jeu = debut_tour;
		isActive = false;
	}

	public int getDureeTourTotale() {
		return dureeTourTotale;
	}

	public String beginTurn( int current ) {
		// Il faut régénérer
		// Regarder les bonus/malus encore en cours
		// Virer les poisons
		// Mouches ?
		// remettre l'esquive !
		// gérer la concentration
		// Calculer la nouvelle DLA
		// Remettre les PA.
		String s = "";
		boolean modify = false;

		if( isDead() && nbResu > 0 ) {
			nbResu-- ;
			s += "\nVous étiez mort mais vous revenez à la vie\nIl ne vous reste plus que "
					+ nbResu + " résurrection(s)\n";
			if( MHAGame.instance().getMode() == gameModes.teamdeathmatch
					&& MHAGame.instance().isRegroupe() ) {
				MHAGame.instance().placeTrollInHisTeam( this );
			} else setPos( MHAGame.instance().roll( 1,
					MHAGame.instance().getSizeArena() ) - 1, MHAGame.instance().roll( 1,
					MHAGame.instance().getSizeArena() ) - 1, -MHAGame.instance().roll( 1,
					( MHAGame.instance().getSizeArena() + 1 ) / 2 ) );
			pvRestant = ( pvMax * 50 ) / 100;
			s += "Vous réapparaissez en X=" + posX + " Y=" + posY + " N=" + posN
					+ " avec seulement " + pvRestant + "PV";
			removeAllExternalsBMs();
			pa = 0;
			debut_tour = current;
		} else {
			while( debut_tour <= current ) {
				if( poids + ( 250 * ( pvMax - pvRestant ) / pvMax ) + bmTurnFull.dlaMin < 0 ) {
					debut_tour += dlaMinutes;
					dureeTourTotale = dlaMinutes;
				} else {
					debut_tour += dlaMinutes + poids
							+ ( 250 * ( pvMax - pvRestant ) / pvMax ) + bmTurnFull.dlaMin;
					dureeTourTotale = dlaMinutes + poids
							+ ( 250 * ( pvMax - pvRestant ) / pvMax ) + bmTurnFull.dlaMin;
				}
			}
			removeBMs();
			s += "\nVotre tour de jeu et votre nouvelle date limite d'action ont été calculés.";
			if( pvMax - pvRestant != 0
					&& ( 250 * ( pvMax - pvRestant ) / pvMax )
							+ Math.min( 0, poids + bmTurnFull.dlaMin ) > 0 ) s += "\nSa durée est augmentée de "
					+ convertTime( ( 250 * ( pvMax - pvRestant ) / pvMax )
							+ Math.min( 0, poids + bmTurnFull.dlaMin ) )
					+ " à cause de vos blessures.";
			if( poids + bmTurnFull.dlaMin > 0 ) {
				s += "\nSa durée est augmentée de "
						+ convertTime( poids + bmTurnFull.dlaMin )
						+ " à cause du poids de votre Equipement.";
			}
		}
		s += "\nVotre nouvelle date limite d'action est le "
				+ hour2string( debut_tour );
		int i = Math.max( MHAGame.instance().roll( regD, 3 ) + bmTurnFull.reg
				+ bmTurnFull.regM, 0 );
		int j = i + bmTurnFull.soin;
		if( pvRestant < pvMax ) {
			s += "\nVous aviez " + pvRestant + " Points de Vie.";
			s += "\nVous avez régénéré de " + i + " Points de Vie.";
			nbPVRegen += Math.min( pvMax - pvRestant, i );
			modify = true;
		}
		pvRestant = Math.min( pvMax, pvRestant + j );
		if( bmTurnFull.soin < 0 ) {
			s += "\nLe venin vous fait perdre " + bmTurnFull.soin + " Points de Vie.";
			if( pvRestant <= 0 ) {
				s += "\nVous êtes mort !!!!";
				return s.substring( 1 );
			}
			modify = true;
		}
		if( modify ) s += "\nVotre total actuel est donc de " + pvRestant
				+ " Points de Vie.";
		// Le venin !!!!
		if( pa != 0 ) {
			s += "\n"
					+ pa
					+ " PA de votre tour précédent n'ont pas été utilisés et on été automatiquement attribués à une concentration.";
			concentration += pa * 5;
		}
		if( fatigue > 0 ) {
			fatigue = (int) Math.floor( ( (double) fatigue ) / 1.25 );
			s += "\nVotre fatigue est de " + fatigue;
		}
		isActive = true;
		reservedCompTryed = false;
		reservedSortTryed = false;
		bmTurnFull.esqD = 0;
		bmTurnFull.attD = 0;
		bmTurnFull.parades = 0;
		bmTurnFull.CA = 0;
		pa = 6;
		return s.substring( 1 );
	}

	public boolean getCamouflage() {
		return isCamoufle;
	}

	public void setCamouflage( boolean b ) {
		isCamoufle = b;
	}

	public boolean getInvisible() {
		return isInvisible;
	}

	public void setInvisible( boolean b ) {
		isInvisible = b;
	}

	public int getNbCA() {
		return bmTurnFull.CA;
	}

	public void setNbCA( int i ) {
		bmTurnFull.CA = i;
	}

	public int getNbParade() {
		return bmTurnFull.parades;
	}

	public void setNbParade( int i ) {
		bmTurnFull.parades = i;
	}

	public int getTempsRestant() {
		return tempsRestant;
	}

	public void setTempsRestant( int i ) {
		tempsRestant = i;
	}

	public boolean isVisibleFrom( int x, int y, int n, int v ) {
		return isVisibleFrom( x, y, n, v, false );
	}

	public boolean isVisibleFrom( int x, int y, int n, int v, boolean VlC ) {
		if( isDead() ) { return false; }
		if( v < 0 ) {
			v = 0;
		}
		if( x == getPosX() && y == getPosY() && n == getPosN()
				&& ( !getInvisible() || VlC ) ) {
			// invisible sur la même case <=> invi et non VlC
			return true;
		}
		if( getCamouflage() || getInvisible() ) {
			if( VlC ) {
				// TODO use the good values for VlC.
				return Math.abs( x - posX ) <= v && Math.abs( y - posY ) <= v
						&& Math.abs( n - posN ) <= ( v + 1 ) / 2;
			} else {
				return false;
			}
		} else {
			return Math.abs( x - posX ) <= v && Math.abs( y - posY ) <= v
					&& Math.abs( n - posN ) <= ( v + 1 ) / 2;
		}
	}

	public static String convertTime( int t ) {
		if( t >= 0 ) return ( t / 60 ) + " heures et " + ( t % 60 ) + " minutes";
		else return "-" + ( -t / 60 ) + " heures et -" + ( ( -t ) % 60 )
				+ " minutes";
	}

	public static String hour2string( int t ) {
		return "jour " + ( t / ( 60 * 24 ) + 1 ) + " à "
				+ convertTime( t % ( 60 * 24 ) );
	}

	public Vector<String> getInbox() {
		return inbox;
	}

}
