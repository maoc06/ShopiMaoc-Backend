package co.edu.usbcali.demo.service;

import java.util.List;
import java.util.Optional;

import co.edu.usbcali.demo.domain.ShoppingCart;

public interface ShoppingCartService extends GenericService<ShoppingCart, Integer>{

	Optional<ShoppingCart> findByCustomerEnableTrue(String email) throws Exception;

	List<ShoppingCart> findByCustomerShops(String email) throws Exception;

}
