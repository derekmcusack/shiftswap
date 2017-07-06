package com.chinaglia.model;



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

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Entity
@Table(name = "swaporiginator")
public class SwapOrig {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "swapOrigID")
	private int id;
	@Column(name = "StartTime")
	@NotEmpty(message = "*Please enter the start time")
	private String startTime;
	@Column(name = "FinishTime")
	@NotEmpty(message = "*Please enter the finish time")
	private String finishTime;
	@Column(name = "confirmed")
	private int confirmed;
	@Column(name = "note")
	private String note;

	@Column(name = "email")
	private String email;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "users", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "userID"))


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public int getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(int confirmed) {
		this.confirmed = confirmed;
	}
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}	
	
	public String getEmail() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = (String) auth.getPrincipal();
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
