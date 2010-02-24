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

import java.io.Serializable;
import java.lang.reflect.Field;

public class BM implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;

	protected String name = "";
	protected int att = 0;
	protected int attM = 0;
	protected int attD = 0;
	protected int esq = 0;
	protected int esqM = 0;
	protected int esqD = 0;
	protected int deg = 0;
	protected int degM = 0;
	protected int dlaMin = 0;
	protected int reg = 0;
	protected int regM = 0;
	protected int vue = 0;
	protected int vueM = 0;
	protected int soin = 0;
	protected int pvMax = 0;
	protected int arm = 0;
	protected int armM = 0;
	protected int mm = 0;
	protected int rm = 0;
	protected boolean glued = false;
	protected boolean levite = false;
	protected boolean frenetique = false;
	protected int parades = 0;
	protected int CA = 0;
	protected int retraites = 0;
	protected int dirX = 0;
	protected int dirY = 0;
	protected int dirZ = 0;

	protected int duree = 0;

	/**
	 * Big constructor
	 */
	public BM( String name, int att, int attM, int attD, int esq, int esqM,
			int esqD, int deg, int degM, int dlaMin, int reg, int regM, int vue,
			int vueM, int soin, int pvMax, int arm, int armM, int mm, int rm,
			boolean glued, boolean levitate, boolean frenetique, int parades, int CA,
			int retraites, int dirX, int dirY, int dirZ, int duree ) {

		this.name = name;
		this.att = att;
		this.attM = attM;
		this.attD = attD;
		this.esq = esq;
		this.esqM = esqM;
		this.esqD = esqD;
		this.deg = deg;
		this.degM = degM;
		this.dlaMin = dlaMin;
		this.reg = reg;
		this.regM = regM;
		this.vue = vue;
		this.vueM = vueM;
		this.soin = soin;
		this.pvMax = pvMax;
		this.arm = arm;
		this.armM = armM;
		this.mm = mm;
		this.rm = rm;
		this.glued = glued;
		this.levite = levitate;
		this.frenetique = frenetique;
		this.parades = parades;
		this.CA = CA;
		this.retraites = retraites;
		this.dirX = dirX;
		this.dirY = dirY;
		this.dirZ = dirZ;

		this.duree = duree;
	}

	public BM( String name, int att, int esq, int deg, int dlaMalus, int reg,
			int vue, int soin, int arm, int armM, int mm, int rm, boolean glued,
			int duree ) {
		this( name, att, 0, 0, esq, 0, 0, deg, 0, dlaMalus, reg, 0, vue, 0, soin,
				0, arm, armM, mm, rm, glued, false, false, 0, 0, 0, 0, 0, 0, duree );
	}

	/**
	 * generates a basic bonus with no special effect.
	 */
	public BM() {}

	public BM( String name, int att, int attM, int esq, int deg, int degM,
			int dla, int reg, int vue, int soin, int arm, int armM, int mm, int rm,
			boolean glued, int duree ) {
		this( name, att, attM, 0, esq, 0, 0, deg, degM, dla, reg, 0, vue, 0, soin,
				0, arm, armM, mm, rm, glued, false, false, 0, 0, 0, 0, 0, 0, duree );
	}

	/**
	 * constructor used for Mouches
	 */
	public BM( int attM, int esqM, int degM, int dla, int regM, int vueM,
			int armM, int PvsMax ) {
		this( "", 0, attM, 0, 0, esqM, 0, 0, degM, dla, 0, regM, 0, vueM, 0, 0, 0,
				armM, 0, 0, false, false, false, 0, 0, 0, 0, 0, 0, 0 );
	}

	/**
	 * constructor for Equips
	 */
	public BM( String name, int att, int attM, int esq, int esqM, int deg,
			int degM, int dlaMin, int reg, int regM, int vue, int vueM, int soin,
			int pvMax, int arm, int armM, int mm, int rm ) {
		this( name, att, attM, 0, esq, esqM, 0, deg, degM, dlaMin, reg, regM, vue,
				vueM, soin, pvMax, arm, armM, mm, rm, false, false, false, 0, 0, 0, 0,
				0, 0, 0 );
	}

	/**
	 * format a bonus according to the sign of the value
	 * @param description
	 *          the name of the bonus
	 * @param value
	 *          the effect of the bonus
	 * @return how the bonus is supposed to be shown to the user.
	 */
	public static String formate( String description, int value ) {
		return formate( description, value, "" );
	}

	/**
	 * format a bonus which has a magical and a physical part.
	 * @param description
	 *          the description of the carac bonused
	 * @param physical
	 *          the physical value of the bonus
	 * @param magical
	 *          the magical value of the bonus
	 * @return how the bonus to the carac is to be shown to user.
	 */
	public static String formate( String description, int physical, int magical ) {
		if( physical == 0 && magical == 0 ) { return ""; }
		String ret = description;
		if( physical >= 0 ) {
			ret += " +" + physical;
		} else {
			ret += " " + physical;
		}
		if( magical != 0 ) {
			ret += "/" + ( magical > 0 ? "+" : "" ) + magical;
		}
		return ret;
	}

	/**
	 * format a bonus according to the sign of the value
	 * @param description
	 *          the name of the bonus
	 * @param value
	 *          the effect of the bonus
	 * @param postdesc
	 *          the description that should appear after the value
	 * @return how the bonus is supposed to be shown to the user.
	 */
	public static String formate( String description, int value, String postdesc ) {
		if( value == 0 ) { return ""; }
		return formate( description, value, 0 ) + postdesc;
	}

	public static String formate( String description, int bmD, int bm, int bmm ) {
		if( bmD == 0 && bm == 0 && bmm == 0 ) { return ""; }
		String ret = description;
		if( bmD != 0 ) {
			ret += ( bmD > 0 ? " +" + bmD : " " + bmD ) + "D";
		}
		if( bm != 0 || bmm != 0 ) {
			ret += ( bm > 0 ? " +" + bm : " " + bm );
		}
		if( bmm != 0 ) {
			ret += "/" + ( bmm > 0 ? " +" + bmm : " " + bmm );
		}
		return ret;
	}

	public String toString() {
		return name + " : " + formate( "Att", attD, att, attM )
				+ formate( "Esq", esqD, esq, esqM ) + formate( "Deg", deg, degM )
				+ formate( "Vue", vue, vueM ) + formate( "DLA", dlaMin )
				+ formate( "Reg", reg, regM ) + formate( "PV", soin )
				+ formate( "PVMax", pvMax ) + formate( "Arm", arm + armM )
				+ formate( "MM", mm, "%, " ) + formate( "RM", rm, "%, " ) + ", Durée "
				+ duree + " tour(s)";
	}

	public String getName() {
		return name;
	}

	public int getDuree() {
		return duree;
	}

	/**
	 * a turn has ended
	 * @return is the bm to be suppessed?
	 */
	public boolean newTurn() {
		if( duree <= 0 ) return true;
		duree-- ;
		return false;
	}

	public int getAtt() {
		return att;
	}

	public int getAttM() {
		return attM;
	}

	public int getAttD() {
		return attD;
	}

	public int getEsq() {
		return esq;
	}

	public int getEsqM() {
		return esqM;
	}

	public int getEsqD() {
		return esqD;
	}

	public int getDeg() {
		return deg;
	}

	public int getDegM() {
		return degM;
	}

	public int getDlaMin() {
		return dlaMin;
	}

	public int getReg() {
		return reg;
	}

	public int getRegM() {
		return regM;
	}

	public int getVue() {
		return vue;
	}

	public int getVueM() {
		return vueM;
	}

	public int getSoin() {
		return soin;
	}

	public int getPvMax() {
		return pvMax;
	}

	public int getArm() {
		return arm;
	}

	public int getArmM() {
		return armM;
	}

	public int getMm() {
		return mm;
	}

	public int getRm() {
		return rm;
	}

	public boolean isGlued() {
		return glued;
	}

	public boolean isLevite() {
		return levite;
	}

	public boolean isFrenetique() {
		return frenetique;
	}

	public int retraitesDone() {
		return retraites;
	}

	public int getDirX() {
		return dirX;
	}

	public int getDirY() {
		return dirY;
	}

	public int getDirZ() {
		return dirZ;
	}

	/**
	 * add the effect of another BM to this. Remove the sense of the retraite,
	 * directions and duree fileds.
	 * @param other
	 *          the BM to add to this
	 * @param minturn
	 *          the minimum duree for other to be considered
	 */
	public void addOtherBM( BM other, int minturn ) {
		if( other.duree < minturn ) { return; }
		for( Field f : BM.class.getDeclaredFields() ) {
			if( f.getName().equals( "duree" ) ) {
				continue;
			}
			f.setAccessible( true );
			if( f.getType() == int.class || Integer.class == f.getType() ) {
				try {
					f.set( this, (int) ( (Integer) f.get( this ) + (Integer) f
							.get( other ) ) );
				} catch( IllegalArgumentException e ) {
					e.printStackTrace();
				} catch( IllegalAccessException e ) {
					e.printStackTrace();
				}
			}
			if( boolean.class == f.getType() || Boolean.class == f.getType() ) {
				try {
					f.set( this, (boolean) ( (Boolean) f.get( this ) || (Boolean) f
							.get( other ) ) );
				} catch( IllegalArgumentException e ) {
					e.printStackTrace();
				} catch( IllegalAccessException e ) {
					e.printStackTrace();
				}
			}
		}
	}

	public void addOtherBM( BM other ) {
		addOtherBM( other, 0 );
	}

	/**
	 * remove the integer values contained in another BM.
	 * @param other
	 *          the BM we want to remove to this
	 */
	public void remove( BM other ) {
		for( Field f : BM.class.getDeclaredFields() ) {
			if( f.getName().equals( "duree" ) ) {
				continue;
			}
			f.setAccessible( true );
			if( f.getType() == int.class || Integer.class == f.getType() ) {
				try {
					f.set( this, (int) ( (Integer) f.get( this ) - (Integer) f
							.get( other ) ) );
				} catch( IllegalArgumentException e ) {
					e.printStackTrace();
				} catch( IllegalAccessException e ) {
					e.printStackTrace();
				}
			}
		}
	}
}
