package com.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {
	@Id
	String username;
	String password;
	Boolean active;
	Boolean admin;
	@OneToMany(mappedBy = "account")
	List<Retail> retail;
	@OneToMany(mappedBy = "account")
	List<Account_Details> details;
}