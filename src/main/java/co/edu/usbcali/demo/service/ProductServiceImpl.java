package co.edu.usbcali.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.domain.Product;
import co.edu.usbcali.demo.repository.ProductRepository;

@Service
@Scope("singleton")
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	ProductRepository productRepository;
	
	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return productRepository.count();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Product> findAll() {
		return productRepository.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<Product> findById(String id) throws Exception {
		return productRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Product save(Product entity) throws Exception {
		
		validate(entity);
		
		//Si existe lanza el error
		if(productRepository.existsById(entity.getProId())){
			throw new Exception("El Product con id:"+entity.getProId()+" ya existe");
		}
		
		return productRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public Product update(Product entity) throws Exception {

		validate(entity);
		
		//Si existe lanza el error
		if(productRepository.existsById(entity.getProId())==false){
			throw new Exception("El Product con id:"+entity.getProId()+" no existe");
		}
		
		return productRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void delete(Product entity) throws Exception {
		
		if(entity==null) {
			throw new Exception("El Product es nulo");
		}
		
		if(entity.getProId()==null || entity.getProId().isBlank()==true) {
			throw new Exception("El ProId es obligatorio");
		}
		
		//Si no existe lanza el error
		if(productRepository.existsById(entity.getProId())==false){
			throw new Exception("El Product con id:"+entity.getProId()+" no existe. No se puede borrar.");
		}
		
		productRepository.findById(entity.getProId()).ifPresent(product->{
			if(product.getShoppingProducts()!=null && product.getShoppingProducts().isEmpty()==false) {
				throw new RuntimeException("El Product con id:"+entity.getProId()+" tiene ShoppingProducts. No se puede borrar.");
			}
		});
		
		productRepository.deleteById(entity.getProId());
		
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void deleteById(String id) throws Exception {
		if(id==null || id.isBlank()==true) {
			throw new Exception("El ProId es obligatoria");
		}
		
		if(productRepository.existsById(id)) {
			delete(productRepository.findById(id).get());
		}		
	}

	@Override
	public void validate(Product entity) throws Exception {
		if(entity==null) {
			throw new Exception("El Product es nulo");
		}
		
		if(entity.getDetail()==null || entity.getDetail().isBlank()) {
			throw new Exception("El Detail es obligatorio");
		}
		
		if(entity.getProId()==null || entity.getProId().isBlank()) {
			throw new Exception("El Id es obligatorio");
		}

		if(entity.getEnable()==null || entity.getEnable().isBlank()) {
			throw new Exception("El Enable es obligatorio");
		}
		
		if(entity.getName()==null || entity.getName().isBlank()) {
			throw new Exception("El Name es obligatorio");
		}
		
		if(entity.getPrice()==null || entity.getPrice() <= 0) {
			throw new Exception("El Price es obligatorio");
		}
		
		if(entity.getImage()==null || entity.getImage().isBlank()) {
			throw new Exception("El Image es obligatorio");
		}		
	}

}
