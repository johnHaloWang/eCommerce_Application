package com.example.demo.unit_testings.model.persistence;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CartTest {
    @Test
    public void getIdTest(){
        Cart cart = new Cart();
        cart.setId(0l);
        Long expected = new Long(0l);
        assertEquals(expected, cart.getId());
    }
    @Test
    public void getTotalTest(){
        Cart cart = new Cart();
        long input = (long) 23.23;
        cart.setTotal(BigDecimal.valueOf(input));
        assertEquals(BigDecimal.valueOf(input), cart.getTotal());
    }
    @Test
    public void getItemsTest(){
        Cart cart = new Cart();
        List<Item> items = TestUtils.getItems();
        cart.setItems(items);
        assertEquals(items, cart.getItems());
    }
    @Test
    public void removeItemsTest(){
        Cart cart = new Cart();
        cart.setItems(null);
        cart.setTotal(null);
        Item item = TestUtils.getItems().get(0);
        cart.removeItem(item);
        BigDecimal expected  = new BigDecimal(0).subtract(item.getPrice());
        assertEquals(expected, cart.getTotal());
    }

    @Test
    public void removeItemsTest2(){
        Cart cart = new Cart();
        cart.setItems(null);
        cart.setTotal(new BigDecimal(0));
        Item item = TestUtils.getItems().get(0);
        cart.removeItem(item);
        BigDecimal expected  = new BigDecimal(0).subtract(item.getPrice());
        assertEquals(expected, cart.getTotal());
    }
}
