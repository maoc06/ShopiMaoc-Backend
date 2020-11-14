package co.edu.usbcali.demo.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import co.edu.usbcali.demo.domain.ShoppingCart;
import co.edu.usbcali.demo.domain.ShoppingProduct;
import co.edu.usbcali.demo.service.CartService;

@SpringBootTest
@Rollback(false)
class CartServiceTest {
	
	@Autowired
	CartService cartService;

	@Test
	void debeCrearUnShoppingCart() throws Exception {
		// Arrange
		String email = "miguelorrego@outlook.com";
		ShoppingCart shoppingCart = null;
		
		// Act
		shoppingCart= cartService.createCart(email);
		
		// Assert
		assertNotNull(shoppingCart);
	}
	
	@Test
	void noDebeCrearUnShoppingCart() throws Exception {
		// Arrange
		String email = "abeamondqq@harvard.edu";
		
		// Act
		assertThrows(Exception.class, ()->cartService.createCart(email));
	}
	
	@Test
	void debeAgregarProductShoppingCart() throws Exception {
		// Arrange
		Integer carId = 3;
		String proId = "APPL-WCH-9";
		Integer quantity = 9;
		ShoppingProduct shoppingProduct = null;
		
		// Act
		shoppingProduct = cartService.addProduct(carId, proId, quantity);
		
		// Assert
		assertNotNull(shoppingProduct, "El shoppingProduct es nulo");
	}
	
	@Test
	void debeBorrarProductShoppingCart() throws Exception {
		// Arrange
		Integer carId = 3;
		String proId = "XI-REN8";
		
		// Act
		cartService.removeProduct(carId, proId);		
	}
	
	@Test
	void noDebeBorrarProductShoppingCartPorCarNoExiste() throws Exception {
		// Arrange
		Integer carId = 99;
		String proId = "XI-REN8";
		
		// Act
		assertThrows(Exception.class, ()->cartService.removeProduct(carId, proId));	
	}
	
	@Test
	void noDebeBorrarProductShoppingCartPorCarDisable() throws Exception {
		// Arrange
		Integer carId = 9;
		String proId = "XI-REN8";
		
		// Act
		assertThrows(Exception.class, ()->cartService.removeProduct(carId, proId));	
	}
	
	@Test
	void noDebeBorrarProductShoppingCartPorProductDisable() throws Exception {
		// Arrange
		Integer carId = 9;
		String proId = "XI-RN9";
		
		// Act
		assertThrows(Exception.class, ()->cartService.removeProduct(carId, proId));	
	}
	
	@Test
	void noDebeBorrarProductShoppingCartPorProductNoExiste() throws Exception {
		// Arrange
		Integer carId = 9;
		String proId = "NN";
		
		// Act
		assertThrows(Exception.class, ()->cartService.removeProduct(carId, proId));	
	}
	
	@Test
	void debeLimpiarShoppingCart() throws Exception {
		// Arrange
		Integer carId = 3;
		
		// Act
		cartService.clearCart(carId);		
	}
	
	@Test
	void noDebeLimpiarShoppingCartPorCarIdNoExiste() throws Exception {
		// Arrange
		Integer carId = 20;
		
		// Act
		assertThrows(Exception.class, ()->cartService.clearCart(carId));	
	}
	
	@Test
	void noDebeLimpiarShoppingCartPorCarDisable() throws Exception {
		// Arrange
		Integer carId = 9;
		
		// Act
		assertThrows(Exception.class, ()->cartService.clearCart(carId));	
	}
	
	@Test
	void noDebeLimpiarShoppingCartPorCartVacio() throws Exception {
		// Arrange
		Integer carId = 9;
		
		// Act
		assertThrows(Exception.class, ()->cartService.clearCart(carId));	
	}
	
	@Test
	void debeTraerProductsShoppingCart() throws Exception {
		// Arrange
		Integer carId = 3;
		List<ShoppingProduct> shoppingProducts = null;
		
		// Act
		shoppingProducts = cartService.findShoppingProductByShoppingCart(carId);
		
		// Assert
		assertNotNull(shoppingProducts, "El shoppingProducts es nulo");
	}

}
