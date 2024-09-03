package com.onefactor.app.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity

@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, length = 10)
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

	private Boolean isPanVerified;

	private Boolean isEmailVerified;

	private String verificationId;
	
	private int creditScore;
	

    @OneToMany(mappedBy = "user")
    private List<UserGroup> userGroups = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<BillShare> billShares = new ArrayList<>();

	


}
