package co.edu.usbcali.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.usbcali.demo.domain.Product;

public interface ProductRepository extends JpaRepository<Product, String>{
	
	public List<Product> findByPriceGreaterThan(Integer price);
	
	public List<Product> findByOrderByPriceAsc();
	
	@Query("SELECT pro FROM Product pro WHERE pro.enable='Y'")
	public List<Product> findByEnableTrue();
	
	@Query("SELECT pro FROM Product pro WHERE pro.name LIKE 'iPhone%'")
	public List<Product> findIphone();
	
	@Query("SELECT pro FROM Product pro WHERE pro.name LIKE 'iP%' AND pro.enable='Y'")
	public List<Product> findiPhoneAndEnable();
	
	@Query("SELECT pro FROM Product pro WHERE pro.name LIKE %?1%")
	public List<Product> findByQuery(String query);
	
	//@Query("SELECT pro FROM Product pro WHERE pro.name LIKE %?1%")
	public List<Product> findByPriceLessThan(Integer maxPrice);
}
