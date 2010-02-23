/*********************************************************************************
 *    This file is part of Mountyhall Arena                                       *
 *                                                                                *
 *    Mountyhall Arena is free software; you can redistribute it and/or modify    *
 *    it under the terms of the GNU General Public License as published by        *
 *    the Free Software Foundation; either version 2 of the License, or           *
 *    (at your option) any later version.                                         *
 *                                                                                *
 *    Mountyhall Arena is distributed in the hope that it will be useful,         *
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of              *
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the               *
 *    GNU General Public License for more details.                                *
 *                                                                                *
 *    You should have received a copy of the GNU General Public License           *
 *    along with Mountyzilla; if not, write to the Free Software                  *
 *    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA  *
 *********************************************************************************/

package mha.engine.core;

import mha.engine.MHABot;
import mha.engine.core.Equipement.types;
import mha.engine.core.MHAGame.gameModes;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class Troll {

	public static enum races {
			skrim(Sort.hypno, Competences.BS),
			durakuir(Sort.RP, Competences.RA),
			kastar(Sort.vampi, Competences.AM),
			tomawak(Sort.projo, Competences.camou);

		protected Sort sortReserve;
		protected Competences compReserve;

		races(Sort sortReserve, Competences compReserve) {
			this.sortReserve = sortReserve;
			this.compReserve = compReserve;
		}

		public Sort sortReserve() {
			return sortReserve;
		}

		public Competences compReserve() {
			return compReserve;
		}
	};

	private Hashtable<Competences, Hashtable<Integer, Integer>> pourcentagesComp = new Hashtable<Competences, Hashtable<Integer, Integer>>();
	private Map<Sort, Integer> pourcentagesSort = new HashMap<Sort, Integer>();
	private int deginflig = 0;
	private int kill = 0;
	private int nbPA_utile = 0;
	private int nbPVRegen = 0;

	private String name;
	private int id;
	private int idSocket;
	private int team;
	private races race;
	private int niveau = 1;
	private int tempsRestant = 0;
	private int des_Attaque = 3;
	private int des_Esquive = 3;
	private int des_Degat = 3;
	private int pv_totaux = 30;
	private int pv_actuels = 30;
	private int des_Regeneration = 1;
	private int vue = 3;
	private int duree_tour = 12 * 60;
	private int date_jeu; // en minutes depuis le d�but
	private int debut_tour; // en minutes depuis le d�but
	private boolean isCreated = false; // Est ce que le joueur a valid� son
	// troll
	private boolean isActive = false; // Est ce que la DLA est jou�e ?
	private boolean compReservee = false; // A t il d�ja essayer de faire une
	// comp r�serv�e ?
	private boolean sortReserve = false;
	private boolean isCertif = false;
	private String icon = "";

	private int malus_des_esquive = 0;
	private int malus_des_attaque = 0;
	private int bmAttaque = 0;
	private int bmmAttaque = 0;
	private int bmEsquive = 0;
	private int bmmEsquive = 0;
	private int bmDegat = 0;
	private int bmmDegat = 0;
	private int bmRegeneration = 0;
	private int bmmRegeneration = 0;
	private int bmVue = 0;
	private int bmmVue = 0;
	private int bmDLA = 0;
	private int bmMM = 0;
	private int bmRM = 0;
	private int venin = 0;
	private int glue = 0;
	private int nbParade = 0;
	private int nbCA = 0;

	private int rm = 25;
	private int mm = 25;

	private int armure_phy = 0;
	private int armure_mag = 0;

	private int fatigue = 0;
	private int pa;
	private int posx = 0;
	private int posy = 0;
	private int posn = 0;
	private int concentration = 0;
	private int poids = 0;

	private int dureeTourTotale = 0;

	private boolean camoufle = false;
	private boolean invisible = false;
	private boolean frenetique = false;
	private int nbResu = 0;
	private Vector<String> inbox = new Vector<String>();
	private Vector<BM> listeBM = new Vector<BM>();
	private Vector<Mouche> listeMouches = new Vector<Mouche>();

	// Gestion de l'�quipement

	private Vector<Equipement> listeEquipement = new Vector<Equipement>();
	// null signifie qu'il n'y a rien
	private Equipement tete = null;
	private Equipement cou = null;
	private Equipement mainDroite = null;
	private Equipement mainGauche = null;
	private Equipement torse = null;
	private Equipement pieds = null;

	public MHABot bot = null;

	public Troll(String n, int i, int s, int c, races race) {
		name = n;
		id = i;
		team = c;
		idSocket = s;
		pa = 0;
		debut_tour = MHAGame.instance().roll(
			1,
			duree_tour) - 1;
		date_jeu = debut_tour;
		this.race = race;
		setComp(
			race.compReserve(),
			25,
			1);
		pourcentagesSort.put(
			race.sortReserve(),
			25);
		switch (race) {
		case skrim:
			this.race = race;
			des_Attaque = 4;
			break;
		case kastar:
			des_Degat = 4;
			break;
		case durakuir:
			pv_totaux = pv_actuels = 40;
			break;
		case tomawak:
			vue = 4;
			break;
		default:
			System.err.println("error : race : " + race
					+ "not handled in troll creation");
		}
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean b) {
		isActive = b;
	}

	public void addBM(BM bm) {
		listeBM.add(bm);
		if (bm.getBMAttaque() != 0) bmAttaque += bm.getBMAttaque();
		if (bm.getBMMAttaque() != 0) bmmAttaque += bm.getBMMAttaque();
		if (bm.getBMEsquive() != 0) bmEsquive += bm.getBMEsquive();
		if (bm.getBMMEsquive() != 0) bmmEsquive += bm.getBMMEsquive();
		if (bm.getBMDegat() != 0) bmDegat += bm.getBMDegat();
		if (bm.getBMMDegat() != 0) bmmDegat += bm.getBMMDegat();
		if (bm.getBMMRegeneration() != 0)
			bmRegeneration += bm.getBMMRegeneration();
		if (bm.getBMRegeneration() != 0)
			bmmRegeneration += bm.getBMRegeneration();
		if (bm.getBMVue() != 0) bmVue += bm.getBMVue();
		if (bm.getBMMVue() != 0) bmmVue += bm.getBMMVue();
		if (bm.getBMDLA() != 0) bmDLA += bm.getBMDLA();
		if (bm.getBMArmurePhysique() != 0)
			armure_phy += bm.getBMArmurePhysique();
		if (bm.getBMArmureMagique() != 0)
			armure_mag += bm.getBMArmureMagique();
		if (bm.isGlue()) glue++;
		if (bm.getVenin() != 0) venin += bm.getVenin();
		if (bm.getBMMM() != 0) bmMM += bm.getBMMM();
		if (bm.getBMRM() != 0) bmRM += bm.getBMRM();

	}

	private void setComp(Competences comp, int pourcentage, int level) {
		Hashtable<Integer, Integer> compLvls = pourcentagesComp.get(comp);
		if (compLvls == null) pourcentagesComp.put(
			comp,
			compLvls = new Hashtable<Integer, Integer>());
		compLvls.put(
			level,
			pourcentage);
	}

	public String getBM() {
		if (listeBM.size() == 0) return "";
		String s = listeBM.elementAt(
			0).toString();
		for (int i = 1; i < listeBM.size(); i++)
			s += "\n" + listeBM.elementAt(
				i).toString();
		return s;
	}

	public String getMouches() {
		if (listeMouches.size() == 0) return "";
		String s = listeMouches.elementAt(
			0).toString();
		for (int i = 1; i < listeMouches.size(); i++)
			s += "\n" + listeMouches.elementAt(
				i).toString();
		return s;
	}

	public void addMouche(Mouche m) {
		switch (m.getType()) {
		case Mouche.CROBATE:
			bmmAttaque++;
			break;
		case Mouche.VERTIE:
			bmmEsquive++;
			break;
		case Mouche.LUNETTES:
			bmmVue++;
			break;
		case Mouche.MIEL:
			bmmRegeneration++;
			break;
		case Mouche.XIDANT:
			armure_mag++;
			break;
		case Mouche.RIVATANT:
			bmDLA -= 20;
			break;
		case Mouche.HEROS:
			break;
		case Mouche.CARNATION:
			break;
		case Mouche.NABOLISANT:
			bmmDegat++;
			break;

		}
		// System.out.println("Mouche de type "+m.getType()+" ajout�e");
		listeMouches.add(m);
	}

	public void setTeam(int i) {
		team = i;
	}

	public void setResu(int i) {
		nbResu = i;
	}

	public int getResu() {
		return nbResu;
	}

	public void setIcon(String i) {
		icon = i;
	}

	public String getIcon() {
		return icon;
	}

	public void addKill() {
		kill++;
	}

	public int getNbKills() {
		return kill;
	}

	public void addDeg(int i) {
		deginflig += i;
	}

	public int getDegInflig() {
		return deginflig;
	}

	public void addPAUtil(int i) {
		nbPA_utile += i;
	}

	public int getPAUtil() {
		return nbPA_utile;
	}

	public void addPVReg(int i) {
		nbPVRegen += i;
	}

	public int getPVRegen() {
		return nbPVRegen;
	}

	private void removeBMs() {
		for (int i = 0; i < listeBM.size(); i++) {
			BM bm = listeBM.elementAt(i);
			if (bm.newTurn()) {
				listeBM.remove(i);
				i--;
				bmAttaque -= bm.getBMAttaque();
				bmmAttaque -= bm.getBMMAttaque();
				bmEsquive -= bm.getBMEsquive();
				bmmEsquive -= bm.getBMMEsquive();
				bmDegat -= bm.getBMDegat();
				bmmDegat -= bm.getBMMDegat();
				bmRegeneration -= bm.getBMRegeneration();
				bmmRegeneration -= bm.getBMMRegeneration();
				bmVue -= bm.getBMVue();
				bmmVue -= bm.getBMMVue();
				bmDLA -= bm.getBMDLA();
				armure_phy -= bm.getBMArmurePhysique();
				armure_mag -= bm.getBMArmureMagique();
				bmMM -= bm.getBMMM();
				bmRM -= bm.getBMRM();
				if (bm.isGlue()) glue--;
				venin -= bm.getVenin();

			}
		}
	}

	private void removeAllBMs() {
		for (int i = 0; i < listeBM.size(); i++) {
			BM bm = listeBM.elementAt(i);
			listeBM.remove(i);
			i--;
			bmAttaque -= bm.getBMAttaque();
			bmmAttaque -= bm.getBMMAttaque();
			bmEsquive -= bm.getBMEsquive();
			bmmEsquive -= bm.getBMMEsquive();
			bmDegat -= bm.getBMDegat();
			bmmDegat -= bm.getBMMDegat();
			bmRegeneration -= bm.getBMRegeneration();
			bmmRegeneration -= bm.getBMMRegeneration();
			bmVue -= bm.getBMVue();
			bmmVue -= bm.getBMMVue();
			bmDLA -= bm.getBMDLA();
			armure_phy -= bm.getBMArmurePhysique();
			armure_mag -= bm.getBMArmureMagique();
			bmMM -= bm.getBMMM();
			bmRM -= bm.getBMRM();
			if (bm.isGlue()) glue--;
			venin -= bm.getVenin();
		}
	}

	public void addEquipement(Equipement e) {
		if (!listeEquipement.contains(e)) {
			listeEquipement.add(e);
			poids += e.getPoids();
		}
	}

	public void removeEquipement(Equipement e) {
		if (listeEquipement.contains(e)) {
			listeEquipement.remove(e);
			poids -= e.getPoids();
		}
	}

	private void bonusEquip(Equipement e, boolean equip) {
		int coeff = 1;
		if (!equip) coeff = -1;
		bmAttaque += e.getBMAttaque() * coeff;
		bmmAttaque += e.getBMMAttaque() * coeff;
		bmEsquive += e.getBMEsquive() * coeff;
		bmmEsquive += e.getBMMEsquive() * coeff;
		bmDegat += e.getBMDegat() * coeff;
		bmmDegat += e.getBMMDegat() * coeff;
		bmRegeneration += e.getBMRegeneration() * coeff;
		bmmRegeneration += e.getBMMRegeneration() * coeff;
		bmVue += e.getBMVue() * coeff;
		bmmVue += e.getBMMVue() * coeff;
		bmDLA += e.getBMDLA() * coeff;
		armure_phy += e.getBMArmurePhysique() * coeff;
		armure_mag += e.getBMArmureMagique() * coeff;
		bmMM += e.getBMMM() * coeff;
		bmRM += e.getBMRM() * coeff;
	}

	public void setProfil(int niv, int att, int esq, int deg, int reg, int pv,
			int v, int dla, int resm, int maim) {
		niveau = niv;
		des_Attaque = att;
		des_Esquive = esq;
		des_Degat = deg;
		pv_totaux = pv_actuels = pv;
		des_Regeneration = reg;
		vue = v;
		duree_tour = dla;
		rm = resm;
		mm = maim;
	}

	public String getInfosTroll() {
		if (!isCreated) return "Error: Le troll n'a pas encore �t� valid� !";
		String r = ("Num�ro      : " + id + "\n" + "Nom         : " + name
				+ "\n" + "Race        : " + race.name() + "\n"
				+ "Niveau      : " + niveau + "\n" + "Mouches     : "
				+ listeMouches.size() + "\n" + formatEquipePorte(tete)
				+ formatEquipePorte(cou) + formatEquipePorte(mainDroite)
				+ formatEquipePorte(mainGauche) + formatEquipePorte(torse) + formatEquipePorte(pieds));
		return r.substring(
			0,
			r.length() - 1);
	}

	private String formatEquipePorte(Equipement e) {
		if (e == null) return "";
		switch (e.getType()) {
		case armure:
			return "Torse       : " + e.getId() + " " + e.getName() + "\n";
		case bouclier:
			return "Main gauche : " + e.getId() + " " + e.getName() + "\n";
		case casque:
			return "T�te        : " + e.getId() + " " + e.getName() + "\n";
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
		String returnstring = "Nom : " + name + "\n" + "Niveau " + niveau
				+ "\n" + "Race : " + race.name() + "\n\n" + "Attaque : "
				+ des_Attaque + "D6 + " + bmAttaque;
		returnstring += "/" + bmmAttaque;
		returnstring += "\n" + "Esquive : " + des_Esquive + "D6 + " + bmEsquive
				+ "/" + bmmEsquive + "\n" + "D�gats : " + des_Degat + "D3 + "
				+ bmDegat;
		returnstring += "/" + bmmDegat;
		returnstring += "\n" + "R�g�n�ration : " + des_Regeneration + "D3 + "
				+ bmRegeneration + "/" + bmmRegeneration + "\n" + "Vue : "
				+ vue + " cases + " + bmVue + "/" + bmmVue + "\n" + "PV : "
				+ pv_actuels + "/" + pv_totaux + "\n\n" + "Dur�e DLA : "
				+ convertTime(duree_tour) + "\n" + "Poids �quipement : "
				+ convertTime(poids) + "\n" + "Bonus/malus DLA : "
				+ convertTime(bmDLA) + "\n\n" + "Armure : " + armure_phy
				+ " + " + armure_mag + "\n" + "MM : " + mm + " + "
				+ ((mm * bmMM) / 100) + "\n" + "RM : " + rm + " + "
				+ ((rm * bmRM) / 100) + "\n";

		returnstring += "Comp�tences : \n";

		Iterator<Competences> it = pourcentagesComp.keySet().iterator();
		while (it.hasNext()) {
			Competences idComp = it.next();
			Iterator<Integer> it2 = pourcentagesComp.get(
				idComp).keySet().iterator();
			while (it2.hasNext()) {
				int level = it2.next();
				int pour = pourcentagesComp.get(
					idComp).get(
					level);
				if (pour > 0)
					returnstring += idComp.toString() + " : " + pour
							+ "% (Niveau " + level + ")\n";
			}
		}
		returnstring += "Sorts : \n";
		for (Sort sort : Sort.values()) {
			if (pourcentagesSort.get(sort) > 0) {
				returnstring += sort + " : " + pourcentagesSort.get(sort)
						+ "%\n";
			}
		}
		return returnstring.substring(
			0,
			returnstring.length() - 1);
	}

	public String getFullProfil() {
		String s = debut_tour + ";" + duree_tour + ";" + bmDLA + ";"
				+ (250 * (pv_totaux - pv_actuels) / pv_totaux) + ";" + poids
				+ ";" + posx + ";" + posy + ";" + posn + ";" + vue + ";"
				+ formateCarac(bmVue) + "/" + formateCarac(bmmVue) + ";"
				+ niveau + ";" + pv_actuels + ";" + pv_totaux + ";"
				+ des_Regeneration + ";" + formateCarac(bmRegeneration) + "/"
				+ formateCarac(bmmRegeneration) + ";" + fatigue + ";"
				+ des_Attaque + ";";
		s += formateCarac(bmAttaque) + "/" + formateCarac(bmmAttaque) + ";";
		s += des_Esquive + ";" + formateCarac(bmEsquive) + "/"
				+ formateCarac(bmmEsquive) + ";" + des_Degat + ";";
		s += formateCarac(bmDegat) + "/" + formateCarac(bmmDegat) + ";";
		s += armure_phy + ";" + armure_mag + ";" + malus_des_esquive + ";"
				+ nbParade + ";" + nbCA + ";" + rm + ";" + bmRM + ";" + mm
				+ ";" + bmMM + ";" + concentration + ";";
		if (camoufle) s += "1;";
		else s += "0;";
		if (invisible) s += "1";
		else s += "0";
		return s;
	}

	public boolean addComp(Competences comp, int prctSuccess, int level) {
		if (comp.isReserved() && comp != race.compReserve()) return false;
		if (prctSuccess > 95) return false;
		if (level < 1 || level > comp.maxCompLvl) {
			level = comp.maxCompLvl;
		}
		setComp(
			comp,
			prctSuccess,
			level);
		return true;
	}

	public boolean addSort(Sort sort, int pour) {
		if (sort.isReserved() && sort != race.sortReserve()) return false;
		if (pour > 85) return false;
		pourcentagesSort.put(
			sort,
			pour);
		return true;
	}

	private int somme(int i) {
		return ((i + 1) * i) / 2;
	}

	public boolean valideTroll() {
		if (!verifNiveau()) return false;
		isCreated = true;
		return true;
	}

	public boolean isCreated() {
		return isCreated;
	}

	private String formateCarac(int carac) {
		if (carac > 0) return "+" + carac;
		return "" + carac;
	}

	public boolean verifNiveau() {
		int px = 0;
		if (race == races.skrim) px += somme(des_Attaque - 4) * 12;
		else px += somme(des_Attaque - 3) * 16;
		if (race == races.durakuir) px += somme((pv_totaux - 40) / 10) * 12;
		else px += somme((pv_totaux - 30) / 10) * 16;
		if (race == races.kastar) px += somme(des_Degat - 4) * 12;
		else px += somme(des_Degat - 3) * 16;
		if (race == races.tomawak) px += somme(vue - 4) * 12;
		else px += somme(vue - 3) * 16;
		px += somme(des_Esquive - 3) * 16;
		px += somme(des_Regeneration - 1) * 30;
		switch (duree_tour) {
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
			if (duree_tour > 555) return false;
			px += (somme((555 - duree_tour) * 2 / 5) + 55 + 10 * ((555 - duree_tour) * 2 / 5)) * 18;
		}
		Iterator<Competences> it = pourcentagesComp.keySet().iterator();
		while (it.hasNext()) {
			Competences ownedComp = it.next();
			int level = 0;
			Iterator<Integer> it2 = pourcentagesComp.get(
				ownedComp).keySet().iterator();
			while (it2.hasNext()) {
				int lev = it2.next();
				if (lev > level) level = lev;
			}
			for (int i = 1; i < level; i++)
				if (pourcentagesComp.get(
					ownedComp).get(
					i) == null || pourcentagesComp.get(
					ownedComp).get(
					i) < 75) return false;
			px += ownedComp.minTrollLvl() * ((level * (level + 1)) / 2);
		}
		// if((niveau)*(niveau+3)*5<=px)
		// System.out.println("Probl�me : Un niveau "+niveau+" a au max "+((niveau)*(niveau+3)*5)+" et vous en avez d�pens� "+px);
		return (niveau) * (niveau + 3) * 5 > px;
	}

	public void equipe(Equipement e) {
		if (!listeEquipement.contains(e)) return;
		switch (e.getType()) {
		case armure:
			if (torse != null) bonusEquip(
				torse,
				false);
			torse = e;
			bonusEquip(
				e,
				true);
			return;
		case bouclier:
			if (mainGauche != null) bonusEquip(
				mainGauche,
				false);
			if (mainDroite != null && mainDroite.getType() == types.arme2h) {
				bonusEquip(
					mainDroite,
					false);
				mainDroite = null;
			}
			mainGauche = e;
			bonusEquip(
				e,
				true);
			return;
		case casque:
			if (tete != null) bonusEquip(
				tete,
				false);
			tete = e;
			bonusEquip(
				e,
				true);
			return;
		case arme1H:
			if (mainDroite != null) bonusEquip(
				mainDroite,
				false);
			mainDroite = e;
			bonusEquip(
				e,
				true);
			return;
		case talisman:
			if (cou != null) bonusEquip(
				cou,
				false);
			cou = e;
			bonusEquip(
				e,
				true);
			return;
		case bottes:
			if (pieds != null) bonusEquip(
				pieds,
				false);
			pieds = e;
			bonusEquip(
				e,
				true);
			return;
		case arme2h:
			if (mainDroite != null) bonusEquip(
				mainDroite,
				false);
			if (mainGauche != null) {
				bonusEquip(
					mainGauche,
					false);
				mainGauche = null;
			}
			mainDroite = e;
			bonusEquip(
				e,
				true);
			return;
		}
		return;
	}

	public Equipement getEquipementById(int id) {
		for (int i = 0; i < listeEquipement.size(); i++) {
			if (listeEquipement.elementAt(
				i).getId() == id) return listeEquipement.elementAt(i);
		}
		return null;
	}

	public String getEquip() {
		String s = "";
		for (int i = 0; i < listeEquipement.size(); i++) {
			int equip = 0;
			Equipement e = listeEquipement.elementAt(i);
			if (e == torse || e == mainGauche || e == mainDroite || e == tete
					|| e == cou || e == pieds) equip = 1;
			s += e.getId() + ";" + e.getType() + ";" + equip + ";"
					+ e.getDescr() + ";" + e.getName() + "\n";
		}
		if (s.equals("")) return "Aucun �quipement";
		return s.substring(
			0,
			s.length() - 1);
	}

	public boolean isEquipe(Equipement e) {
		switch (e.getType()) {
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
		return bmAttaque;
	}

	public int getBMMAttaque() {
		return bmmAttaque;
	}

	public int getBMEsquive() {
		return bmEsquive;
	}

	public int getBMMEsquive() {
		return bmmEsquive;
	}

	public int getBMDegat() {
		return bmDegat;
	}

	public int getBMMDegat() {
		return bmmDegat;
	}

	public int getBMVue() {
		return bmVue;
	}

	public int getBMMVue() {
		return bmmVue;
	}

	public int getRM() {
		return rm + ((rm * bmRM) / 100);
	}

	public int getMM() {
		return mm + ((mm * bmMM) / 100);
	}

	public boolean isGlue() {
		return glue > 0;
	}

	public int getReussiteSort(Sort sort) {
		return pourcentagesSort.get(sort);
	}

	public int getLevelComp(Competences comp) {
		Hashtable<Integer, Integer> h = pourcentagesComp.get(comp);
		if (h == null) return 0;
		int level = 0;
		Iterator<Integer> it = h.keySet().iterator();
		while (it.hasNext()) {
			int lev = it.next();
			if (lev > level) level = lev;
		}
		return level;
	}

	public int getReussiteComp(Competences comp, int level) {
		Hashtable<Integer, Integer> h = pourcentagesComp.get(comp);
		if (h == null) return 0;
		Integer i = h.get(level);
		if (i == null) return 0;
		return i;
	}

	public void augmentComp(Competences comp, int level, int pour) {
		Hashtable<Integer, Integer> h = pourcentagesComp.get(comp);
		if (h == null) return;
		Integer i = h.get(level);
		if (i == null) return;
		h.put(
			level,
			i + pour);
	}

	public void augmentSort(Sort sort, int i) {
		int val = pourcentagesSort.get(sort);
		val += i;
		if (val > 80) {
			val = 80;
		}
		pourcentagesSort.put(
			sort,
			val);
	}

	public void setPos(int x, int y, int n) {
		posx = x;
		posy = y;
		posn = n;
	}

	public int getPosX() {
		return posx;
	}

	public int getPosY() {
		return posy;
	}

	public int getPosN() {
		return posn;
	}

	public void move(int x, int y, int n) {
		posx += x;
		posy += y;
		posn += n;
	}

	public String getPos() {
		return posx + " " + posy + " " + posn;
	}

	public int getTeam() {
		return team;
	}

	public void blesse(int i) {
		pv_actuels -= i;
		// Je suis mort :) peut etre ?
	}

	public int getArmurePhy() {
		return armure_phy;
	}

	public int getArmureMag() {
		return armure_mag;
	}

	public int getAttaque() {
		return Math.max(
			0,
			des_Attaque - malus_des_attaque);
	}

	public boolean isDead() {
		return (pv_actuels <= 0);
	}

	public int getEsquive() {
		return Math.max(
			0,
			des_Esquive - malus_des_esquive);
	}

	public int getEsquiveTotale() {
		return des_Esquive;
	}

	public int getDegat() {
		return des_Degat;
	}

	public int getRegeneration() {
		return des_Regeneration;
	}

	public void getTouch() {
		malus_des_esquive++;
	}

	public void getParade() {
		malus_des_attaque++;
		malus_des_esquive++;
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

	public boolean sameNameOrId(Troll t) {
		return ((t.getId() == id) || (t.getName().equals(name)));
	}

	public int getDateJeu() {
		return date_jeu;
	}

	public int getNouveauTour() {
		return debut_tour;
	}

	public void setNouveauTour(int i) {
		debut_tour = i;
	}

	public int getFatigue() {
		return fatigue;
	}

	public void setFatigue(int i) {
		fatigue = i;
	}

	public void decale(int i) {
		date_jeu += i;
		isActive = false;
	}

	public int getPA() {
		return pa;
	}

	public int getPV() {
		return pv_actuels;
	}

	public int getPVTotaux() {
		return pv_totaux;
	}

	public void setPV(int i) {
		pv_actuels = i;
	}

	public void setPA(int i) {
		pa = i;
	}

	public void setSortReserve(boolean b) {
		sortReserve = b;
	}

	public boolean getSortReserve() {
		return sortReserve;
	}

	public void setCertif(boolean b) {
		isCertif = b;
	}

	public boolean isCertif() {
		return isCertif;
	}

	public void setCompReservee(boolean b) {
		compReservee = b;
	}

	public boolean getCompReservee() {
		return compReservee;
	}

	public int getConcentration() {
		return concentration;
	}

	public void setConcentration(int i) {
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

	public String beginTurn(int current) {
		// Il faut r�g�n�rer
		// Regarder les bonus/malus encore en cours
		// Virer les poisons
		// Mouches ?
		// remettre l'esquive !
		// g�rer la concentration
		// Calculer la nouvelle DLA
		// Remettre les PA.
		String s = "";
		boolean modify = false;

		if (isDead() && nbResu > 0) {
			nbResu--;
			s += "\nVous �tiez mort mais vous revenez � la vie\nIl ne vous reste plus que "
					+ nbResu + " r�surrection(s)\n";
			if (MHAGame.instance().getMode() == gameModes.teamdeathmatch
					&& MHAGame.instance().isRegroupe()) {
				MHAGame.instance().placeTrollInHisTeam(
					this);
			} else setPos(
				MHAGame.instance().roll(
					1,
					MHAGame.instance().getSizeArena()) - 1,
				MHAGame.instance().roll(
					1,
					MHAGame.instance().getSizeArena()) - 1,
				-MHAGame.instance().roll(
					1,
					(MHAGame.instance().getSizeArena() + 1) / 2));
			pv_actuels = (pv_totaux * 50) / 100;
			s += "Vous r�apparaissez en X=" + posx + " Y=" + posy + " N="
					+ posn + " avec seulement " + pv_actuels + "PV";
			removeAllBMs();
			pa = 0;
			debut_tour = current;
		} else {
			while (debut_tour <= current) {
				if (poids + (250 * (pv_totaux - pv_actuels) / pv_totaux)
						+ bmDLA < 0) {
					debut_tour += duree_tour;
					dureeTourTotale = duree_tour;
				} else {
					debut_tour += duree_tour + poids
							+ (250 * (pv_totaux - pv_actuels) / pv_totaux)
							+ bmDLA;
					dureeTourTotale = duree_tour + poids
							+ (250 * (pv_totaux - pv_actuels) / pv_totaux)
							+ bmDLA;
				}
			}
			removeBMs();
			s += "\nVotre tour de jeu et votre nouvelle date limite d'action ont �t� calcul�s.";
			if (pv_totaux - pv_actuels != 0
					&& (250 * (pv_totaux - pv_actuels) / pv_totaux) + Math.min(
						0,
						poids + bmDLA) > 0)
				s += "\nSa dur�e est augment�e de "
						+ convertTime((250 * (pv_totaux - pv_actuels) / pv_totaux)
								+ Math.min(
									0,
									poids + bmDLA))
						+ " � cause de vos blessures.";
			if (poids + bmDLA > 0) {
				s += "\nSa dur�e est augment�e de "
						+ convertTime(poids + bmDLA)
						+ " � cause du poids de votre Equipement.";
			}
		}
		s += "\nVotre nouvelle date limite d'action est le "
				+ hour2string(debut_tour);
		int i = Math.max(
			MHAGame.instance().roll(
				des_Regeneration,
				3) + bmRegeneration + bmmRegeneration,
			0);
		int j = i - venin;
		if (pv_actuels < pv_totaux) {
			s += "\nVous aviez " + pv_actuels + " Points de Vie.";
			s += "\nVous avez r�g�n�r� de " + i + " Points de Vie.";
			nbPVRegen += Math.min(
				pv_totaux - pv_actuels,
				i);
			modify = true;
		}
		pv_actuels = Math.min(
			pv_totaux,
			pv_actuels + j);
		if (venin > 0) {
			s += "\nLe venin vous fait perdre " + venin + " Points de Vie.";
			if (pv_actuels <= 0) {
				s += "\nVous �tes mort !!!!";
				return s.substring(1);
			}
			modify = true;
		}
		if (modify)
			s += "\nVotre total actuel est donc de " + pv_actuels
					+ " Points de Vie.";
		// Le venin !!!!
		if (pa != 0) {
			s += "\n"
					+ pa
					+ " PA de votre tour pr�c�dent n'ont pas �t� utilis�s et on �t� automatiquement attribu�s � une concentration.";
			concentration += pa * 5;
		}
		if (fatigue > 0) {
			fatigue = (int) Math.floor(((double) fatigue) / 1.25);
			s += "\nVotre fatigue est de " + fatigue;
		}
		isActive = true;
		compReservee = false;
		sortReserve = false;
		frenetique = false;
		malus_des_esquive = 0;
		malus_des_attaque = 0;
		nbParade = 0;
		nbCA = 0;
		pa = 6;
		return s.substring(1);
	}

	public boolean getCamouflage() {
		return camoufle;
	}

	public void setCamouflage(boolean b) {
		camoufle = b;
	}

	public boolean getFrenetique() {
		return frenetique;
	}

	public void setFrenetique(boolean b) {
		frenetique = b;
	}

	public boolean getInvisible() {
		return invisible;
	}

	public void setInvisible(boolean b) {
		invisible = b;
	}

	public int getNbCA() {
		return nbCA;
	}

	public void setNbCA(int i) {
		nbCA = i;
	}

	public int getNbParade() {
		return nbParade;
	}

	public void setNbParade(int i) {
		nbParade = i;
	}

	public int getTempsRestant() {
		return tempsRestant;
	}

	public void setTempsRestant(int i) {
		tempsRestant = i;
	}

	public boolean isVisibleFrom(int x, int y, int n, int v) {
		if (v < 0) v = 0;
		if (invisible || isDead()) return false;
		if (camoufle) return x == posx && y == posy && n == posn && v >= 0;
		return Math.abs(x - posx) <= v && Math.abs(y - posy) <= v
				&& Math.abs(n - posn) <= (v + 1) / 2;
	}

	public static String convertTime(int t) {
		if (t >= 0) return (t / 60) + " heures et " + (t % 60) + " minutes";
		else return "-" + (-t / 60) + " heures et -" + ((-t) % 60) + " minutes";
	}

	public static String hour2string(int t) {
		return "jour " + (t / (60 * 24) + 1) + " � "
				+ convertTime(t % (60 * 24));
	}

	public Vector<String> getInbox() {
		return inbox;
	}

}