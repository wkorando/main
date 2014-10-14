package com.dealership.model.loaders;

import com.dealership.model.Automobile;

/**
 * Interface for transforming an object into an {@link Automobile} class.
 * 
 * @author Billy
 * 
 */
public interface AutomobileLoader {
	Automobile loadAutomobile(Object object);
}
