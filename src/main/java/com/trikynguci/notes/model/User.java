package com.trikynguci.notes.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id")
        private Long userId;

        @NotBlank
        @Size(max = 20)
        @Column(name = "username")
        private String userName;

        @NotBlank
        @Size(max = 50)
        @Email
        @Column(name = "email")
        private String email;

        @Size(max = 120)
        @Column(name = "password")
        @JsonIgnore
        private String password;

        private boolean accountNonLocked = true;
        private boolean accountNonExpired = true;
        private boolean credentialsNonExpired = true;
        private boolean enabled = true;

        private LocalDate credentialsExpiryDate;
        private LocalDate accountExpiryDate;

        private String twoFactorSecret;
        private boolean isTwoFactorEnabled = false;
        private String signUpMethod;

        @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
        @JoinColumn(name = "role_id", referencedColumnName = "role_id")
        @JsonBackReference
        @ToString.Exclude
        private Role role;

        @CreationTimestamp
        @Column(updatable = false)
        private LocalDateTime createdAt;

        @UpdateTimestamp
        private LocalDateTime updatedAt;

        public User(String userName, String email, String password, Role role) {
                this.userName = userName;
                this.email = email;
                this.password = password;
                this.role = role;
        }

}
