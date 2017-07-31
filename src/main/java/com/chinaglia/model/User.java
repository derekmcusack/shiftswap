package com.chinaglia.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

@Entity
@Table(name = "Users")
public class User implements Serializable {

	/**
	 * 
	 */
	
	//constructor, for use when testing User repository
	public User(String name, String lastName, String email, String password){
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}
	
	//no arg constructor
	public User(){
		
	}
	
	private static final long serialVersionUID = 4907758094962942870L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "userID")
	private int id;
	@Column(name = "email")
	@Email(message = "*Please provide a valid Email")
	@NotEmpty(message = "*Please enter an email")
	private String email;
	@Column(name = "password")
	@Length(min = 5, message = "*Your password must have at least 5 characters")
	@NotEmpty(message = "*Please select your password")
	@Transient
	private String password;
	@Column(name = "fname")
	@NotEmpty(message = "*Please enter your first name")
	private String name;
	@Column(name = "lastname")
	@NotEmpty(message = "*Please enter your last name")
	private String lastName;
	@Column(name = "isActive")
	private int active;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "User_Role", joinColumns = @JoinColumn(name = "userID"), inverseJoinColumns = @JoinColumn(name = "roleID"))
	private Set<Role> roles;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
