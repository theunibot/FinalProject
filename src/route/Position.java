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

import utils.Utils;

/**
 * Define a position in space as a cartesian coordinate
 *
 */
public class Position {
	private double x;
	private double y;
	private double z;
	private double pitch;
	private double yaw;
	private double roll;
	private boolean xDelta = false;
	private boolean yDelta = false;
	private boolean zDelta = false;
	private boolean pitchDelta = false;
	private boolean yawDelta = false;
	private boolean rollDelta = false;
	private boolean xDeltaForward = true;
	private boolean yDeltaForward = true;
	private boolean zDeltaForward = true;
	private boolean pitchDeltaForward = true;
	private boolean yawDeltaForward = true;
	private boolean rollDeltaForward = true;
	private String name = null;

	public Position(String name) {
		this.x = 0.;
		this.y = 0.;
		this.z = 0.;
		this.pitch = 0.;
		this.yaw = 0.;
		this.roll = 0.;
		this.name = name;
	}

	public Position(String name, double x, double y, double z, double pitch, double yaw, double roll) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.pitch = pitch;
		this.yaw = yaw;
		this.roll = roll;
		this.name = name;
	}

	public Position(String name, Position sourcePosition) {
		this.x = sourcePosition.x;
		this.y = sourcePosition.y;
		this.z = sourcePosition.z;
		this.pitch = sourcePosition.pitch;
		this.yaw = sourcePosition.yaw;
		this.roll = sourcePosition.roll;
		this.name = name;
	}

	public boolean equals(Object other) {
		if (other == null)
			return false;

		if (this.getClass() != other.getClass())
			return false;

		Position op = (Position) other;
		if ((this.x != op.x)
			|| (this.y != op.y)
			|| (this.z != op.z)
			|| (this.pitch != op.pitch)
			|| (this.yaw != op.yaw)
			|| (this.roll != op.roll)
			|| (this.name.equals(op.name)))
			return false;

		return true;
	}

	/**
	 * Offset this position by the relative amounts contained in the provided
	 * offsetPosition
	 *
	 * @param offsetPosition relative offsets to adjust by
	 *
	 * @return This object, now adjusted
	 */
	public Position offsetPositions(Position offsetPosition) {
		this.x += offsetPosition.getX();
		this.y += offsetPosition.getY();
		this.z += offsetPosition.getZ();
		this.pitch += offsetPosition.getPitch();
		this.yaw += offsetPosition.getYaw();
		this.roll += offsetPosition.getRoll();

		return this;
	}

	/**
	 * Determines a delta position by using this position which may or may not
	 * contain delta coordinates (those that have had their posDeltaXXX() method
	 * called), and when a position is delta, the provided reference position is
	 * then used to calculate the delta. A unique new Position object is
	 * returned with the resulting positions.
	 *
	 * @param referencePositionPrior Prior position object that has no delta
	 *                               positions (all absolute coordinates)
	 * @param referencePositionNext  Next position object that has no delta
	 *                               positions (all absolute coordinates)
	 *
	 * @return Position object with all absolute positions, using all absolute
	 *         positions from this object and using deltas relative to the
	 *         referencePostion.
	 */
	public Position getDeltaPosition(Position referencePositionPrior, Position referencePositionNext) {
		// construct a new position with the same name and values as this one
		Position newPos = new Position(name, this);

		// now offset deltas where they exist
		if (xDelta)
			if (xDeltaForward)
				newPos.x = referencePositionNext.x + this.x;
			else
				newPos.x = referencePositionPrior.x + this.x;
		if (yDelta)
			if (yDeltaForward)
				newPos.y = referencePositionNext.y + this.y;
			else
				newPos.y = referencePositionPrior.y + this.y;
		if (zDelta)
			if (zDeltaForward)
				newPos.z = referencePositionNext.z + this.z;
			else
				newPos.z = referencePositionPrior.z + this.z;
		if (pitchDelta)
			if (pitchDeltaForward)
				newPos.pitch = referencePositionNext.pitch + this.pitch;
			else
				newPos.pitch = referencePositionPrior.pitch + this.pitch;
		if (yawDelta)
			if (yawDeltaForward)
				newPos.yaw = referencePositionNext.yaw + this.yaw;
			else
				newPos.yaw = referencePositionPrior.yaw + this.yaw;
		if (rollDelta)
			if (rollDeltaForward)
				newPos.roll = referencePositionNext.roll + this.roll;
			else
				newPos.roll = referencePositionPrior.roll + this.roll;
		return newPos;
	}
	
	public double distance(Position secondPosition) {
		// compute the distance between this point and a secondary point
		double xDist = secondPosition.getX() - this.getX();
		double yDist = secondPosition.getY() - this.getY();
		double zDist = secondPosition.getZ() - this.getZ();
		double dist = Math.sqrt(xDist*xDist + yDist*yDist + zDist*zDist);
		return dist;
	}

	public String getRoboforth() {
		return getXStr() + " X ! " + getYStr() + " Y ! " + getZStr() + " Z ! " + getPitchStr() + " PITCH ! " + getYawStr() + " YAW ! " + getRollStr() + " ROLL ! POINT " + getName();
	}

	public double getX() {
		return x;
	}

	public String getXStr() {
		return Utils.formatDouble(x);
	}

	public void setX(String x) {
		this.x = Double.valueOf(x);
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public String getYStr() {
		return Utils.formatDouble(y);
	}

	public void setY(String y) {
		this.y = Double.valueOf(y);
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public String getZStr() {
		return Utils.formatDouble(z);
	}

	public void setZ(String z) {
		this.z = Double.valueOf(z);
	}

	public void setZ(double z) {
		this.z = z;
	}
	
	public void setXYZ(Position other) {
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
	}

	public double getPitch() {
		return pitch;
	}

	public String getPitchStr() {
		return Utils.formatDouble(pitch);
	}

	public void setPitch(String pitch) {
		this.pitch = Double.valueOf(pitch);
	}

	public void setPitch(double pitch) {
		this.pitch = pitch;
	}

	public double getYaw() {
		return yaw;
	}

	public String getYawStr() {
		return Utils.formatDouble(yaw);
	}

	public void setYaw(String yaw) {
		this.yaw = Double.valueOf(yaw);
	}

	public void setYaw(double yaw) {
		this.yaw = yaw;
	}

	public double getRoll() {
		return roll;
	}

	public String getRollStr() {
		return Utils.formatDouble(roll);
	}

	public void setRoll(String roll) {
		this.roll = Double.valueOf(roll);
	}

	public void setRoll(double roll) {
		this.roll = roll;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return name + " ("
			+ (xDelta ? (xDeltaForward ? ">" : "<") : "") + getXStr() + ", "
			+ (yDelta ? (yDeltaForward ? ">" : "<") : "") + getYStr() + ", "
			+ (zDelta ? (zDeltaForward ? ">" : "<") : "") + getZStr() + "; "
			+ (pitchDelta ? (pitchDeltaForward ? ">" : "<") : "") + getPitchStr() + ", "
			+ (yawDelta ? (yawDeltaForward ? ">" : "<") : "") + getYawStr() + ", "
			+ (rollDelta ? (rollDeltaForward ? ">" : "<") : "") + getRollStr() + ")";
	}

	public String hash() {
		StringBuilder serialize = new StringBuilder();
		serialize.append(getX());
		serialize.append(getY());
		serialize.append(getZ());
		serialize.append(getRoll());
		serialize.append(getYaw());
		serialize.append(getPitch());

		return Utils.hash(serialize.toString(), 32);
	}
	
	public void posDeltaX(boolean forward) {
		xDelta = true;
		xDeltaForward = forward;
	}

	public void posDeltaY(boolean forward) {
		yDelta = true;
		yDeltaForward = forward;
	}

	public void posDeltaZ(boolean forward) {
		zDelta = true;
		zDeltaForward = forward;
	}

	public void posDeltaPitch(boolean forward) {
		pitchDelta = true;
		pitchDeltaForward = forward;
	}

	public void posDeltaYaw(boolean forward) {
		yawDelta = true;
		yawDeltaForward = forward;
	}

	public void posDeltaRoll(boolean forward) {
		rollDelta = true;
		rollDeltaForward = forward;
	}

	public boolean hasDelta() {
		return xDelta || yDelta || zDelta || pitchDelta || yawDelta || rollDelta;
	}

	/**
	 * Compares two positions for value equality. Ignores name - looks only at
	 * actual position values
	 *
	 * @param other other position to compare
	 *
	 * @return true if equal, false if not
	 */
	public boolean equals(Position other) {
		if ((x != other.x) || (y != other.y) || (z != other.z))
			return false;

		if ((pitch != other.pitch) || (yaw != other.yaw) || (roll != other.roll))
			return false;

		if ((xDelta != other.xDelta) || (yDelta != other.yDelta) || (zDelta != other.zDelta))
			return false;

		if ((pitchDelta != other.pitchDelta) || (yawDelta != other.yawDelta) || (rollDelta != other.rollDelta))
			return false;

		if (xDelta && (xDeltaForward != other.xDeltaForward))
			return false;
		if (yDelta && (yDeltaForward != other.yDeltaForward))
			return false;
		if (zDelta && (zDeltaForward != other.zDeltaForward))
			return false;
		if (pitchDelta && (pitchDeltaForward != other.pitchDeltaForward))
			return false;
		if (yawDelta && (yawDeltaForward != other.yawDeltaForward))
			return false;
		if (rollDelta && (rollDeltaForward != other.rollDeltaForward))
			return false;

		return true;
	}

}
