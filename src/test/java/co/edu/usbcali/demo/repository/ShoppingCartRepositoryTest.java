package co.edu.usbcali.demo.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import co.edu.usbcali.demo.domain.Customer;
import co.edu.usbcali.demo.domain.PaymentMethod;
import co.edu.usbcali.demo.domain.ShoppingCart;

@SpringBootTest
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)
class ShoppingCartRepositoryTest {
	
	private final static Logger log = LoggerFactory.getLogger(ShoppingCartRepositoryTest.class);
	
	private static Integer cartId = null;
	
	private static String email = "abaglowbn@furl.net";
	
	private static Integer payId = 1;
	
	@Autowired
	ShoppingCartRepository shoppingCartRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	PaymentMethodRepository paymentMethodRepository;

	@Test
	@Transactional
	@Order(1)
	void save() {
		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.setItems(2);
		shoppingCart.setTotal(1550877L);
		shoppingCart.setEnable("Y");
		
		Optional<Customer> customerOptional = customerRepository.findById(email); 
		assertTrue(customerOptional.isPresent(), "El customer con email "+ email + " NO existe");
		
		Customer customer = customerOptional.get();
		shoppingCart.setCustomer(customer);
		
		Optional<PaymentMethod> paymentMethodOptional = paymentMethodRepository.findById(payId);
		assertTrue(paymentMethodOptional.isPresent(), "El PaymentMethod con id "+ payId + " NO existe");
		
		PaymentMethod paymentMethod = paymentMethodOptional.get();
		shoppingCart.setPaymentMethod(paymentMethod);
		
		shoppingCart = shoppingCartRepository.save(shoppingCart);
		
		cartId = shoppingCart.getCarId();
		
		assertNotNull(cartId, "El cartId es nulo");
		
		log.info("Se guardo el cartId: ", cartId);
	}
	
	@Test
	@Transactional
	@Order(2)
	void findById() {
		Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findById(cartId);
		assertTrue(shoppingCartOptional.isPresent(), "El shoppingCartOptional con cartId: "+ cartId + " NO existe");
	}

	@Test
	@Transactional
	@Order(3)
	void update() {
		Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findById(cartId);
		assertTrue(shoppingCartOptional.isPresent(), "El shoppingCartOptional con cartId: "+ cartId + " NO existe");
		
		ShoppingCart shoppingCart = shoppingCartOptional.get();
		shoppingCart.setEnable("N");
		
		shoppingCartRepository.save(shoppingCart);
	}
	
	@Test
	@Transactional
	@Order(4)
	void delete() {
		Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findById(cartId);
		assertTrue(shoppingCartOptional.isPresent(), "El shoppingCartOptional con cartId: "+ cartId + " NO existe");
		
		ShoppingCart shoppingCart = shoppingCartOptional.get();
		shoppingCartRepository.delete(shoppingCart);
	}
}
