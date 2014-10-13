package com.dealership.dao.impl;

import java.math.BigDecimal;

import junit.framework.Assert;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dealership.dao.DealershipDAO;
import com.dealership.model.Automobile;
import com.dealership.model.Van;

public class TestDealershipDaoHazelImpl {
	private DealershipDAO dao;

	@BeforeClass
	public void setupTest() {
		dao = new DealershipDAOHazelImpl();
	}

	@Test
	public void testAdd() {
		Van van = new Van();
		van.setId(1L);
		van.setMake("Ford");
		van.setModel("Caravan");
		van.setPassangerCapacity(7);
		van.setPrice(new BigDecimal(14500.00));
		van.setYear(2007);
		dao.addNew(van);
	}

	@Test(dependsOnMethods = { "testAdd" })
	public void testLookup() {
		Automobile automobile = dao.lookupById(1L);
		Assert.assertEquals(2007, automobile.getYear());
		Assert.assertEquals("Ford", automobile.getMake());
		Assert.assertEquals(7, ((Van) automobile).getPassangerCapacity());
	}

	@Test(dependsOnMethods = { "testAdd" })
	public void testUpdate() {
		Automobile automobile = dao.lookupById(1L);
		automobile.setModel("Grand Caravan");
		dao.update(automobile);
		automobile = dao.lookupById(1L);
		Assert.assertEquals("Grand Caravan", "Grand Caravan");
	}

	@Test(dependsOnMethods = { "testUpdate", "testLookup" })
	public void testDelete() {
		Automobile automobile = dao.lookupById(1L);
		dao.delete(automobile);
		Assert.assertNull(dao.lookupById(1L));
	}
}
