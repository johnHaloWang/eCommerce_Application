package com.example.demo;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.ModifyCartRequest;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    public static final String PASSWORD_ENCODER = "thisIsHashed";
    public static final String RAW_PASSWORD = "testPassword";
    public static final String USERNAME = "test";
    public static final String WRONG_USERNAME = "wrong";
    public static final long USER_ID = 0L;
    public static final int BAD_REQUEST = 400;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int OK = 200;

    public static CreateUserRequest createUserRequest(){
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername(USERNAME);
        request.setPassword(RAW_PASSWORD);;
        request.setConfirmPassword(RAW_PASSWORD);
        return request;
    }

    public static User createUser(){
        User user = new User();
        user.setId(USER_ID);
        user.setUsername(USERNAME);
        user.setPassword(RAW_PASSWORD);
        return user;
    }

    public static void injectObjects(Object target, String fieldName, Object toInject){
        boolean wasPrivate = false;
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            if(!field.canAccess(target)){
                field.setAccessible(true);
                wasPrivate = true;
            }
            field.set(target, toInject);
            if(wasPrivate){
                field.setAccessible(false);
            }
        }catch(NoSuchFieldException e){
            e.printStackTrace();
        }catch(IllegalAccessException e){
            e.printStackTrace();
        }
    }

    public static List<Item> getItems(){
        List<Item> list = new ArrayList<>();
        for(int i = 0; i<2; i++){
            Item item = new Item();
            item.setId(Long.valueOf(i));
            item.setDescription("desc " + i);
            item.setPrice(BigDecimal.valueOf(++i));
            item.setName("name");
            list.add(item);
        }
        return list;
    }

    public static List<UserOrder> getUserOrders(){
        List<UserOrder> list = new ArrayList<>();
        UserOrder order = new UserOrder();
        order.setUser(createUser());
        Cart cart = getCart();
        order.setItems(cart.getItems());
        order.setTotal(cart.getTotal());
        order.setId(0L);
        list.add(order);
        return list;
    }

    public static Cart getCart(){
        Cart cart = new Cart();
        cart.setId(0L);
        List<Item> items = getItems();
        for(Item item:items){
            cart.addItem(item);
        }
        return cart;
    }

    public static ModifyCartRequest getModifyCartRequest(){
        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(TestUtils.getItems().get(0).getId());
        request.setQuantity(1);
        request.setUsername(USERNAME);
        return request;
    }

}
