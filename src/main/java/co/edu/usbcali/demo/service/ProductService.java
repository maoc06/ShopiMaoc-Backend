package co.edu.usbcali.demo.service;

import java.util.List;

import co.edu.usbcali.demo.domain.Product;

public interface ProductService extends GenericService<Product, String>{

	List<Product> findByQuery(String query) throws Exception;

	List<Product> findByMaxPrice(Integer maxPrice) throws Exception;

	List<Product> findByEnableTrue();

}
