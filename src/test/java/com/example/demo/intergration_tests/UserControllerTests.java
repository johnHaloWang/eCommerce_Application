package com.example.demo.intergration_tests;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class UserControllerTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    UserRepository userRepository;

    private final HttpHeaders httpHeaders = new HttpHeaders();

    @Test
    public void createUser() throws Exception {
        CreateUserRequest user = TestUtils.createUserRequest();
        MvcResult response = mvc.perform(
                post("/api/user/create")
                        .content(mapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, response.getResponse().getHeader("Authorization"));
    }

    @Test
//    @Order(2)
    public void login() throws Exception {
//        CreateUserRequest user = TestUtils.createUserRequest();
//        List<User> list = userRepository.findAll();
//        User testing = userRepository.findByUsername(user.getUsername());
//        if(testing==null){
//            createUser();
//        }
//        MvcResult response = mvc.perform(
//                post("/api/user/login")
//                        .content(mapper.writeValueAsString(user))
//                        .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(status().isOk())
//                .andReturn();
        //httpHeaders.set(HttpHeaders.AUTHORIZATION, response.getResponse().getHeader("Authorization"));
    }

//    @Test
//    public void findById() throws Exception {
//        CreateUserRequest user = TestUtils.createUserRequest();
//        User result = userRepository.findByUsername(user.getUsername());
//        if(result == null){
//            createUser();
////            User input = new User();
////            input.setUsername(user.getUsername());
////            input.setPassword(user.getPassword());
////            User save = userRepository.save(input);
//            result = userRepository.findByUsername(user.getUsername());
//        }
//        mvc.perform(get("/api/user/id/" + result.getId()).headers(httpHeaders)).andExpect(status().isOk());
//    }
//
//    @Test
//    public void findByUserName() throws Exception {
//        CreateUserRequest user = TestUtils.createUserRequest();
//        User result = userRepository.findByUsername(user.getUsername());
//        if(Objects.isNull(result)){
//            createUser();
//        }else{
//            login();
//        }
//        mvc.perform(get("/api/user/id/" + user.getUsername()).headers(httpHeaders)).andExpect(status().isOk());
//    }

}
