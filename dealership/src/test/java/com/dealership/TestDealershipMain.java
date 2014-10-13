package com.dealership;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Set;

import junit.framework.Assert;

import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dealership.dao.SearchParameter;
import com.dealership.dao.SearchParameter.Operator;
import com.dealership.dao.SearchParameter.ValueOperator;
import com.dealership.model.Automobile;
import com.dealership.model.Truck;
import com.dealership.model.Van;

public class TestDealershipMain {
	private DealershipMain main = new DealershipMain();

	@BeforeClass
	public void setup() throws FileNotFoundException, IOException, ParseException {
		main.loadAutomobiles(new File(this.getClass().getResource("loadfile.json").getFile()));
	}

	@Test
	public void testFindAutosInPriceRange() {
		Set<Automobile> automobiles = main.findAllAutosInPriceRange(new BigDecimal(5000), new BigDecimal(12000));
		for (Automobile automobile : automobiles) {
			System.out.println("price: " + automobile.getPrice());
		}
		Assert.assertEquals(3, automobiles.size());
	}

	@Test 
	public void testRunCustomSearch(){
		Set<Automobile> automobiles = main.runCustomSearch(new SearchParameter("make", "Ford", Operator.AND, ValueOperator.EQUALS));
		Assert.assertEquals(2, automobiles.size());
		boolean checkedF150 = false;
		boolean checkedWindstar = false;
		for(Automobile automobile : automobiles){
			if(automobile.getModel().equals("F-150")){
				Assert.assertEquals(new BigDecimal("25000"), automobile.getPrice());
				Assert.assertEquals(2014, automobile.getYear());
				Assert.assertEquals(10000, ((Truck)automobile).getTowingCapacity());
				checkedF150 = true;
			}
			else if(automobile.getModel().equals("Windstar")){
				Assert.assertEquals(new BigDecimal("4000"), automobile.getPrice());
				Assert.assertEquals(1998, automobile.getYear());
				Assert.assertEquals(7, ((Van)automobile).getPassangerCapacity());
				checkedWindstar = true;
			}
		}
		Assert.assertTrue(checkedWindstar);
		Assert.assertTrue(checkedF150);
	}
}
