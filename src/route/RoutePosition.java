/*
 This file is part of theunibot.

 theunibot is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 theunibot is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with theunibot.  If not, see <http://www.gnu.org/licenses/>.

 Copyright (c) 2014 Unidesk Corporation
 */
package route;

/**
 *
 */
public class RoutePosition {
	private Position position;
	private String routeName;
	private int line;

	public RoutePosition(Position pos, String routeName, int line) {
		this.position = pos;
		this.routeName = routeName;
		this.line = line;
	}

	public RoutePosition(RoutePosition pos, String routeName) {
		this.position = pos.getPosition();
		this.routeName = routeName;
		this.line = pos.getLine();
	}

	public RoutePosition(RoutePosition pos, String routeName, int line) {
		this.position = pos.getPosition();
		this.routeName = routeName;
		this.line = line;
	}

	public Position getPosition() {
		return this.position;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}
/*
	public String toString() {
		return "DECIMAL " + position.getRollStr() + " "
			+ position.getYawStr() + " "
			+ position.getPitchStr() + " "
			+ position.getZStr() + " "
			+ position.getYStr() + " "
			+ position.getXStr() + " "
			+ routeName + " " + line + " LINE DLD\r";
	}
*/
}
