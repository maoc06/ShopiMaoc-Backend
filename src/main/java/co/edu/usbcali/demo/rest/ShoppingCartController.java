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

import co.edu.usbcali.demo.domain.ShoppingCart;
import co.edu.usbcali.demo.dto.ShoppingCartDTO;
import co.edu.usbcali.demo.mapper.ShoppingCartMapper;
import co.edu.usbcali.demo.service.ShoppingCartService;

@RestController
@RequestMapping("/api/shopping-cart")
@CrossOrigin("*")
public class ShoppingCartController {
	
	@Autowired
	ShoppingCartService shoppingCartService;
	
	@Autowired
	ShoppingCartMapper shoppingCartMapper;
	
	@PostMapping("/save")
	public ResponseEntity<?> save(@Valid @RequestBody ShoppingCartDTO shoppingCartDTO) throws Exception {

		ShoppingCart shoppingCart = shoppingCartMapper.toShoppingCart(shoppingCartDTO);
		shoppingCart = shoppingCartService.save(shoppingCart);
		shoppingCartDTO = shoppingCartMapper.toShoppingCartDTO(shoppingCart);

		return ResponseEntity.ok().body(shoppingCartDTO);
	}

	@PutMapping("/update")
	public ResponseEntity<?> update(@Valid @RequestBody ShoppingCartDTO shoppingCartDTO) throws Exception {

		ShoppingCart product = shoppingCartMapper.toShoppingCart(shoppingCartDTO);
		product = shoppingCartService.update(product);
		shoppingCartDTO = shoppingCartMapper.toShoppingCartDTO(product);

		return ResponseEntity.ok().body(shoppingCartDTO);
	}
	
	@DeleteMapping("/delete/{carId}")
	public ResponseEntity<?> delete(@PathVariable("carId") Integer carId) throws Exception {

		shoppingCartService.deleteById(carId);

		return ResponseEntity.ok("Se elimino el shoppingCart con carId = " + carId);
	}

	@GetMapping("/findAll")
	public ResponseEntity<?> findAll() throws Exception {
		List<ShoppingCart> shoppingCarts = shoppingCartService.findAll();
		List<ShoppingCartDTO> shoppingCartsDTOs = shoppingCartMapper.toShoppingCartsDTO(shoppingCarts);

		return ResponseEntity.ok().body(shoppingCartsDTOs);
	}

	@GetMapping("/findById/{carId}")
	public ResponseEntity<?> findById(@PathVariable("carId") Integer carId) throws Exception {

		Optional<ShoppingCart> shoppingCartOptional = shoppingCartService.findById(carId);
		if (shoppingCartOptional.isPresent() == false) {
			return ResponseEntity.ok().body(null);
		}

		ShoppingCart shoppingCart = shoppingCartOptional.get();

		ShoppingCartDTO shoppingCartDTO = shoppingCartMapper.toShoppingCartDTO(shoppingCart);

		return ResponseEntity.ok().body(shoppingCartDTO);
	}
	
	

}
