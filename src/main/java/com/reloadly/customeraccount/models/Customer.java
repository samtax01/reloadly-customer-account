package com.reloadly.customeraccount.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(length = 50)
    private String firstName;

    @NotBlank
    @Column(length = 50)
    private String lastName;

    @NotBlank
    @Email
    @Column(length = 50, unique = true)
    private String email;

    @Column(length = 30)
    private String phoneNumber;

    @JsonIgnore
    @NotBlank
    @Size(min = 6)
    private String password;

    private String roles;

    private final LocalDateTime createdAt = LocalDateTime.now();

    private final LocalDateTime updatedAt = LocalDateTime.now();

}
