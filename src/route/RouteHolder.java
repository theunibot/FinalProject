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

import enums.RouteEffectType;
import enums.CabinetType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import utils.Utils;

/**
 *
 */
public class RouteHolder {

	private static RouteHolder rh = null;

	private List<Route> routes = Collections.synchronizedList(new ArrayList<Route>());

	public static RouteHolder getInstance() {
		if (rh == null)
			rh = new RouteHolder();
		return rh;
	}

	private RouteHolder() {
	}

	/**
	 * Clears all routes from memory; used when reloading routes from disk file
	 * during runtime
	 */
	public void clearRoutes() {
		routes.clear();
	}

	public void addRoute(Route r) {
		synchronized (routes) {
			routes.add(r);
		}
	}

	public List<Route> getAllRoutes() {
		return routes;
	}

	public List<Route> getRoutes(RouteProperties rp) {
		System.out.println("GETTING a list of Routes of from " + rp.getFrom() + " to " + rp.getTo() + " with effect " + rp.getEffect());
		List<Route> routesToGive = new ArrayList<>();
		for (Route r : routes) {
			RouteProperties props = r.getRouteProperties();
			if (props.getEffect() == rp.getEffect()
				&& props.getFrom() == rp.getFrom()
				&& props.getTo() == rp.getTo())
				routesToGive.add(r);
		}
		return routesToGive;
	}

	/**
	 * Gets a random Route matching the specified params. Error thrown if
	 * command not found.
	 *
	 * @param from
	 * @param to
	 * @param effect
	 *
	 * @return
	 */
	public Route getRoute(CabinetType from, CabinetType to, RouteEffectType effect) {
		List<Route> routesToGive = new ArrayList<>();
		for (Route r : routes) {
			RouteProperties props = r.getRouteProperties();
			if (props.getEffect() == effect
				&& props.getFrom() == from
				&& props.getTo() == to)
				routesToGive.add(r);
		}
		if (routesToGive.size() > 0)
			return routesToGive.get(Utils.getRandInt(routesToGive.size()));

		System.err.println("ERROR, Route from " + from + " to " + to + " with effect " + effect + " not found!");
		return null;
	}

	/**
	 * Returns a count of how many routes exist with the same short name
	 * (from/to/effect)
	 *
	 * @param routeShortName name of the route to look for
	 *
	 * @return count of the same named routes
	 */
	public int countSimilarRoutes(String routeShortName) {
		int count = 0;

		for (Route r : routes) {
			RouteProperties props = r.getRouteProperties();
			if (props.getRouteFriendlyShortName().equals(routeShortName))
				++count;
		}
		return count;
	}
}
