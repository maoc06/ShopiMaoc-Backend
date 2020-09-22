package co.edu.usbcali.demo.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import co.edu.usbcali.demo.domain.Customer;
import co.edu.usbcali.demo.domain.PaymentMethod;
import co.edu.usbcali.demo.domain.ShoppingCart;
import co.edu.usbcali.demo.service.CustomerService;
import co.edu.usbcali.demo.service.PaymentMethodService;
import co.edu.usbcali.demo.service.ShoppingCartService;

@SpringBootTest
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)
class ShoppingCartServiceTest {
	
	private final static Logger log = LoggerFactory.getLogger(ShoppingCartServiceTest.class);
	
	private static Integer carId = null;
	
	private static String email = "brosenbloom62@delicious.com";
	
	private static Integer payId = 1;
	
	@Autowired
	ShoppingCartService shoppingCartService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	PaymentMethodService paymentMethodService;

	@Test
	@Order(1)
	void save() throws Exception {
		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.setTotal(158632L);
		shoppingCart.setItems(15);
		shoppingCart.setEnable("Y");
		
		Optional<Customer> customerOptional = customerService.findById(email);
		assertTrue(customerOptional.isPresent(), "El customer no existe");
		
		Customer customer = customerOptional.get();
		shoppingCart.setCustomer(customer);
		
		Optional<PaymentMethod> paymentMethodOptional = paymentMethodService.findById(payId);
		assertTrue(paymentMethodOptional.isPresent(), "El PaymentMethod con id "+ payId + " NO existe");
		
		PaymentMethod paymentMethod = paymentMethodOptional.get();
		shoppingCart.setPaymentMethod(paymentMethod);
		
		shoppingCart = shoppingCartService.save(shoppingCart);
		carId = shoppingCart.getCarId();
		assertNotNull(carId, "El carId es nulo");
		
		log.info("carId:", carId);
	}
	
	@Test
	@Order(2)
	void findById() throws Exception {
		log.info("findById");
		
		Optional<ShoppingCart> shoppingCartdOptional = shoppingCartService.findById(carId);
		
		assertTrue(shoppingCartdOptional.isPresent(),  "El shoppingCart no existe");		
	}
	
	@Test
	@Order(3)
	void update()throws Exception {
		log.info("update");
	
		Optional<ShoppingCart> shoppingCartOptional = shoppingCartService.findById(carId);
		assertTrue(shoppingCartOptional.isPresent(),"El shoppingCart no existe");
		
		ShoppingCart shoppingCart = shoppingCartOptional.get();
		
		shoppingCart.setEnable("N");
		
		shoppingCartService.update(shoppingCart);		
	}
	
	@Test
	@Order(4)
	void delete()throws Exception {		
		log.info("delete");
	
		Optional<ShoppingCart> shoppingCartOptional = shoppingCartService.findById(carId);
		
		//Siga si es true. Quiere decir que existe
		assertTrue(shoppingCartOptional.isPresent(),"El shoppingCart no existe");
		
		ShoppingCart shoppingCart = shoppingCartOptional.get();
		
		shoppingCartService.delete(shoppingCart);		
	}

}
