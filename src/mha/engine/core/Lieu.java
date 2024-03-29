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

public class Lieu {

	protected String name;
	protected Case position;
	protected final int id = newId();

	private static int nblieu = 0;

	private final static int newId() {
		nblieu++ ;
		return nblieu;
	}

	public Lieu( String name, int posX, int posY, int posN ) {
		this.name = name;
		position = new Case( posX, posY, posN );
	}

	public Lieu( String name, Case position ) {
		this.name = name;
	}

	public Case getPosition() {
		return position;
	}

	public String getPos() {
		return position.toString();
	}

	public String getName() {
		return name;
	}

	public String getInfos() {
		return toString();
	}

	public String toString() {
		return id + " " + name + " " + getPos();
	}

	final public int getId() {
		return id;
	}
}
