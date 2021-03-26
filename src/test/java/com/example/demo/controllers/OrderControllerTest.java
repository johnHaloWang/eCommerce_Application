package com.example.demo.unit_testings.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {
    private OrderController orderController;
    private OrderRepository orderRepository = mock(OrderRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);

    private Cart cart;
    private User user;

    @Before
    public void setUp(){
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "orderRepository", orderRepository);
        TestUtils.injectObjects(orderController, "userRepository", userRepository);
        user = TestUtils.createUser();
        cart = TestUtils.getCart();
        user.setCart(cart);
        cart.setUser(user);
        when(userRepository.findByUsername(TestUtils.USERNAME)).thenReturn(user);
        when(orderRepository.findByUser(user)).thenReturn(TestUtils.getUserOrders());
    }

    @Test
    public void submit_happy_path_test(){
        final ResponseEntity<UserOrder> resp = orderController.submit(TestUtils.USERNAME);
        assertNotNull(resp);
        assertEquals(TestUtils.OK, resp.getStatusCodeValue());
        UserOrder result = resp.getBody();
        assertNotNull(result);
        assertEquals(result.getId(), null);
        assertEquals(result.getTotal(), TestUtils.getUserOrders().get(0).getTotal());
        assertEquals( result.getUser().getUsername(), user.getUsername());
        assertEquals( result.getUser().getPassword(), user.getPassword());
        assertEquals( result.getItems().size(), TestUtils.getItems().size());
        assertEquals( result.getItems().get(0).toString(), TestUtils.getItems().get(0).toString());
    }

    @Test
    public void submit_username_not_found_test(){
        final ResponseEntity<UserOrder> resp = orderController.submit(TestUtils.WRONG_USERNAME);
        assertNotNull(resp);
        assertEquals(TestUtils.NOT_FOUND, resp.getStatusCodeValue());
        UserOrder result = resp.getBody();
        assertNull(result);
    }

    @Test
    public void getOrdersForUser_happy_path_test(){
        final ResponseEntity<List<UserOrder>> resp = orderController.getOrdersForUser(TestUtils.USERNAME);
        assertNotNull(resp);
        assertEquals(TestUtils.OK, resp.getStatusCodeValue());
        List<UserOrder> result = resp.getBody();
        assertNotNull(result);
        assertEquals( result.size(), 1);
        assertEquals( result.get(0).getUser().getUsername(), user.getUsername());
        assertEquals( result.get(0).getUser().getPassword(), user.getPassword());
        assertEquals( result.get(0).getItems().size(), TestUtils.getItems().size());
        assertEquals( result.get(0).getItems().get(0).toString(), TestUtils.getItems().get(0).toString());
    }

    @Test
    public void getOrdersForUser_username_not_found_test(){
        final ResponseEntity<List<UserOrder>> resp = orderController.getOrdersForUser(TestUtils.WRONG_USERNAME);
        assertNotNull(resp);
        assertEquals(TestUtils.NOT_FOUND, resp.getStatusCodeValue());
        List<UserOrder> result = resp.getBody();
        assertNull(result);
    }
}
