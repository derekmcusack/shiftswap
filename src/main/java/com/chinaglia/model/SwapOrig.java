package com.chinaglia.model;



import java.io.Serializable;

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


@Entity
@Table(name = "swaporiginator")
public class SwapOrig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6303331429337067875L;
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
	@JoinTable(name = "Users", joinColumns = @JoinColumn(name = "email"), inverseJoinColumns = @JoinColumn(name = "email"))


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
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
