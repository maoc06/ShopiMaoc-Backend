package co.edu.usbcali.demo.rest;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usbcali.demo.domain.ShoppingProduct;
import co.edu.usbcali.demo.dto.ShoppingProductDTO;
import co.edu.usbcali.demo.mapper.ShoppingProductMapper;
import co.edu.usbcali.demo.service.ShoppingProductService;

@RestController
@RequestMapping("/api/shopping-product")
@CrossOrigin("*")
public class ShoppingProductController {
	
	@Autowired
	ShoppingProductService shoppingProductService;
	
	@Autowired
	ShoppingProductMapper shoppingProductMapper;
	
	@PostMapping("/save")
	public ResponseEntity<?> save(@Valid @RequestBody ShoppingProductDTO shoppingProductDTO) throws Exception {

		ShoppingProduct shoppingProduct = shoppingProductMapper.toShoppingProduct(shoppingProductDTO);
		shoppingProduct = shoppingProductService.save(shoppingProduct);
		shoppingProductDTO = shoppingProductMapper.toShoppingProductDTO(shoppingProduct);

		return ResponseEntity.ok().body(shoppingProductDTO);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@Valid @RequestBody ShoppingProductDTO shoppingProductDTO) throws Exception {

		ShoppingProduct shoppingProduct = shoppingProductMapper.toShoppingProduct(shoppingProductDTO);
		shoppingProduct = shoppingProductService.update(shoppingProduct);
		shoppingProductDTO = shoppingProductMapper.toShoppingProductDTO(shoppingProduct);

		return ResponseEntity.ok().body(shoppingProductDTO);
	}
	
	@DeleteMapping("/delete/{shprId}")
	public ResponseEntity<?> delete(@PathVariable("shprId") Integer shprId) throws Exception {

		shoppingProductService.deleteById(shprId);

		return ResponseEntity.ok("Se elimino el shoppingProduct con shprId = " + shprId);
	}
	
	@GetMapping("/findAll")
	public ResponseEntity<?> findAll() throws Exception {
		List<ShoppingProduct> shoppingProducts = shoppingProductService.findAll();
		List<ShoppingProductDTO> shoppingProductsDTOs = shoppingProductMapper.toShoppingProductsDTO(shoppingProducts);

		return ResponseEntity.ok().body(shoppingProductsDTOs);
	}
	
	@GetMapping("/findById/{shprId}")
	public ResponseEntity<?> findById(@PathVariable("shprId") Integer shprId) throws Exception {

		Optional<ShoppingProduct> shoppingProductOptional = shoppingProductService.findById(shprId);
		if (shoppingProductOptional.isPresent() == false) {
			return ResponseEntity.ok().body(null);
		}

		ShoppingProduct shoppingProduct = shoppingProductOptional.get();

		ShoppingProductDTO shoppingProducttDTO = shoppingProductMapper.toShoppingProductDTO(shoppingProduct);

		return ResponseEntity.ok().body(shoppingProducttDTO);
	}
	
	
	
	@GetMapping("/findByCarId/{carId}")
	public ResponseEntity<?> findByCarId(@PathVariable("carId") Integer carId) throws Exception {
		List<ShoppingProduct> shoppingProducts = shoppingProductService.findByCarId(carId);
		List<ShoppingProductDTO> shoppingProductsDTOs = shoppingProductMapper.toShoppingProductsDTO(shoppingProducts);

		return ResponseEntity.ok().body(shoppingProductsDTOs);
	}
	
	@GetMapping("/findByProId/{carId}/{proId}")
	public ResponseEntity<?> findByProId(@PathVariable("carId") Integer carId,
										 @PathVariable("proId") String proId) throws Exception {
		
		List<ShoppingProduct> shoppingProducts = shoppingProductService.findByProId(carId, proId);
		List<ShoppingProductDTO> shoppingProductsDTOs = shoppingProductMapper.toShoppingProductsDTO(shoppingProducts);
		
		return ResponseEntity.ok().body(shoppingProductsDTOs);
	}

}
