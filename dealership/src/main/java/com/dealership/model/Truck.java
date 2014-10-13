package com.dealership.model;

import java.io.Serializable;

public class Truck extends Automobile implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3167287145745342330L;
	private int towingCapacity;
	private double truckBedSize;
	private int haulingCapacity;

	public int getTowingCapacity() {
		return towingCapacity;
	}

	public void setTowingCapacity(int towingCapacity) {
		this.towingCapacity = towingCapacity;
	}

	public double getTruckBedSize() {
		return truckBedSize;
	}

	public void setTruckBedSize(double truckBedSize) {
		this.truckBedSize = truckBedSize;
	}

	public int getHaulingCapacity() {
		return haulingCapacity;
	}

	public void setHaulingCapacity(int haulingCapacity) {
		this.haulingCapacity = haulingCapacity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + haulingCapacity;
		result = prime * result + towingCapacity;
		long temp;
		temp = Double.doubleToLongBits(truckBedSize);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Truck other = (Truck) obj;
		if (haulingCapacity != other.haulingCapacity) {
			return false;
		}
		if (towingCapacity != other.towingCapacity) {
			return false;
		}
		if (Double.doubleToLongBits(truckBedSize) != Double.doubleToLongBits(other.truckBedSize)) {
			return false;
		}
		return super.equals(obj);
	}

}
