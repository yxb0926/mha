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

public class Equipement extends BM {

	private static final long serialVersionUID = 1L;
	
	protected int id;
	protected types type;
	protected boolean zone;
	protected boolean droppable;
	protected int poids;

	public static enum types {
			armure("Armure"),
			bouclier("Bouclier"),
			casque("Casque"),
			arme1H("Arme (une main)"),
			talisman("Talisman"),
			bottes("Bottes"),
			bidouille("Bidouille"),
			anneau("Anneau"),
			bricabrac("Bric à Brac"),
			arme2h("Arme (deux mains)"),
			composant("Composant"),
			parchemin("Parchemin"),
			potion("Potion"),
			tarot("Tarot"),
			champignon("Chamignon"),
			minerai("Minerai");

		protected String name;

		types(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	};

	/** big constructor */
	// TODO use the PVs
	public Equipement(int id, String name, boolean zone, boolean droppable,
			int poidsMins, types type, int att, int attM, int esq, int esqM,
			int deg, int degM, int dlaMin, int reg, int regM, int vue,
			int vueM, int soin, int pvMax, int arm, int armM, int mm, int rm) {
		super(name, att, attM, esq, esqM, deg, degM, dlaMin, reg, regM, vue,
			vueM, soin, pvMax, arm, armM, mm, rm);
		this.id = id;
		this.zone = zone;
		this.droppable = droppable;
		this.poids = poidsMins;
		this.type = type;
	}

	public Equipement(int id, String name, types type, int att, int esq,
			int deg, int dla, int reg, int vue, int soin, int arm, int armM,
			int mm, int rm, boolean zone, boolean drop, int poids) {
		this(id, name, zone, drop, poids, type, att, 0, esq, 0, deg, 0, dla,
			reg, 0, vue, 0, soin, 0, arm, armM, mm, rm);
	}

	public Equipement(int id, String name, types type, int att, int attM,
			int esq, int deg, int degM, int dla, int reg, int vue, int soin,
			int arm, int armM, int mm, int rm, boolean zone, boolean drop,
			int poids) {
		this(id, name, zone, drop, poids, type, att, 0, esq, 0, deg, degM, dla,
			reg, 0, vue, 0, soin, 0, arm, armM, mm, rm);
	}

	public Equipement(int id, String name, types type, int att, int attM,
			int esq, int esqM, int deg, int degM, int dla, int reg, int regM,
			int vue, int vueM, int soin, int arm, int armM, int mm, int rm,
			boolean zone, boolean drop, int poids) {
		this(id, name, zone, drop, poids, type, att, attM, esq, esqM, deg,
			degM, dla, reg, regM, vue, vueM, soin, 0, arm, armM, mm, rm);
	}

	public static String formate(String s, int v) {
		if (v > 0) return s + " +" + v + ", ";
		if (v < 0) return s + " " + v + ", ";
		return "";
	}

	public static String formate(String s, int v1, int v2) {
		if (v2 == 0) return formate(s, v1);
		String result = s;
		if (v1 > 0) result += "+";
		result += v1 + "/";
		if (v2 > 0) result += "+";
		result += v2 + ", ";
		return result;
	}

	protected String formate(String s, int v1, int v2, String s1) {
		if (v2 == 0) return formate(s, v1, s1);
		String result = s;
		if (v1 > 0) result += "+";
		result += v1 + "/";
		if (v2 > 0) result += "+";
		result += v2 + s1;
		return result;
	}

	public static String formate(String s, int v, String s1) {
		if (v > 0) return s + "+" + v + s1;
		if (v < 0) return s + v + s1;
		return "";
	}

	protected String formate(String s, boolean b, String s1) {
		if (b) return s + s1;
		else return "";
	}

	public String toString() {
		switch (type) {
		case potion:
		case parchemin:
			return name + " (" + type.toString() + ") : "
					+ formate("Att", att, "D3, ") + formate("Esq", esq, "D3, ")
					+ formate("Deg", deg) + formate("DLA", dlaMin, " min, ")
					+ formate("Reg", reg) + formate("PV", soin, "D3, ")
					+ formate("Arm", arm + armM) + formate("Vue", vue + vueM)
					+ formate("MM", mm, "%, ") + formate("RM", rm, "%, ")
					+ formate("Effet de zone, ", zone, ", ") + "Poids: "
					+ poids + " min";
		default:
			return name + " (" + type.toString() + ") : "
					+ formate("Att", att, attM) + formate("Esq", esq, esqM)
					+ formate("Deg", deg, degM) + formate("DLA", dlaMin)
					+ formate("Reg", reg + regM) + formate("PV", soin)
					+ formate("Arm", arm, armM) + formate("Vue", vue)
					+ formate("MM", mm, "%") + formate("RM", rm, "%")
					+ "Poids: " + poids + " min";
		}
	}

	public String getDescr() {
		switch (type) {
		case potion:
		case parchemin:
			return formate("Att ", att, attM, "D3 | ")
					+ formate("Esq ", esq + esqM, "D3 | ")
					+ formate("Deg ", deg, degM, " | ")
					+ formate("DLA ", dlaMin, " min | ")
					+ formate("Reg ", reg + regM, " | ")
					+ formate("PV ", soin, "D3 | ")
					+ formate("PVMax ", pvMax, " | ")
					+ formate("Arm ", arm, armM, " | ")
					+ formate("Vue ", vue + vueM, " | ")
					+ formate("MM", mm, "% | ") + formate("RM ", rm, "% | ")
					+ formate("Effet de zone", zone, " | ") + "Poids " + poids
					+ " min";
		default:
			return formate("Att ", att, attM, " | ")
					+ formate("Esq ", esq + esqM, " | ")
					+ formate("Deg ", deg, degM, " | ")
					+ formate("DLA ", dlaMin, " min | ")
					+ formate("Reg ", reg + regM, " | ")
					+ formate("PV ", soin, " | ")
					+ formate("Arm ", arm, armM, " | ")
					+ formate("Vue ", vue + vueM, " | ")
					+ formate("MM ", mm, "% | ") + formate("RM ", rm, "% | ")
					+ "Poids " + poids + " min";
		}
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

	public boolean isDroppable() {
		return droppable;
	}
}
