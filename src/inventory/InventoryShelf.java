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
package inventory;

/**
 * Class defines a shelf in inventory.
 */
public class InventoryShelf {
	public int count;               // count of discs in the shelf
	public int originalShelf;       // the original shelf ID (for discs in desktops)

	/**
	 * Constructor builds an inventory shelf with the specified number of discs
	 *
	 * @param count Count of discs
	 */
	public InventoryShelf(int count) {
		this.count = count;
		this.originalShelf = -1;
	}

	/**
	 * Constructor builds an inventory shelf with the specified number of discs,
	 * of a specific shelf id
	 *
	 * @param count         Count of discs
	 * @param originalShelf ID of the original shelf
	 */
	public InventoryShelf(int count, int originalShelf) {
		this.count = count;
		this.originalShelf = originalShelf;
	}
}
