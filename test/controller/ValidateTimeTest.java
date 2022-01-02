package controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ValidateTimeTest {
	private PlaceRushOrderController placeOrderController;
	
	@BeforeEach
	void setUp() throws Exception{
		placeOrderController = new PlaceRushOrderController();
	}
	
	
	@ParameterizedTest
	@CsvSource({
		"12,true",
		"we,false",
		"32,false",
		"-1,false"
	})
	void testDate(String date,boolean expected) {
		boolean isValided = placeOrderController.validateDate(date);
		assertEquals(expected, isValided);
	}
	
	
	@ParameterizedTest
	@CsvSource({
		"10,true",
		"we,false",
		"13,false",
		"-1,false"
	})
	void testMonth(String month,boolean expected) {
		boolean isValided = placeOrderController.validateMonth(month);
		assertEquals(expected, isValided);
	}
	
	@ParameterizedTest
	@CsvSource({
		"10,true",
		"we,false",
		"24,false",
		"-1,false"
	})
	void testHour(String hour,boolean expected) {
		boolean isValided = placeOrderController.validateMonth(hour);
		assertEquals(expected, isValided);
	}
	
	@ParameterizedTest
	@CsvSource({
		"10,true",
		"we,false",
		"60,false",
		"-1,false"
	})
	void testMinute(String minute,boolean expected) {
		boolean isValided = placeOrderController.validateMonth(minute);
		assertEquals(expected, isValided);
	}

}
