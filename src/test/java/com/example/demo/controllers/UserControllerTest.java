package com.example.demo.unit_testings.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    private UserController userController;
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    private CreateUserRequest request;
    private User user;

    @Before
    public void setUp(){
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepository);
        TestUtils.injectObjects(userController, "cartRepository", cartRepository);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", encoder);
        request = TestUtils.createUserRequest();
        user = TestUtils.createUser();
        when(encoder.encode(TestUtils.RAW_PASSWORD)).thenReturn(TestUtils.PASSWORD_ENCODER);
        when(userRepository.findByUsername(TestUtils.USERNAME)).thenReturn(user);
        when(userRepository.findById(TestUtils.USER_ID)).thenReturn(Optional.of(user));
    }

    @Test
    public void create_user_happy_path(){
        final ResponseEntity<User> resp = userController.createUser(request);
        assertNotNull(resp);
        assertEquals(TestUtils.OK, resp.getStatusCodeValue());
        User user = resp.getBody();
        assertNotNull(user);
        assertEquals(TestUtils.USER_ID, user.getId());
        assertEquals(TestUtils.USERNAME, user.getUsername());
        assertEquals(TestUtils.PASSWORD_ENCODER, user.getPassword());
    }

    @Test
    public void create_user_happy_path_badPassword(){
        request.setPassword("123456");
        final ResponseEntity<User> resp = userController.createUser(request);
        assertNotNull(resp);
        assertEquals(TestUtils.BAD_REQUEST, resp.getStatusCodeValue());
        User user = resp.getBody();
        assertNull(user);
    }

    @Test
    public void create_user_happy_path_badRequest_password_isNotEqualTo_confirmPassword(){
        request.setPassword("123456");
        request.setPassword("1234567");
        final ResponseEntity<User> resp = userController.createUser(request);
        assertNotNull(resp);
        assertEquals(TestUtils.BAD_REQUEST, resp.getStatusCodeValue());
        User user = resp.getBody();
        assertNull(user);
    }

    @Test
    public void findByUserName(){
        final ResponseEntity<User> resp = userController.findByUserName(request.getUsername());
        User ans = resp.getBody();
        assertNotNull(ans);
        assertEquals(TestUtils.USER_ID, ans.getId());
        assertEquals(TestUtils.USERNAME, ans.getUsername());
        assertEquals(TestUtils.RAW_PASSWORD, ans.getPassword());
        assertEquals(TestUtils.OK, resp.getStatusCodeValue());
    }

    @Test
    public void findById(){
        final ResponseEntity<User> resp = userController.findById(TestUtils.USER_ID);
        User ans = resp.getBody();
        assertNotNull(ans);
        assertEquals(TestUtils.USER_ID, ans.getId());
        assertEquals(TestUtils.USERNAME, ans.getUsername());
        assertEquals(TestUtils.RAW_PASSWORD, ans.getPassword());
        assertEquals(TestUtils.OK, resp.getStatusCodeValue());
    }

    @Test
    public void findById_NotFound(){
        final ResponseEntity<User> resp = userController.findById(1l);
        User ans = resp.getBody();
        assertNull(ans);
        assertEquals(TestUtils.NOT_FOUND, resp.getStatusCodeValue());
    }
}
