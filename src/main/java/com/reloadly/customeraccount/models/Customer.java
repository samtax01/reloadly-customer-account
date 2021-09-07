package com.reloadly.customeraccount.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reloadly.customeraccount.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    @Column(length = 50, name = "first_name")
    private String firstName;

    @NotBlank
    @Column(length = 50, name = "last_name")
    private String lastName;

    @NotBlank
    @Email
    @Column(length = 50, unique = true)
    private String email;

    @Column(length = 30, name = "phone_number")
    private String phoneNumber;

    @JsonIgnore
    @NotBlank
    @Size(min = 6)
    private String password;

    private String roles;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

}
