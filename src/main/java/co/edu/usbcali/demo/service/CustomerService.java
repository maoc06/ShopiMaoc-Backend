package co.edu.usbcali.demo.service;

import java.util.List;

import co.edu.usbcali.demo.domain.Customer;

public interface CustomerService extends GenericService<Customer, String>{

	List<Customer> findByQuery(String query);
	
}
