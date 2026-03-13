package com.test.cph.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.cph.entity.User;
import com.test.cph.serviceinf.UserInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserInf userServiceInf;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createUser(
            @RequestPart("user") String userJson,
            @RequestPart(value = "photo", required = false) MultipartFile photo) throws IOException {
        System.out.println("🔥 Controller HIT");
        User user = new ObjectMapper().readValue(userJson, User.class);
        User userDetails = userServiceInf.createUser(user, photo);
//         return ResponseEntity.ok("User created successfully");
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "User created successfully"
        ));
    }

//    @GetMapping("/all")
//    public ResponseEntity<List<User>> getAllUsers() {
//        List<User> users = userServiceInf.getAllUsers();
//        return ResponseEntity.ok(users);



    //    @GetMapping("/all")
    @GetMapping
    public Page<User> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String status
    ) {
        return userServiceInf.getAllUsers(page, size, username, role, status);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestPart("user") String userJson,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) throws IOException {

        System.out.println("🔥 Update Controller HIT");

        User user = new ObjectMapper().readValue(userJson, User.class);

        userServiceInf.updateUser(id, user, photo);

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "User updated successfully"
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {

        userServiceInf.deleteUserById(id);

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "User deleted successfully"
        ));
    }

   @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userServiceInf.getUserById(id);
        return new ResponseEntity(user, HttpStatus.OK);
   }

}
