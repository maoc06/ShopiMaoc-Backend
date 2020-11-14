package co.edu.usbcali.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.domain.PaymentMethod;
import co.edu.usbcali.demo.repository.PaymentMethodRepository;

@Service
@Scope("singleton")
public class PaymentMethodServiceImpl implements PaymentMethodService{
	
	@Autowired
	PaymentMethodRepository paymentMethodRepository;

	@Override
	@Transactional(readOnly = true)
	public List<PaymentMethod> findAll() {
		return paymentMethodRepository.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<PaymentMethod> findByEnableTrue() {
		return paymentMethodRepository.findByEnableTrue();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<PaymentMethod> findById(Integer id) throws Exception {
		return paymentMethodRepository.findById(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return paymentMethodRepository.count();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public PaymentMethod save(PaymentMethod entity) throws Exception {
		
		validate(entity);
		
		return paymentMethodRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public PaymentMethod update(PaymentMethod entity) throws Exception {
		
		if(entity == null) {
			throw new Exception("El PaymentMethod es nulo");
		}
		
		if(entity.getPayId()==null) {
			throw new Exception("El payId es Obligatorio");
		}
		
		validate(entity);
		
		if(!paymentMethodRepository.existsById(entity.getPayId())) {
			throw new Exception("El PaymentMethod con id:"+entity.getPayId()+" NO existe");
		}
		
		return paymentMethodRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void delete(PaymentMethod entity) throws Exception {
		if(entity == null) {
			throw new Exception("El PaymentMethod es nulo");
		}
		
		if(entity.getPayId()==null || entity.getPayId() < 0) {
			throw new Exception("El payId es obligatorio");
		}
		
		if(!paymentMethodRepository.existsById(entity.getPayId())) {
			throw new Exception("El paymentMethod con id:"+entity.getPayId()+" No existe. No se puede borrar.");
		}
		
		paymentMethodRepository.findById(entity.getPayId()).ifPresent(paymentMethod->{
			if(paymentMethod.getShoppingCarts()!=null && paymentMethod.getShoppingCarts().isEmpty()==false) {
				throw new RuntimeException("El PaymentMethod con id:"+entity.getPayId()+" tiene ShoppingCarts. No se puede borrar.");
			}
		});
		
		paymentMethodRepository.deleteById(entity.getPayId());
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void deleteById(Integer id) throws Exception {
		if(id==null || id < 0) {
			throw new Exception("El payId es obligatorio");
		}
		
		if(paymentMethodRepository.existsById(id)) {
			delete(paymentMethodRepository.findById(id).get());
		} else {
			throw new Exception("El paymentMethod con id:"+id+" No existe");
		}	
	}

	@Override
	public void validate(PaymentMethod entity) throws Exception {
		if(entity == null) {
			throw new Exception("El PaymentMethod es Nulo");
		}
		
		if(entity.getEnable()==null || entity.getEnable().isBlank()) {
			throw new Exception("El Enable es Obligatorio");
		}
		
		if(entity.getName()==null || entity.getName().isBlank()) {
			throw new Exception("El Name es Obligatorio");
		}
	}

}
