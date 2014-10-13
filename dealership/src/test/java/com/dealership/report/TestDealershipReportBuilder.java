package com.dealership.report;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.dealership.model.Automobile;
import com.dealership.model.Truck;

public class TestDealershipReportBuilder {
	private Logger LOG = Logger.getLogger(TestDealershipReportBuilder.class);
	@Test
	public void testSortAutomobiles(){
		Truck truck1 = new Truck();
		truck1.setPrice(new BigDecimal(100));
		Truck truck2 = new Truck();
		truck2.setPrice(new BigDecimal(10));
		Truck truck3 = new Truck();
		truck3.setPrice(new BigDecimal(700));
		Truck truck4 = new Truck();
		truck4.setPrice(new BigDecimal(1));
		Truck truck5 = new Truck();
		truck5.setPrice(new BigDecimal(600));
		Set<Automobile> automobiles = new HashSet<Automobile>();
		truck1.equals(truck5);
		automobiles.add(truck1);
		automobiles.add(truck2);
		automobiles.add(truck3);
		automobiles.add(truck4);
		automobiles.add(truck5);
		
		DealershipReportBuilder  reportBuilder = new DealershipReportBuilder();
		for(Automobile automobile :reportBuilder.sortAutomobiles("", SortOrder.ASCENDING, automobiles)){
			System.out.println(automobile.getPrice());
		}
		
	}
}
