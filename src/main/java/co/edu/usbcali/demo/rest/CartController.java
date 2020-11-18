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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usbcali.demo.domain.Customer;
import co.edu.usbcali.demo.domain.ShoppingCart;
import co.edu.usbcali.demo.domain.ShoppingProduct;
import co.edu.usbcali.demo.dto.CartDTO;
import co.edu.usbcali.demo.dto.CustomerDTO;
import co.edu.usbcali.demo.dto.ShoppingCartDTO;
import co.edu.usbcali.demo.dto.ShoppingProductDTO;
import co.edu.usbcali.demo.mapper.CustomerMapper;
import co.edu.usbcali.demo.mapper.ShoppingCartMapper;
import co.edu.usbcali.demo.mapper.ShoppingProductMapper;
import co.edu.usbcali.demo.service.CartService;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin("*")
public class CartController {
	
	@Autowired
	CartService cartService;
	
	@Autowired
	ShoppingCartMapper shoppingCartMapper;
	
	@Autowired
	ShoppingProductMapper shoppingProductMapper;
	
	@Autowired
	CustomerMapper customerMapper;
	
	@PostMapping("/createCart")
	public ResponseEntity<?> createCart(@Valid @RequestBody CustomerDTO customerDTO) throws Exception {

		Customer customer = customerMapper.toCustomer(customerDTO);
	
		ShoppingCart shoppingCart = cartService.createCart(customer.getEmail());
		ShoppingCartDTO shoppingCartDTO = shoppingCartMapper.toShoppingCartDTO(shoppingCart);

		return ResponseEntity.ok().body(shoppingCartDTO);
	}
	
	@PostMapping("/addProduct")
	public ResponseEntity<?> addProduct(@Valid @RequestBody CartDTO cartDTO) throws Exception {
		
		Integer carId = cartDTO.getCarId();
		String proId = cartDTO.getProId();
		Integer quantity = cartDTO.getQuantity();
		
		ShoppingProduct shoppingProduct = cartService.addProduct(carId, proId, quantity);
		ShoppingProductDTO shoppingProductDTO = shoppingProductMapper.toShoppingProductDTO(shoppingProduct);

		return ResponseEntity.ok().body(shoppingProductDTO);
	}
	
	@DeleteMapping("/removeProduct/{carId}/{proId}/{quantity}")
	public ResponseEntity<?> removeProduct(
			@PathVariable("carId") Integer carId, 
			@PathVariable("proId") String proId,
			@PathVariable("quantity") Integer quantity) throws Exception {

		cartService.removeProduct(carId, proId, quantity);
		
		return ResponseEntity.ok().build();

		// return ResponseEntity.ok("Se removio un product con proId = " + proId + " del cart con carId = " + carId);
	}
	
	@DeleteMapping("/clearCart/{carId}")
	public ResponseEntity<?> clearCart(@PathVariable("carId") Integer carId) throws Exception {

		cartService.clearCart(carId);
		
		return ResponseEntity.ok().build();

		// return ResponseEntity.ok("Se borro el cart con carId = " + carId);
	}
	
	@GetMapping("/findShoppingProducts/{carId}")
	public ResponseEntity<?> findShoppingProductByShoppingCart(@PathVariable("carId") Integer carId) throws Exception {
		
		List<ShoppingProduct> shoppingProducts = cartService.findShoppingProductByShoppingCart(carId);
		List<ShoppingProductDTO> shoppingProductsDTOs = shoppingProductMapper.toShoppingProductsDTO(shoppingProducts);

		return ResponseEntity.ok().body(shoppingProductsDTOs);
	}
	
	@GetMapping("/findByCustomerEnable/{email}")
	public ResponseEntity<?> findByCustomerEnableTrue(@PathVariable("email") String email) throws Exception {
		
		Optional<ShoppingCart> shoppingCartOptional = cartService.findByCustomerEnableTrue(email);
		if (shoppingCartOptional.isPresent() == false) {
			return ResponseEntity.ok().body(null);
		}
		
		ShoppingCart shoppingCart = shoppingCartOptional.get();
		
		ShoppingCartDTO shoppingCartDTO = shoppingCartMapper.toShoppingCartDTO(shoppingCart);
		
		return ResponseEntity.ok().body(shoppingCartDTO);
	}
	
	@GetMapping("/findByCustomerShops/{email}")
	public ResponseEntity<?> findByCustomerShops(@PathVariable("email") String email) throws Exception {
		
		List<ShoppingCart> shoppingCarts = cartService.findByCustomerShops(email);
		List<ShoppingCartDTO> shoppingCartsDTOs = shoppingCartMapper.toShoppingCartsDTO(shoppingCarts);

		return ResponseEntity.ok().body(shoppingCartsDTOs);
	}

}
