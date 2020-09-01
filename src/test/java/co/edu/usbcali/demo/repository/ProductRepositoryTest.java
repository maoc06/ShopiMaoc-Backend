package co.edu.usbcali.demo.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
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
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.domain.Product;

@SpringBootTest
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)
class ProductRepositoryTest {
	
	private final static Logger log = LoggerFactory.getLogger(ProductRepositoryTest.class);
	
	private final static String proId = "HUA28";
	
	@Autowired
	ProductRepository productRepository;

	@Test
	@Transactional
	@Order(1)
	void save() {
		log.info("save");
		
		Optional<Product> productrOptional = productRepository.findById(proId);
		
		// Siga si es falo -> No existe
		assertFalse(productrOptional.isPresent(), "El producto ya existe");
		
		Product product=new Product();
		product.setProId(proId);
		product.setName("Huawei Y9");
		product.setPrice(950000);
		product.setDetail("Huawei Y9 Primer 2019");
		product.setImage("https://falabella.scene7.com/is/image/FalabellaCO/4229118_1?wid=800&hei=800&qlt=70");
		product.setEnable("Y");		
		
		productRepository.save(product);
	}
	
	@Test
	@Transactional
	@Order(2)
	void findById() {
		log.info("findById");
		
		Optional<Product> productrOptional = productRepository.findById(proId);
		
		// Siga si es true -> existe
		assertTrue(productrOptional.isPresent(), "El producto no existe");
		
		log.info("Product:"+productrOptional.get().getName());
	}
	
	@Test
	@Transactional
	@Order(3)
	void update() {
		log.info("update");
		
		Optional<Product> productOptional = productRepository.findById(proId);
		
		// Siga si es true -> existe
		assertTrue(productOptional.isPresent(), "El producto no existe");
		
		Product product = productOptional.get();
		
		product.setEnable("N");
		
		productRepository.save(product);
	}
	
	@Test
	@Transactional
	@Order(4)
	void delete() {
		log.info("delete");
		
		Optional<Product> productOptional = productRepository.findById(proId);
		
		// Siga si es true -> existe
		assertTrue(productOptional.isPresent(), "El product no existe");
		
		Product product = productOptional.get();
		
		productRepository.delete(product);
	}
	
	@Test
	@Transactional
	@Order(5)
	void findAll() {
		productRepository.findAll().forEach(product->{
			log.info("Name:"+product.getName());
			log.info("Price:"+product.getPrice());
		});
	}

	@Test
	@Transactional
	@Order(6)
	void count() {
		log.info("Count:"+productRepository.count());
	}
	
	@Test
	@Transactional
	@Order(7)
	void findByPriceGreaterThan() {
		log.info("findByPriceGreaterThan");
		
		List<Product> products = productRepository.findByPriceGreaterThan(1000000);
		
		assertFalse(products.isEmpty());
		
		products.forEach(product -> {
			log.info("Name:"+product.getName());
		});
	}
	
	@Test
	@Transactional
	@Order(8)
	void findByOrderByPriceAsc() {
		log.info("findByOrderByPriceAsc");
		
		List<Product> products = productRepository.findByOrderByPriceAsc();
		
		assertFalse(products.isEmpty());
		
		products.forEach(product -> {
			log.info("Name:"+product.getName());
			log.info("Price:"+product.getPrice());
		});
	}
	
	@Test
	@Transactional
	@Order(9)
	void findByEnableTrue() {
		log.info("findByEnableTrue");
		
		List<Product> products = productRepository.findByEnableTrue();
		
		assertFalse(products.isEmpty());
		
		products.forEach(product -> {
			log.info("Name:"+product.getName());
		});
	}

	
	@Test
	@Transactional
	@Order(10)
	void findIphone() {
		log.info("findIphone");
		
		List<Product> products = productRepository.findIphone();
		
		assertFalse(products.isEmpty());
		
		products.forEach(product -> {
			log.info("Name:"+product.getName());
		});
	}
	
	@Test
	@Transactional
	@Order(11)
	void findiPhoneAndEnable() {
		log.info("findiPhoneAndEnable");
		
		List<Product> products = productRepository.findiPhoneAndEnable();
		
		assertFalse(products.isEmpty());
		
		products.forEach(product -> {
			log.info("Name:"+product.getName());
			log.info("Enable:"+product.getEnable());
		});
	}
}
