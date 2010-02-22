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

public class Equipement {

	protected String name;
	protected int id;
	protected types type;
	protected int bmAttaque;
	protected int bmmAttaque = 0;
	protected int bmEsquive;
	protected int bmmEsquive;
	protected int bmDegat;
	protected int bmmDegat = 0;
	protected int bmDLA;
	protected int bmRegeneration;
	protected int bmmRegeneration;
	protected int bmPV;
	protected int bmVue;
	protected int bmmVue;
	protected int bmArmurePhysique;
	protected int bmArmureMagique;
	protected int bmMM;
	protected int bmRM;
	protected boolean zone;
	protected boolean droppable;
	protected int poids;

	public static enum types {
		armure("Armure"), bouclier("Bouclier"), casque("Casque"), arme1H(
				"Arme (une main)"), talisman("Talisman"), bottes("Bottes"), bidouille(
				"Bidouille"), anneau("Anneau"), bricabrac("Bric à Brac"), arme2h(
				"Arme (deux mains)"), composant("Composant"), parchemin(
				"Parchemin"), potion("Potion"), tarot("Tarot"), champignon(
				"Chamignon"), minerai("Minerai");

		protected String name;

		types(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	};

	public Equipement(int i, String n, types t, int a, int e, int d, int dla,
			int r, int vue, int pv, int ap, int am, int bmm, int brm,
			boolean g, boolean dr, int p) {
		id = i;
		name = n;
		type = t;
		bmAttaque = a;
		bmEsquive = e;
		bmDegat = d;
		bmDLA = dla;
		bmRegeneration = r;
		bmPV = pv;
		bmVue = vue;
		bmArmurePhysique = ap;
		bmArmureMagique = am;
		;
		zone = g;
		droppable = dr;
		poids = p;
		bmMM = bmm;
		bmRM = brm;
	}

	public Equipement(int i, String n, types type, int a, int bam, int e,
			int d, int dm, int dla, int r, int vue, int pv, int ap, int am,
			int bmm, int brm, boolean g, boolean dr, int p) {
		this(i, n, type, a, e, d, dla, r, vue, pv, ap, am, bmm, brm, g, dr, p);
		bmmAttaque = bam;
		bmmDegat = dm;
	}

	public Equipement(int i, String n, types type, int a, int bam, int e,
			int em, int d, int dm, int dla, int r, int rem, int vue, int vuem,
			int pv, int ap, int am, int bmm, int brm, boolean g, boolean dr,
			int p) {
		this(i, n, type, a, e, d, dla, r, vue, pv, ap, am, bmm, brm, g, dr, p);
		bmmAttaque = bam;
		bmmDegat = dm;
		bmmEsquive = em;
		bmmRegeneration = rem;
		bmmVue = vuem;
	}

	protected String formate(String s, int v) {
		if (v > 0)
			return s + " +" + v + ", ";
		if (v < 0)
			return s + " " + v + ", ";
		return "";
	}

	protected String formate(String s, int v1, int v2) {
		if (v2 == 0)
			return formate(s, v1);
		String result = s;
		if (v1 > 0)
			result += "+";
		result += v1 + "/";
		if (v2 > 0)
			result += "+";
		result += v2 + ", ";
		return result;
	}

	protected String formate(String s, int v1, int v2, String s1) {
		if (v2 == 0)
			return formate(s, v1, s1);
		String result = s;
		if (v1 > 0)
			result += "+";
		result += v1 + "/";
		if (v2 > 0)
			result += "+";
		result += v2 + s1;
		return result;
	}

	protected String formate(String s, int v, String s1) {
		if (v > 0)
			return s + "+" + v + s1;
		if (v < 0)
			return s + v + s1;
		return "";
	}

	protected String formate(String s, boolean b, String s1) {
		if (b)
			return s + s1;
		else
			return "";
	}

	public String toString() {
		switch(type) {
		case potion:
		case parchemin:
			return name + " (" + type.toString() + ") : "
					+ formate("Att", bmAttaque, "D3, ")
					+ formate("Esq", bmEsquive, "D3, ")
					+ formate("Deg", bmDegat) + formate("DLA", bmDLA, " min, ")
					+ formate("Reg", bmRegeneration)
					+ formate("PV", bmPV, "D3, ")
					+ formate("Arm", bmArmurePhysique + bmArmureMagique)
					+ formate("Vue", bmVue) + formate("MM", bmMM, "%, ")
					+ formate("RM", bmRM, "%, ")
					+ formate("Effet de zone, ", zone, ", ") + "Poids: "
					+ poids + " min";
		default :
			return name + " (" + type.toString() + ") : "
				+ formate("Att", bmAttaque, bmmAttaque)
				+ formate("Esq", bmEsquive, bmmEsquive)
				+ formate("Deg", bmDegat, bmmDegat) + formate("DLA", bmDLA)
				+ formate("Reg", bmRegeneration) + formate("PV", bmPV)
				+ formate("Arm", bmArmurePhysique + bmArmureMagique)
				+ formate("Vue", bmVue) + formate("MM", bmMM)
				+ formate("RM", bmRM) + "Poids: " + poids + " min";
		}
	}

	public String getDescr() {
		switch(type) {
		case potion:
		case parchemin :
			return formate("Att ", bmAttaque, "D3 | ")
					+ formate("Esq ", bmEsquive, "D3 | ")
					+ formate("Deg ", bmDegat, " | ")
					+ formate("DLA ", bmDLA, " min | ")
					+ formate("Reg ", bmRegeneration, " | ")
					+ formate("PV ", bmPV, "D3 | ")
					+ formate("Arm ", bmArmurePhysique + bmArmureMagique, " | ")
					+ formate("Vue ", bmVue, " | ")
					+ formate("MM", bmMM, "% | ")
					+ formate("RM ", bmRM, "% | ")
					+ formate("Effet de zone", zone, " | ") + "Poids " + poids
					+ " min";
		default :
			return formate("Att ", bmAttaque, bmmAttaque, " | ")
				+ formate("Esq ", bmEsquive, bmmEsquive, " | ")
				+ formate("Deg ", bmDegat, bmmDegat, " | ")
				+ formate("DLA ", bmDLA, " min | ")
				+ formate("Reg ", bmRegeneration, bmmRegeneration, " | ")
				+ formate("PV ", bmPV, " | ")
				+ formate("Arm ", bmArmurePhysique + bmArmureMagique, " | ")
				+ formate("Vue ", bmVue, bmmVue, " | ")
				+ formate("MM ", bmMM, "% | ") + formate("RM ", bmRM, "% | ")
				+ "Poids " + poids + " min";	
		}
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public types getType() {
		return type;
	}

	public int getPoids() {
		return poids;
	}

	public boolean isZone() {
		return zone;
	}

	public boolean isBidouille() {
		return droppable;
	}

	public int getBMAttaque() {
		return bmAttaque;
	}

	public int getBMMAttaque() {
		return bmmAttaque;
	}

	public boolean isDroppable() {
		return droppable;
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

	public int getBMDLA() {
		return bmDLA;
	}

	public int getBMRegeneration() {
		return bmRegeneration;
	}

	public int getBMMRegeneration() {
		return bmmRegeneration;
	}

	public int getPV() {
		return bmPV;
	}

	public int getBMVue() {
		return bmVue;
	}

	public int getBMMVue() {
		return bmVue;
	}

	public int getBMArmurePhysique() {
		return bmArmurePhysique;
	}

	public int getBMArmureMagique() {
		return bmArmureMagique;
	}

	public int getBMMM() {
		return bmMM;
	}

	public int getBMRM() {
		return bmRM;
	}
}
