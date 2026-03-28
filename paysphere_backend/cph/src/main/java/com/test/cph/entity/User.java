package com.test.cph.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //add comment
    @Column(unique = true)
    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 20, message = "Username must be 4-20 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Pattern(
    regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$",
    message = "Password must contain uppercase, lowercase, and number")
    private String password;

    @NotBlank(message = "Full name is required")
    @Size(max = 50, message = "Full name must not exceed 50 characters")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "ACTIVE|INACTIVE", message = "Status must be ACTIVE or INACTIVE")
    private String status;

    @NotBlank(message = "Role is required")
    @Pattern(regexp = "USER|ADMIN", message = "Role must be USER or ADMIN")
    private String role;

    @Size(max = 255, message = "Photo path too long")
    private String photoPath;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private String resetToken;

    private LocalDateTime tokenExpiry;

}
