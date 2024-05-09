package com.isttmicroservice.authservice.entity;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "user")
@EqualsAndHashCode(callSuper = false)
public class User  {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	private String username;

	private String password;
	private String address;
	private String phone;
	private String country;
	
	private String avatar; // luu url

	private boolean enabled;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthdate;

	@Column(name = "email", unique = true)
	private String email;

	@OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
	private List<Role> roles;


}
