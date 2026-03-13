package com.test.cph.servioceimpl;
import com.test.cph.entity.User;
import com.test.cph.exception.ResourceNotFoundException;
import com.test.cph.repository.UserRepository;
import com.test.cph.serviceinf.UserInf;
import com.test.cph.specification.UserSpecification;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserInf {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public User createUser(User request, MultipartFile photo) throws IOException {

        User user = new User();
        user.setUsername(request.getUsername());
        // 🔐 BCrypt encoding happens HERE
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        user.setStatus(request.getStatus());
        user.setFullName(request.getFullName());
        //for storing image in uploads/users folders
        if (photo != null && !photo.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + photo.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir);

            // Create directories safely
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            //  Save file (no crash if same name)
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(
                    photo.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

          //  Save relative path
            user.setPhotoPath("/uploads/users/" + fileName);
        }

        User savedUser = userRepository.save(user);
        emailService.sendUserCreatedMail(savedUser.getEmail(), savedUser.getUsername());
        return savedUser;
    }

    @Override
    public Page<User> getAllUsers(int page, int size, String username, String role, String status) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());
        Specification<User> spec =
                UserSpecification.filter(username, role, status);
        Page<User> page1 = userRepository.findAll(
                spec,
                pageable);
        return page1;
    }

    @Override
    public User updateUser(Long id, User request, MultipartFile photo) throws IOException {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(request.getUsername());

        if(request.getPassword() != null && !request.getPassword().isEmpty()){
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        user.setStatus(request.getStatus());
        user.setFullName(request.getFullName());

        // Image upload
        if (photo != null && !photo.isEmpty()) {

            String fileName = UUID.randomUUID() + "_" + photo.getOriginalFilename();

            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);

            Files.copy(
                    photo.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            user.setPhotoPath("/uploads/users/" + fileName);
        }

        User savedUser = userRepository.save(user);
        emailService.sendUserUpdatedMail(savedUser.getEmail(), savedUser.getUsername());
        return savedUser;
    }

    @Override
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    @Override
    public User getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user;
    }

    @Override
    public void generateResetToken(String email) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String token = UUID.randomUUID().toString();

        user.setResetToken(token);
        user.setTokenExpiry(LocalDateTime.now().plusMinutes(30));

        userRepository.save(user);

        String resetLink = "http://localhost:4200/reset-password?token=" + token;

        emailService.sendResetEmail(user.getEmail(), resetLink);

        System.out.println("Reset Link : " + resetLink);

        // send email here
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if(user.getTokenExpiry().isBefore(LocalDateTime.now())){
            throw new RuntimeException("Token expired");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setTokenExpiry(null);

        userRepository.save(user);
    }

//    @Override
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }

//    public void sendResetEmail(String toEmail, String resetLink) throws Exception {
//
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
////        helper.setTo("bhagyashrisay@gmail.com");
//        helper.setTo(toEmail);
//        helper.setSubject("Reset Your Password");
//
//        String htmlContent = """
//        <div style="font-family:Arial,sans-serif;padding:20px">
//            <h2 style="color:#333;">Reset Your Password</h2>
//
//            <p>Hello,</p>
//
//            <p>You requested to reset your password. Click the button below to reset it.</p>
//
//            <a href="%s"
//               style="display:inline-block;padding:12px 20px;
//               background-color:#4CAF50;color:white;
//               text-decoration:none;border-radius:5px;
//               font-weight:bold;">
//               Reset Password
//            </a>
//
//            <p style="margin-top:20px;">This link will expire in <b>30 minutes</b>.</p>
//
//            <p>If you did not request this, please ignore this email.</p>
//
//            <hr>
//            <p style="font-size:12px;color:gray;">
//                © 2026 PaySphere
//            </p>
//        </div>
//        """.formatted(resetLink);
//
//        helper.setText(htmlContent, true);
//
//        mailSender.send(message);
//    }



}
