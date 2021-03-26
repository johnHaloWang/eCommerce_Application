package com.example.demo.controllers;

import java.util.Optional;
import java.util.stream.IntStream;

//import jdk.jfr.internal.Repository;
//import lombok.extern.slf4j.Slf4j;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;


@RestController
@RequestMapping("/api/cart")
public class CartController {

	public final static String TAG_ = "CartController";
	public static final Logger log = LoggerFactory.getLogger(CartController.class);
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@PostMapping("/addToCart")
	public ResponseEntity<Cart> addTocart(@RequestBody ModifyCartRequest request) {
		log.info("{} -- addTocart function -- Adding modifications to cart for user {}", TAG_, request);
		User user = userRepository.findByUsername(request.getUsername());
		if(user == null) {
			log.error("{} User {} not found -- addTocart function . Cannot add modifications.", TAG_, request.getUsername());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Optional<Item> item = itemRepository.findById(request.getItemId());
		if(!item.isPresent()) {
			log.error("{}:  Item {} not found -- addTocart function. Cannot add modifications.", TAG_, request.getItemId());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Cart cart = user.getCart();
		IntStream.range(0, request.getQuantity())
			.forEach(i -> cart.addItem(item.get()));
		cartRepository.save(cart);
		log.info("{}: Modifications added to cart successfully for user {}", TAG_, request.getUsername());
		return ResponseEntity.ok(cart);
	}
	
	@PostMapping("/removeFromCart")
	public ResponseEntity<Cart> removeFromcart(@RequestBody ModifyCartRequest request) {
		log.info("{} -> removeFromcart is called: Removing modifications from cart for user {}", TAG_, request.getUsername());
		User user = userRepository.findByUsername(request.getUsername());
		if(user == null) {
			log.error("{} -> removeFromcart, User {} not found. Cannot remove modifications.", TAG_, request.getUsername());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Optional<Item> item = itemRepository.findById(request.getItemId());
		if(!item.isPresent()) {
			log.error(TAG_ + "-> removeFromcart: item is null");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Cart cart = user.getCart();
		IntStream.range(0, request.getQuantity())
			.forEach(i -> cart.removeItem(item.get()));
		cartRepository.save(cart);
		log.info("{} -> removeFromcart, Modifications removed from cart successfully for user {}", TAG_, request.getUsername());
		return ResponseEntity.ok(cart);
	}
}
