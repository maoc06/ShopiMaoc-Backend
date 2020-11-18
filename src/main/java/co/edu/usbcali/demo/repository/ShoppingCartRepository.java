package co.edu.usbcali.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.usbcali.demo.domain.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer>{
	
	@Query("SELECT sc FROM ShoppingCart sc WHERE sc.customer.email=?1 AND sc.enable='Y'")
	public Optional<ShoppingCart> findByCustomerEnableTrue(String email);
	
	@Query("SELECT sc FROM ShoppingCart sc WHERE sc.customer.email=?1 AND sc.enable='N'")
	public List<ShoppingCart> findByCustomerShops(String email);

}
