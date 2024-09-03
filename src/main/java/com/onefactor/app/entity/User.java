package com.onefactor.app.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import com.onefactor.app.entity.UserGroup;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "users")  // Plural table name to follow conventions
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 10, nullable = false)
    private String phone;

    @Column(unique = true, length = 10)
    private String pan;

     private String firstName;

     private String lastName;

    @Column(unique = true)
    private String email;

    private String dob;

    private String gender;

    private String hno;

    private String building;

    private String area;

    private String pinCode;

    private Boolean isPanVerified = false;

    private Boolean isEmailVerified = false;

    private String verificationId;

    private int creditScore;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserGroup> userGroups = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillShare> billShares = new ArrayList<>();
}
