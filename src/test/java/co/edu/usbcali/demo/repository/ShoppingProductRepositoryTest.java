package co.edu.usbcali.demo.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.domain.Product;
import co.edu.usbcali.demo.domain.ShoppingCart;
import co.edu.usbcali.demo.domain.ShoppingProduct;

@SpringBootTest
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)
class ShoppingProductRepositoryTest {
	
	private final static Logger log = LoggerFactory.getLogger(ShoppingProductRepositoryTest.class);

	private static Integer shprId = null;
	
	private static String proId = "APPL45";
	
	private static Integer cartId = 3;
	
	@Autowired
	ShoppingProductRepository shoppingProductRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ShoppingCartRepository shoppingCartRepository;
	
	@Test
	@Order(1)
	void save() {
		ShoppingProduct shoppingProduct = new ShoppingProduct();
		shoppingProduct.setQuantity(5);
		shoppingProduct.setTotal(3850106L);
		
		Optional<Product> productOptional = productRepository.findById(proId);
		assertTrue(productOptional.isPresent(), "El product con ID: " + proId + " NO existe");
		
		Product product = productOptional.get();
		shoppingProduct.setProduct(product);
		
		Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findById(cartId);
		assertTrue(shoppingCartOptional.isPresent(), "El shoppingCart con ID: " + cartId + " NO existe");
		
		ShoppingCart shoppingCart = shoppingCartOptional.get();
		shoppingProduct.setShoppingCart(shoppingCart);
		
		shoppingProduct = shoppingProductRepository.save(shoppingProduct);
		
		shprId = shoppingProduct.getShprId();
		
		log.info("Se guardo el shoppingProduct con Id: " + shprId);
	}
	
	@Test
	@Transactional
	@Order(2)
	void findById() {
		Optional<ShoppingProduct> shoppingProductOptional = shoppingProductRepository.findById(shprId);
		assertTrue(shoppingProductOptional.isPresent(), "El shoppingProductOptional con shprId: "+ shprId + " NO existe");
		log.info("Se encontro el shoppingProduct con Id: " + shoppingProductOptional.get().getShprId());
	}

	@Test
	@Transactional
	@Order(3)
	void update() {
		Optional<ShoppingProduct> shoppingProductOptional = shoppingProductRepository.findById(shprId);
		assertTrue(shoppingProductOptional.isPresent(), "El shoppingProductOptional con shprId: "+ shprId + " NO existe");
		
		ShoppingProduct shoppingProduct = shoppingProductOptional.get();
		shoppingProduct.setQuantity(12);
		
		shoppingProductRepository.save(shoppingProduct);
		
		log.info("Se Actualizo el shoppingProduct con Id: " + shoppingProduct.getShprId());
		log.info("Actualizacion: " + shoppingProduct.getQuantity());
	}
	
	@Test
	@Transactional
	@Order(4)
	void delete() {
		Optional<ShoppingProduct> shoppingProductOptional = shoppingProductRepository.findById(shprId);
		assertTrue(shoppingProductOptional.isPresent(), "El shoppingProductOptional con shprId: "+ shprId + " NO existe");
		
		ShoppingProduct shoppingProduct = shoppingProductOptional.get();
		shoppingProductRepository.delete(shoppingProduct);
		
		log.info("Se Elimino el shoppingProduct con Id: " + shoppingProduct.getShprId());
	}

}
