package co.edu.usbcali.demo.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import co.edu.usbcali.demo.service.ShoppingProductService;

@SpringBootTest
@Rollback(false)
class ShoppingProductTest {
	
	@Autowired
	ShoppingProductService shoppingProductService;

	@Test
	void test() {
		// Arrange
		Long total = 0L;
		Integer carId = 3;
		
		// Act
		//total = shoppingProductService.totalShoppingProductByShoppingCart(carId);
		
		// Assert
		//assertTrue(total > 0);		
	}

}
