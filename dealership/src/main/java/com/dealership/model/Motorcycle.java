package com.dealership.model;

import java.io.Serializable;

public class Motorcycle extends Automobile implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4818768945353442196L;
	private int engineSize;
	private boolean hasSideCar;

	public boolean isHasSideCar() {
		return hasSideCar;
	}

	public void setHasSideCar(boolean hasSideCar) {
		this.hasSideCar = hasSideCar;
	}

	public int getEngineSize() {
		return engineSize;
	}

	public void setEngineSize(int engineSize) {
		this.engineSize = engineSize;
	}

}
