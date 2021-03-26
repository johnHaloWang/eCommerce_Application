package com.example.demo.unit_testings.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {
    private CartController cartController;
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private ItemRepository itemRepository = mock(ItemRepository.class);

    private Cart cart;
    private ModifyCartRequest request;
    private User user;

    @Before
    public void setUp(){
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepository);
        cart = new Cart();
        user = TestUtils.createUser();

        user.setCart(cart);
        cart.setUser(user);
        when(userRepository.findByUsername(TestUtils.USERNAME)).thenReturn(user);
        when(itemRepository.findById(TestUtils.getItems().get(0).getId())).thenReturn(Optional.of(TestUtils.getItems().get(0)));
    }

    @Test
    public void addToCartHappyPathTest(){
        request = TestUtils.getModifyCartRequest();
        final ResponseEntity<Cart> resp = cartController.addTocart(request);
        assertNotNull(resp);
        assertEquals(TestUtils.OK, resp.getStatusCodeValue());
        Cart result = resp.getBody();
        assertNotNull(result);
        assertEquals( result.getUser().getUsername(), user.getUsername());
        assertEquals( result.getUser().getPassword(), user.getPassword());
        assertEquals( result.getItems().size(), 1);
        assertEquals( result.getItems().get(0).toString(), TestUtils.getItems().get(0).toString());
    }

    @Test
    public void addToCart_wrong_username_test(){
        request = TestUtils.getModifyCartRequest();
        request.setUsername("wrong");
        final ResponseEntity<Cart> resp = cartController.addTocart(request);
        assertNotNull(resp);
        assertEquals(TestUtils.NOT_FOUND, resp.getStatusCodeValue());
        Cart result = resp.getBody();
        assertNull(result);
    }

    @Test
    public void addToCart_item_not_found_test(){
        request = TestUtils.getModifyCartRequest();
        request.setItemId(2L);
        final ResponseEntity<Cart> resp = cartController.addTocart(request);
        assertNotNull(resp);
        assertEquals(TestUtils.NOT_FOUND, resp.getStatusCodeValue());
        Cart result = resp.getBody();
        assertNull(result);
    }


    @Test
    public void removeFromCartHappyPathTest(){
        request = TestUtils.getModifyCartRequest();
        cartController.addTocart(request);
        final ResponseEntity<Cart> resp = cartController.removeFromcart(request);
        assertNotNull(resp);
        assertEquals(TestUtils.OK, resp.getStatusCodeValue());
        Cart result = resp.getBody();
        assertNotNull(result);
        assertEquals(result.getUser().getUsername(), user.getUsername());
        assertEquals(result.getUser().getPassword(), user.getPassword());
        assertEquals(result.getItems().size(), 0);
    }

    @Test
    public void removeFromCart_wrong_username_test(){
        request = TestUtils.getModifyCartRequest();
        request.setUsername(TestUtils.WRONG_USERNAME);
        final ResponseEntity<Cart> resp = cartController.removeFromcart(request);
        assertNotNull(resp);
        assertEquals(TestUtils.NOT_FOUND, resp.getStatusCodeValue());
        Cart result = resp.getBody();
        assertNull(result);
    }

    @Test
    public void removeFromCart_item_not_found_test(){
        request = TestUtils.getModifyCartRequest();
        request.setItemId(2L);
        final ResponseEntity<Cart> resp = cartController.removeFromcart(request);
        assertNotNull(resp);
        assertEquals(TestUtils.NOT_FOUND, resp.getStatusCodeValue());
        Cart result = resp.getBody();
        assertNull(result);
    }

    @Test
    public void removeFromCart_item_is_null(){
        request = TestUtils.getModifyCartRequest();
        request.setItemId(2L);
        final ResponseEntity<Cart> resp = cartController.removeFromcart(request);
        assertNotNull(resp);
        assertEquals(TestUtils.NOT_FOUND, resp.getStatusCodeValue());
        Cart result = resp.getBody();
        assertNull(result);
    }
}
