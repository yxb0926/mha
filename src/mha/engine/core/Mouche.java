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

public enum Mouche {
		crobate(new BM(1, 0, 0, 0, 0, 0, 0, 0)),
		vertie(new BM(0, 1, 0, 0, 0, 0, 0, 0)),
		lunettes(new BM(0, 0, 0, 0, 0, 1, 0, 0)),
		miel(new BM(0, 0, 0, 0, 1, 0, 0, 0)),
		xidant(new BM(0, 0, 0, 0, 0, 0, 1, 0)),
		rivatant(new BM(0, 0, 0, -20, 0, 0, 0, 0)),
		heros(new BM(0, 0, 0, 0, 0, 0, 0, 0)),
		carnation(new BM(0, 0, 0, 0, 0, 0, 0, 0)),
		nabolisant(new BM(0, 0, 1, 0, 0, 0, 0, 0));

	protected final BM effect;

	Mouche(BM effect) {
		this.effect = effect;
	}
	
	public BM getEffet() {
		return effect;
	}
}
