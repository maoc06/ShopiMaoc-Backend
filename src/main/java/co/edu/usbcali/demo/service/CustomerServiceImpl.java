package co.edu.usbcali.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.domain.Customer;
import co.edu.usbcali.demo.repository.CustomerRepository;
import co.edu.usbcali.demo.security.UserApplication;

@Service
@Scope("singleton")
public class CustomerServiceImpl implements CustomerService{
	
	private final static Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	Validator validator;
	
	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return customerRepository.count();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<Customer> findById(String id) throws Exception {
		return customerRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Customer> findAll() {
		return customerRepository.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Customer> findByQuery(String query) {
		return customerRepository.findByQuery(query);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Customer save(Customer entity) throws Exception {
		
		validate(entity);
		
		if(customerRepository.existsById(entity.getEmail())) {
			throw new Exception("El Customer con id:"+entity.getEmail()+" ya existe");
		}
		
		BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
		
		UserApplication userApplication=new UserApplication(
				entity.getEmail(), 
				bCryptPasswordEncoder.encode(entity.getToken())
		);
		
		entity.setToken(userApplication.getPassword());
		
		return customerRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Customer update(Customer entity) throws Exception {
		 
		validate(entity);
		
		if(!customerRepository.existsById(entity.getEmail())) {
			throw new Exception("El Customer con id:"+entity.getEmail()+" no existe");
		}
		
		return customerRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void delete(Customer entity) throws Exception {
		if(entity==null) {
			throw new Exception("El Customer es nulo");
		}
		
		if(entity.getEmail()==null || entity.getEmail().isBlank()) {
			throw new Exception("El Email es obligatorio");
		}
		
		if(!customerRepository.existsById(entity.getEmail())) {
			throw new Exception("El customer con id:"+entity.getEmail()+" no existe. No se puede borrar.");
		}
		
		customerRepository.findById(entity.getEmail()).ifPresent(customer -> {
			if(customer.getShoppingCarts()!=null && customer.getShoppingCarts().isEmpty()==false) {
				throw new RuntimeException("El customer con id:"+entity.getEmail()+" tiene ShoppingCarts. No se puede borrar.");
			}
		});
		
		customerRepository.deleteById(entity.getEmail());
	}
	
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void deleteById(String id) throws Exception {
		
		if(id == null || id.isBlank()) {
			throw new Exception("El Email es obligatorio");
		}
		
		if(customerRepository.existsById(id)) {
			delete(customerRepository.findById(id).get());
		} else {
			throw new Exception("El customer con id:"+id+" No existe");
		}
		
	}

	@Override
	public void validate(Customer entity) throws Exception {
		
		if(entity==null) {
			throw new Exception("El Customer es nulo");
		}
		
		Set<ConstraintViolation<Customer>> constraintViolations=validator.validate(entity);
		
		if(constraintViolations.isEmpty()==false) {
			throw new ConstraintViolationException(constraintViolations);
		}
	}

}
