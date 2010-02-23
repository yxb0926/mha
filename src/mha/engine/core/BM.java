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

import java.lang.reflect.Field;

public class BM implements Cloneable {

	protected String name;
	protected int bmAttaque = 0;
	protected int bmmAttaque = 0;
	protected int bmEsquive = 0;
	protected int bmmEsquive = 0;
	protected int bmDegat = 0;
	protected int bmmDegat = 0;
	protected int bmDLA = 0;
	protected int bmRegeneration = 0;
	protected int bmmRegeneration = 0;
	protected int bmVue = 0;
	protected int bmmVue = 0;
	protected int bmVenin = 0;
	protected int bmArmurePhysique = 0;
	protected int bmArmureMagique = 0;
	protected int bmMM = 0;
	protected int bmRM = 0;
	protected boolean glue = false;
	protected boolean levitation = false;
	protected int parades = 0;
	protected int retraites = 0;
	protected int retraiteX = 0;
	protected int retraiteY = 0;
	protected int retraiteZ = 0;

	protected int duree = 0;

	public BM(String name, int att, int esq, int deg, int dlaMalus, int reg,
			int vue, int venin, int arm, int armM, int mm, int rm,
			boolean glued, int duree) {
		this(name, att, 0, esq, 0, deg, 0, dlaMalus, reg, 0, vue, 0, venin,
				arm, armM, mm, rm, glued, false, 0, 0, 0, 0, 0, duree);
	}

	/**
	 * generates a basic bonus with no special effect.
	 * 
	 * @param name
	 * @param duree
	 */
	public BM(String name, int duree) {
		this.name = name;
		this.duree = duree;
	}

	public BM(String name, int att, int attM, int esq, int deg, int degM,
			int dla, int reg, int vue, int venin, int arm, int armM, int mm,
			int rm, boolean glued, int duree) {
		this(name, att, attM, esq, 0, deg, degM, dla, reg, 0, vue, 0, venin, arm, armM, mm, rm,
				glued, false, 0, 0, 0, 0, 0, duree);
	}

	/**
	 * full definition of a BM.
	 */
	public BM(String name, int att, int attM, int esq, int esqM, int deg,
			int degM, int dlaMalus, int reg, int regM, int vue, int vueM,
			int venin, int arm, int armM, int mm, int rm, boolean glued, boolean levitation,
			int parades, int retraites, int retraiteX, int retraiteY,
			int retraiteZ, int duree) {

		this.name = name;
		this.bmAttaque = att;
		this.bmmAttaque = attM;
		this.bmEsquive = esq;
		this.bmmEsquive = esqM;
		this.bmDegat = deg;
		this.bmmDegat = degM;
		this.bmDLA = dlaMalus;
		this.bmRegeneration = reg;
		this.bmmRegeneration = regM;
		this.bmVue = vue;
		this.bmmVue = vueM;
		this.bmVenin = venin;
		this.bmArmurePhysique = arm;
		this.bmArmureMagique = armM;
		this.bmMM = mm;
		this.bmRM = rm;
		this.glue = glued;
		this.levitation = levitation;
		this.parades = parades;
		this.retraites = retraites;
		this.retraiteX = retraiteX;
		this.retraiteY = retraiteY;
		this.retraiteZ = retraiteZ;

		this.duree = duree;
	}

	/**
	 * format a bonus according to the sign of the value
	 * 
	 * @param description
	 *            the name of the bonus
	 * @param value
	 *            the effect of the bonus
	 * @return how the bonus is supposed to be shown to the user.
	 */
	public static String formate(String description, int value) {
		return formate(description, value, "");
	}

	/**
	 * format a bonus which has a magical and a physical part.
	 * 
	 * @param description
	 *            the description of the carac bonused
	 * @param physical
	 *            the physical value of the bonus
	 * @param magical
	 *            the magical value of the bonus
	 * @return how the bonus to the carac is to be shown to user.
	 */
	public static String formate(String description, int physical, int magical) {
		if (physical == 0 && magical == 0)
			return "";
		String ret = description;
		if (physical >= 0)
			ret += " +" + physical;
		else
			ret += " " + physical;
		if (magical != 0)
			ret += "/" + (magical > 0 ? "+" : "") + magical;
		return ret;
	}

	/**
	 * format a bonus according to the sign of the value
	 * 
	 * @param description
	 *            the name of the bonus
	 * @param value
	 *            the effect of the bonus
	 * @param postdesc
	 *            the description that should appear after the value
	 * @return how the bonus is supposed to be shown to the user.
	 */
	public static String formate(String description, int value, String postdesc) {
		if (value == 0) {
			return "";
		}
		return formate(description, value, 0) + postdesc;
	}

	public String toString() {
		return name + " : " + formate("Att", bmAttaque, bmmAttaque)
				+ formate("Esq", bmEsquive, bmmEsquive)
				+ formate("Deg", bmDegat, bmmDegat)
				+ formate("Vue", bmVue, bmmVue) + formate("DLA", bmDLA)
				+ formate("Reg", bmRegeneration, bmmRegeneration)
				+ formate("Venin", bmVenin)
				+ formate("Arm", bmArmurePhysique + bmArmureMagique)
				+ formate("MM", bmMM, "%, ") + formate("RM", bmRM, "%, ")
				+ ", Durée " + duree + " tour(s)";
	}

	public String getName() {
		return name;
	}

	public int getDuree() {
		return duree;
	}

	public boolean newTurn() {
		if (duree <= 0)
			return true;
		duree--;
		return false;
	}

	public boolean isGlue() {
		return glue;
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

	public int getBMDLA() {
		return bmDLA;
	}

	public int getBMRegeneration() {
		return bmRegeneration;
	}

	public int getBMMRegeneration() {
		return bmmRegeneration;
	}

	public int getVenin() {
		return bmVenin;
	}

	public int getBMVue() {
		return bmVue;
	}

	public int getBMMVue() {
		return bmmVue;
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

	/**
	 * add the effect of another BM to this. Remove the sense o the retraite
	 * directions and duree.
	 * 
	 * @param other
	 *            the BM to add to this
	 * @param minturn
	 *            the minimum duree for other to be considered
	 */
	public void addOtherBM(BM other, int minturn) {
		if (other.duree < minturn) {
			return;
		}
		for (Field f : BM.class.getDeclaredFields()) {
			if (f.getName().equals("duree")) {
				continue;
			}
			f.setAccessible(true);
			if (f.getType() == int.class || Integer.class == f.getType()) {
				try {
					f.set(this, (int) ((Integer) f.get(this) + (Integer) f
							.get(other)));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			if (boolean.class == f.getType() || Boolean.class == f.getType()) {
				try {
					f.set(this, (boolean) ((Boolean) f.get(this) || (Boolean) f
							.get(other)));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
