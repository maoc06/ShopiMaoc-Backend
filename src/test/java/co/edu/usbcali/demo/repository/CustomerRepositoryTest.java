package co.edu.usbcali.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

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
class CustomerRepositoryTest {
	
	private final static Logger log = LoggerFactory.getLogger(CustomerRepositoryTest.class);
	
	private final static String email = "miguelorrego@outlook.com";
	
	@Autowired
	CustomerRepository customerRepository;

	@Test
	@Transactional
	@Order(1)
	void save() {
		log.info("save");
		
		Optional<Customer> customerOptional = customerRepository.findById(email);
		
		// Siga si es falo -> No existe
		assertFalse(customerOptional.isPresent(), "El customer ya existe");
		
		Customer customer=new Customer();
		customer.setAddress("Avenida Siempre Viva 123");
		customer.setEmail(email);
		customer.setEnable("Y");
		customer.setName("Miguel Orrego");
		customer.setPhone("123 456 7890");
		customer.setToken("NJAGF455SSJA655");
		
		customerRepository.save(customer);
	}
	
	@Test
	@Transactional
	@Order(2)
	void findById() {
		log.info("findById");
		
		Optional<Customer> customerOptional = customerRepository.findById(email);
		
		// Siga si es true -> existe
		assertTrue(customerOptional.isPresent(), "El customer no existe");
	}
	
	@Test
	@Transactional
	@Order(3)
	void update() {
		log.info("update");
		
		Optional<Customer> customerOptional = customerRepository.findById(email);
		
		// Siga si es true -> existe
		assertTrue(customerOptional.isPresent(), "El customer no existe");
		
		Customer customer = customerOptional.get();
		
		customer.setEnable("N");
		
		customerRepository.save(customer);
	}
	
	@Test
	@Transactional
	@Order(4)
	void delete() {
		log.info("delete");
		
		Optional<Customer> customerOptional = customerRepository.findById(email);
		
		// Siga si es true -> existe
		assertTrue(customerOptional.isPresent(), "El customer no existe");
		
		Customer customer = customerOptional.get();
		
		customerRepository.delete(customer);
	}
	
	@Test
	@Transactional
	@Order(5)
	void findAll() {
		customerRepository.findAll().forEach(customer->{
			log.info("Name:"+customer.getName());
			log.info("Email:"+customer.getEmail());
		});
	}

	@Test
	@Transactional
	@Order(6)
	void count() {
		log.info("Count:"+customerRepository.count());
	}
	
	@Test
	@Transactional
	@Order(7)
	void findByEnableAndEmail() {
		List<Customer> customers=customerRepository.findByEnableAndEmail("Y", "miguelorrego@outlook.com");
		assertFalse(customers.isEmpty());
		customers.forEach(customer->{
			log.info("Name:"+customer.getName());
			log.info("Email:"+customer.getEmail());
		});
	}
	
	@Test
	@Transactional
	@Order(8)
	void findCustomerLikeMar() {
		List<Customer> customers=customerRepository.findCustomerLikeMar();
		assertFalse(customers.isEmpty());
		customers.forEach(customer->{
			log.info("Name:"+customer.getName());
			log.info("Email:"+customer.getEmail());
		});
	}
}
