package co.edu.usbcali.demo.service;

import java.util.List;

import co.edu.usbcali.demo.domain.ShoppingProduct;

public interface ShoppingProductService extends GenericService<ShoppingProduct, Integer>{

	List<ShoppingProduct> findByCarId(Integer carId) throws Exception;

	List<ShoppingProduct> findByProId(Integer carId, String proId) throws Exception;
	
	Long totalShoppingProductByShoppingCart(Integer carId) throws Exception;

}
