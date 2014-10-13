package com.dealership.model;

import java.io.Serializable;

public class Van extends Automobile implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5510417759693487697L;
	private int passangerCapacity;

	public int getPassangerCapacity() {
		return passangerCapacity;
	}

	public void setPassangerCapacity(int passangerCapacity) {
		this.passangerCapacity = passangerCapacity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + passangerCapacity;
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
		Van other = (Van) obj;
		if (passangerCapacity != other.passangerCapacity) {
			return false;
		}
		return true;
	}
}
