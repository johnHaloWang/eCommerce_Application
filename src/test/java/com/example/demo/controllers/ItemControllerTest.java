package com.example.demo.unit_testings.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {
    private ItemController itemController;
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp(){
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepository);
        
        when(itemRepository.findAll()).thenReturn(TestUtils.getItems());
        Item indexItem = TestUtils.getItems().get(0);
        when(itemRepository.findById(indexItem.getId())).thenReturn(Optional.of(indexItem));
        when(itemRepository.findByName(indexItem.getName())).thenReturn(TestUtils.getItems());
    }

    @Test
    public void getItemsTest(){
        final ResponseEntity<List<Item>> resp = itemController.getItems();
        assertNotNull(resp);
        assertEquals(TestUtils.OK, resp.getStatusCodeValue());
        List<Item> items = resp.getBody();
        assertNotNull(items);
        assertArrayEquals(items.toArray(), TestUtils.getItems().toArray());
    }

    @Test
    public void getItemByIdTest(){
        final ResponseEntity<Item> resp = itemController.getItemById(TestUtils.getItems().get(0).getId());
        assertNotNull(resp);
        assertEquals(TestUtils.OK, resp.getStatusCodeValue());
        Item item = resp.getBody();
        assertNotNull(item);
        assertEquals(item.toString(), TestUtils.getItems().get(0).toString());
        assertEquals(item.getDescription(), TestUtils.getItems().get(0).getDescription());
    }

    @Test
    public void getItemsByName_happy_pathTest(){
        final ResponseEntity<List<Item>> resp = itemController.getItemsByName(TestUtils.getItems().get(0).getName());
        assertNotNull(resp);
        assertEquals(TestUtils.OK, resp.getStatusCodeValue());
        List<Item> items = resp.getBody();
        assertNotNull(items);
        assertArrayEquals(items.toArray(), TestUtils.getItems().toArray());
    }

    @Test
    public void getItemsByName_name_not_foundTest(){
        final ResponseEntity<List<Item>> resp = itemController.getItemsByName(TestUtils.WRONG_USERNAME);
        assertNotNull(resp);
        assertEquals(TestUtils.NOT_FOUND, resp.getStatusCodeValue());
        List<Item> items = resp.getBody();
        assertNull(items);
    }

}
