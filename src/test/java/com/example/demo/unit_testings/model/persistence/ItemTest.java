package com.example.demo.unit_testings.model.persistence;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.*;

public class ItemTest {
    @Test
    public void itemEqualsNullTest(){
        Item item = new Item();
        boolean result = item.equals(null);
        assertFalse(result);;

    }

    @Test
    public void itemEqualsGetClassTest(){
        Item item = new Item();
        boolean result = item.equals(new User());
        assertFalse(result);;
    }

    @Test
    public void itemEqualsIdCheckTest(){
        Item item = new Item();
        //item.setId(0l);
        Item obj = new Item();
        obj.setId(1L);
        boolean result = item.equals(obj);
        assertFalse(result);;
    }

    @Test
    public void itemEqualsIdCheckTest2(){
        Item item = new Item();
        item.setId(0l);
        Item obj = new Item();
        boolean result = item.equals(obj);
        assertFalse(result);;
    }

    @Test
    public void itemEqualsIdCheckTest3(){
        Item item = new Item();
        item.setId(0l);
        Item obj = new Item();
        obj.setId(0l);
        boolean result = item.equals(obj);
        assertTrue(result);;
    }
}
