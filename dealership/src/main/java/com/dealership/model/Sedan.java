package com.dealership.model;

import java.io.Serializable;

public class Sedan extends Automobile implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4583379133239511881L;
	private double mpg;
	private int horsePower;

	public double getMpg() {
		return mpg;
	}

	public void setMpg(double mpg) {
		this.mpg = mpg;
	}

	public int getHorsePower() {
		return horsePower;
	}

	public void setHorsePower(int horsePower) {
		this.horsePower = horsePower;
	}

}
