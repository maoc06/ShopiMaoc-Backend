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

import co.edu.usbcali.demo.domain.Product;

@SpringBootTest
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)
class ProductTest {
	
	private final static String proId = "HUA28";
	private final static Logger log = LoggerFactory.getLogger(ProductTest.class);
	
	@Autowired
	EntityManager entityManager;
	
	@Test
	@Transactional
	@Order(1)
	void save() {
		assertNotNull(entityManager, "El entityManager es nulo");
		Product product = entityManager.find(Product.class, proId);
		
		assertNull(product, "El producto con id "+proId+" ya existe");
		
		product = new Product();
		product.setProId(proId);
		product.setName("Huawei Y9");
		product.setPrice(950000);
		product.setDetail("Huawei Y9 Primer 2019");
		product.setImage("https://falabella.scene7.com/is/image/FalabellaCO/4229118_1?wid=800&hei=800&qlt=70");
		product.setEnable("Y");
		
		entityManager.persist(product);
		
		log.info("Se guardo el producto con id "+product.getProId());
	}

	@Test
	@Transactional
	@Order(2)
	void findById() {
		assertNotNull(entityManager, "El entityManager es nulo");
		
		Product product = entityManager.find(Product.class, proId);
		
		assertNotNull(product, "El product "+proId+" no existe");
		
		log.info("Se encontro el producto "+ product.getName());
	}
	
	@Test
	@Transactional
	@Order(3)
	void update() {
		assertNotNull(entityManager, "El entityManager es nulo");

		Product product = entityManager.find(Product.class, proId);
		
		assertNotNull(product, "El product "+proId+" no existe");
		
		product.setEnable("N");
		
		entityManager.merge(product);
		
		log.info("Se actualizo el producto con id "+product.getProId());
		
	}
	
	@Test
	@Transactional
	@Order(4)
	void delete() {
		assertNotNull(entityManager, "El entityManager es nulo");
		
		Product product = entityManager.find(Product.class, proId);
		
		assertNotNull(product, "El product "+proId+" no existe");
		
		entityManager.remove(product);
		
		log.info("Se removio el producto con id "+product.getProId());
		
	}

}
