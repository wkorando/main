package com.dealership;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dealership.dao.SearchParameter;
import com.dealership.dao.SearchParameter.Operator;
import com.dealership.dao.SearchParameter.ValueOperator;
import com.dealership.model.Automobile;
import com.dealership.model.Motorcycle;
import com.dealership.model.Truck;
import com.dealership.model.Van;
import com.dealership.report.SortOrder;

public class TestDealershipMain {
	private DealershipMain main = new DealershipMain();

	@BeforeClass
	public void setup() throws FileNotFoundException, IOException, ParseException {
		main.loadAutomobiles(new File(this.getClass().getResource("loadfile.json").getFile()));
	}

	@Test
	public void testFindAutosInPriceRange() {
		Set<Automobile> automobiles = main.findAllAutosInPriceRange(BigDecimal.valueOf(5000), BigDecimal.valueOf(12000));
		for (Automobile automobile : automobiles) {
			System.out.println("price: " + automobile.getPrice());
		}
		Assert.assertEquals(3, automobiles.size());
	}

	@Test
	public void testRunCustomSearch() {
		Set<Automobile> automobiles = main.runCustomSearch(new SearchParameter("make", "Ford", Operator.AND, ValueOperator.EQUALS));
		Assert.assertEquals(2, automobiles.size());
		boolean checkedF150 = false;
		boolean checkedWindstar = false;
		for (Automobile automobile : automobiles) {
			if (automobile.getModel().equals("F-150")) {
				Assert.assertEquals(BigDecimal.valueOf(25000), automobile.getPrice());
				Assert.assertEquals(2014, automobile.getYear());
				Assert.assertEquals(10000, ((Truck) automobile).getTowingCapacity());
				checkedF150 = true;
			} else if (automobile.getModel().equals("Windstar")) {
				Assert.assertEquals(BigDecimal.valueOf(4000), automobile.getPrice());
				Assert.assertEquals(1998, automobile.getYear());
				Assert.assertEquals(7, ((Van) automobile).getPassangerCapacity());
				checkedWindstar = true;
			}
		}
		Assert.assertTrue(checkedWindstar);
		Assert.assertTrue(checkedF150);
	}

	@Test(dependsOnMethods = { "testTotalValueOfStock" })
	public void testRunSale() {
		Set<Automobile> automobiles = main.runCustomSearch(new SearchParameter("make", "Harley-Davidson", Operator.AND, ValueOperator.EQUALS));
		main.runSale(BigDecimal.valueOf(.1), automobiles);
		automobiles = main.runCustomSearch(new SearchParameter("make", "Harley-Davidson", Operator.AND, ValueOperator.EQUALS));
		boolean fortyEight = false;
		boolean custom = false;
		for (Automobile automobile : automobiles) {
			if (automobile.getModel().equals("Forty-Eight")) {
				Assert.assertEquals(BigDecimal.valueOf(9000.0), automobile.getPrice());
				fortyEight = true;
			} else if (automobile.getModel().equals("1200 Custom")) {
				Assert.assertEquals(BigDecimal.valueOf(9450.0), automobile.getPrice());
				custom = true;
			}
		}
		Assert.assertTrue(fortyEight);
		Assert.assertTrue(custom);
	}

	@Test
	public void testTotalValueOfStock() {
		BigDecimal totalValueOfStock = main.totalValueOfStock();
		Assert.assertEquals(BigDecimal.valueOf(488000), totalValueOfStock);
	}

	@Test
	public void testRunReport() {
		List<Automobile> automobiles = main.runReport("Price", SortOrder.DESCENDING, new SearchParameter("make", "Ford", Operator.OR, ValueOperator.EQUALS),
				new SearchParameter("make", "Harley-Davidson", Operator.OR, ValueOperator.EQUALS));

		Assert.assertEquals(4, automobiles.size());
		Assert.assertTrue(((Motorcycle) automobiles.get(2)).isHasSideCar());
		Assert.assertEquals(7, ((Van) automobiles.get(3)).getPassangerCapacity());
		Assert.assertEquals(BigDecimal.valueOf(25000), automobiles.get(0).getPrice());
		Assert.assertEquals(BigDecimal.valueOf(10500), automobiles.get(1).getPrice());
		Assert.assertEquals("1200 Custom", automobiles.get(1).getModel());
	}
	
	@Test
	public void testSearchById(){
		List<Automobile> automobiles = main.runReport("Id", SortOrder.ASCENDING, new SearchParameter("id", "0", Operator.AND, ValueOperator.GREATER_THAN_OR_EQUAL_TO));
		
		Assert.assertNull(main.searchById(Long.valueOf(0), automobiles));
		Assert.assertNotNull(main.searchById(Long.valueOf(1), automobiles));
		Assert.assertNull(main.searchById(Long.valueOf(4), automobiles));
		Assert.assertNotNull(main.searchById(Long.valueOf(5), automobiles));
		Assert.assertNotNull(main.searchById(Long.valueOf(6), automobiles));
		Assert.assertNotNull(main.searchById(Long.valueOf(12), automobiles));
		Assert.assertNull(main.searchById(Long.valueOf(14), automobiles));
	}
}
