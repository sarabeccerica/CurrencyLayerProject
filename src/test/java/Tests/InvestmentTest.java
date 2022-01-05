package Tests;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import BaseClasses.Investment;

@TestInstance(Lifecycle.PER_CLASS)
public class InvestmentTest {
	private Investment inv;
	Calendar date = Calendar.getInstance();
	@BeforeAll
	public void setUp() {
		date.add(Calendar.DAY_OF_MONTH, -2);
		inv = new Investment("eur","sara",1000,date);
	}

	@Test
	public void testDaysNumber() {
		assertEquals(inv.DaysNumber(), 3);
	}
}
