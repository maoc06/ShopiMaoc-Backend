package co.edu.usbcali.demo.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usbcali.demo.domain.ShoppingCart;
import co.edu.usbcali.demo.service.CartService;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin("*")
public class CartController {
	
	@Autowired
	CartService cartService;
	
	@PostMapping("/save")
	public ResponseEntity<?> save(@Valid @RequestBody String email) throws Exception {

		ShoppingCart shoppingCart = cartService.createCart(email);
		
		return ResponseEntity.ok().body(shoppingCart);
	}

}
