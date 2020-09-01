package co.edu.usbcali.demo.jpa;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.domain.Customer;

@SpringBootTest
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)
class CustomerTest {
	
	private final static String email="miguelorrego@outlook.com";
	private final static Logger log = LoggerFactory.getLogger(CustomerTest.class);
	
	@Autowired
	EntityManager entityManager;

	@Test
	@Transactional
	@Order(1)
	void save() {
		assertNotNull(entityManager, "El entityManager es nulo");
		Customer customer = entityManager.find(Customer.class, email);
		
		assertNull(customer, "El cliente con email "+email+" ya existe");
		
		customer=new Customer();
		customer.setAddress("Avenida Siempre Viva 123");
		customer.setEmail(email);
		customer.setEnable("Y");
		customer.setName("Miguel Orrego");
		customer.setPhone("123 456 7890");
		customer.setToken("NJAGF455SSJA655");
		
		entityManager.persist(customer);
	}
	
	@Test
	@Transactional
	@Order(2)
	void findById() {
		assertNotNull(entityManager, "El entityManager es nulo");
		
		Customer customer = entityManager.find(Customer.class, email);
		
		assertNotNull(customer, "El cliente con email "+email+" no existe");
		
		log.info(customer.getName());
	}
	
	@Test
	@Transactional
	@Order(3)
	void update() {
		assertNotNull(entityManager, "El entityManager es nulo");
		
		Customer customer = entityManager.find(Customer.class, email);
		
		assertNotNull(customer, "El cliente con email "+email+" no existe");
		
		customer.setEnable("N");
		
		entityManager.merge(customer);
	}

	@Test
	@Transactional
	@Order(4)
	void delete() {
		assertNotNull(entityManager, "El entityManager es nulo");
		
		Customer customer = entityManager.find(Customer.class, email);
		
		assertNotNull(customer, "El cliente con email "+email+" no existe");
		
		entityManager.remove(customer);
	}
}
