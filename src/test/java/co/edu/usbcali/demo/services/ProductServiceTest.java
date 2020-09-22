package co.edu.usbcali.demo.services;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import co.edu.usbcali.demo.domain.Product;
import co.edu.usbcali.demo.service.ProductService;

@SpringBootTest
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)
public class ProductServiceTest {
	
	private final static Logger log = LoggerFactory.getLogger(ProductServiceTest.class);
	
	private final static String proId = "SA20";
	
	@Autowired
	ProductService productService;
	
	@Test
	@Order(1)
	void save() throws Exception {
		log.info("save");
		
		Product product=new Product();
		product.setProId(proId);
		product.setName("Samsung Galaxy S20");
		product.setDetail("Resistente al agua en hasta 1.5 metros de agua dulce durante hasta 30 minutos.");
		product.setEnable("Y");
		product.setPrice(3199900);
		product.setImage("https://images.samsung.com/is/image/samsung/in-galaxy-s20-plus-sm-g985-sm-g985fzpdinu-frontbpurple-261486035");
		
		productService.save(product);
	}
	
	@Test
	@Order(2)
	void findById() throws Exception{
		log.info("findById");
	
		Optional<Product> productOptional=productService.findById(proId);
		
		//Siga si es true. Quiere decir que existe
		assertTrue(productOptional.isPresent(),"El Product no existe");
	}
	
	@Test
	@Order(3)
	void update()throws Exception {
		log.info("update");
	
		Optional<Product> productOptional=productService.findById(proId);
		
		//Siga si es true. Quiere decir que existe
		assertTrue(productOptional.isPresent(),"El Product no existe");
		
		Product product=productOptional.get();
		
		product.setEnable("N");
		
		productService.update(product);		
	}

	@Test
	@Order(4)
	void delete()throws Exception {		
		log.info("delete");
	
		Optional<Product> productOptional=productService.findById(proId);
		
		//Siga si es true. Quiere decir que existe
		assertTrue(productOptional.isPresent(),"El product no existe");
		
		Product product=productOptional.get();
		
		productService.delete(product);		
	}
}
