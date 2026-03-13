package com.test.cph.serviceinf;

import com.test.cph.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface UserInf {
    public User createUser(User user, MultipartFile photo) throws IOException;

    Page<User> getAllUsers(int page,
             int size,
             String username,
             String role,
             String status
    );

    User updateUser(Long id, User user, MultipartFile photo) throws IOException;

    void deleteUserById(Long id);

    User getUserById(Long id);

    void generateResetToken(String email) throws Exception;

    void resetPassword(String token, String password);
}
